package com.memoritta.summarizer.bot.discord.utils;

import com.memoritta.summarizer.bot.discord.config.DiscordConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CommandsUtils {

    public static final String SUMMARY_COMMAND_PREFIX = "!sc";
    public static final String SUMMARY_FOR_CHANNEL_COMMAND_PREFIX = SUMMARY_COMMAND_PREFIX + ":";
    private final DiscordConfig config;

    public boolean isCommand(MessageReceivedEvent event) {
        String userId = event.getAuthor().getId();
        String message = event.getMessage().getContentDisplay();
        return isCommand(message, userId);
    }

    public boolean isCommand(MessageUpdateEvent event) {
        String userId = event.getAuthor().getId();
        String message = event.getMessage().getContentDisplay();
        return isCommand(message, userId);
    }

    private boolean isCommand(String message, String userId) {
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

    public String getChannelName(MessageReceivedEvent event) {
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

    public String getChannelId(MessageReceivedEvent event) {
        if (config.getAdmins().contains(event.getAuthor().getId())) {
            return event.getChannel().getId();
        }
        return null;
    }

    public boolean isUnknownServer(GenericMessageEvent event) {
        return !event.isFromGuild() || event.getGuild() == null || event.getGuild().getId() == null;
    }
}
