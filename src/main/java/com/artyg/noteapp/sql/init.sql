drop table if exists note;
drop type if exists priority;

CREATE DOMAIN priority AS TEXT
    CHECK (
            VALUE = 'high'
            OR VALUE = 'medium'
            OR VALUE = 'low'
        );

create table if not exists note
(
    id            SERIAL primary key,
    text          varchar(128) default null,
    creation_date varchar(128) default null,
    priority      priority default 'low'
);

insert into note (text, priority) values ('Enjoy the app', 'high');
insert into note (text, priority) values ('Hire me', 'high');
