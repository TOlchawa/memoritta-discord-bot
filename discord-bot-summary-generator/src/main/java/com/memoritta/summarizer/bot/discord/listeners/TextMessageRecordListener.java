package com.memoritta.summarizer.bot.discord.listeners;

import com.memoritta.summarizer.bot.discord.client.TextMessageRecorderClient;
import com.memoritta.summarizer.bot.discord.utils.CommandsUtils;
import com.memoritta.summarizer.domain.Discussion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static java.time.ZoneOffset.UTC;

@Slf4j
@Service
@AllArgsConstructor
public class TextMessageRecordListener extends ListenerAdapter {

    private final TextMessageRecorderClient textMessageRecorderClient;
    private final CommandsUtils commandsUtils;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (commandsUtils.isCommand(event)) {
            return;
        }

        String message = event.getMessage().getContentDisplay();
        String channel = event.getChannel().getName();
        String userName = event.getAuthor().getGlobalName();
        String userId = event.getAuthor().getId();
        log.info("Message from {} body.len: {}", userId, StringUtils.length(message));

        Discussion discussion = new Discussion();
        discussion.setChannel(channel);
        Discussion.User author = new Discussion.User();
        author.setId(userId);
        author.setName(userName);
        discussion.setAuthor(author);
        discussion.setText(message);
        discussion.setDatetime(Instant.now().atZone(UTC).toLocalDateTime());

        textMessageRecorderClient.record(discussion);

    }
}
