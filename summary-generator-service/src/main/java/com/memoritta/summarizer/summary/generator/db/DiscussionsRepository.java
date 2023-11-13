package com.memoritta.summarizer.summary.generator.db;

import com.memoritta.summarizer.domain.Discussion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiscussionsRepository extends MongoRepository<Discussion, String> {
}
