package com.company.repository;

import com.company.model.SearchResult;

import java.util.Set;

public interface SearchResultRepository {

    void save(Set<SearchResult> results);
    boolean removeById(Long id);
    boolean removeByPermalink(String permalink);

    Set<SearchResult> getAll();
}
