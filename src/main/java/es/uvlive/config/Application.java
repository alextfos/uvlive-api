package es.uvlive.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class Application implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {

    }

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    // various @Bean definitions
}