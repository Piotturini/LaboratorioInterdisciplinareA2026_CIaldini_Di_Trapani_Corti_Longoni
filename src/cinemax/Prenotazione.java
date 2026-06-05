package cinemax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap ;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Rappresenta una prenotazione effettuata da un cliente per una specifica proiezione
 * La classe gestisce la generazione di codici univoci, il calcolo del costo totale
 * e la logica di validazione temporale delle modifiche
 * @author ...
 * @version 1.8.2
 */
public class Prenotazione {

    // CAMPI
    private String codice;
    private  String usernamenCliente;
    private Proiezioni proiezione;//associazione diretta alla classe Proiezioni
    private Film film; // associazione diretta alla classe film
    private int numBiglietti;

    // COSTRUTTORI
    /**
     * Costruttore per creare una nuova prenotazione
     * Genere automaticamente un codice univoco tramite il metodo {@link #GeneraCodiceUnivoco()}
     * @param usernameCliente Identificativo del cliente
     * @param proiezioni Oggetto proiezioni selezionato
     * @param film Oggetto film associato
     * @param numBiglietti Quantità di biglietti da acquistare
     */
    public Prenotazione (String usernameCliente, Proiezioni proiezioni, Film film, int numBiglietti){
        this.usernamenCliente = usernameCliente;
        this.proiezione = proiezioni;
        this.numBiglietti = numBiglietti;
        this.film = film;
        this.codice = GeneraCodiceUnivoco();
    }

    /**
     * Costruttore di caricamento utilizzato per costruire oggetti
     * Questo costruttore accetta un codice preesistente
     * @param codice Il codice univoco già esistente
     * @param usernameCliente Identificativo del cliente
     * @param proiezioni Oggetto proiezione associato
     * @param film Oggetto film associato
     * @param numBiglietti Quantità di biglietti registrati
     */
    public Prenotazione (String codice, String usernameCliente, Proiezioni proiezioni, Film film, int numBiglietti){
        this.codice = codice;
        this.usernamenCliente = usernameCliente;
        this.proiezione= proiezioni;
        this.film = film;
        this.numBiglietti = numBiglietti;
    }

    /**
     * Genera un codice identificativo univoco
     * Utilizza {@link UUID} per garantire l'univocità e ne estrae i primi 8 caratteri
     * preceduti dal prefisso "PRN-"
     * @return Il codice in formato String
     */
    private String GeneraCodiceUnivoco(){
        // Genera una stringa del tipo "PRN-3f8x9a2b-1234-4567..."
        //estraiamo solo i primi 8 caratteri (per non avere un codice troppo lungo);
        //oppure lo teniamo intero per la massima sicurezza
        String uuidCompleto = UUID.randomUUID().toString().replace("-","");
        //prendiamo i primi 8 caratteri
        return "PRN-" + uuidCompleto.substring(0,8).toUpperCase();
    }

