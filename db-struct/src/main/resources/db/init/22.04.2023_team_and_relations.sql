create table public._team
(
    id          bigint generated by default as identity primary key,
    name        varchar(255) unique,
    created_at  timestamp default CURRENT_TIMESTAMP,
    admin_id    bigint  not null
        constraint _admin_id_fk
            references public._users,
    category_id integer not null
        constraint _category_id_fk
            references public._category
);

create table public._team_tournament
(
    id            bigint generated by default as identity primary key,
    team_id       bigint  not null
        constraint _team_id_fk
            references public._team,
    tournament_id integer not null
        constraint _tournament_id_fk
            references public._tournament
);

create table public._user_team
(
    id      bigint generated by default as identity primary key,
    user_id bigint not null
        constraint _user_id_fk
            references public._users,
    team_id bigint not null
        constraint _team_id_fk
            references public._team
);