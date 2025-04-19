do
$$
begin
        if not exists (select from pg_type where typname  = 'duration') THEN
create type "duration" as enum ('SECONDS', 'MINUTES', 'HOURS');
end if;
end
$$;

create table if not exists  "processing_time"
(
    "id" varchar primary key,
    "dish_name" varchar,
    "preparation_duration" double precision,
    "duration_unit" duration,
    "idSynchroLog" varchar not null references "synchro_log"("id")
    );
