package com.demo.dao;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.demo.util.KeyBuilder;

public class Shard {

    private static final Logger logger = LoggerFactory.getLogger(Shard.class);
    
    private Integer id;
    
    private KeyBuilder keyBuilder;

    private TreeMap<String, String> wordTree = new TreeMap<>();
    
    public Shard(Integer id, KeyBuilder keyBuilder) {
        this.id = id;
        this.keyBuilder = keyBuilder;
    }    
    
    public String store(String word) {        
        Assert.notNull(word);
        String key = keyBuilder.build(word);
        wordTree.put(key, word);
        logger.debug("Shard {} Added key: {} value: {}", id, key, word);
        return key;
    }
    
    public List<String> keys() {
        return wordTree.keySet().stream().collect(Collectors.toList());
    }
    
    public List<String> keys(String startKey, String endKey) {
        return wordTree.subMap(startKey, endKey).keySet().stream().collect(Collectors.toList());
    }
    
    public Collection<String> values() {
        return wordTree.values();
    }
    
    public String value(String key) {
        return wordTree.get(key);
    }
    
}
