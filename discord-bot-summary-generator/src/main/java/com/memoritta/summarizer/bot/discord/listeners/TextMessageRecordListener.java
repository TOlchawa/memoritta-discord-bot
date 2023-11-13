package com.memoritta.summarizer.bot.discord.listeners;

import com.memoritta.summarizer.bot.discord.client.TextMessageRecorderClient;
import com.memoritta.summarizer.domain.Discussion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TextMessageRecordListener extends ListenerAdapter {

    private final TextMessageRecorderClient textMessageRecorderClient;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
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

        textMessageRecorderClient.record(discussion);

    }
}
