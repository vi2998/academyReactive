import re
"""1) Leggere il file CSV: Scrivi una funzione per leggere il file CSV e memorizzare i dati in una lista di dizionari.
2) Contare il numero totale di incident con classe ITIL = "INCIDENT".
3) Calcolare il tempo medio di gestione per tutti gli incident.
4) Contare il numero di incident che non rispettano il SLA (NO e N.A.).
5) Contare la percentuale degli incident che rispettano il SLA.
6) Calcolare il tempo totale di gestione per tutti gli incident.
7) Contare il numero di incident per ciascuna priorità di apertura.
8) Calcolare il tempo medio di presa in carico per tutti gli incident.
9) Trovare l'incident con il tempo totale di gestione più lungo.
10) Calcolare il numero medio di passaggi per tutti gli incident.
11) Verificare la formattazione della data: Scrivi una funzione per verificare che tutte le date nel dataset siano nel formato YYYY-MM-DD.
12) Contare il numero di incident per ciascun anno. 
13) Contare il numero di incident per ciascun mese 
14) Trovare l'incident più recente"""

# 1) Leggere il file CSV: Scrivi una funzione per leggere il file CSV e memorizzare i dati in una lista di dizionari
def read_input_file(file_path):
    """Reads a text file and converts each line to a dictionary."""
    data_list = []
    with open(file_path, 'r') as file:
        lines = file.readlines()

        # Get header keys from the first line
        keys = lines[0].strip().split(';')

        # Process each remaining line into a dictionary
        for line in lines[1:]:
            values = line.strip().split(';')
            entry = dict(zip(keys, values)) # entry -> dizionario di chiavi-valore
            data_list.append(entry)
    return data_list

# 2) Contare il numero totale di incident con classe ITIL = "INCIDENT"
def conta_classe_itil_incident(data_list):
    count = 0
    for entry in data_list:
        if (entry.get("CLASSE ITIL") == "INCIDENT"):
            count += 1
    return count

# 3) Calcolare il tempo medio di gestione per tutti gli incident.
def calcolo_tempomedio_incident(data_list):
    tempo_tot = 0
    count = 0
    for entry in data_list:
        tempo_gestione = float(entry["Tempo medio di gestione"])
        tempo_tot += tempo_gestione
        count += 1
                
    if count > 0:
     media = tempo_tot / count
    else: 
        media = 0
    return media

# 4) Contare il numero di incident che non rispettano il SLA (NO e N.A.).
def conta_non_rispettano_sla(data_list):
    count = 0
    for entry in data_list:
        if entry["Rispetto SLA Competenza Gruppo"] in ["NO", "N.A."]:
            count += 1
    return int(count)

# 5) Contare la percentuale degli incident che rispettano il SLA.
def conta_percentuale_sla(data_list):
    count = 0
    for entry in data_list:
        if entry["Rispetto SLA Competenza Gruppo"] in ["SI"]:
            count += 1
    percentuale = (count/len(data_list)) * 100
    return (percentuale)

# 6) Calcolare il tempo totale di gestione per tutti gli incident.
def tempo_totale(data_list):
    tempo_totale = 0
    for entry in data_list:
        tempo_totale += float(entry["Tempo totale di gestione incident"])
    return tempo_totale

# 7) Contare il numero di incident per ciascuna priorità di apertura.
def numero_incident_priorita(data_list):
    count = {}
    for entry in data_list:
        key = entry ["PRIORITA DI APERTURA INCIDENT"]
        count[key] = count.get(key, 0) + 1
    return count

# 8) Calcolare il tempo medio di presa in carico per tutti gli incident.
def tempo_medio_presa_incident(data_list):
    tempo_tot = 0
    count = 0
    for entry in data_list:
        if (entry.get("CLASSE ITIL") == "INCIDENT"):
         tempo_gestione = float(entry["Tempo medio di presa in carico"])
         tempo_tot += tempo_gestione
         count += 1
    if count > 0:
     media = tempo_tot / count
    else: 
        media = 0
    return media

