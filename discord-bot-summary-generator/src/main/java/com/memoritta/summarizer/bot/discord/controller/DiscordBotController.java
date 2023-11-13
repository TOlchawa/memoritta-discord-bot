package com.memoritta.summarizer.bot.discord.controller;

import com.memoritta.summarizer.bot.discord.config.DiscordConfig;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DiscordBotController extends ListenerAdapter {

    private DiscordBotController(DiscordConfig discordConfig, EventListener[] listeners) {
        try {
            log.info("token: {}...", discordConfig.getDiscordKey().substring(0, 16));
            JDA jda = JDABuilder.createDefault(discordConfig.getDiscordKey())
                    .addEventListeners(this)
                    .setActivity(Activity.listening("memory"))
                    .setStatus(OnlineStatus.ONLINE)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .build();
            log.info("Starting listener ...");
            jda.awaitReady();
            jda.addEventListener(listeners);
        } catch (InterruptedException e) {
            log.error("Unable to login; error: {}", e.getMessage());
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
        log.info("Logged into Discord as {}: ", event.getJDA().getSelfUser().getName());
    }

}