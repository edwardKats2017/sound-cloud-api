package com.company.sender;

import com.company.model.SearchResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class SearchQuerySender {

    private static final String TOKEN_URL_PARAM = "client_id";
    private static final String TOKEN = "pCNN85KHlpoe5K6ZlysWZBEgLJRcftOd";

    private static final String BASE_URL = "http://api.soundcloud.com/tracks/";

    private static final Integer RESULTS_LIMIT = 10;

    @SneakyThrows
    public static Set<SearchResult> getResults(String searchFor) {

        String rawJsonResponse = getRawResponse(searchFor);

        Set<SearchResult> results = new ObjectMapper().readValue(rawJsonResponse, new TypeReference<TreeSet<SearchResult>>() {});

        log.info("Search request for [{}] has returned {} results", searchFor, results.size());

        return results;
    }


    private static String getRawResponse(String searchFor) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> body = restTemplate.getForEntity(getRequestQuery(searchFor), String.class);
        if (body.getStatusCodeValue() > 200) {
            log.error("asdasdsd");
        }

        return body.getBody();
    }

    private static String getRequestQuery(String searchFor) {
        log.info("Creating a request URL");
        StringBuilder builder = new StringBuilder()
                .append(BASE_URL)
                .append("?");

        if (searchFor != null && !searchFor.isEmpty()) {
            builder.append("q=")
                    .append(searchFor)
                    .append("&");
        }

        builder.append(TOKEN_URL_PARAM)
                .append("=")
                .append(TOKEN)
                .append("&")
                .append("limit")
                .append("=")
                .append(RESULTS_LIMIT);
        log.debug("Created a request URL: {}", builder.toString());
        return builder.toString();
    }
}
