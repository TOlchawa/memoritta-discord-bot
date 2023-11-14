package com.memoritta.summarizer.bot.discord.client;

import com.memoritta.summarizer.bot.discord.config.ServerConfig;
import com.memoritta.summarizer.bot.discord.config.WebClientConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class ResetChannelClient {

    private final ServerConfig serverConfig;
    private final WebClientConfiguration webClientConfiguration;

    public Mono<String> generateSummary(String server, String channel) {
        log.info("server: {}; channel: {}", server, channel);
        return webClientConfiguration.webClient().get()
                .uri(serverConfig.getResetUri(), server, channel)
                .retrieve()
                .bodyToMono(String.class);

    }
}
