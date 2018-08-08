package userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import userservice.repositories.User;
import userservice.repositories.UserRepository;
import userservice.services.UserService;

@SpringBootApplication
//@EnableNeo4jRepositories
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("Starting context");
        SpringApplication.run(Application.class);
        log.info("Finishing context");
    }

    @Bean
    public CommandLineRunner demo(UserService service) {
        return (args) -> {
            log.info("Application is running");
            service.saveUser("user", "pppppp");
            //log.info(":");
            log.info("-------------------------------");
        };
    }
}
