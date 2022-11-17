CREATE TABLE "group" (
     "id" bigint generated always as identity primary key,
     "private" boolean,
     "id_admin" bigint
);