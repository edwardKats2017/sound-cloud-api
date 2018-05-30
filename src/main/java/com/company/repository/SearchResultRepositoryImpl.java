package com.company.repository;

import com.company.model.SearchResult;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class SearchResultRepositoryImpl implements SearchResultRepository {


    private static volatile Map<String, SearchResult> permalinkResult = Collections.synchronizedMap(new HashMap<>());
    private static volatile Set<SearchResult> originalCollection = Collections.synchronizedSet(new TreeSet<>());

    @Override
    public void save(Set<SearchResult> results) {
        int initialSize= originalCollection.size();
        originalCollection.addAll(results);
        permalinkResult.putAll(
                results
                .stream()
                .collect(Collectors.toMap(SearchResult::getPermalinkUrl, x -> x))
        );

        log.info("Saved {} new elements", originalCollection.size() - initialSize);
    }

    @Override
    public boolean removeById(Long id) {

        boolean result = originalCollection
                .removeIf(x -> x.getId().equals(id));

        log.info("Search result with ID {} was {}removed", id, result ? "" : "NOT ");
        return result;
    }

    @Override
    public boolean removeByPermalink(String permalink) {
        SearchResult objectToRemove = permalinkResult.remove(permalink);

        if (objectToRemove == null) {
            log.info("Record with permalink {} does not exist", permalink);
            return false;
        }

        boolean result = originalCollection.remove(objectToRemove);

        log.info("Record with permalink {} was{} removed", permalink, result ? "" : "NOT ");

        return result;
    }

    @Override
    public Set<SearchResult> getAll() {
        return originalCollection;
    }
}
