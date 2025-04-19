create table if not exists  "best_sale"
(
    "id" varchar primary key,
    "dish_name" varchar,
    "quantity_sold" int,
    "total_amount"  DOUBLE PRECISION,
    "id_synchroLog" varchar not null references "synchro_log"("id")
);
