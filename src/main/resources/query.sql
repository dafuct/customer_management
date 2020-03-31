CREATE SEQUENCE IF NOT EXISTS role_id_seq;
CREATE SEQUENCE IF NOT EXISTS client_id_seq;

create table if not exists role (
id integer DEFAULT nextval('role_id_seq') not null primary key ,
name varchar(255) not null);

INSERT INTO role (name) VALUES ('admin');
INSERT INTO role (name) VALUES ('client');

create table if not exists client (id integer DEFAULT nextval('client_id_seq') not null primary key ,
first_name varchar(255), last_name varchar(255),
login varchar(255), password varchar(255), email varchar(255), birthday date, role_id integer,
foreign key (role_id) references role(id));

INSERT INTO client (first_name, last_name, login, password, email, birthday, role_id)
VALUES ('dmitri', 'makarenko', 'admin', 'admin', 'dafuct@gmail.com', '1984-09-21', 1);

INSERT INTO client (first_name, last_name, login, password, email, birthday, role_id)
VALUES ('anna', 'drug', 'client', 'client', 'anna@gmail.com', '2000-09-21', 2);