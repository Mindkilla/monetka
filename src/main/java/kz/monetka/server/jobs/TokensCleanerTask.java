package kz.monetka.server.jobs;

import kz.monetka.server.services.UserService;
import kz.monetka.server.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class TokensCleanerTask {

    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = Consts.PARSE_JOB_INTERVAL)
    public void cleanTokens() {
        Date now = Date.from(Instant.now());
        userService.deleteAllExpiredSince(now);
    }
}