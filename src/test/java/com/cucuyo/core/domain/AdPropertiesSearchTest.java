package com.cucuyo.core.domain;

import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AdPropertiesSearchTest {

    @Test
    public void isAdvancedShouldReturnFalseWithCero() {
        val search = new AdPropertiesSearch();
        search.setFromPrice(0);
        search.setToPrice(0);
        assertThat(search.isAdvanced()).isFalse();
    }

    @Test
    public void isAdvancedShouldReturnFalseWith() {
        val search = new AdPropertiesSearch();
        search.setFromPrice(10);
        search.setToPrice(2);
        assertThat(search.isAdvanced()).isFalse();
    }

    @Test
    public void isAdvancedShouldReturnTrue() {
        val search = new AdPropertiesSearch();
        search.setFromPrice(2);
        search.setToPrice(10);
        assertThat(search.isAdvanced()).isTrue();
    }
}