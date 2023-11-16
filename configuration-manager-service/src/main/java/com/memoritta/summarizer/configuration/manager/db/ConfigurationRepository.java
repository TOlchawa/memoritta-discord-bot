package com.memoritta.summarizer.configuration.manager.db;

import com.memoritta.summarizer.domain.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
    void deleteByServerAndChannelId(String server, String channelId);
    Optional<Configuration> findByServerAndChannelId(String server, String channelId);

    List<Configuration> findByServer(String server);
}
