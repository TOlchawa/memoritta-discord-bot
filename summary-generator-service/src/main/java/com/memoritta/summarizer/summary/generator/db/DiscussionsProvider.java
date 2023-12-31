package com.memoritta.summarizer.summary.generator.db;

import com.memoritta.summarizer.domain.Discussion;
import com.memoritta.summarizer.summary.generator.db.DiscussionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DiscussionsProvider {
    private final MongoTemplate mongoTemplate;

    public List<Discussion> getDiscussionsForChannel(String server, String channelId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("server").is(server));
        query.addCriteria(Criteria.where("channelId").is(channelId));
        query.fields().include("_id", "datetime", "user.id", "text");

        return mongoTemplate.find(query, Discussion.class)
                .stream()
                .toList();
    }

}
