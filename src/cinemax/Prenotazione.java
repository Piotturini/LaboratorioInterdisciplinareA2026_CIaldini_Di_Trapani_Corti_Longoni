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
 * @version 1.4
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
     * @return Il codice in formato double
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
     * Calcola il costo totale delle prenotazioni
     * Moltiplica il numero di biglietti e il prezzo unitario del film associato
     * @return Il valore totale in formato double
     */
    public double getCostoTotale(){
        return this.numBiglietti * this.film.getPrezzo();
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
    // riscriviamo da zero il file contenente le prenotazioni partendo dalla mappa aggiornata
// è fondamentale dopo ogni modifica
    public static void sovrascriviFile(String percorsoFile, Map<String, Prenotazione> mappa, List<Proiezioni> elencoProiezioni){
        try(java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(percorsoFile, false))) {
            for (Prenotazione p : mappa.values()) {
                int idProiezione = elencoProiezioni.indexOf(p.getProiezione());
                if (idProiezione == -1) idProiezione = 0;

                String riga = String.format("%s;%s;%d;%d",
                        p.getCodice(),
                        p.getUsernamenCliente(),
                        idProiezione,
                        p.getNumBiglietti()
                );
                pw.println(riga);
            }
        }catch(java.io.IOException e){
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
    //creiamo un metodo per salvare su file le prenotazioni
    public void salvaSufile(String percorsoFile, List<Proiezioni> elencoProiezioni){
        // troviamo la posizione (l'indice ) della proiezione corrente all'interno generale
        int idProizione = elencoProiezioni.indexOf(this.proiezione);
        // se per qualche motivo la proiezione non è nella lista impostiamo un valore di sicurezza a 0
        if (idProizione == -1){
            idProizione = 0;
        }
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(percorsoFile, true))) {
            // costruiamo la stringa formattata
            String rigaSalvataggio = String.format("%s;%s;%d;%d",
                    this.codice,
                    this.usernamenCliente,
                    idProizione,
                    this.numBiglietti
            );
            pw.println(rigaSalvataggio);
        }catch(java.io.IOException e){
            System.out.println("errore durante il salvataggio delle prenotazione" + e.getMessage());
        }
    }

    // Legge l'intero file una volta sola all'avvio  e riempie una HashMap

    public static Map<String, Prenotazione> caricaMappaPrenotazioni (String percorsoFile, List<Proiezioni> elencoProiezioni){
        Map<String, Prenotazione> mappa = new HashMap <>();

        try(BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String riga;
            while ((riga = br.readLine()) != null) {
                // Ipotizziamo che la riga sia divisa da punti e virgola: CODICE;USERNAME;ID_PROIEZIONE;NUM_BIGLIETTI
                if ((riga = br.readLine()) !=null);
                String[] dati = riga.split(";");
                String codice = dati[0].trim();
                String username = dati[1].trim();
                int idProiezione = Integer.parseInt(dati[2].trim());
                int numBiglietti = Integer.parseInt(dati[3].trim());
                // Recuperiamo gli oggetti associati ( Film e proiezione) dalla lista  generale
                Proiezioni proiezione = elencoProiezioni.get(idProiezione);
                Film filmAssociato = new Film(
                        proiezione.getTitolo(),
                        proiezione.getGenere(),
                        proiezione.getRegista(),
                        proiezione.getAnno(),
                        proiezione.getDurata(),
                        proiezione.getEtaMinima(),
                        proiezione.getPrezzo()
                ) ;
                // Ricostruiamo l'oggetto usando il secondo costruttore (quello con il codice)
                Prenotazione p = new Prenotazione(codice, username, proiezione, filmAssociato, numBiglietti);
                //lo inseriamo come chiave della has map
                mappa.put(codice.toUpperCase(), p);
            }
        } catch (Exception e) {
            System.out.println("archivio prenotazioni vuoto o non ancora creato ");
        }
        return mappa;
    }
    public static void creaPrenotazione (java.util.Scanner in, String filePrenotazioni, List<Proiezioni> elencoProiezioni, Map <String, Prenotazione> mappaPrenotazioni, String fileCsv){
        Proiezioni.cercaProiezione(fileCsv);
        System.out.println("\nVuoi effettuare una prenotazione per uno di questi film? (si/no):");
        String risposta = in.nextLine().trim().toLowerCase();
        if (!risposta.equals("si")){
            System.out.println("operazione annullata ritorno al menu principale");
            return;
        }
        System.out.print("inserisci L'id progressivo del film scelto (1 per il primo,2 per il secondo...");
        int sceltaFilm = in.nextInt();
        System.out.print("quanti biglietti desideri acquistare? ");
        int quantita = in.nextInt();
        in.nextLine();
        int indiceLista = sceltaFilm -1;

        if (indiceLista >= 0 && indiceLista < elencoProiezioni.size()){
            Proiezioni proiezioneScelta = elencoProiezioni.get(indiceLista);
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
            }else {
                System.out.println("posti insufficenti.");
            }
        }else {
            System.out.println("scelta film non valida.");
        }
    }
    public static void CercaPrenotazione (java.util.Scanner in, String filePrenotazioni, List<Proiezioni> elencoProiezioni, Map <String, Prenotazione> mappaPrenotazioni, String fileCsv){
        System.out.println("verifica Biglietto");
        System.out.println("inserisci il codice univoco della prenotazione: ");
        String codiceCerca = in.nextLine().toUpperCase().trim();
         if (mappaPrenotazioni.containsKey(codiceCerca)){
             Prenotazione trovata = mappaPrenotazioni.get(codiceCerca);
        System.out.println("Biglietto valido" );
        System.out.println("Intenstatario:"+ trovata.getUsernamenCliente());
        System.out.println("Film "+  trovata.getFilm().getTitolo());
        System.out.println("Orario"+ trovata.getProiezione().getDataOra());
        System.out.println("Posti:"+ trovata.getNumBiglietti());
        System.out.println("Totale; "+ trovata.getCostoTotale());
    }else{
        System.out.println("nessuna prenotazione trovate");
    }

    }

    public static void modificaPrenotazione(java.util.Scanner in, String filePrenotazioni, List<Proiezioni> elencoProiezioni, Map <String, Prenotazione> mappaPrenotazioni, String fileCsv){
        System.out.println("inizio modfica prenotazione");
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

        if(!p.IsModificabile()){
            System.out.println("inpossibile modificare spettacolo gia iniziato");
            return;
        }

        // scelta CAMBIO FILM/DATA

        Proiezioni nuovaProiezione = p.getProiezione();
        System.out.print("\nVuoi cambiare film o data? (premi INVIO per non cambiarlo, scrivi 'si' per cambiarlo): ");
        String sceltaCambioSpettacolo = in.nextLine().trim().toLowerCase();

        if(!sceltaCambioSpettacolo.isEmpty() && sceltaCambioSpettacolo.equals("si")){
            Proiezioni.cercaProiezione(fileCsv);
            System.out.print("\ninserisci il numero progressivo del nuovo spettacolo: (1 per il primo, 2 per il secondo...");
            String inpuId = in.nextLine().trim();

            if(!inpuId.isEmpty()){
                try{
                    int indiceLista = Integer.parseInt(inpuId) - 1;
                    if (indiceLista >= 0 && indiceLista < elencoProiezioni.size()){
                        nuovaProiezione = elencoProiezioni.get(indiceLista);
                    } else {
                        System.out.println("Selezione fuori intervallo. modifica non effetutata");
                }
            }catch (NumberFormatException e){
                    System.out.println("input non valido. modifica non effettuata");
                }
        }
    }
        int nuoviPosti = p.getNumBiglietti();
        System.out.println("inserire il nuovo numero totale di biglietti (premi invio oer non cambiarli");
        String inputPosti = in.nextLine().trim();
        if(!inputPosti.isEmpty()){
            try{
                nuoviPosti = Integer.parseInt(inputPosti);
                if(nuoviPosti <= 0) {
                    System.out.println("Quantità non valida. Mantenuti i posti precedenti.");
                    nuoviPosti = p.getNumBiglietti();
                }
                }catch (NumberFormatException e){
                    System.out.println("input non numerico. mantenuti i posti precedenti");
                }
            }
            int postiDisponibili;
        if (nuovaProiezione == p.getProiezione()){
            postiDisponibili = nuovaProiezione.getPostiLiberi() + p.getNumBiglietti();
        }else {
            postiDisponibili = nuovaProiezione.getPostiLiberi();
        }
        if (nuoviPosti > postiDisponibili ){
            System.out.println("impossibile salvare le modifiche posti insufficenti");
            return;
        }
        if (nuovaProiezione != p.getProiezione()){
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