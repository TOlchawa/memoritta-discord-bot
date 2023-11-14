package com.memoritta.summarizer.discussion.collector.db;

import com.memoritta.summarizer.domain.Discussion;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class DiscussionsProvider {
    private final MongoTemplate mongoTemplate;

    public void reset(String server, String channel) {
        Query query = new Query();
        query.addCriteria(Criteria.where("channel").is(channel));
        query.addCriteria(Criteria.where("datetime").lt(LocalDateTime.now().minus(1, ChronoUnit.HOURS)));
        query.fields().include("_id", "datetime", "channel");

        mongoTemplate.findAllAndRemove(query, Discussion.class);
    }

}
