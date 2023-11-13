package com.memoritta.summarizer.bot.discord.client;

import com.memoritta.summarizer.bot.discord.config.ServerConfig;
import com.memoritta.summarizer.bot.discord.config.WebClientConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@Service
@AllArgsConstructor
public class SummaryGeneratorClient {

    private final ServerConfig serverConfig;
    private final WebClientConfiguration webClientConfiguration;

    public Mono<String> generateSummary(String channel) {
        log.info("channel: {}", channel);
        return webClientConfiguration.webClient().get()
                .uri(serverConfig.getSummaryGeneratorUri(), channel)
                .retrieve()
                .bodyToMono(String.class);

    }
}
