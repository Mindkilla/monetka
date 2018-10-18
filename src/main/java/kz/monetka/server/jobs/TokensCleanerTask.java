package kz.monetka.server.jobs;

import kz.monetka.server.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class TokensCleanerTask {
    private static final Logger LOGGER = Logger.getLogger(TokensCleanerTask.class);

    @Autowired
    private UserService userService;

    /**
     * Задача очищающая старые токены пользователей
     */
    @Scheduled(fixedRate = 120000)
    public void cleanTokens() {
        LOGGER.info("Start TokensCleanerTask...");
        Date now = Date.from(Instant.now());
        userService.deleteAllExpiredSince(now);
        LOGGER.info("Stop TokensCleanerTask...");
    }
}
