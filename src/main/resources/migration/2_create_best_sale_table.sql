create table if not exists  "bestSale"
(
    "id" varchar primary key,
    "dishName" varchar
    "quantitySold" double,
    "totalAmount" double,
    "idSynchroLog" varchar not null references "sychroLog"("id")
);
