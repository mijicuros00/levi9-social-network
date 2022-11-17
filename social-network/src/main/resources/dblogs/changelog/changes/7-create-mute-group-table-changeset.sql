CREATE TABLE "Mute_Group" (
    "isPermanent" boolean,
    "endOfMute" timestamp,
    "id_user" bigint UNIQUE,
    "id_group" bigint UNIQUE,
    PRIMARY KEY ("id_user", "id_group")
);