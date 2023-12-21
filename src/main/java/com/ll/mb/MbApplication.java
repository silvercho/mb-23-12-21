package com.ll.mb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbApplication.class, args);
    }

}
