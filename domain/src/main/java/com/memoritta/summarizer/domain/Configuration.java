package com.memoritta.summarizer.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Document(collection = "configuration")
public class Configuration {
    @Id
    private String id;

    @Field("server")
    private String server;

    @Field("channelName")
    private String channelName;

    @Field("channelId")
    private String channelId;

    @Field("datetime")
    private LocalDateTime datetime;

    @Field("scheduleTime")
    private LocalTime scheduleTime;

}
