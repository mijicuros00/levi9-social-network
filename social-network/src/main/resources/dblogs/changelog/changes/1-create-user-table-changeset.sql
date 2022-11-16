--liquibase formatted sql

--changeset postgres:1

CREATE TABLE "User" (
    "id" bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "name" varchar,
    "surname" varchar,
    "email" varchar,
    "password" varchar
);
-- rollback drop table applicationinfo