package com.memoritta.summarizer.bot.discord.utils;

import com.memoritta.summarizer.bot.discord.config.DiscordConfig;
import com.memoritta.summarizer.domain.Configuration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Slf4j
@Service
@AllArgsConstructor
public class CommandsUtils {

    public static final String SUMMARY_COMMAND_PREFIX = "!sc";
    public static final String SCHEDULE_AT_COMMAND_SUFFIX = "at";
    public static final String SUMMARY_FOR_CHANNEL_COMMAND_PREFIX = SUMMARY_COMMAND_PREFIX + ":";
    private final DiscordConfig config;

    public boolean commandDetected(MessageReceivedEvent event) {
        String userId = event.getAuthor().getId();
        String message = event.getMessage().getContentDisplay();
        return commandDetected(message, userId);
    }

    public boolean commandDetected(MessageUpdateEvent event) {
        String userId = event.getAuthor().getId();
        String message = event.getMessage().getContentDisplay();
        return commandDetected(message, userId);
    }

    public String provideCommand(MessageReceivedEvent event) {
        String userId = event.getAuthor().getId();
        String message = event.getMessage().getContentDisplay();
        return provideCommand(message, userId);
    }

    public String provideCommand(MessageUpdateEvent event) {
        String userId = event.getAuthor().getId();
        String message = event.getMessage().getContentDisplay();
        return provideCommand(message, userId);
    }

    private String provideCommand(String message, String userId) {
        log.info("user id: {}", userId);
        if (config.getAdmins().contains(userId)) {
            log.info("admin user: {}", userId);
            if (StringUtils.contains(message, SUMMARY_COMMAND_PREFIX)) {
                String command = StringUtils.substringAfter(message, SUMMARY_COMMAND_PREFIX);
                log.info("command: {}", command);
                return command;
            }
        } else {
            log.info("unknown user: {}", userId);
        }
        return null;
    }

    private boolean commandDetected(String message, String userId) {
        log.info("user id: {}", userId);
        if (config.getAdmins().contains(userId)) {
            log.info("admin user: {}", userId);
            if (StringUtils.contains(message, SUMMARY_COMMAND_PREFIX)) {
                log.info("command: {}", message);
                return true;
            }
        } else {
            log.info("unknown user: {}", userId);
        }
        return false;
    }

    public String extractChannelName(MessageReceivedEvent event) {
        String authorId = event.getAuthor().getId();
        String contentDisplay = event.getMessage().getContentDisplay();
        String channelName = event.getChannel().getName();
        return extractChannelName(authorId, contentDisplay, channelName);
    }

    @Nullable
    private String extractChannelName(String authorId, String contentDisplay, String channelName) {
        if (config.getAdmins().contains(authorId)) {
            if (StringUtils.contains(contentDisplay, SUMMARY_FOR_CHANNEL_COMMAND_PREFIX)) {
                return StringUtils.substringAfter(contentDisplay, SUMMARY_FOR_CHANNEL_COMMAND_PREFIX);
            } else {
                return channelName;
            }
        }
        return null;
    }

    public String extractChannelId(MessageReceivedEvent event) {
        if (config.getAdmins().contains(event.getAuthor().getId())) {
            return event.getChannel().getId();
        }
        return null;
    }

    public boolean unknownServerDetected(GenericMessageEvent event) {
        return !event.isFromGuild() || event.getGuild() == null || event.getGuild().getId() == null;
    }

    public Configuration provideConfigurationScheduleAt(MessageReceivedEvent event) {
        String server = event.getGuild().getId();
        String channelName = event.getChannel().getName();
        String channelId = event.getChannel().getId();
        String text = event.getMessage().getContentDisplay();
        String time = StringUtils.substringAfter(text, SCHEDULE_AT_COMMAND_SUFFIX);
        LocalTime scheduleTime = null;
        try {
            scheduleTime = LocalTime.parse(time);
            log.info("schedule server {} ; channel: {}; at time {}", server, channelName, scheduleTime);

        } catch (Throwable t) {
            log.error("Error: {}", t.getMessage(), t);
        }

        Configuration result = null;

        if (scheduleTime != null) {
            result = new Configuration();
            result.setServer(server);
            result.setChannelId(channelId);
            result.setChannelName(channelName);
            result.setScheduleTime(scheduleTime);
        }

        return result;
    }
}
