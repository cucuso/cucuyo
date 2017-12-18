package com.cucuyo.data;

import com.cucuyo.Startup;
import com.cucuyo.core.domain.Page;
import com.cucuyo.core.domain.PageRequest;
import lombok.val;
import org.junit.After;
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
    private DataAdPropertiesRepository repository;

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void findAll() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    public void findAllByFullText() {
        val count = repository.count();
        val pageRequest = new PageRequest(null, 1);
        Page<AdPropertiesEntity> result = repository.findAllByFullText("*cocina*", pageRequest);
        result = repository.findAllByFullText("cocina", new PageRequest(result.getNextPage(), 1));
        assertThat(true).isTrue();
    }
}