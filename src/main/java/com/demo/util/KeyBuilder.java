package com.demo.util;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * KeyBuilder constructs a key for given value.
 * 
 * @author sbhatia
 *
 */
@Component
public class KeyBuilder {

    /**
     * Build a key by sorting the characters in given string.
     * 
     * @param value string
     * @return key
     */
    public String build(String value) {        
        Assert.notNull(value);
        char[] cArray = value.toLowerCase().toCharArray();
        Arrays.sort(cArray);
        return new String(cArray);
    }
}
