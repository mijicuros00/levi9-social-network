ALTER TABLE "group" ADD FOREIGN KEY ("id_admin") REFERENCES "user" ("id");

ALTER TABLE "post" ADD FOREIGN KEY ("id_user") REFERENCES "user" ("id");

ALTER TABLE "post" ADD FOREIGN KEY ("id_group") REFERENCES "group" ("id");

ALTER TABLE "item" ADD FOREIGN KEY ("id_post") REFERENCES "post" ("id");

ALTER TABLE "comment" ADD FOREIGN KEY ("id_replied_to") REFERENCES "comment" ("id");

ALTER TABLE "comment" ADD FOREIGN KEY ("id_user") REFERENCES "user" ("id");

ALTER TABLE "comment" ADD FOREIGN KEY ("id_post") REFERENCES "post" ("id");

ALTER TABLE "event" ADD FOREIGN KEY ("id_user") REFERENCES "user" ("id");

ALTER TABLE "event" ADD FOREIGN KEY ("id_location") REFERENCES "address" ("id");

ALTER TABLE "event" ADD FOREIGN KEY ("id_group") REFERENCES "group" ("id");

ALTER TABLE "member" ADD FOREIGN KEY ("id_user") REFERENCES "user" ("id");

ALTER TABLE "member" ADD FOREIGN KEY ("id_group") REFERENCES "group" ("id");

ALTER TABLE "request" ADD FOREIGN KEY ("id_user") REFERENCES "user" ("id");

ALTER TABLE "request" ADD FOREIGN KEY ("id_group") REFERENCES "group" ("id");

ALTER TABLE "member" ADD FOREIGN KEY ("id_user") REFERENCES "user" ("id");

ALTER TABLE "member" ADD FOREIGN KEY ("id_group") REFERENCES "group" ("id");

ALTER TABLE "friend" ADD FOREIGN KEY ("id_user") REFERENCES "user" ("id");

ALTER TABLE "friend" ADD FOREIGN KEY ("id_friend") REFERENCES "user" ("id");

ALTER TABLE "member_event" ADD FOREIGN KEY ("id_user","id_group") REFERENCES "member" ("id_user","id_group");

ALTER TABLE "member_event" ADD FOREIGN KEY ("id_event") REFERENCES "event" ("id");

ALTER TABLE "user_roles" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

-- ALTER TABLE "user_roles" ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");

-- ALTER TABLE "user_roles" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");
