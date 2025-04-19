create table if not exists  "synchro_log"
(
    "id" varchar primary key,
    "updated_at" timestamp not null,
    "id_sale_point" varchar references "sale_point"("id")
);
