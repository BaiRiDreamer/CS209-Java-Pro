package com.example.projectfinal.CrawlData;

import com.example.projectfinal.CrawlData.Service.StackOverflowCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 应用启动后执行数据爬取
 */
@Component
public class DataCrawlerRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataCrawlerRunner.class);

    @Autowired
    private StackOverflowCrawlerService crawlerService;

    @Override
    public void run(String... args) {
        log.error("\n\n\n\n\n==================Start crawling data==================\n\n\n\n\n");
        crawlerService.crawlQuestionData();
        log.error("\n\n\n\n\n==================End crawling data==================\n\n\n\n\n");
    }
}