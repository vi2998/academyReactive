-- inserimento Autori
insert into Autori (nome, cognome) values ('Giuseppe', 'Verdi');
insert into Autori (nome, cognome) values ('Italo', 'Calvino');
insert into Autori (nome, cognome) values ('Dante', 'Alighieri');
insert into Autori (nome, cognome) values ('Leonardo', 'Da Vinci');
insert into Autori (nome, cognome) values ('Luigi', 'Pirandello');
insert into Autori (nome, cognome) values ('Vito', 'Pitrelli'); -- usato per query se l’autore non ha scritto libri 

--inserimento Generi
insert into Generi (nome_genere) values ('Classico');
insert into Generi (nome_genere) values ('Fantasy');
insert into Generi (nome_genere) values ('Poetico');
insert into Generi (nome_genere) values ('Storico');
insert into Generi (nome_genere) values ('Giallo');

--inserimento CaseEditrici
insert into CaseEditrici (casa) values ('Mondadori');
insert into CaseEditrici (casa) values ('Einaudi');
insert into CaseEditrici (casa) values ('Feltrinelli');
insert into CaseEditrici (casa) values ('Rizzoli');
insert into CaseEditrici (casa) values ('Garzanti');
insert into CaseEditrici (casa) values ('Senza Libro'); -- usato per query casaeditrice senza libro

--inserimento Libri
insert into Libri (titolo, genere, autore_nome, autore_cognome, dataPubblicazione, idCasaEditrice) values
('Nessun Dorma', 'Classico', 'Giuseppe', 'Verdi', '1887-01-01', 1);

insert into Libri (titolo, genere, autore_nome, autore_cognome, dataPubblicazione, idCasaEditrice) values
('Le Città Invisibili', 'Fantasy', 'Italo', 'Calvino', '1972-06-12', 2);

insert into Libri (titolo, genere, autore_nome, autore_cognome, dataPubblicazione, idCasaEditrice) values
('Divina Commedia', 'Poetico', 'Dante', 'Alighieri', '1320-01-01', 3);

insert into Libri (titolo, genere, autore_nome, autore_cognome, dataPubblicazione, idCasaEditrice) values
('Codice da Vinci', 'Storico', 'Leonardo', 'Da Vinci', '2003-03-01', 4);

insert into Libri (titolo, genere, autore_nome, autore_cognome, dataPubblicazione, idCasaEditrice) values
('Sei Personaggi in Cerca d Autore', 'Giallo', 'Luigi', 'Pirandello', '1921-10-20', 5);

insert into Libri (titolo, genere, autore_nome, autore_cognome, dataPubblicazione, idCasaEditrice)
values ('Libro Senza Casa Editrice', 'Giallo', 'Luigi', 'Pirandello', '2024-12-01', null); -- usato per query libro senza casa editrice 

--inserimento Librerie
insert into Librerie (nome, citta) values ('Libreria Mondadori', 'Torino');
insert into Librerie (nome, citta) values ('Libreria Feltrinelli', 'Milano');
insert into Librerie (nome, citta) values ('Libreria Einaudi', 'Roma');
insert into Librerie (nome, citta) values ('Libreria Rizzoli', 'Napoli');
insert into Librerie (nome, citta) values ('Libreria Garzanti', 'Torino');

--inserimento Libri_Librerie
insert into Libri_Librerie (id_Libri, nome_Libreria, citta_libreria) values (1, 'Libreria Mondadori', 'Torino');
insert into Libri_Librerie (id_Libri, nome_Libreria, citta_libreria) values (2, 'Libreria Feltrinelli', 'Milano');
insert into Libri_Librerie (id_Libri, nome_Libreria, citta_libreria) values (3, 'Libreria Einaudi', 'Roma');
insert into Libri_Librerie (id_Libri, nome_Libreria, citta_libreria) values (4, 'Libreria Rizzoli', 'Napoli');
insert into Libri_Librerie (id_Libri, nome_Libreria, citta_libreria) values (5, 'Libreria Garzanti', 'Torino');
insert into Libri_Librerie (id_Libri, nome_Libreria, citta_libreria) values (6, 'Libreria Garzanti', 'Torino');
