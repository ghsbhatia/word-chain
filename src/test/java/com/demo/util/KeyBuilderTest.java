package com.demo.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.demo.util.KeyBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class KeyBuilderTest {

    KeyBuilder keyBuilder = new KeyBuilder();
    
    @Test
    public void shouldGenerateKeyInNaturalOrder() {
        String word = "CaB";
        String key = keyBuilder.build(word);
        assertThat(key, is("abc"));
    }
}
