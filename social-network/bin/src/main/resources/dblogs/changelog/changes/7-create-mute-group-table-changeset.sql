CREATE TABLE "Mute_Group" (
    "id" bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "isPermanent" boolean,
    "endOfMute" timestamp,
    "id_user" bigint UNIQUE,
    "id_group" bigint UNIQUE
);