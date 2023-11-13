package com.memoritta.summarizer.bot.discord.utils;

import com.memoritta.summarizer.bot.discord.config.DiscordConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CommandsUtils {

    public static final String SUMMARY_FOR_CHANNEL_COMMAND_PREFIX = "!sc:";
    private final DiscordConfig config;

    public boolean isCommand(MessageReceivedEvent event) {
        log.info("user id: {}", event.getAuthor().getId());
        if (config.getAdmins().contains(event.getAuthor().getId())) {
            String message = event.getMessage().getContentDisplay();
            if (StringUtils.startsWith(message, SUMMARY_FOR_CHANNEL_COMMAND_PREFIX)) {
                return true;
            }
        }
        return false;
    }

    public String getChannel(MessageReceivedEvent event) {
        if (config.getAdmins().contains(event.getAuthor().getId())) {
            String message = event.getMessage().getContentDisplay();
            if (StringUtils.startsWith(message, SUMMARY_FOR_CHANNEL_COMMAND_PREFIX)) {
                return StringUtils.substring(message, SUMMARY_FOR_CHANNEL_COMMAND_PREFIX.length());
            }
        }
        return null;
    }

}
