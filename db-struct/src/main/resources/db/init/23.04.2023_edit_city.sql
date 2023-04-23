alter table _city
    rename column name to kk_name;

alter table _city
    add ru_name varchar(255) not null;

alter table _city
    add en_name varchar(255) not null;
