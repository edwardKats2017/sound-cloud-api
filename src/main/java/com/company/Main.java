package com.company;

import com.company.model.SearchResult;
import com.company.repository.SearchResultRepository;
import com.company.repository.SearchResultRepositoryImpl;
import com.company.sender.SearchQuerySender;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Main {

    private static String[] SEARCH_REQUESTS_1 = new String[]{"Shrek", "Star Wars", "Balkan Beat Box"};
    private static String[] SEARCH_REQUESTS_2 = new String[]{"Titanic", "Avengers", "Sonata Arctica"};

    @SneakyThrows
    public static void main(String[] args) {

        withoutMultithreading();

        Thread.sleep(2000);
        withMultithreading();
    }

    private static void withoutMultithreading() {
        long start = System.currentTimeMillis();
        SearchResultRepository resultRepository = new SearchResultRepositoryImpl();

        TreeSet<SearchResult> collect = Arrays.stream(SEARCH_REQUESTS_1)
                .map(SearchQuerySender::getResults)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(TreeSet::new));

        long end = System.currentTimeMillis();
        log.info("A request without multithreading, executed three API calls in {} ms", (end - start));

        resultRepository.save(collect);

        resultRepository.getAll().forEach(System.out::println);

    }

    private static void withMultithreading() {
        long start = System.currentTimeMillis();

        TreeSet<SearchResult> collect = Arrays.asList(SEARCH_REQUESTS_2)
                .parallelStream()
                .map(SearchQuerySender::getResults)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(TreeSet::new));


        long end = System.currentTimeMillis();
        log.info("A request with multithreading, executed three API calls in {} ms", (end - start));


        SearchResultRepository resultRepository = new SearchResultRepositoryImpl();
        resultRepository.save(collect);
        resultRepository.getAll().forEach(x -> log.info(x.toString()));


    }


}