alter table _category
    rename column name to kk_name;

alter table _category
    alter column kk_name set not null;

alter table _category
    add ru_name varchar(255) not null;

alter table _category
    add en_name varchar(255) not null;
