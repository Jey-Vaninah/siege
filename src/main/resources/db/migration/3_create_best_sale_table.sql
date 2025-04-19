create table if not exists "best_sale"
(
    "id" varchar primary key,
    "dish_name" varchar not null,
    "id_dish" varchar not null,
    "quantity" int not null,
    "total_amount" double precision,
    "id_sale_point" varchar not null references "sale_point"("id")
);
