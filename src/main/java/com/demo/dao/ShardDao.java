package com.demo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.demo.util.KeyBuilder;

@Component
public class ShardDao {
    
    @Autowired
    private KeyBuilder keyBuilder;

    private Map<Integer, Shard> shards = new HashMap<>();
    
    public String write(String word) {
        Assert.notNull(word);
        Integer shardId = Integer.valueOf(word.length());
        Shard shard = fetch(shardId);
        return shard.store(word);        
    }

    public List<String> keys(int size) {
        Assert.state(size > 0);
        Integer shardId = Integer.valueOf(size);
        Shard shard = fetch(shardId);
        return shard.keys();
    }
    
    public List<String> keys(int size, String startKey, String endKey) {
        Assert.state(size > 0);
        Integer shardId = Integer.valueOf(size);
        Shard shard = fetch(shardId);
        return shard.keys(startKey, endKey);
    }

    public List<Integer> distinctKeySizes() {
        return shards.keySet().stream().sorted().collect(Collectors.toList());
    }
    
    public String read(String key) {
        Assert.notNull(key);
        Integer shardId = Integer.valueOf(key.length());
        Shard shard = fetch(shardId);
        return shard.value(key);
    }

    private Shard fetch(Integer size) {
        Shard shard = shards.get(size);
        return shard != null ? shard : create(size);        
    }
    
    private Shard create(Integer id) {
        Shard shard = new Shard(id, keyBuilder);
        shards.put(id, shard);
        return shard;
    }
    
}
