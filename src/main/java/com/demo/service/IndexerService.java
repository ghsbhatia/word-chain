package com.demo.service;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * IndexerService build a dictionary of words from a given URL.
 * 
 * @author sbhatia
 */
@Service
public class IndexerService {

    private static final Logger logger = LoggerFactory.getLogger(IndexerService.class);
    
    @Autowired
    private DictionaryService dictionaryService;
    
    /**
     * Index words in given url.
     * 
     * @param url resource 
     * @throws Exception when error accessing the url
     */
    public void index(URL url) throws Exception {
        
        Assert.notNull(url);
        
        logger.info("Indexing url: {}", url);
        
        List<String> words = scan(url);
        words.stream().forEach(word -> {
            word = sanitize(word);
            if (word.length() < 2) return;
            dictionaryService.add(word);
        });
        
        logger.info("Indexing complete");
    }
    
    /**
     * Scan words in given URL.
     * 
     * @param url resource that returns a word per line
     * @return list of words
     * @throws Exception when error accessing the url
     */
    private List<String> scan(URL url) throws Exception {
        
        InputStream inputStream = null;
        
        try {
            inputStream = url.openStream();
            List<String> words = IOUtils.readLines(inputStream);
            return words;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    /**
     * Remove all non alpha characters.
     * 
     * @param word string to sanitize
     * @return sanitized string
     */
    private String sanitize(String word) {
        return StringUtils.defaultString(word).replaceAll("[^a-zA-Z]", "");
    }
}
