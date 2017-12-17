package com.cucuyo.data;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface DataAdPropertiesRepository extends CassandraRepository<AdPropertiesEntity>, PagingDataAdPropertiesRepository {

}
