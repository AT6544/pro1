package com.example.team_pro_ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TeamProExApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamProExApplication.class, args);
    }

}
