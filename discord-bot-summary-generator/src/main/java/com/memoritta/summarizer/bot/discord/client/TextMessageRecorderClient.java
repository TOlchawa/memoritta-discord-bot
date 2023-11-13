package com.memoritta.summarizer.bot.discord.client;

import com.memoritta.summarizer.bot.discord.config.ServerConfig;
import com.memoritta.summarizer.bot.discord.config.WebClientConfiguration;
import com.memoritta.summarizer.domain.Discussion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class TextMessageRecorderClient {

    private final ServerConfig serverConfig;
    private final WebClientConfiguration webClientConfiguration;

    public void record(Discussion discussion) {
        log.info("discussion: {}", discussion);
        Mono<String> result = webClientConfiguration.webClient().post()
                .uri(serverConfig.getRecorderUri(), discussion.getChannel())
                .bodyValue(discussion)
                .retrieve()
                .bodyToMono(String.class);
        result.blockOptional().ifPresent(response -> {
            log.info("response: {}", response);
        });
    }
}
