package run.serverstatus.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = "run/serverstatus/app/repository")
public class Application {
    private static ConfigurableApplicationContext CONTEXT;

    public static void main(String[] args) {
        // Run application
        CONTEXT = SpringApplication.run(Application.class, args);
    }

    public static ConfigurableApplicationContext getContext() {
        return CONTEXT;
    }
}
