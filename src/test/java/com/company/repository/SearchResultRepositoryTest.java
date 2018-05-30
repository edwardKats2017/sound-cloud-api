package com.company.repository;

import com.company.model.SearchResult;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchResultRepositoryTest {

    private SearchResultRepository repository;

    @Before
    public void setUp() {
        System.out.println();
        repository = new SearchResultRepositoryImpl();

        Set<SearchResult> collect =
                Stream.of(SearchResult.builder()
                        .id(1L)
                        .title("AAA")
                        .permalinkUrl("http://AAA")
                        .build(),
                SearchResult.builder()
                        .id(2L)
                        .title("BBB")
                        .permalinkUrl("http://BBB")
                        .build(),
                SearchResult.builder()
                        .id(3L)
                        .title("CCC")
                        .permalinkUrl("http://CCC")
                        .build(),
                SearchResult.builder()
                        .id(4L)
                        .title("DDD")
                        .permalinkUrl("http://DDD")
                        .build(),
                SearchResult.builder()
                        .id(5L)
                        .title("EEE")
                        .permalinkUrl("http://EEE")
                        .build())
                .collect(Collectors.toSet());

        repository.save(collect);

    }


    @Test
    public void deleteByIdResponse_positive() {
        boolean beforeDeletion = repository.getAll().stream().anyMatch(x -> x.getId().equals(1L));
        assertTrue(beforeDeletion);


        boolean b = repository.removeById(1L);
        assertTrue(b);
    }

    @Test
    public void deleteByIdContent_positive() {
        boolean beforeDeletion = repository.getAll().stream().anyMatch(x -> x.getId().equals(1L));
        assertTrue(beforeDeletion);

        repository.removeById(1L);

        boolean result = repository.getAll().stream().anyMatch(x -> x.getId().equals(1L));
        assertFalse(result);
    }

    @Test
    public void deleteByPermalinkResponse_positive() {
        boolean beforeDeletion = repository.getAll().stream().anyMatch(x -> x.getPermalinkUrl().equals("http://BBB"));
        assertTrue(beforeDeletion);

        boolean response = repository.removeByPermalink("http://BBB");
        assertTrue(response);
    }

    @Test
    public void deleteByPermalinkContent_negative() {
        boolean beforeDeletion = repository.getAll().stream().anyMatch(x -> x.getPermalinkUrl().equals("http://BBB"));
        assertTrue(beforeDeletion);

        repository.removeByPermalink("http://BBB");
        boolean result = repository.getAll().stream().anyMatch(x -> x.getPermalinkUrl().equals("http://BBB"));

        assertFalse(result);
    }

    @Test
    public void deleteByNonExistingId_negative() {
        final Long idToRemove = 100L;
        boolean before = repository.getAll().stream().anyMatch(x -> x.getId().equals(idToRemove));
        assertFalse(before);

        boolean result = repository.removeById(idToRemove);
        assertFalse(result);
    }

    @Test
    public void deleteByNonExistingPermalink_negative() {
        final String permalink = "http://ZZZZ";
        boolean before = repository.getAll().stream().anyMatch(x -> x.getPermalinkUrl().equals(permalink));
        assertFalse(before);

        boolean result = repository.removeByPermalink(permalink);
        assertFalse(result);
    }

    @After
    public void destroy() {
        repository = null;
    }
}
