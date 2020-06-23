package net.golikov.springdddexample.anemic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringDddExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDddExampleApplication.class, args);
    }

}
