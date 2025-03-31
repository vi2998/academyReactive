CREATE TABLE clienti (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(20),
	cognome VARCHAR(20),
	comune VARCHAR(20),
	PRIMARY KEY (id)
);

CREATE TABLE clientiricevuti (
	id INT AUTO_INCREMENT NOT NULL,
	nomecompleto VARCHAR(40),
	comune VARCHAR(20),
	PRIMARY KEY (id)
);

CREATE TABLE persona(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(10),
    cognome VARCHAR(20),
    eta INT
);

CREATE TABLE personacensita(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomecompleto VARCHAR(30),
    eta INT
);
