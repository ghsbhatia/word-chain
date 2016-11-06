package com.demo.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.demo.dao.Shard;
import com.demo.dao.ShardDao;
import com.demo.util.KeyBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ShardDao.class})
public class ShardDaoTest {

    @Spy KeyBuilder keyBuilder = new KeyBuilder();
    
    @InjectMocks ShardDao dao;
    
    Map<Integer, Shard> shards;
    
    @Before
    public void init() {
        shards = Whitebox.getInternalState(dao, "shards");
    }

    @Test
    public void shouldReturnStoredWord() {        
        String key = dao.write("word");
        assertThat(dao.read(key), is("word"));
    }

    @Test
    public void shouldStoreInCorrectShard() {        
        dao.write("word");
        assertThat(shards.get(Integer.valueOf(4)).values(), contains("word"));
    }
    
    @Test
    public void shouldGetKeysFromCorrectShard() {        
        dao.write("word");
        List<String> keys = dao.keys(4);
        assertThat(shards.get(Integer.valueOf(4)).keys().size(), is(keys.size()));
    }
    
    @Test
    public void shouldGetKeysInRange() {
        dao.write("abc");
        dao.write("aet");
        dao.write("cet");
        List<String> keys = dao.keys(4, "a", "b");
        assertThat(shards.get(Integer.valueOf(4)).keys("a", "b").size(), is(keys.size()));
    }
    
    @Test
    public void shouldReturnDistinctKeySizesInOrder() {
        dao.write("abc");
        dao.write("ceet");
        dao.write("aet");
        assertThat(dao.distinctKeySizes(), contains(3,4));
    }
    
    @Test
    public void shouldReturnKeysForGivenSize() {
        dao.write("abc");
        dao.write("ceet");
        dao.write("aet");
        assertThat(dao.keys(3).size(), is(2));
    }
}
