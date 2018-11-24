CREATE TABLE cucuyo.properties (
    id bigint,
    address text,
    description text,
    images List<text>,
    name text,
    phone text,
    price double,
    email text,
    latitude float,
    longitude float,
    PRIMARY KEY (id)
);
 
CREATE CUSTOM INDEX properties_index ON cucuyo.properties () USING 'com.stratio.cassandra.lucene.Index' WITH OPTIONS = {'refresh_seconds': '10', 'schema': '{fields: {description:{type:"string",case_sensitive:false},price:{type:"integer"}, location: {type: "geo_point", latitude: "latitude", longitude: "longitude"}}}'};