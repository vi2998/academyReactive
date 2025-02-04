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
12) Contare il numero di incident per ciascun anno. 13) Contare il numero di incident per ciascun mese 14) Trovare l'incident più recente"""

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



def main():
    input_file = 'incident.csv'

    data_list = read_input_file(input_file)
    print("Data_list: ", data_list)

    conta_incident = conta_classe_itil_incident(data_list)
    print("Conta_incident: ", conta_incident)

    tempo_medio_gestione_incident = calcolo_tempomedio_incident(data_list)
    print("Tempo_medio_gestione_incident", tempo_medio_gestione_incident)


if __name__ == "__main__":
    main()