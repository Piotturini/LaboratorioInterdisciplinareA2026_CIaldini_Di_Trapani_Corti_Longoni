package cinemax;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Prenotazione {
    private String codice;
    private  String usernamenCliente;
    private Proiezioni proiezione;//associone diretta alla classe Proiezioni
    private Film film; // associazione diretta alla classe film
    private int numBiglietti;


    public Prenotazione (String usernameCliente, Proiezioni proiezioni, Film film, int numBiglietti){
        this.usernamenCliente = usernameCliente;
        this.proiezione = proiezioni;
        this.numBiglietti = numBiglietti;
        this.film = film;
        this.codice = GeneraCodiceUnivoco();
    }
    // costruttore di caricamento per ricostruire lo storico del file di testo.
    public Prenotazione (String codice, String usernameCliente, Proiezioni proiezioni, Film film, int numBiglietti){
        this.codice = codice;
        this.usernamenCliente = usernameCliente;
        this.proiezione= proiezioni;
        this.film = film;
        this.numBiglietti = numBiglietti;
    }
    private String GeneraCodiceUnivoco(){
        // Genera una stringa del tipo "PRN-3f8x9a2b-1234-4567..."
        //estraiamo solo i primi 8 caratteri (per non avere un codice troppo lungo);
        //oppure lo teniamo intero per la massima sicurezza
        String uuidCompleto = UUID.randomUUID().toString().replace("-","");
        //prendiamo i primi 8 caratteri
        return "PRN-" + uuidCompleto.substring(0,8).toUpperCase();
    }
    public double getCostoTotale(){
        return this.numBiglietti * this.film.getPrezzo();
    }


    // controllo se la prenotazione è ancora modificabile o cancellabile.
    public boolean IsModificabile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            LocalDateTime dataOraProiezione = LocalDateTime.parse(proiezione.getDataOra(), formatter);
            return LocalDateTime.now().isBefore(dataOraProiezione);
        } catch (Exception e) {
            return false;
        }
    }

    public String getCodice() {
        return codice;
    }

    public String getUsernamenCliente() {
        return usernamenCliente;
    }

    public Proiezioni getProiezione() {
        return proiezione;
    }

    public Film getFilm() {
        return film;
    }

    public int getNumBiglietti() {
        return numBiglietti;
    }
}