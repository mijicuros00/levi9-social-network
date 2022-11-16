CREATE TABLE "Friend" (
    "id_user" bigint,
    "id_friend" bigint,
    PRIMARY KEY ("id_user", "id_friend")
);