# 9) Trovare l'incident con il tempo totale di gestione più lungo.
def incidenti_con_tempo_max(data_list):
    max_time = 0
    for entry in data_list:
        tempo = float(entry["Tempo totale di gestione incident"])
        if tempo > max_time:
            max_time = tempo
    return max_time

# 10) Calcolare il numero medio di passaggi per tutti gli incident.
def passaggi_medi_incident(data_list):
    media = 0
    contatore = 0
    conta_passaggi = 0
    for entry in data_list:
       if (entry.get("CLASSE ITIL") == "INCIDENT"):
           num_passaggi = int(entry["Num. Passaggi"])
           conta_passaggi += num_passaggi
           contatore += 1
    media = conta_passaggi / contatore
    return media

# 11) Verificare la formattazione della data: Scrivi una funzione per verificare che tutte le date nel dataset siano nel formato YYYY-MM-DD.
def correct_format(list):
    format_error = 0
    pattern = re.compile(r'\d{2}/\d{2}/\d{4}')
    for dict in list:
        if not pattern.match(str(dict["Data creazione"])):
            format_error += 1
    return format_error

#12) Contare il numero di incident per ciascun anno. 
def conta_incident_anno(data_list):
    anno_count = {}
    for entry in data_list:
            if (entry.get("CLASSE ITIL") == "INCIDENT"):
             data_creazione = entry["Data creazione"]
             giorno, mese, anno = data_creazione.split('/')
             if anno in anno_count:
                anno_count[anno] += 1
            else:
                anno_count[anno] = 1
    return anno_count


#13) Contare il numero di incident per ciascun mese 
def conta_incident_mese(data_list):
    mese_count = {}
    for entry in data_list:
            if (entry.get("CLASSE ITIL") == "INCIDENT"):
             data_creazione = entry["Data creazione"]
             giorno, mese, anno = data_creazione.split('/')
             if mese in mese_count:
                mese_count[mese] += 1
             else:
                mese_count[mese] = 1
    return mese_count

#14) Trovare l'incident più recente
def conta_incident_mese(data_list):
    mese_count = {}
    for entry in data_list:
            data_creazione = entry["Data creazione"]
            giorno, mese, anno = data_creazione.split('/')
            if mese in mese_count:
                mese_count[mese] += 1
            else:
                mese_count[mese] = 1
    return mese_count

def main():
    input_file = 'incident.csv'

    #1
    data_list = read_input_file(input_file)
    print("Data_list: ", data_list)

    #2
    conta_incident = conta_classe_itil_incident(data_list)
    print("Conta_incident: ", conta_incident)

    #3
    tempo_medio_gestione_incident = calcolo_tempomedio_incident(data_list)
    print("Tempo_medio_gestione_incident", tempo_medio_gestione_incident)

    #4
    conta_non_sla_rispettato = conta_non_rispettano_sla(data_list)
    print("Conta_non_sla_rispettato", conta_non_sla_rispettato)

    #5
    conta_sla_rispettato = conta_percentuale_sla(data_list)
    print("Conta_sla_rispettato", conta_sla_rispettato)

    #6
    tempo_totale_gestione = tempo_totale(data_list)
    print("Tempo_totale_gestione", tempo_totale_gestione)

    #7
    conta_priorita = numero_incident_priorita(data_list)
    print("Conta_priorita", conta_priorita)

    #8
    tempo_medio_presa_in_carico = tempo_medio_presa_incident(data_list)
    print("Tempo_medio_presa_in_carico_incident", tempo_medio_presa_in_carico)

    #9
    tempo_max_incident = incidenti_con_tempo_max(data_list)
    print("tempo_max_incident", tempo_max_incident)

    #10
    passaggi_incident_media = passaggi_medi_incident(data_list)
    print("Passaggi_incident_media", passaggi_incident_media)

    #11
    conta_formato_data_corretta = correct_format(data_list)
    print("Conta_formato_data_corretta", conta_formato_data_corretta)

    # 12
    conta_incidente_xanno = conta_incident_anno (data_list)
    print("Conta_incidente_xanno", conta_incidente_xanno)

    # 13
    conta_incidente_mese = conta_incident_mese (data_list)
    print("Conta_incidente_mese", conta_incidente_mese)
    # 14)


if __name__ == "__main__":
    main()