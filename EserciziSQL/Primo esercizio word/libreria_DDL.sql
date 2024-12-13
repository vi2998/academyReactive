drop table if exists Autori cascade;
drop table if exists Generi cascade;
drop table if exists Libri_Librerie cascade;
drop table if exists Librerie cascade;
drop table if exists Libri cascade;
drop table if exists CaseEditrici cascade;


--La tabella librerie ha i campi nome e citta entrambi chiave primaria e deve essere definita con una sola istruzione SQL.
CREATE TABLE Librerie(
	nome varchar(100), 
	citta varchar(100), 
	primary key(nome, citta)
);

--La tabella libri ha un id autoincrementale PK, un titolo ed 
--i riferimenti al genere ed all’autore (entrambi not null). Inoltre, sono presenti i campi, senza vincoli,: 
--date per memorizzare la data di pubblicazione del libro 
--int campo idCasaEditrice.
create table Libri(
	id serial primary key,
	titolo varchar(100),
	genere varchar(100) not null,
	autore_nome varchar(100) not null,
	autore_cognome varchar(100) not null,
	dataPubblicazione date,
	idCasaEditrice integer
);

--La tabella caseEditrici con le colonne id e casa.
create table CaseEditrici(
	id serial,
	casa varchar(100)
);

--Una tabella per relazionare i libri e le librerie con tutti i campi nella PK e 2 FK verso libri e libreria. 
--Definire il tutto con una sola istruzione SQL.
create table Libri_Librerie(
	id_Libri integer,
	nome_Libreria varchar(100),	--sono costretto a mettere sia nome_librerira che citta_libreria perchè entrambe sono PK di Librerie
	citta_libreria varchar(100),
	primary key(id_Libri, nome_Libreria, citta_libreria)
	);
	

--Una tabella autori con 2 colonne nome e cognome dell’autore. 
-- La PK crearla contestualmente alla creazione della tabella. 
--Aggiungere una Alter per definire una chiave univoca su nome e cognome. ---> non fatto perchè con PK sono unique e not null
create table Autori(
	nome varchar(100),	
	cognome varchar(100),
	primary key(nome, cognome)
);

--Una tabella generi con un campo testo di nome genere.
create table Generi(
	nome_genere varchar(100) primary key
);

--Creare la vista librerie_libri_torino che resituisce nomelibreria e tutti i campi di libri per le librerie di Torino
create view librerie_libri_torino as
select ll.nome_libreria, l.* from Libri_Librerie  as ll
join Libri as l
on ll.id_Libri = l.id
where ll.citta_libreria = 'Torino';

ALTER TABLE Libri_Librerie ADD CONSTRAINT FK_Libri_Librerie FOREIGN KEY (id_Libri) REFERENCES Libri (id);
ALTER TABLE Libri_Librerie ADD CONSTRAINT FK_att_Librerie FOREIGN KEY (nome_Libreria, citta_libreria) REFERENCES Librerie (nome, citta);

--Definire in coda allo script DDL l’alter della tabella libri per memorizzare 
--le FK verso generi ed autori.
alter table Libri add constraint FK_Generi foreign key (genere) references Generi (nome_genere); 
alter table Libri add constraint FK_Autore_nome_cognome foreign key (autore_nome, autore_cognome) references Autori (nome, cognome); -- faccio solo un FK con due campi dato che la PK ha due campi