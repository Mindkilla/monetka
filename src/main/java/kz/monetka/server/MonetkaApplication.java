package kz.monetka.server;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonetkaApplication {


	public static void main(String[] args) {
        new SpringApplicationBuilder(MonetkaApplication.class).bannerMode(Banner.Mode.OFF).run(args);
	}
}
