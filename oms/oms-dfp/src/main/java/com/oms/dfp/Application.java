package com.oms.dfp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@SpringBootApplication(scanBasePackages={"com.oms"})
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"com.oms.repository","org.tavant.api.auth.repository"})
@ComponentScan({"com.api.email.*","com.oms.*", "org.tavant.api.*"})
@EntityScan(basePackages={"com.oms.model", "org.tavant.api.auth.model"})
public class Application extends SpringBootServletInitializer { 

    /**
     * Entry point for the application.
     *
     * @param args Command line arguments.
     * @throws Exception Thrown when an unexpected Exception is thrown from the
     *         application.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    
    

}

