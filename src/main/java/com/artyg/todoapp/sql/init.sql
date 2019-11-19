drop table if exists todo;
drop type if exists priority;

CREATE DOMAIN priority AS TEXT
    CHECK (
            VALUE = 'high'
            OR VALUE = 'medium'
            OR VALUE = 'low'
        );

create table if not exists todo
(
    id            SERIAL primary key,
    text          varchar(128) default null,
    creation_date varchar(128) default null,
    priority      priority default 'low'
);

insert into todo (text, priority) values ('first todo', 'low');
insert into todo (text, priority) values ('second todo', 'high');
