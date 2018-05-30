package com.company.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {

    @JsonProperty("avatar_url")
    private String avatarUrl;


    @JsonProperty("id")
    private Long id;

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("permalink_url")
    private String permalinkUrl;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("username")
    private String username;

    @JsonProperty("permalink")
    private String permalink;

    @JsonProperty("last_modified")
    private String lastModified;

}
