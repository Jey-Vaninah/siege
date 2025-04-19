create table if not exists "best_sale"
(
    "id" varchar primary key,
    "dish_name" varchar not null,
    "id_dish" varchar not null,
    "quantity" int not null,
    "total_amount" double precision not null,
    "id_sale_point" varchar not null references "sale_point"("id") not null,
    "created_at" timestamp default current_timestamp not null
);
