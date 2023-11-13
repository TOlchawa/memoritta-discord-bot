package com.memoritta.summarizer.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "discussion")
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
    private String datetime;

    @Data
    public static class User {
        @Id
        private String id;

        @Field("name")
        private String name;
    }
}
