--1.	Recuperare tutte le colonne di libri ed autori mettendo in join le due tabelle con la sintassi senza la parola Join
	SELECT * FROM libri AS l, autori AS a
	where l.autore_nome = a.nome 
	and l.autore_cognome = a.cognome; --così non duplico i record
	
--2.	Recuperare le colonne titolo, genere ed nome dell’autore con la sintassi inner join su tutte le relazioni
	select l.titolo, l.genere, a.nome from Libri as l
	inner join autori as a
	on l.autore_nome = a.nome 
	and l.autore_cognome = a.cognome;

--3.	Recuperare gli autori ed i libri riportando le colonne dei libri vuote se l’autore non ha scritto libri (con l’istruzione left outer join)
	select a.nome, a.cognome, l.titolo from autori as a 
	left join libri as l 
	on l.autore_nome = a.nome 
	and l.autore_cognome = a.cognome;

--4.	Recuperare gli autori ed i libri riportando le colonne dei libri vuote se l’autore non ha scritto libri (con l’istruzione right outer join)
	select a.nome, a.cognome, l.titolo from libri as l 
	right join autori as a 
	on l.autore_nome = a.nome 
	and l.autore_cognome = a.cognome;

--5.	Recuperare i libri e le case editrici visualizzando le colonne della casa editrice come null se non presente la relazione
--6.	Recuperare i libri e le case editrici visualizzando le colonne dei libri come null se non presente la relazione
--7.	Estrarre il conteggio dei libri per genere ed autore riportando le colonne genere, cognome e conteggio per tutti i libri scritti dopo il ’01-01-2021’
--8.	Estrarre il conteggio dei libri per genere ed autore riportando le colonne genere, cognome e conteggio per tutte le occorrenze presenti almeno due volte
