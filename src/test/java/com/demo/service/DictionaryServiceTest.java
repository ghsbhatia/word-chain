package com.demo.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.demo.dao.ShardDao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryServiceTest {

    @Mock ShardDao dao;
    
    @InjectMocks DictionaryService service;
    
    @Test
    public void shouldAddWord() {
        service.add("word");
        Mockito.verify(dao).write("word");
    }
    
    @Test
    public void shouldGetKeys() {
        Mockito.when(dao.keys(2)).thenReturn(Arrays.asList("aa", "ab"));
        List<String> keys = service.keys(2);
        assertThat(keys, contains("aa", "ab"));
    }
    
    @Test
    public void shouldGetKeysWithGivenBase() {
        Mockito.when(dao.keys(2, "b", "c")).thenReturn(Arrays.asList("ba", "bc"));
        List<String> keys = service.keys(2, "ba");
        assertThat(keys, contains("ba", "bc"));
    }
    
    @Test
    public void shouldReturnValueForKey() {
        Mockito.when(dao.read("abt")).thenReturn("bat");
        String value = service.value("abt");
        assertThat(value, is("bat"));
    }
    
    @Test
    public void shouldReturnDistinctKeySizes() {
        Mockito.when(dao.distinctKeySizes()).thenReturn(Arrays.asList(2,3));
        List<Integer> sizes = service.distinctKeySizes();
        assertThat(sizes, contains(2, 3));
    }
    
    @Test
    public void shouldDetectAsChainable() {
        assertThat(service.chainable("ab", "abc"), is(true));
        assertThat(service.chainable("ab", "aab"), is(true));
        assertThat(service.chainable("ad", "abd"), is(true));
        assertThat(service.chainable("ad", "ade"), is(true));
        assertThat(service.chainable("ad", "add"), is(true));
    }
    
    @Test
    public void shouldDetectAsNotChainable() {
        assertThat(service.chainable("ab", "ac"),   is(false));
        assertThat(service.chainable("ab", "aad"),  is(false));
        assertThat(service.chainable("ab", "aabb"), is(false));
        assertThat(service.chainable("ad", "addd"), is(false));
        assertThat(service.chainable("ad", "addd"), is(false));
    }
    
}
