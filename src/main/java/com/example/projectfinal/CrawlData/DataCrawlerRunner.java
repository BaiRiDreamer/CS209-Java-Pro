package com.example.projectfinal.CrawlData;

import com.example.projectfinal.CrawlData.Service.StackOverflowCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 应用启动后执行数据爬取
 */
@Component
public class DataCrawlerRunner implements CommandLineRunner {

    @Autowired
    private StackOverflowCrawlerService crawlerService;

    @Override
    public void run(String... args) {
        System.out.println("Starting data crawling...");
        crawlerService.crawlQuestionData();
        System.out.println("Data crawling finished.");
    }
}