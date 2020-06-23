package net.golikov.springdddexample.ddd;

import net.golikov.springframework.beans.factory.PartialAutowireAnnotationConfigApplicationContext;
import net.golikov.springframework.beans.factory.PartialAutowireAnnotationConfigServletWebServerApplicationContext;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class SpringDddExampleApplication {

    public static void main(String[] args) {
        runSpringBoot(args);
    }

    private static void runSpringBoot(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringDddExampleApplication.class);
        springApplication.setApplicationContextClass(PartialAutowireAnnotationConfigServletWebServerApplicationContext.class);
        springApplication.run(args);
    }

    private static void runSpringFramework(String[] args) {
        PartialAutowireAnnotationConfigApplicationContext context =
                new PartialAutowireAnnotationConfigApplicationContext("net.golikov.springdddexample");
        context.start();
    }

}
