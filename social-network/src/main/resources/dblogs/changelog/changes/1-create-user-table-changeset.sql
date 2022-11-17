--liquibase formatted sql

--changeset postgres:1

CREATE TABLE "user" (
    "id" bigint generated always as identity primary key,
    "name" varchar,
    "surname" varchar,
    "email" varchar,
    "password" varchar
);
-- rollback drop table applicationinfo