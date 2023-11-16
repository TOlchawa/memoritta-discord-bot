package com.memoritta.summarizer.configuration.manager.controller;

import com.memoritta.summarizer.configuration.manager.db.ConfigurationRepository;
import com.memoritta.summarizer.domain.Configuration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ConfigurationManagerController {

    private final ConfigurationRepository configurationRepository;

    @PostMapping("/config/save")
    public String save(@RequestBody Configuration configuration) {
        log.info("save configuration: {}", configuration);
        configurationRepository.deleteByServerAndChannelId(configuration.getServer(), configuration.getChannelId());
        configuration.setDatetime(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        Configuration result = configurationRepository.save(configuration);
        log.info("saved configuration: {}", result);
        return "OK";
    }

    @GetMapping("/config/load/all")
    public List<Configuration> loadAll() {
        log.info("loadAll...");
        List<Configuration> result = configurationRepository.findAll();
        log.info("List of configuration size: {}", result.size());
        return result;
    }

    @GetMapping("/config/load/server/{server}")
    public List<Configuration> load(@PathVariable String server) {
        log.info("load for server: {}", server);
        List<Configuration> result = configurationRepository.findByServer(server);
        log.info("List of configuration for server: {} size: {}", server, result.size());
        return result;
    }

    @GetMapping("/config/load/server/{server}/channel/{channel}")
    public Configuration load(@PathVariable String server, @PathVariable String channel) {
        log.info("load for server: {} and channelId: {}", server, channel);
        Configuration result = configurationRepository.findByServerAndChannelId(server, channel).orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
        log.info("Configuration for server: {} ; configuration: {}", server, result);
        return result;
    }


}
