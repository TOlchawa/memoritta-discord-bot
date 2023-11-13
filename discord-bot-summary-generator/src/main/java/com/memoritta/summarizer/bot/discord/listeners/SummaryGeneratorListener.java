package com.memoritta.summarizer.bot.discord.listeners;

import com.memoritta.summarizer.bot.discord.client.SummaryGeneratorClient;
import com.memoritta.summarizer.bot.discord.utils.CommandsUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.time.Duration.ofSeconds;

@Slf4j
@Service
@AllArgsConstructor
public class SummaryGeneratorListener extends ListenerAdapter {

    private static final String PREFIX = "Podsumowanie z **";
    private static final String _NL = "** \n";
    private final CommandsUtils commandsUtils;
    private final SummaryGeneratorClient summaryGeneratorClient;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (!commandsUtils.isCommand(event)) {
            return;
        }

        Optional.ofNullable(commandsUtils.getChannel(event))
                .ifPresent(channel -> {
                    event.getChannel().sendTyping().delay(ofSeconds(120)).queue();
                    summaryGeneratorClient.generateSummary(channel)
                            .blockOptional()
                            .map(summary -> PREFIX + channel + _NL + summary)
                            .ifPresent(summary -> {
                                event.getChannel().sendMessage(summary).queue();
                            });
                });
    }
}
