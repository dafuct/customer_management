CREATE SEQUENCE IF NOT EXISTS role_id_seq;
CREATE SEQUENCE IF NOT EXISTS client_id_seq;

create table if not exists role (
id INTEGER DEFAULT NEXTVAL('role_id_seq') primary key, name varchar(255) not null);

INSERT INTO role (name) SELECT 'admin'
WHERE NOT EXISTS (SELECT name FROM role WHERE name = 'admin');

INSERT INTO role (name) SELECT 'user'
WHERE NOT EXISTS (SELECT name FROM role WHERE name = 'user');

create table if not exists client (id INTEGER DEFAULT NEXTVAL('client_id_seq') primary key,
first_name varchar(255), last_name varchar(255),
login varchar(255), password varchar(255), email varchar(255), birthday date, role_id integer,
foreign key (role_id) references role(id));

INSERT INTO client (first_name, last_name, login, password, email, birthday, role_id)
SELECT 'dmitri', 'makarenko', 'admin', 'admin', 'dafuct@gmail.com', '1984-09-21', 1
WHERE NOT EXISTS (SELECT login FROM client WHERE login = 'admin');

INSERT INTO client (first_name, last_name, login, password, email, birthday, role_id)
SELECT 'anna', 'drug', 'user', 'user', 'anna@gmail.com', '2000-09-21', 2
WHERE NOT EXISTS (SELECT login FROM client WHERE login = 'user');


