package com.memoritta.summarizer.bot.discord.listeners;

import com.memoritta.summarizer.bot.discord.client.TextMessageRecorderClient;
import com.memoritta.summarizer.bot.discord.utils.CommandsUtils;
import com.memoritta.summarizer.domain.Discussion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static java.time.ZoneOffset.UTC;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Service
@AllArgsConstructor
public class TextMessageRecordListener extends ListenerAdapter {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final TextMessageRecorderClient textMessageRecorderClient;
    private final CommandsUtils commandsUtils;


    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        try {
            processUpdate(event);
        } catch (Throwable t) {
            log.error("Error: {}", t.getMessage(), t);
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            processMessage(event);
        } catch (Throwable t) {
            log.error("Error: {}", t.getMessage(), t);
        }
    }

    private void processUpdate(MessageUpdateEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (commandsUtils.unknownServerDetected(event)) {
            log.error("Unknown server for event: from: {}, channel: {}", event.getAuthor(), event.getChannel());
            return;
        }

        if (commandsUtils.commandDetected(event)) {
            return;
        }

        readMessage(event);
    }

    private void processMessage(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (commandsUtils.unknownServerDetected(event)) {
            log.error("Unknown server for event: from: {}, channel: {}", event.getAuthor(), event.getChannel());
            return;
        }

        if (commandsUtils.commandDetected(event)) {
            return;
        }

        readMessage(event);
    }

    private void readMessage(MessageUpdateEvent event) {
        String message = event.getMessage().getContentDisplay();
        String channelId = event.getChannel().getId();
        String channelName = event.getChannel().getName();
        String server = event.getGuild().getId();
        String userName = event.getAuthor().getGlobalName();
        String userId = event.getAuthor().getId();
        log.info("Message from {} body.len: {}", userId, StringUtils.length(message));

        processMessage(message, channelId, channelName, server, userName, userId);
    }

    private void readMessage(MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();
        String channelId = event.getChannel().getId();
        String channelName = event.getChannel().getName();
        String server = event.getGuild().getId();
        String userName = event.getAuthor().getGlobalName();
        String userId = event.getAuthor().getId();
        log.info("Message from {} body.len: {}", userId, StringUtils.length(message));

        processMessage(message, channelId, channelName, server, userName, userId);
    }

    private void processMessage(String message, String channelId, String channelName, String server, String userName, String userId) {
        Discussion discussion = new Discussion();
        discussion.setServer(server);
        discussion.setChannelId(channelId);
        discussion.setChannelName(channelName);
        Discussion.User author = new Discussion.User();
        author.setId(userId);
        author.setName(userName);
        discussion.setAuthor(author);
        discussion.setText(message);
        discussion.setDatetime(Instant.now().atZone(UTC).toLocalDateTime());

        textMessageRecorderClient.record(discussion);
    }
}
