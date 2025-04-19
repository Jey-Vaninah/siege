create table if not exists  "sale_point"
(
    "id" varchar primary key,
    "name" varchar not null unique,
    "api_url" varchar not null,
    "api_key" varchar not null
);
