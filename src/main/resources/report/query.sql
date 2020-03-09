CREATE SEQUENCE IF NOT EXISTS role_id_seq;
CREATE SEQUENCE IF NOT EXISTS client_id_seq;

create table if not exists role
(
id integer DEFAULT nextval('role_id_seq') not null,
name varchar(255) not null,
primary key (id)
);

ALTER SEQUENCE role_id_seq OWNED BY role.id;

INSERT INTO role (name) SELECT 'admin'
WHERE NOT EXISTS (SELECT name FROM role WHERE name = 'admin');

INSERT INTO role (name) VALUES ('admin');
INSERT INTO role (name) VALUES ('client');

INSERT INTO role (name) SELECT 'client'
WHERE NOT EXISTS (SELECT name FROM role WHERE name = 'client');

create table if not exists client (id serial primary key,
first_name varchar(255), last_name varchar(255),
login varchar(255), password varchar(255), email varchar(255), birthday date, role_id integer,
foreign key (role_id) references role(id));

INSERT INTO client (first_name, last_name, login, password, email, birthday, role_id)
SELECT 'dmitri', 'makarenko', 'admin', 'admin', 'dafuct@gmail.com', '1984-09-21', 1
WHERE NOT EXISTS (SELECT login FROM client WHERE login = 'admin');

INSERT INTO client (first_name, last_name, login, password, email, birthday, role_id)
SELECT 'anna', 'drug', 'client', 'client', 'anna@gmail.com', '2000-09-21', 2
WHERE NOT EXISTS (SELECT login FROM client WHERE login = 'client');

INSERT INTO client (first_name, last_name, login, password, email, birthday, role_id)
VALUES ('anna', 'drug', 'client', 'client', 'anna@gmail.com', '2000-09-21', 2);