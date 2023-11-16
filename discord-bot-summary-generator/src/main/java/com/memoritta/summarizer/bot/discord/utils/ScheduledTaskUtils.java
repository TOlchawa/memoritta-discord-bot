package com.memoritta.summarizer.bot.discord.utils;

import com.memoritta.summarizer.bot.discord.client.ConfigurationManagerClient;
import com.memoritta.summarizer.bot.discord.client.DynamicSchedulerClient;
import com.memoritta.summarizer.bot.discord.client.SummaryGeneratorClient;
import com.memoritta.summarizer.bot.discord.manager.ScheduledTasksManager;
import com.memoritta.summarizer.domain.Configuration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class ScheduledTaskUtils {


    private final DynamicSchedulerClient dynamicSchedulerClient;
    private final ConfigurationManagerClient configurationManagerClient;
    private final SummaryGeneratorUtils summaryGeneratorUtils;
    private final ScheduledTasksManager scheduledTasksManager;

    private void schedule(final JDA jda, final Configuration configuration) {
        final String server = configuration.getServer();
        final String channelId = configuration.getChannelId();
        final String channelName = configuration.getChannelName();

        Runnable task = () -> {
            log.info("Sending scheduled summary to server: {}, channelId: {}, channelName: {}", server, channelId, channelName);
            jda.setAutoReconnect(true);
            TextChannel channel = jda.getTextChannelById(configuration.getChannelId());
            log.info("Sending scheduled summary to channel: {}", channel);
            if (channel != null) {
                // Send summary to channel
                summaryGeneratorUtils.sendSummaryToChannel(server, channelId, channelName, channel);
            } else {
                log.error("Unable to send summary to server: {}, channelId: {}, channelName: {}", configuration.getServer(), configuration.getChannelId(), configuration.getChannelName());
            }
        };
        scheduledTasksManager.register(server, channelId, dynamicSchedulerClient.scheduleTaskAtSpecificTime(task, configuration.getScheduleTime()));
        jda.setAutoReconnect(true);
        TextChannel channel = jda.getTextChannelById(configuration.getChannelId());
        channel.sendMessage(scheduledTasksManager.provideSchedule(server, channelId).filter(Objects::nonNull).map(s -> "schedule created").orElse("error")).queue();
    }

    public void setupScheduleAt(MessageReceivedEvent event, Configuration configuration) {
        if (configuration != null) {
            Mono<String> save = configurationManagerClient.save(configuration);
            log.info("save configuration {} with result: {}", configuration, save.blockOptional().orElseGet(() -> "ERROR"));
            schedule(event.getJDA(), configuration);
        }
    }


}
