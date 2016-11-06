package com.demo;

import java.net.URL;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.service.IndexerService;
import com.demo.service.SearchService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WordListApplication.class)
public class WordListApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(WordListApplicationTest.class);
            
    @Autowired
    IndexerService indexerService;
    
    @Autowired
    SearchService searchService;
    
	@Test
	public void indexAndSearch() throws Exception {
	    indexerService.index(new URL("http://www-01.sil.org/linguistics/wordlists/english/wordlist/wordsEn.txt"));
	    List<String> resultList = searchService.findLongestWordChain(5);
	    logger.info("Result:"+ resultList);
	}

}
