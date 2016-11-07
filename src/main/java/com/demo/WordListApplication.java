package com.demo;

import java.net.URL;
import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.demo.service.IndexerService;
import com.demo.service.SearchService;

@SpringBootApplication
public class WordListApplication {

    public static void main(String[] args) throws Exception {
        if(args.length != 2) {
            System.out.println("usage: java -jar target/word-miner.jar <url> <size>");
            System.exit(0);
        }
        ApplicationContext appContext = SpringApplication.run(WordListApplication.class, args);
        Long startTime = new Date().getTime();
        IndexerService indexerService = (IndexerService) appContext.getBean(IndexerService.class);
        SearchService searchService = (SearchService) appContext.getBean(SearchService.class);
        indexerService.index(new URL(args[0]));
        List<String> resultList = searchService.findLongestWordChain(Integer.valueOf(args[1]));
        Long endTime = new Date().getTime();
        System.out.println("Elapsed Time (ms) " + (endTime - startTime) + " Result:" + resultList);
    }
}
