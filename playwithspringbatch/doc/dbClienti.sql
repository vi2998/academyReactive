CREATE TABLE clienti (
	id serial4 NOT NULL,
	nome varchar(20) NULL,
	cognome varchar(20) NULL,
	comune varchar(20) NULL,
	CONSTRAINT clienti_pkey PRIMARY KEY (id)
);

CREATE TABLE clientiricevuti (
	id serial4 NOT NULL,
	nomecompleto varchar(40) NULL,
	comune varchar(20) NULL,
	CONSTRAINT clientiricevuti_pkey PRIMARY KEY (id)
);

CREATE TABLE persona(
    id serial primary key,
    nome varchar(10),
    cognome varchar(20),
    eta int
);


insert into persona (nome, cognome, eta) values ('MARIO', 'BIANCHI', 15);
insert into persona (nome, cognome, eta) values ('ANNA', 'VERDI', 23);
insert into persona (nome, cognome, eta) values ('SERGIO', 'GIALLI', 8);
insert into persona (nome, cognome, eta) values ('ALDO', 'ROSSI', 75);
insert into persona (nome, cognome, eta) values ('MARIO', 'FERRERO', 23);


CREATE TABLE personacensita(
    id serial primary key,
    nomecompleto varchar(30),
    eta int
);

CREATE TABLE persona (
    id serial primary key,
    nome varchar(10),
    cognome varchar(20),
    eta int
);