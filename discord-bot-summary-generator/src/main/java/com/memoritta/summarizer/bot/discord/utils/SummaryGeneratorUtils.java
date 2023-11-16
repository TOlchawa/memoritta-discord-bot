package com.memoritta.summarizer.bot.discord.utils;

import com.memoritta.summarizer.bot.discord.client.SummaryGeneratorClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class SummaryGeneratorUtils {

    private static final String PREFIX = "Podsumowanie z **";
    private static final String _NL = "** \n";
    private static final String UNABLE_TO_COMPLETE_TASK = "Unable to complete task";

    private final SummaryGeneratorClient summaryGeneratorClient;

    public void generateSummary(String server, String channelId, String channelName, TextChannel channel) {
        summaryGeneratorClient.generateSummary(server, channelId)
                .blockOptional()
                .map(summary -> PREFIX + channelName + _NL + summary)
                .ifPresentOrElse(summary -> channel.sendMessage(summary).queue(), () -> log.error(UNABLE_TO_COMPLETE_TASK));
    }

    public void sendSummaryToChannel(String server, String channelId, String channelName, TextChannel channel) {
        Runnable summaryTask = () -> {
            generateSummary(server, channelId, channelName, channel);
        };

        RestAction<?> restAction = channel.sendTyping();
        CompletableFuture<?> future = restAction.submit();
        future.thenRun(summaryTask);
        restAction.timeout(300, TimeUnit.SECONDS).queue();
    }

}
