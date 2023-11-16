package com.memoritta.summarizer.bot.discord.manager;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

import static java.util.Optional.ofNullable;

@Repository
public class ScheduledTasksManager {

    private Map<String, ScheduledFuture<?>> scheduledTasks = new LinkedHashMap<>();

    public void register(String server, String channelId, ScheduledFuture<?> scheduledTask) {
        String key = server + ":" + channelId;
        if (scheduledTasks.containsKey(key)) {
            scheduledTasks.get(key).cancel(false);
        }
        scheduledTasks.put(key, scheduledTask);
    }

    public Optional<ScheduledFuture<?>> provideSchedule(String server, String channelId) {
        String key = server + ":" + channelId;
        return ofNullable(scheduledTasks.get(key));
    }
}
