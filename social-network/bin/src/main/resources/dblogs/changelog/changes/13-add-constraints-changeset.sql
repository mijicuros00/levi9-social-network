ALTER TABLE "Group" ADD FOREIGN KEY ("id_admin") REFERENCES "User" ("id");

ALTER TABLE "Post" ADD FOREIGN KEY ("id_user") REFERENCES "User" ("id");

ALTER TABLE "Post" ADD FOREIGN KEY ("id_group") REFERENCES "Group" ("id");

ALTER TABLE "Item" ADD FOREIGN KEY ("id_post") REFERENCES "Post" ("id");

ALTER TABLE "Comment" ADD FOREIGN KEY ("id_replied_to") REFERENCES "Comment" ("id");

ALTER TABLE "Comment" ADD FOREIGN KEY ("id_user") REFERENCES "User" ("id");

ALTER TABLE "Comment" ADD FOREIGN KEY ("id_post") REFERENCES "Post" ("id");

ALTER TABLE "Event" ADD FOREIGN KEY ("id_user") REFERENCES "User" ("id");

ALTER TABLE "Event" ADD FOREIGN KEY ("id_location") REFERENCES "Address" ("id");

ALTER TABLE "Event" ADD FOREIGN KEY ("id_group") REFERENCES "Group" ("id");

ALTER TABLE "Member" ADD FOREIGN KEY ("id_user") REFERENCES "Mute_Group" ("id_user");

ALTER TABLE "Member" ADD FOREIGN KEY ("id_group") REFERENCES "Mute_Group" ("id_group");

ALTER TABLE "Request" ADD FOREIGN KEY ("id_user") REFERENCES "User" ("id");

ALTER TABLE "Request" ADD FOREIGN KEY ("id_group") REFERENCES "Group" ("id");

ALTER TABLE "Member" ADD FOREIGN KEY ("id_user") REFERENCES "User" ("id");

ALTER TABLE "Member" ADD FOREIGN KEY ("id_group") REFERENCES "Group" ("id");

ALTER TABLE "Friend" ADD FOREIGN KEY ("id_user") REFERENCES "User" ("id");

ALTER TABLE "Friend" ADD FOREIGN KEY ("id_friend") REFERENCES "User" ("id");

ALTER TABLE "Member_Event" ADD FOREIGN KEY ("id_user") REFERENCES "Member" ("id_user");

ALTER TABLE "Member_Event" ADD FOREIGN KEY ("id_event") REFERENCES "Event" ("id");