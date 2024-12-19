package com.example.projectfinal.HTTPServer;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectFinalApplication {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProjectFinalApplication.class);

    public static void main(String[] args) {
        log.info("Starting application: ProjectFinalApplication");
        SpringApplication.run(ProjectFinalApplication.class, args);
    }

}
