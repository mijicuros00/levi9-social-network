CREATE TABLE "Member" (
    "id_user" bigint UNIQUE,
    "id_group" bigint,
    PRIMARY KEY ("id_user", "id_group")
);