--1)	Estrarre il nome e il cognome dei clienti nati nel 1982        
		SELECT c.nome, c.cognome FROM clienti AS c
		where extract (year from c.datanascita) = 1982;	--https://learnsql.it/blog/funzioni-di-data-postgresql/
	
--2)	Estrarre una colonna di nome “Denominazione” contenente il nome, seguito da un carattere “-“, seguito dal cognome, per i soli clienti residenti nella regione Lombardia
		select concat(c.nome,'-', c.cognome) as Denominazione from clienti as c
		where c.regioneresidenza = 'Lombardia';

--3)	Qual è il numero di fatture con iva al 22%? 
		select count(*) as numeroFattureIva22
		from fatture
		where iva = 22;

--4)	Riportare il numero di fatture e la somma dei relativi importi divisi per anno di fatturazione
		select extract (year from f.datafattura) as anno_fattura, count(*), sum(f.importo) 
		from fatture as f
		group by anno_fattura;
                                                                        
--5)	Estrarre i prodotti attivati nel 2017 e che sono in produzione oppure in commercio 
		select * from prodotti as p
		where extract (year from p.dataattivazione) = 2017
		and (inproduzione = true or incommercio = true); 

--6)	Considerando soltanto le fatture con iva al 22 per cento, qual è il numero di fatture per ogni anno?
		select extract(year from f.datafattura) as anno_fattura, count(*) as numero_fatture
		from fatture as f
		where f.iva = 22
		group by anno_fattura;
		
--7)	In quali anni sono state registrate più di 2 fatture con tipologia ‘A’?  
		select extract (year from f.datafattura) as anno_fatture_a
		from fatture as f 
		where f.tipologia = 'A'
		group by anno_fatture_a
		having count(*) > 2; 

--8)	Riportare l’elenco delle fatture (numero, importo, iva e data) con in aggiunta il nome del fornitore  
		select f.numerofattura , f.importo, f.iva, f.datafattura, fo.denominazione as nome_fornitore
		from fatture as f, fornitori as fo;

--9)	--Estrarre il totale degli importi delle fatture divisi per residenza dei clienti
		select sum(f.importo) as somma_tot_fatture, c.regioneresidenza as regioneResidenza
		from fatture as f, clienti as c
		group by regioneResidenza;
		
--10)	Estrarre il numero dei clienti nati nel 1980 che hanno almeno una fattura superiore a 50 euro 
		select count(*) as numero_clienti
		from clienti as c, fatture as f
		where f.idcliente = c.numerocliente 
		and extract (year from c.datanascita) = 1980 and f.importo > 50;
                                                                                                                                                       