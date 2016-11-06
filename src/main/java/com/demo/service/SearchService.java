package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SearchService provides functions to search for word patterns.
 * 
 * @author sbhatia
 */
@Service
public class SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
    
    @Autowired
    private DictionaryService dictionaryService;
    
    /**
     * Find longest chain of words where next word in the chain
     * matches all the characters of its predecessor and has only
     * one extra character.
     * 
     * @param threshold maximum desired length of chain
     */
    public List<String> findLongestWordChain(int threshold) {
        
        logger.info("Find chain of size {}", threshold);
        
        List<String> keyList = new ArrayList<>();
        
        for (int i = 0; i < dictionaryService.distinctKeySizes().size() && keyList.size() < threshold; i++) {
            buildChain(dictionaryService.distinctKeySizes().get(i), keyList, threshold);
        }
        
        return keyList.stream().map(key -> dictionaryService.value(key)).collect(Collectors.toList());
    }
    
    /**
     * Lookup the list of keys that match given size and use
     * them as a base to build the chain until threshold is met.
     * 
     * @param size word size to use as a base of chain
     * @param keyList list of chainable keys
     * @param threshold maximum desired length of chain
     */
    private void buildChain(int size, List<String> keyList, int threshold) {
        
        for (String key: dictionaryService.keys(size)) {
            if (keyList.size() >= threshold) return;
            Stack<String> keyStack = new Stack<>();
            keyStack.push(key);
            buildStack(keyStack, getNextSize(size), keyList, threshold);            
            keyStack.clear();
        }
        
    }
    
    /**
     * Put keys on stack until threshold is met or all word groups have been processed.
     * 
     * @param stack key stack
     * @param size length of words in current group being processed
     * @param keyList list of chainable keys
     * @param threshold maximum desired length of chain
     */
    private void buildStack(Stack<String> stack, int size, List<String> keyList, int threshold) {        
        
        if (size == -1 || size > threshold+1) return;
        
        String base = stack.peek();
        
        List<String> keys = dictionaryService.keys(size, base);
        for (String key: keys) {
            if (keyList.size() >= threshold) return;
            if (dictionaryService.chainable(base, key)) {
                stack.push(key);
                buildStack(stack, getNextSize(size), keyList, threshold); 
                if (stack.size() > keyList.size()) populateKeyList(stack, keyList);
                stack.pop();
            }
        } 
        
    }
    
    /**
     * Populate key list from stack of keys.
     * 
     * @param stack key stack
     * @param keyList key list
     */
    private void populateKeyList(Stack<String> stack, List<String> keyList) {
        keyList.clear();
        keyList.addAll(stack.stream().collect(Collectors.toList()));
        logger.debug("Key List: {}", keyList);
    }
    
    /**
     * Get size of words in next group given the size of words in current group.
     * 
     * @param currentSize word size in current group
     * @return next group word size
     */
    private int getNextSize(int currentSize) {        
        int nextSize = currentSize + 1;
        return dictionaryService.distinctKeySizes().contains(Integer.valueOf(nextSize)) ? nextSize : -1;    
    }
}
