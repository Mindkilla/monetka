package kz.monetka.server.services;

import kz.monetka.server.utils.Consts;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IBStatementParser {

    @Scheduled(fixedRate = Consts.PARSE_JOB_INTERVAL)
    public void doJob() {
        System.out.printf("Yes it works \r\n");
        parseStatements();
    }

    private void parseStatements() {

    }
}
