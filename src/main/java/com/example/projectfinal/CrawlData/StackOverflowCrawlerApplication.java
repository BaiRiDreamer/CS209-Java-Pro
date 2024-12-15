package com.example.projectfinal.CrawlData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class StackOverflowCrawlerApplication {

    private static final Logger log = LoggerFactory.getLogger(StackOverflowCrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StackOverflowCrawlerApplication.class, args);
    }

}