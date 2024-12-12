drop table if exists Librerie;
drop table if exists Libri;
drop table if exists CaseEditrici;
drop table if exists Libri_Librerie;

--La tabella librerie ha i campi nome e citta entrambi chiave primaria e deve essere definita con una sola istruzione SQL.
CREATE TABLE Librerie(nome varchar(100), 
	citta varchar(100), 
	primary key(nome, citta));

--La tabella libri ha un id autoincrementale PK, un titolo ed 
--i riferimenti al genere ed all’autore (entrambi not null). Inoltre, sono presenti i campi, senza vincoli,: 
--date per memorizzare la data di pubblicazione del libro 
--int campo idCasaEditrice.
create table Libri(
	    id serial primary key,
	    titolo varchar(100),
	    genere varchar(100) not null,
	    autore varchar(100) not null,
	    dataPubblicazione date,
	    idCasaEditrice integer
);

--La tabella caseEditrici con le colonne id e casa.
create table CaseEditrici(
	id serial primary key,
	casa varchar(100)
);

--Una tabella per relazionare i libri e le librerie con tutti i campi nella PK e 2 FK verso libri e libreria. 
--Definire il tutto con una sola istruzione SQL.
create table Libri_Librerie(
	id_Libri integer,
	nome_Libreria varchar(100),
	citta_libreria varchar(100),
	primary key(id_Libri, nome_Libreria, citta_libreria)
	);
	
	ALTER TABLE Libri_Librerie ADD CONSTRAINT FK_Libri_Librerie FOREIGN KEY (id_Libri) REFERENCES Libri (id);
	ALTER TABLE Libri_Librerie ADD CONSTRAINT FK_att_Librerie FOREIGN KEY (nome_Libreria, citta_libreria) REFERENCES Librerie (nome, citta);
