package com.jlearn;

import com.jlearn.auth.annotation.rest.AnonymousGetMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.oas.annotations.EnableOpenApi;


/**
 * @author dingjuru
 * @date 2021/11/12
 */
@RestController
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.jlearn", "com.jlearn.**.**", "com.jlearn.common.exception"})
@EnableJpaRepositories
@EnableOpenApi
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(App.class, args);

        getLog(run);
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }

    @AnonymousGetMapping( "/")
    @GetMapping
    @PostMapping
    public String index() {
        return "Backend service started ok!";
    }

    private static void getLog(ApplicationContext context) {


        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
        System.out.println(
                "---------------------------" +
                "\nApplicationName:" + context.getApplicationName() +
                "\nDisplayName:" + context.getDisplayName() + "\n" +
                "---------------------------"

        );
    }
}
