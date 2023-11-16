package com.memoritta.summarizer.bot.discord.client;

import com.memoritta.summarizer.bot.discord.config.ServerConfig;
import com.memoritta.summarizer.bot.discord.config.WebClientConfiguration;
import com.memoritta.summarizer.domain.Configuration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ConfigurationManagerClient {

    private final ServerConfig serverConfig;
    private final WebClientConfiguration webClientConfiguration;

    public Mono<String> save(Configuration configuration) {
        log.info("configuration: {}", configuration);
        return webClientConfiguration.webClient().post()
                .uri(serverConfig.getConfigurationSaveUri())
                .bodyValue(configuration)
                .retrieve()
                .bodyToMono(String.class);
    }

    public List<Configuration> loadAllConfiguration() {
        return webClientConfiguration.webClient().get()
                .uri(serverConfig.getLoadAllConfigurationSaveUri())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Configuration>>() {})
                .blockOptional()
                .orElse(Collections.emptyList());

    }

    public List<Configuration> loadConfigurationForServer(String server) {
        return webClientConfiguration.webClient().get()
                .uri(serverConfig.getLoadAllConfigurationSaveUri())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Configuration>>() {})
                .blockOptional()
                .orElse(Collections.emptyList());

    }
}
