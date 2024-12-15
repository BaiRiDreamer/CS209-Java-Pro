package com.example.projectfinal.CrawlData;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class StackOverflowCrawlerApplication {

    public static void main(String[] args) {
        log.info("Start application");
        SpringApplication.run(StackOverflowCrawlerApplication.class, args);
    }

}