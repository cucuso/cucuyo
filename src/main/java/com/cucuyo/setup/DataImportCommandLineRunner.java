package com.cucuyo.setup;

import com.cucuyo.core.AdProperties;
import com.cucuyo.data.AdPropertiesEntity;
import com.cucuyo.data.AdPropertiesKey;
import com.cucuyo.data.SpringDataAdPropertiesRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class DataImportCommandLineRunner implements CommandLineRunner {

    private static final String DEFAULT_FILE = "cassandra/import-data.csv";

    private final SpringDataAdPropertiesRepository repository;

    @SneakyThrows
    private static <T> List<T> loadObjectList(Class<T> type, String fileName) {
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper mapper = new CsvMapper();
        File file = new ClassPathResource(fileName).getFile();
        MappingIterator<T> readValues = mapper.readerFor(type).with(bootstrapSchema).readValues(file);
        return readValues.readAll();
    }

    @Override
    public void run(String... strings) {
        log.info("Data import process started.");
        val data = loadObjectList(AdProperties.class, DEFAULT_FILE)
                .stream()
                .map(this::asAdPropertiesEntity)
                .collect(Collectors.toList());
        log.info("{} rows loaded from {}", data.size(), DEFAULT_FILE);
        repository.save(data);
        log.info("Data import process finished.");
    }

    private AdPropertiesEntity asAdPropertiesEntity(AdProperties adProperties) {
        val entity = new AdPropertiesEntity();
        entity.setId(new AdPropertiesKey(adProperties.getTitle()));
        entity.setPrice(adProperties.getPrice());
        entity.setDescription(adProperties.getDescription());
        return entity;
    }
}
