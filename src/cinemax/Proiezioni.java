package cinemax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe che gestisce le proiezioni all'interno del sistema Cinemax
 * @author ...
 * @version 1.2.1
 */

public class Proiezioni {

    // CAMPI
    private String dataOra;
    private String titolo;
    private String genere;
    private String regista;
    private int anno;
    private int durata;
    private int etaMinima;
    private double prezzo;
    private static final int capienzaSala = 200;
    private int postiPrenotati = 0;

    // COSTRUTTORI
    /**
     * Costruisce un oggetto che rappresenta tutte le Proiezioni inizializzando
     * tutti i campi di informazione relativi al film
     * @param dataOra La data e l'ora del film
     * @param titolo Il titolo del film
     * @param genere Il genere del film
     * @param regista Il nome del regista
     * @param anno L'anno di pubblicazione
     * @param durata La durata in minuti
     * @param etaMinima L'età minima per la visione
     * @param prezzo Il costo del biglietto
     */
    public Proiezioni(String dataOra, String titolo, String genere, String regista, int anno, int durata, int etaMinima, double prezzo) {
        this.dataOra = dataOra;
        this.titolo = titolo;
        this.genere = genere;
        this.regista = regista;
        this.anno = anno;
        this.durata = durata;
        this.etaMinima = etaMinima;
        this.prezzo = prezzo;
    }

    // METODI

    /**
     * Carica un elenco di proiezioni di un film in formato CSV
     * <p>
     *    Il metodo legge il file riga per riga, ignora l'intestazione
     *    e utilizza un espressione ottimizzata per gestire lo le virgole
     *    con lo split
     * </p>
     * <strong>Logica di elaborazione:</strong>
     * <ul>
     *     <li>Salto della riga</li>
     *     <li>Parsing dei campi con gestione dei delimitatori</li>
     *     <li>Conversione dei tipi di dato</li>
     *     <li>Gestione degli errori: se una riga non è corretta, viene saltata senza interrompere
     *     il caricamento del file</li>
     * </ul>
     * @param percorsoFile percorso proiezioni.csv
     * @return Una {@link List} di oggetti {@link Proiezioni} caricati correttamente
     * @throws IOException Se si verifica un errore di accesso al file
     */
    public static List<Proiezioni> caricaDaCSV(String percorsoFile) {
        List<Proiezioni> listaP = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String riga;
            br.readLine();

            while ((riga = br.readLine()) != null) {
                try {
                    if (riga.trim().isEmpty()) continue;
                    String[] d = riga.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    
                    if (d.length >= 8) {
                        for (int i = 0; i < d.length; i++) {
                            d[i] = d[i].replace("^\"|\"$", "").trim();
                        }

                        Proiezioni p = new Proiezioni(
                                d[0],
                                d[1],
                                d[2],
                                d[3],
                                Integer.parseInt(d[4].trim()),
                                Integer.parseInt(d[5].trim()),
                                Integer.parseInt(d[6].trim()),
                                Double.parseDouble(d[7].trim().replace(",", "."))
                        );

                        listaP.add(p);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Errore riga ignorata: " + riga + " -> " + e.getMessage());
                }
            }
        } catch (IOException e){
            System.out.println("errore nella lettura del file" + e.getMessage());
        }
        return listaP;
    }

