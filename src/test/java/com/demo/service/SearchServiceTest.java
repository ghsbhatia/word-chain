package com.demo.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {

    @Mock DictionaryService dictionaryService;
    
    @InjectMocks SearchService searchService;
    
    @Before
    public void init() {
        
        Mockito.when(dictionaryService.distinctKeySizes())
               .thenReturn(Arrays.asList(2,3,4,5));

        Answer<List<String>> keysAnswer = new Answer<List<String>>() {
            @Override
            public List<String> answer(InvocationOnMock invocation) throws Throwable {
                Integer value = (Integer)invocation.getArguments()[0];
                List<String> keys = Collections.emptyList();
                if (value == 2) keys = Arrays.asList("an","on");
                if (value == 3) keys = Arrays.asList("oen","ont");
                if (value == 4) keys = Arrays.asList("deon","kont");
                if (value == 5) keys = Arrays.asList("adeon","eonts");
                return keys;
            }
        };

        Mockito.when(dictionaryService.keys(Mockito.anyInt(), Mockito.anyString()))
               .thenAnswer(keysAnswer);

        Mockito.when(dictionaryService.keys(Mockito.anyInt()))
               .thenAnswer(keysAnswer);

        Mockito.when(dictionaryService.chainable("on", "oen"))
               .thenReturn(true);

        Mockito.when(dictionaryService.chainable("oen", "deon"))
               .thenReturn(true);
 
        Mockito.when(dictionaryService.chainable("deon", "adeon"))
               .thenReturn(true);

        Mockito.when(dictionaryService.chainable("on", "ont"))
               .thenReturn(true);

        Mockito.when(dictionaryService.chainable("ont", "kont"))
               .thenReturn(true);

        Answer<String> valueAnswer = new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                String value = (String)invocation.getArguments()[0];
                return value;
            }
        };

        Mockito.when(dictionaryService.value(Mockito.anyString()))
               .thenAnswer(valueAnswer);
        
    }
    
    @Test
    public void shouldReturnLongestList() {
        List<String> keyList = searchService.findLongestWordChain(5);        
        assertThat(keyList, contains("on", "oen", "deon", "adeon"));        
    }
    
    @Test
    public void shouldReturnLimitedList() {
        List<String> keyList = searchService.findLongestWordChain(2);        
        assertThat(keyList, contains("on", "oen"));        
    }
}
