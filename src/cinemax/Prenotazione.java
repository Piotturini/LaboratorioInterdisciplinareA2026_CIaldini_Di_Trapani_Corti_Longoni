package cinemax;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Rappresenta una prenotazione effettuata da un cliente per una specifica proiezione
 * La classe gestisce la generazione di codici univoci, il calcolo del costo totale
 * e la logica di validazione temporale delle modifiche
 * @author ...
 * @version 1.0
 */
public class Prenotazione {

    // CAMPI
    private String codice;
    private  String usernamenCliente;
    private Proiezioni proiezione;//associone diretta alla classe Proiezioni
    private Film film; // associazione diretta alla classe film
    private int numBiglietti;

    // COSTRUTTORI

    /**
     * Costruttore per creare una nuova prenotazione
     * Genere automaticamente un codice univoco tramite il metodo {@link #GeneraCodiceUnivoco()}
     * @param usernameCliente Indetificativo del cliente
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
     * Costruttore di caricamente utilizzato per costruire oggetti
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            LocalDateTime dataOraProiezione = LocalDateTime.parse(proiezione.getDataOra(), formatter);
            return LocalDateTime.now().isBefore(dataOraProiezione);
        } catch (Exception e) {
            return false;
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
     * Restitusce lo username del cliente
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
     * Restiutuisce l'oggetto film associato
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
}