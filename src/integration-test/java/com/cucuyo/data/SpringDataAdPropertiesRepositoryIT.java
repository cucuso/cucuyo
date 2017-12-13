package com.cucuyo.data;

import com.cucuyo.Startup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class SpringDataAdPropertiesRepositoryIT {

    @Autowired
    private SpringDataAdPropertiesRepository repository;

    @Test
    public void findAllByTitleStartingWith() {
        assertThat(repository.findAll()).isNotEmpty();
    }
}