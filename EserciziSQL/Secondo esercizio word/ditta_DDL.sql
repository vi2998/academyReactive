drop table if exists clienti CASCADE;
drop table if exists fattrure CASCADE;
drop table if exists prodotti CASCADE;
drop table if exists fornitori CASCADE;

CREATE TABLE clienti(
	numeroCliente integer PRIMARY KEY,
	nome varchar(100),
	cognome varchar(100),
	dataNascita date,
	regioneResidenza varchar(100)
);

CREATE TABLE fatture(
	numeroFattura integer PRIMARY KEY,
	tipologia varchar(100),
	importo integer,
	iva integer,
	idCliente integer,
	dataFattura date,
	numeroFornitore integer
);

CREATE TABLE prodotti(
	idProdotto integer PRIMARY KEY,
	descrizione varchar(100),
	inProduzione boolean,
	inCommercio boolean,
	dataAttivazione date,
	dataDisattivazione date
);

CREATE TABLE fornitori(
	numeroFornitore integer,
	denominazione varchar(100),
	regioneResidenza varchar(100)
);