    /**
     * Verifica se la prenotazione è ancora soggetta a modifiche/cancellazioni
     * Una prenotazione è considerata modificabile sono se il momento attuale è
     * precedente alla data e ora della proiezione
     * @return true se la proiezione non è ancora avvenuta, false altrimenti
     */
    public boolean IsModificabile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            String dataPulita = proiezione.getDataOra().replace("\"", "").trim();
            LocalDateTime dataOraProiezione = LocalDateTime.parse(dataPulita,formatter);
            return LocalDateTime.now().isBefore(dataOraProiezione);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Aggiorna integralmente il file delle prenotazioni
     * @param percorsoFile Percorso file da sovrascrivere
     * @param mappa Mappa contenente tutte le prenotazioni correnti
     * @param elencoProiezioni Lista delle proiezioni
     */
    public static void sovrascriviFile(String percorsoFile, Map<String, Prenotazione> mappa, List<Proiezioni> elencoProiezioni) {
        try(java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(percorsoFile, false))) {
            for (Prenotazione p : mappa.values()) {
                String nomeFilm = p.getFilm().getTitolo().replace("\"", "").trim();
                String regista = p.getFilm().getRegista().replace("\"", "").trim();
                String dataFilm = p.getProiezione().getDataOra().replace("\"", "").trim();

                String riga = String.format(java.util.Locale.US, "%s;%s;%s;%s;%s;%d;%.2f",
                        p.getCodice(),
                        p.getUsernamenCliente(),
                        nomeFilm,
                        regista,
                        dataFilm,
                        p.getNumBiglietti(),
                        p.getCostoTotale()
                );
                pw.println(riga);
            }
        } catch (java.io.IOException e) {
            System.out.println("errore durante l'aggiornamento del file prenotazione" + e.getMessage());
        }
    }

    // METODI GETTER

    /**
     * Restituisce il codice univoco della prenotazione
     * @return Stringa del codice univoco
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Restituisce lo username del cliente
     * @return Stringa dello username
     */
    public String getUsernamenCliente() {
        return usernamenCliente;
    }

    /**
     * Restituisce l'oggetto Proiezioni associato
     * @return Oggetto Proiezioni preso in considerazione
     */
    public Proiezioni getProiezione() {
        return proiezione;
    }

    /**
     * Restituisce l'oggetto film associato
     * @return Oggetto Film preso in considerazione
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Restituisce il numero di biglietti prenotati
     * @return Un intero relativo al numero di biglietti prenotati
     */
    public int getNumBiglietti() {
        return numBiglietti;
    }

    /**
     * Calcola il costo totale delle prenotazioni
     * Moltiplica il numero di biglietti e il prezzo unitario del film associato
     * @return Il valore totale in formato double
     */
    public double getCostoTotale(){
        return this.numBiglietti * this.film.getPrezzo();
    }

    /**
     * Salva la singola istanza della prenotazione corrente su file
     * @param percorsoFile Percorso file da sovrascrivere
     * @param elencoProiezioni Lista delle proiezioni
     */
    public void salvaSufile(String percorsoFile, List<Proiezioni> elencoProiezioni){
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(percorsoFile, true))) {
            // Puliamo i testi dalle virgolette per sicurezza
            String nomeFilm = this.film.getTitolo().replace("\"", "").trim();
            String regista = this.film.getRegista().replace("\"", "").trim();
            String dataFilm = this.proiezione.getDataOra().replace("\"", "").trim();

            // Scrittura in chiaro: CODICE;ACQUIRENTE;FILM;REGISTA;DATA;BIGLIETTI;PREZZO
            String rigaSalvataggio = String.format(java.util.Locale.US, "%s;%s;%s;%s;%s;%d;%.2f",
                    this.codice,
                    this.usernamenCliente,
                    nomeFilm,
                    regista,
                    dataFilm,
                    this.numBiglietti,
                    this.getCostoTotale()
            );
            pw.println(rigaSalvataggio);
        } catch (java.io.IOException e) {
            System.out.println("errore durante il salvataggio delle prenotazione" + e.getMessage());
        }
    }

    /**
     * Carica tutte le prenotazioni da file in una {@link HashMap} all'avvio del sistema
     * @param percorsoFile Percorso file da sovrascrivere
     * @param elencoProiezioni Lista delle proiezioni
     * @return Una mappa con il codice della prenotazione come chiave
     */
    public static Map<String, Prenotazione> caricaMappaPrenotazioni (String percorsoFile, List<Proiezioni> elencoProiezioni){
        Map<String, Prenotazione> mappa = new HashMap <>();

        try(BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String riga;
            while ((riga = br.readLine()) != null) {
                if (riga.trim().isEmpty()) continue;

                String[] dati = riga.split(";");
                String codice = dati[0].trim();
                String username = dati[1].trim();
                String titoloFilm = dati[2].trim().replace("\"", "");
                String registaFilm = dati[3].trim().replace("\"", "");
                String dataOraFilm = dati[4].trim().replace("\"", "");
                int numBiglietti = Integer.parseInt(dati[5].trim());
                Proiezioni proiezioneAbbinata = null;
                for (Proiezioni pr : elencoProiezioni) {
                    String titoloPr = pr.getTitolo().replace("\"", "").trim();
                    String dataPr = pr.getDataOra().replace("\"", "").trim();

                    if (titoloPr.equalsIgnoreCase(titoloFilm) && dataPr.equals(dataOraFilm)) {
                        proiezioneAbbinata = pr;
                        break;
                    }
                }

                // Se troviamo la proiezione usiamo i suoi dati reali, altrimenti usiamo un fallback storico
                Film filmAssociato;
                if (proiezioneAbbinata != null) {
                    filmAssociato = new Film(
                            proiezioneAbbinata.getTitolo(), proiezioneAbbinata.getGenere(),
                            proiezioneAbbinata.getRegista(), proiezioneAbbinata.getAnno(),
                            proiezioneAbbinata.getDurata(), proiezioneAbbinata.getEtaMinima(),
                            proiezioneAbbinata.getPrezzo()
                    );
                } else {
                    // Fallback di sicurezza se la proiezione è passata e non è più nella lista RAM
                    filmAssociato = new Film(titoloFilm, "Generico", registaFilm, 2026, 120, 0, 8.50);
                    proiezioneAbbinata = new Proiezioni(dataOraFilm, titoloFilm, "Generico", registaFilm, 2026, 120, 100, 8.50);
                }

                Prenotazione p = new Prenotazione(codice, username, proiezioneAbbinata, filmAssociato, numBiglietti);
                mappa.put(codice.toUpperCase(), p);
            }
        } catch (Exception e) {
            System.out.println("archivio prenotazioni vuoto o non ancora creato");
        }
        return mappa;
    }

    /**
     * Procedura per la creazione di una nuova prenotazione
     * @param in Scanner per l'input dell'utente
     * @param filePrenotazioni Percorso del file prenotazioni
     * @param elencoProiezioni Lista proiezioni corrente
     * @param mappaPrenotazioni Mappa delle prenotazioni
     * @param fileCsv Percorso del file CSV dei film
     */
    public static void creaPrenotazione (java.util.Scanner in, String filePrenotazioni, List<Proiezioni> elencoProiezioni, Map <String, Prenotazione> mappaPrenotazioni, String fileCsv) {
        Proiezioni.cercaProiezione(fileCsv);
        System.out.println("\nVuoi effettuare una prenotazione per uno di questi film? (si/no):");
        String risposta = in.nextLine().trim().toLowerCase();
        if (!risposta.equals("si")) {
            System.out.println("operazione annullata ritorno al menu principale");
            return;
        }
        System.out.print("Conferma il titolo  del film visto a schermo: ");
        String titoloScelto = in.nextLine().trim();
        System.out.print("Conferma l'orario la data dello spettacolo: ");
        String dataScelta = in.nextLine().trim();

        Proiezioni proiezioneScelta = null;
        for (Proiezioni pr: elencoProiezioni){
            String titoloPr = pr.getTitolo().replace("\"", "").trim();
            String dataPr = pr.getDataOra().replace("\"", "").trim();

            if (titoloPr.equalsIgnoreCase(titoloScelto) && dataPr.contains(dataScelta)){
                proiezioneScelta = pr;
                break;
            }
        }
        if (proiezioneScelta == null) {
            System.out.println("Scelta non valida. Nessun film corrispondente trovato nel palinsesto.");
            return;
        }


        System.out.print("quanti biglietti desideri acquistare? ");
        int quantita = in.nextInt();
        in.nextLine();

            if(quantita <=proiezioneScelta.getPostiLiberi()){
                System.out.print("inserisci username del cliente: ");
                String utenteAcquirente = in.nextLine();

                Film filmAssociato = new Film(
                        proiezioneScelta.getTitolo(), proiezioneScelta.getGenere(),
                        proiezioneScelta.getRegista(), proiezioneScelta.getAnno(),
                        proiezioneScelta.getDurata(), proiezioneScelta.getEtaMinima(),
                        proiezioneScelta.getPrezzo()
                );
                Prenotazione nuovaP = new Prenotazione(utenteAcquirente, proiezioneScelta, filmAssociato, quantita);
                nuovaP.salvaSufile(filePrenotazioni, elencoProiezioni);
                mappaPrenotazioni.put(nuovaP.getCodice().toUpperCase(), nuovaP);

                System.out.println("prenotazione creata! codice: " + nuovaP.getCodice() );
            } else {
                System.out.println("posti insufficenti.");
            }
    }

    /**
     * Procedura per la ricerca e visualizzazione dei dettagli di una prenotazione
     * @param in Scanner per l'input dell'utente
     * @param filePrenotazioni Percorso del file prenotazioni
     * @param elencoProiezioni Lista proiezioni corrente
     * @param mappaPrenotazioni Mappa delle prenotazioni
     * @param fileCsv Percorso del file CSV dei film
     */
    public static void CercaPrenotazione (java.util.Scanner in, String filePrenotazioni, List<Proiezioni> elencoProiezioni, Map <String, Prenotazione> mappaPrenotazioni, String fileCsv){
        System.out.println("Verifica Biglietto");
        System.out.println("Inserisci il codice univoco della prenotazione: ");
        String codiceCerca = in.nextLine().toUpperCase().trim();
         if (mappaPrenotazioni.containsKey(codiceCerca)) {
             Prenotazione trovata = mappaPrenotazioni.get(codiceCerca);
             System.out.println("Biglietto valido" );
             System.out.println("Intenstatario:"+ trovata.getUsernamenCliente());
             System.out.println("Film "+  trovata.getFilm().getTitolo());
             System.out.println("Orario"+ trovata.getProiezione().getDataOra());
             System.out.println("Posti:"+ trovata.getNumBiglietti());
             System.out.println("Totale; "+ trovata.getCostoTotale());
        } else {
        System.out.println("nessuna prenotazione trovate");
        }
    }

    /**
     * Procedura per la modifica di una prenotazione esistente
     * Permette di cambiare film/orario o il numero di biglietti
     * @param in Scanner per l'input dell'utente
     * @param filePrenotazioni Percorso del file prenotazioni
     * @param elencoProiezioni Lista proiezioni corrente
     * @param mappaPrenotazioni Mappa delle prenotazioni
     * @param fileCsv Percorso del file CSV dei film
     */
    public static void modificaPrenotazione(java.util.Scanner in, String filePrenotazioni, List<Proiezioni> elencoProiezioni, Map <String, Prenotazione> mappaPrenotazioni, String fileCsv) {
        System.out.println("Inizio modfica prenotazione");
        System.out.println("Inserisci il codice del biglietto da variare;");
        String codice = in.nextLine().toUpperCase().trim();
        if (!mappaPrenotazioni.containsKey(codice)){
            System.out.println("codice prenotazione inesistente");
            return;
        }
        Prenotazione p = mappaPrenotazioni.get(codice);
        System.out.println("\nBiglietto individuato!");
        System.out.println("Film attuale \"" + p.getFilm().getTitolo() + "\"");
        System.out.println("Data/Ora attuale " + p.getProiezione().getDataOra());
        System.out.println("Posti attuali: " + p.getNumBiglietti());

        if (!p.IsModificabile()) {
            System.out.println("inpossibile modificare spettacolo gia iniziato");
            return;
        }

        // scelta CAMBIO FILM/DATA
        Proiezioni nuovaProiezione = p.getProiezione();
        System.out.print("\nVuoi cambiare film o data? (premi INVIO per non cambiarlo, scrivi 'si' per cambiarlo): ");
        String sceltaCambioSpettacolo = in.nextLine().trim().toLowerCase();

        if (!sceltaCambioSpettacolo.isEmpty() && sceltaCambioSpettacolo.equals("si")) {
            Proiezioni.cercaProiezione(fileCsv);
            System.out.print("\ninserisci il numero progressivo del nuovo spettacolo: (1 per il primo, 2 per il secondo...");
            String inpuId = in.nextLine().trim();

            if (!inpuId.isEmpty()) {
                try {
                    int indiceLista = Integer.parseInt(inpuId) - 1;
                    if (indiceLista >= 0 && indiceLista < elencoProiezioni.size()) {
                        nuovaProiezione = elencoProiezioni.get(indiceLista);
                    } else {
                        System.out.println("Selezione fuori intervallo. modifica non effetutata");
                }
            } catch (NumberFormatException e) {
                    System.out.println("input non valido. modifica non effettuata");
                }
        }
    }
        int nuoviPosti = p.getNumBiglietti();
        System.out.println("inserire il nuovo numero totale di biglietti (premi invio oer non cambiarli");
        String inputPosti = in.nextLine().trim();
        if (!inputPosti.isEmpty()) {
            try {
                nuoviPosti = Integer.parseInt(inputPosti);
                if (nuoviPosti <= 0) {
                    System.out.println("Quantità non valida. Mantenuti i posti precedenti.");
                    nuoviPosti = p.getNumBiglietti();
                }
                } catch (NumberFormatException e) {
                    System.out.println("input non numerico. mantenuti i posti precedenti");
                }
            }
            int postiDisponibili;
        if (nuovaProiezione == p.getProiezione()) {
            postiDisponibili = nuovaProiezione.getPostiLiberi() + p.getNumBiglietti();
        } else {
            postiDisponibili = nuovaProiezione.getPostiLiberi();
        }
        if (nuoviPosti > postiDisponibili ) {
            System.out.println("impossibile salvare le modifiche posti insufficenti");
            return;
        }
        if (nuovaProiezione != p.getProiezione()) {
            p.proiezione = nuovaProiezione;
            p.film = new Film(
                    nuovaProiezione.getTitolo(), nuovaProiezione.getGenere(),
                    nuovaProiezione.getRegista(), nuovaProiezione.getAnno(),
                    nuovaProiezione.getDurata(), nuovaProiezione.getEtaMinima(),
                    nuovaProiezione.getPrezzo()
            );
        }
        p.numBiglietti = nuoviPosti;
        sovrascriviFile(filePrenotazioni,mappaPrenotazioni,elencoProiezioni);
        System.out.println("\n Nuova prenotazione");
        System.out.println(" Film \"" + p.getFilm().getTitolo() +"\"" );
        System.out.println("Date e ora " + p.getProiezione().getDataOra());
        System.out.println("Posti occupati" + p.getNumBiglietti());
        System.out.println("Nuova spesa " + p.getCostoTotale() +"€");
        }
}