package com.memoritta.summarizer.bot.discord.listeners;

import com.memoritta.summarizer.bot.discord.client.SummaryGeneratorClient;
import com.memoritta.summarizer.bot.discord.utils.CommandsUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.internal.requests.CompletedRestAction;
import net.dv8tion.jda.internal.requests.RestActionImpl;
import net.dv8tion.jda.internal.requests.restaction.operator.CombineRestAction;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static java.time.Duration.ofSeconds;

@Slf4j
@Service
@AllArgsConstructor
public class SummaryGeneratorListener extends ListenerAdapter {

    private static final String PREFIX = "Podsumowanie z **";
    private static final String _NL = "** \n";
    private static final String UNABLE_TO_COMPLETE_TASK = "Unable to complete task";
    private final CommandsUtils commandsUtils;
    private final SummaryGeneratorClient summaryGeneratorClient;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (commandsUtils.isUnknownServer(event)) {
            log.error("Unknown server for event: from: {}, channel: {}", event.getAuthor(), event.getChannel());
            return;
        }

        if (!commandsUtils.isCommand(event)) {
            return;
        }

        String server = event.getGuild().getId();
        String channelName = commandsUtils.getChannelName(event);
        String channelId = commandsUtils.getChannelId(event);

        log.info("command text: {}", event.getMessage().getContentDisplay());

        Runnable task = () -> {
            summaryGeneratorClient.generateSummary(server, channelId)
                    .blockOptional()
                    .map(summary -> PREFIX + channelName + _NL + summary)
                    .ifPresent( summary -> event.getChannel().sendMessage(summary).queue());
        };

        RestAction<?> restAction = event.getChannel().sendTyping();
        CompletableFuture<?> future = restAction.submit();
        future.thenRun(task);
        restAction.timeout(300, TimeUnit.SECONDS).queue();

    }

}
