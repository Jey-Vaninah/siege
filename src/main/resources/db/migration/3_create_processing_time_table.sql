do
$$
begin
        if not exists (select from pg_type where typname  = 'duration_type') THEN
            create type "duration_type" as enum ('SECONDS', 'MINUTES', 'HOURS');
        end if;
end
$$;

create table if not exists  "processing_time"
(
    "id" varchar primary key,
    "dish_name" varchar not null,
    "preparation_duration" double precision not null,
    "duration_unit" "duration_type" not null,
    "id_sale_point" varchar not null references "sale_point"("id")
);
