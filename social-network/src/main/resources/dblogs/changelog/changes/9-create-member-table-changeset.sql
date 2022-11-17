CREATE TABLE "member" (
    "id_user" bigint UNIQUE,
    "id_group" bigint,
    PRIMARY KEY ("id_user", "id_group")
);