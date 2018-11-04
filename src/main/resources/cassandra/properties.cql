CREATE TABLE cucuyo.properties (
    id bigint,
    title text,
    description text,
    images text,
    name text,
    phone text,
    price double,
    PRIMARY KEY (id, title)
) WITH CLUSTERING ORDER BY (title ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99PERCENTILE';
CREATE CUSTOM INDEX properties_index ON cucuyo.properties () USING 'com.stratio.cassandra.lucene.Index' WITH OPTIONS = {'refresh_seconds': '1', 'schema': '{fields: {description:{type:"string",case_sensitive:false},price:{type:"integer"}}}'};