package com.demo.service;

import java.util.List;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.demo.dao.ShardDao;

/**
 * DictionaryService stores words and provides utility functions
 * to search for stored words.
 * 
 * @author sbhatia
 */
@Service
public class DictionaryService {

    @Autowired
    private ShardDao shardDao;
    
    /**
     * Add a given word to storage.
     * 
     * @param word word 
     */
    public void add(String word) {        
        Assert.notNull(word);
        shardDao.write(word);
    }
    
    /**
     * Get keys of given size.
     * 
     * @param size key size
     * @return list of keys
     */
    public List<String> keys(int size) {
        Assert.state(size > 0);
        return shardDao.keys(size);
    }
    
    /**
     * Get keys of given size specifying a base. For e.g. if base is 'cad',
     * then all the keys of given size that start with 'c' are returned. 
     * 
     * @param size key size
     * @param base base to match the key
     * @return list of keys
     */
    public List<String> keys(int size, String base) {
        Assert.state(size > 0);
        Assert.notNull(base);
        String startKey = toString(CharUtils.toChar(base));
        String endKey = toString(CharUtils.toChar(base) + 1);
        return shardDao.keys(size, startKey, endKey);
    }
    
    /**
     * Get word for the given key.
     * 
     * @param key
     * @return word
     */
    public String value(String key) {
        Assert.notNull(key);
        return shardDao.read(key);
    }
    
    /**
     * Get list of distinct key sizes in ascending order.
     * 
     * @return list of sizes
     */
    public List<Integer> distinctKeySizes() {
        return shardDao.distinctKeySizes();
    }
    
    /**
     * Find if the given keys can be chained. Keys can be chained if they
     * differ by one and only one character.
     * 
     * @param key1 first key
     * @param key2 second key
     * @return true if keys can be chained, false otherwise
     */
    public boolean chainable(String key1, String key2) {
        Assert.notNull(key1);
        Assert.notNull(key2);
        if (key1.length() == key2.length()) return false;
        return StringUtils.getLevenshteinDistance(key1, key2, 1) == 1;
    }
    
    private String toString(int code) {
        return String.format("%c", code);
    }
    
}