    // Sovrascrive il file CSV con i dati presenti nella lista
    public static void salvasuCSV(String percorsoFile, List<Proiezioni> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(percorsoFile))) {
            bw.write("dataOra,titolo,genere,regista,anno,durata,etaMinima,prezzo");
            bw.newLine();

            for (Proiezioni p : lista) {
                String riga = String.format("%s, %s, %s, %s, %d, %d, %d, %2.f",
                        p.dataOra, p.titolo, p.genere, p.anno, p.durata, p.etaMinima, p.prezzo);
                bw.write(riga.replace(".",","));
                bw.newLine();
            }
        } catch (IOException e)  {
            System.out.println("Errore durante il salvataggio" + e.getMessage());
        }
    }

    /**
     * Restituisce una rappresentazione testuale formattata della proiezione
     * @return Una stringa contenente data/ora, titolo, genere e prezzo (formattati)
     */
    @Override
    public String toString() {
        // Usiamo il simbolo "|" per separare i dati in modo ordinato
        return "DATA: " + dataOra +
                " | FILM: " + titolo +
                " | GENERE: " + genere +
                " | PREZZO: " + prezzo + "€";
    }

    /**
     * Esegue la ricerca tra le proiezioni caricato da File
     * <p>
     *     Il metodo permette all'utente di filtrare i risultato attraverso
     *     diverse possibilità inserite da tastiera. Se un filtro viene lasciato vuoto
     *     non viene applicata nessuna ricerca relativa
     * </p>
     * <b>Criteri di filtraggio supportati:</b>
     * <ul>
     *     <li>TITOLO (anche parziale</li>
     *     <li>GENERE</li>
     *     <li>INTERVALLO DATE</li>
     *     <li>INTERVALLO PREZZO</li>
     * </ul>
     * @param percorsoFile percorso proiezioni.csv
     * @see #caricaDaCSV(String) 
     */
    public static void cercaProiezione(String percorsoFile) {
        // carichiamo i dati dal file con il metodo già creato
        List<Proiezioni> tutteLeProiezioni = caricaDaCSV(percorsoFile);

        if (tutteLeProiezioni.isEmpty()) {
            System.out.println("Nessuna Proiezione disponibile");
            return;
        }

        Scanner in = new Scanner(System.in);

        // Criteri di ricerca
        System.out.println("Filtri di Ricerca (Premi INVIO per saltare un filtro)");

        System.out.println("Cerca per titolo (anche parziale): ");
        String filtroTitolo = in.nextLine().toLowerCase();

        System.out.println("Cerca per genere: ");
        String filtroGenere = in.nextLine().toLowerCase();

        System.out.println("Intervallo di data - Inizio data");
        String dataInizio = in.nextLine();

        System.out.println("Intervallo di data - Fine data");
        String dataFine = in.nextLine();

        System.out.println("Intervallo di prezzo - Prezzo minimo");
        String PrezzominStr = in.nextLine();
        double PrezzoMin = PrezzominStr.isEmpty() ? 0 : Double.parseDouble(PrezzominStr.replace(",", "."));

        System.out.println("Intervallo di prezzo - Prezzo massimo");
        String PrezzomaxStr = in.nextLine();
        double PrezzoMax = PrezzomaxStr.isEmpty() ? 0 : Double.parseDouble(PrezzomaxStr.replace("," , "."));

        System.out.println("RISULTATI DELLE RICERCA: ");

        List<Proiezioni> filmTrovati = new ArrayList<>();

        // ciclo per controllare ogni proiezione
        for (Proiezioni p : tutteLeProiezioni) {
            boolean corrisponde = true;

            // Filtro Titolo
            if (!filtroTitolo.isEmpty() && !p.titolo.toLowerCase().contains(filtroTitolo)) {
                corrisponde = false;
            }

            // Filtro Genere
            if (!filtroGenere.isEmpty() && !p.genere.equalsIgnoreCase(filtroGenere)) {
                corrisponde = false;
            }

            // Filtro date
            if (!dataInizio.isEmpty() && p.dataOra.compareTo(dataInizio) < 0) {
                corrisponde = false;
            }

            if (!dataFine.isEmpty() && p.dataOra.compareTo(dataFine) > 0) {
                corrisponde = false;
            }

            // Filtro prezzo minimo
            if (PrezzoMin > 0 && p.prezzo < PrezzoMin) {
                corrisponde = false;
            }

            // Filtro prezzo Massimo
            if (PrezzoMax > 0 && p.prezzo > PrezzoMax) {
               corrisponde = false;
            }

            // se si passano i controlli stampiamo usando il toString()

            if (corrisponde) {
                filmTrovati.add(p);
            }
        }

        // chiedere all'utente di selezionare un film dalla lista numerata
        if (filmTrovati.isEmpty()) {
            System.out.println("Nessuna proiezione trovata con i criteri inseriti.");
        } else {
            // stampa numerata
            for (int i = 0; i < filmTrovati.size(); i++) {
                System.out.println((i + 1) + ". " + filmTrovati.get(i).toString());
            }

            // domanda all'utente
            System.out.println("Inserisci il numero del film per vederne i dettagli: ");
            int scelta = in.nextInt();

            if (scelta > 0 && scelta <= filmTrovati.size()) {
                Proiezioni filmScelto = filmTrovati.get(scelta - 1);
                visualizzaProiezione(filmScelto);
            } else {
                System.out.println("Numero non valido.");
            }
        }
    }

    /**
     * Restituisce la Data e l'ora della proiezione
     * @return Una stringa contenente la data e l'ora
     */
    public String getDataOra() {
        return dataOra;
    }

    /**
     * Restituisce i posti liberi per la Proiezione selezionata
     * Il metodo fa una differenza fra la capienza della sala e i posti prenotati
     * @return Un intero relativo ai posti liberi
     */
    public int getPostiLiberi() {
        return capienzaSala - postiPrenotati;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getGenere() {
        return genere;
    }

    public String getRegista() {
        return regista;
    }

    public int getAnno() {
        return anno;
    }

    public int getDurata() {
        return durata;
    }

    public int getEtaMinima() {
        return etaMinima;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setDataOra(String dataOra) {
        this.dataOra = dataOra;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setEtaMinima(int etaMinima) {
        this.etaMinima = etaMinima;
    }

    /**
     * Visualizza sul terminale tutte le proiezioni in base ai criteri di ricerca.
     * Successivamente l'utente può scegliere un film per avere i dettagli completi di esso trc cui:
     * <ul>
     *     <li>Dati del film: Titolo, genere, regista, anno e durata</li>
     *     <li>Dati logistici: Data e ora della programmazione e prezzo del biglietto</li>
     *     <li>Stato della sala: Calcola quanti posti sono disponibili</li>
     * </ul>
     * @param p L'oggetto Proiezioni di cui mostrare i dettagli
     */
    public static void visualizzaProiezione(Proiezioni p) {
        //caratteristiche del film
        System.out.println("Titolo: " + p.titolo);
        System.out.println("Genere: " + p.genere);
        System.out.println("Regista: " + p.regista);
        System.out.println("Anno: " + p.anno);
        System.out.println("Durata: " + p.durata + " minuti");

        //data ora e costo della prenotazione
        System.out.println("In programma: " + p.dataOra);
        System.out.println("Prezzo: " + p.prezzo + " €");

        //calcolo dei posti liberi
        int liberi = p.getPostiLiberi();
        System.out.println("Posti disponibili: " + liberi + " su " + capienzaSala);

        if (liberi == 0) {
            System.out.println("Posti esauriti per la proiezione selezionata");
        }
    }

    public static void aggiungiProiezione(Scanner in, String percorsoFIle, List<Proiezioni> elenco){
        System.out.println("INSERISCI UNA NUOVA PROIEZIONE");

        System.out.print("Titolo del film: ");
        String titolo = in.nextLine();

        System.out.print("Genere: ");
        String genere = in.nextLine();

        System.out.print("Regista: ");
        String regista = in.nextLine();

        System.out.print("Anno di uscita: ");
        int anno = in.nextInt();

        System.out.print("Durata (in minuti): ");
        int durata = in.nextInt();

        System.out.print("Età minima consentita (0 se per tutti): ");
        int etaMinima = in.nextInt();
        in.nextLine();

        System.out.print("Costo del biglietto (es. 7.50): ");
        double prezzo = Double.parseDouble(in.nextLine().replace(",", "."));

        System.out.print("Data e Ora di inizio (formato yyyy-MM-dd HH:mm:ss): ");
        String dataOraStr = in.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inizioNuova;
        try {
            inizioNuova = LocalDateTime.parse(dataOraStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Errore: Formato data non valido. Inserimento annullato.");
            return;
        }

        LocalDateTime fineNuova = inizioNuova.plusMinutes(durata);
        boolean sovrapposizione = false;

        // Controllo sovrapposizione
        for (Proiezioni p : elenco) {
            try {
                // puliamo la stringa togliendo eventuali virgolette che potrebbero essere nel CSV
                String dataPulita = p.getDataOra().replace("\"", "").trim();

                LocalDateTime inizioEsistente = LocalDateTime.parse(dataPulita, formatter);
                LocalDateTime fineEsistente = inizioEsistente.plusMinutes(p.durata);

                if (inizioNuova.isBefore(fineEsistente) && fineNuova.isAfter(inizioEsistente)) {
                    sovrapposizione = true;
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Errore --> " + e.getMessage());
                return;
            }
        }
            if(sovrapposizione) {
                System.out.println("Errore: L'orario inserito si sovrappone con un'altra proiezione già a palinsesto!");
                return;
            }

            Proiezioni nuovaProiezione = new Proiezioni(dataOraStr, titolo, genere, regista, anno, durata, etaMinima, prezzo);
            elenco.add(nuovaProiezione);

            try (PrintWriter printWriter = new PrintWriter(new FileWriter(percorsoFIle,  true))) {

                String rigaCsv = String.format("%s,%s,%s,%s,%d,%d,%d,%.2f",
                        nuovaProiezione.getDataOra(),
                        nuovaProiezione.titolo,
                        nuovaProiezione.genere,
                        nuovaProiezione.regista,
                        nuovaProiezione.anno,
                        nuovaProiezione.durata,
                        nuovaProiezione.etaMinima,
                        prezzo);

                printWriter.println(rigaCsv);
                System.out.println("Proiezione salvato con successo");

            } catch (IOException e) {
                System.out.println("Errore critico: Impossibile scrivere nel file delle proiezioni.");
            }
        }

    public static void modificaProiezione(String percorsoFile) {
        List<Proiezioni> elenco = caricaDaCSV(percorsoFile);
        if (elenco.isEmpty()) {
            return;
        }

        Scanner in = new Scanner(System.in);

        // Mostriamo l'elenco numerato
        System.out.println("Seleziona il numero della proiezione da modificare:");
        for (int i = 0; i < elenco.size(); i++) {
            System.out.println((i + 1) + ". " + elenco.get(i).titolo);
        }

        int scelta = in.nextInt();
        in.nextLine(); // pulizia buffer

        if (scelta > 0 && scelta <= elenco.size()) {
            Proiezioni p = elenco.get(scelta - 1);

            // Controllo vincolo: nessuna prenotazione
            if (p.postiPrenotati > 0) {
                System.out.println("Impossibile modificare la proiezione: ci sono già" + p.postiPrenotati + " posti prenotati");
                return;
            }

            // Inserimento dei nuovi dati
            System.out.println("Modifica di: " + p.getTitolo());

            System.out.println("Nuovo Titolo (premi INVIO per non cambiarlo): ");
            String nuovoTitolo = in.nextLine();
            if (!nuovoTitolo.isEmpty()) {
                p.setTitolo(nuovoTitolo);
            }

            System.out.println("Nuovo Genere (preme INVIO per non cambiarlo): ");
            String nuovoGenere = in.nextLine();
            if (!nuovoGenere.isEmpty()) {
                p.setGenere(nuovoGenere);
            }

            System.out.println("Nuovo Regista (premi INVIO per non cambiarlo): ");
            String nuovoRegista = in.nextLine();
            if (!nuovoRegista.isEmpty()) {
                p.setRegista(nuovoRegista);
            }

            System.out.println("Nuovo anno (premi INVIO per non cambiarlo): ");
            int nuovoAnno = in.nextInt();
            if (nuovoAnno > 0) {
                p.setAnno(nuovoAnno);
            }

            System.out.println("Nuova Durata (premi INVIO per non cambiarlo): ");
            int nuovaDurata = in.nextInt();
            if (nuovaDurata > 0) {
                p.setDurata(nuovaDurata);
            }

            System.out.println("Nuova età minima (premi INVIO per non cambiarlo): ");
            int nuovaEtaMinima = in.nextInt();
            if (nuovaEtaMinima >= 0) {
                p.setEtaMinima(nuovaEtaMinima);
            }

            System.out.println("Nuovo Prezzo (premi INVIO per non cambiarlo): ");
            double nuovoPrezzo = in.nextDouble();
            if (nuovoPrezzo > 0) {
                p.setPrezzo(nuovoPrezzo);
            }

            System.out.println("Nuova Data/Ora (premi INVIO per non cambiarlo): ");
            String nuovaData = in.nextLine();
            if (!nuovaData.isEmpty()) {
                p.setDataOra(nuovaData);
            }

            // Salvataggio definitivo
            salvasuCSV(percorsoFile, elenco);
            System.out.println("Proiezione completata con successo");

        } else {
            System.out.println("Scelta non valida!");
        }
    }
}