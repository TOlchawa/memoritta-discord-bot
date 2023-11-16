package com.memoritta.summarizer.bot.discord.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@AllArgsConstructor
public class DynamicSchedulerClient {
    private final TaskScheduler taskScheduler;

    public ScheduledFuture<?> scheduleTaskAtSpecificTime(Runnable task, LocalTime scheduledTime) {
        long initialDelay = calculateInitialDelay(scheduledTime);
        log.info("initialDelay: {}", initialDelay);
        return taskScheduler.scheduleWithFixedDelay(task, Instant.now().plus(initialDelay, ChronoUnit.MINUTES), Duration.ofDays(1));
    }

    private long calculateInitialDelay(LocalTime time) {
        LocalTime now = LocalTime.now();
        if (now.isAfter(time)) {
            return Duration.between(now, time).plusHours(24).toMinutes();
        } else {
            return Duration.between(now, time).toMinutes();
        }
    }
}
