package com.demo.data;

import java.util.Arrays;

import com.demo.dao.Shard;
import com.demo.util.KeyBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class ShardTest {

    Shard shard = new Shard(2, new KeyBuilder());;
    
    @Test
    public void shouldReturnKeysInOrder() {
        
        String[] words = {"no", "to", "an", "of", "as", "is", "if"};
        Arrays.asList(words).stream().forEach(word -> shard.store(word));
        
        assertThat(shard.keys(), contains("an", "as", "fi", "fo", "is", "no", "ot"));
    }
    
    @Test
    public void shouldReturnFromKeysInOrder() {
        
        String[] words = {"no", "to", "an", "of", "as", "is", "if"};
        Arrays.asList(words).stream().forEach(word -> shard.store(word));
        
        assertThat(shard.keys("f","g"), contains("fi", "fo"));
    }
    
    @Test
    public void shouldStoreSingleInstanceOfEquivalentWords() {
        
        String[] words = {"no", "on", "an", "to"};
        Arrays.asList(words).stream().forEach(word -> shard.store(word));
        
        assertThat(shard.keys(), contains("an", "no", "ot"));
    }
    
}
