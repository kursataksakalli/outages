package com.krakenflex.outages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class OutagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutagesApplication.class, args);
    }

}
