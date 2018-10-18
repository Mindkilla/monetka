package kz.monetka.server;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
public class MonetkaApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MonetkaApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }

	/*
	* dataSource из context.xml
	*/
	@Bean(value = "datasource")
	public DataSource dataSource() {
		JndiDataSourceLookup dataSource = new JndiDataSourceLookup();
		dataSource.setResourceRef(true);
		return dataSource.getDataSource("monetka/ds");
	}
}
