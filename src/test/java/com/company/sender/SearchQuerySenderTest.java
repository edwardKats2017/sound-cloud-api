package com.company.sender;

import com.company.model.SearchResult;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SearchQuerySenderTest {

    @Test
    public void searchResultSize_positive() {
        int star_wars = SearchQuerySender.getResults("star wars").size();
        assertEquals(10, star_wars);
    }

    @Test
    public void searchResultsTitleOrder_Positive() {

        List<String> collect = Stream.of(
                SearchResult.builder().title("c").build(),
                SearchResult.builder().title("b").build(),
                SearchResult.builder().title("a").build(),
                SearchResult.builder().title("d").build(),
                SearchResult.builder().title("r").build(),
                SearchResult.builder().title("v").build()
        )
                .sorted()
                .map(SearchResult::getTitle)
                .collect(Collectors.toList());

        assertThat(collect, IsIterableContainingInOrder.contains("a", "b", "c", "d", "r", "v"));
    }

}
