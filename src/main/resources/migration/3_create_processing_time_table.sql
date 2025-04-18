do
$$
begin
        if not exists (select from pg_type where typname  = 'duration') THEN
create type "duration" as enum ('SECONDES', 'MINUTES', 'HOURS');
end if;
end
$$;

create table if not exists  "processingTime"
(
    "id" varchar primary key,
    "dishName" varchar,
    "preparationDuration" double,
    "durationUnit" duration,
    "idSynchroLog" varchar not null references "sychroLog"("id")
    );
