package com.memoritta.summarizer.bot.discord.listeners;

import com.memoritta.summarizer.bot.discord.client.ConfigurationManagerClient;
import com.memoritta.summarizer.bot.discord.client.DynamicSchedulerClient;
import com.memoritta.summarizer.bot.discord.client.SummaryGeneratorClient;
import com.memoritta.summarizer.bot.discord.utils.CommandsUtils;
import com.memoritta.summarizer.bot.discord.utils.ScheduledTaskUtils;
import com.memoritta.summarizer.bot.discord.utils.SummaryGeneratorUtils;
import com.memoritta.summarizer.domain.Configuration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;

@Slf4j
@Service
@AllArgsConstructor
public class CommandListener extends ListenerAdapter {


    private final CommandsUtils commandsUtils;
//    private final SummaryGeneratorClient summaryGeneratorClient;
    private final ConfigurationManagerClient configurationManagerClient;
    private final ScheduledTaskUtils scheduledTaskUtils;
    private final SummaryGeneratorUtils summaryGeneratorUtils;
//    private final DynamicSchedulerClient dynamicSchedulerClient;


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            processCommand(event);
        } catch (Throwable t) {
            log.error("Error: {}", t.getMessage(), t);
        }
    }

    private void processCommand(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (commandsUtils.unknownServerDetected(event)) {
            log.error("Unknown server for event: from: {}, channel: {}", event.getAuthor(), event.getChannel());
            return;
        }

        if (!commandsUtils.commandDetected(event)) {
            return;
        }

        if (StringUtils.startsWith(commandsUtils.provideCommand(event), "at")) {
            Configuration configuration = commandsUtils.provideConfigurationScheduleAt(event);
            log.info("Start processing configuration: {}", configuration);
            scheduledTaskUtils.setupScheduleAt(event, configuration);
        } else {
            createSummary(event);
        }

    }



    private void createSummary(MessageReceivedEvent event) {
        final String server = event.getGuild().getId();
        final String channelName = commandsUtils.extractChannelName(event);
        final String channelId = commandsUtils.extractChannelId(event);
        if (event.getChannelType() != ChannelType.TEXT) {
            log.info("Unable to send summary to different channel than TEXT_CHANNEL !");
            return;
        }

        final TextChannel channel = (TextChannel)event.getChannel();
        log.info("command text: {}", event.getMessage().getContentDisplay());

//        Runnable task = () -> {
//            summaryGeneratorClient.generateSummary(server, channelId)
//                    .blockOptional()
//                    .map(summary -> PREFIX + channelName + _NL + summary)
//                    .ifPresent( summary -> event.getChannel().sendMessage(summary).queue());
//        };
//
//        RestAction<?> restAction = event.getChannel().sendTyping();
//        CompletableFuture<?> future = restAction.submit();
//        future.thenRun(task);
//        restAction.timeout(300, TimeUnit.SECONDS).queue();

        summaryGeneratorUtils.sendSummaryToChannel(server, channelId, channelName, channel);
    }

}
