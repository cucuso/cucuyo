CREATE TABLE cucuyo.users (
    email text PRIMARY KEY,
    password text,
    properties list<frozen<property>>
);