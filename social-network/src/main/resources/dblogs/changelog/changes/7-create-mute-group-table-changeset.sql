CREATE TABLE "mute_group" (
    "is_permanent" boolean,
    "end_of_mute" timestamp,
    "id_user" bigint UNIQUE,
    "id_group" bigint UNIQUE,
    PRIMARY KEY ("id_user", "id_group")
);