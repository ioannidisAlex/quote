package dev.ioalex.springcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringCrudApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringCrudApplication::main).with(TestSpringCrudApplication.class).run(args);
    }

}
