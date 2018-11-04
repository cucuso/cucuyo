Cucuyo Service
===============

#API for cucuyo real estate app.

##Getting Started

1. Get DB setup

make sure docker is running locally and then run

`docker run --name cucuyo-db -d -p 9042:9042 cassandra:3.0`

2. Add com.stratio.cassandra.lucene.Index to cassandra

Checkout project and build specific version for current cassandra

copy jar from `cp plugin/target/cassandra-lucene-index-plugin-*.jar` to `/usr/share/cassandra/lib`
**Make sure you dont copy wrong jar- i was stuck for hours I had put wrong jar**

Now you can connect locally to db and run (this should be automated)

`create_keyspace.cql`

`properties.cql` 

`users.cql`

copy over data csv and run:

`copy cucuyo.properties FROM 'data.csv';`


##TODO
1. show pictures on top of map
2. build Dockerfile for cassandra that includes the lucene plugin
3. figure out how to handle pics
