package run.serverstatus.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.context.Context;

@Configuration
public class SpringBeansConfiguration {
    @Bean
    public Context getContext() {
        return new Context();
    }
}
