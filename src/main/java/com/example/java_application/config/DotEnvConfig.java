package com.example.java_application.config;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotEnvConfig {
     @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().ignoreIfMissing().load();
    }

    static {
        DotenvLoader.loadEnvVariables();
    }

    private static class DotenvLoader {
        private static void loadEnvVariables() {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        }
    }
}