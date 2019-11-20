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
    text          text     default null CHECK (char_length(text) <= 500),
    creation_date text     default null CHECK (char_length(creation_date) <= 30),
    priority      priority default 'low'
);

insert into note (creation_date, text, priority)
values ('2019-11-20 10:00:00.000+07', 'Enjoy the app', 'high');
insert into note (creation_date, text, priority)
values ('2019-11-20 10:00:00.000+07', 'Hire me', 'high');