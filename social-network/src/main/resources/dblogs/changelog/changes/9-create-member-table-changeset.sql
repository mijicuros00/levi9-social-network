CREATE TABLE "member" (
    "id_user" bigint  UNIQUE ,
    "id_group" bigint  UNIQUE  ,
    UNIQUE ("id_user", "id_group"),
    PRIMARY KEY ("id_user", "id_group")
);