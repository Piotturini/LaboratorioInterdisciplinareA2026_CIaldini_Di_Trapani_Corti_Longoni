package cinemax;

import java.util.Date;

/**
 * Rappresenta il profilo di un Utente registrato all'interno del sistema Cinemax
 * <p>
 *     Questa classe estende la classe Utenti e definisce i servizi che può effettuare, tra cui:
 *     <ul>
 *         <li>La possibilità di inserire una prenotazione</li>
 *         <li>Visualizzare le proprie prenotazioni</li>
 *         <li>Effetuare la modifica e la cancellazione delle proprie prenotazioni</li>
 *         <li>Effetuare il Logout</li>
 *     </ul>
 * </p>
 * @author ...
 * @see Utenti
 * @version 1.0
 */

public class Registrati extends Utenti{

    // COSTRUTTORI
    /**
     * Costruisce un nuovo oggetto Registrati con i parametri ereditati
     * dalla classe padre Utenti
     * @param nome
     * @param cognome
     * @param username
     * @param password
     * @param data_di_nascita
     * @param luogoDomicilio
     * @param ruolo
     */
    public Registrati(String nome, String cognome, String username, String password, Date data_di_nascita, String luogoDomicilio, String ruolo) {
        super(nome, cognome, username, password, data_di_nascita, luogoDomicilio, ruolo);
    }
}
