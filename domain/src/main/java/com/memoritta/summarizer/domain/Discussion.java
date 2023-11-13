package com.memoritta.summarizer.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "discussions")
public class Discussion {
    @Id
    private String id;

    @Field("author")
    private User author;

    @Field("text")
    private String text;

    @Field("channel")
    private String channel;

    @Field("datetime")
    private LocalDateTime datetime;

    @Data
    public static class User {
        @Id
        private String id;

        @Field("name")
        private String name;
    }
}
