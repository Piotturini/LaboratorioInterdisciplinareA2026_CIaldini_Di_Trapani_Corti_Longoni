package cinemax;

import java.util.Date;

/**
 * Rappresenta il profilo di un Utente non registrato all'interno del sistema Cinemax
 * <p>
 *     Questa classe estende la classe Utenti e definisce i servizi che può effettuare, tra cui:
 *     <ul>
 *         <li>La possibilità di cercare le proiezioni</li>
 *         <li>Visualizzare i dettagli relativi alle proiezioni</li>
 *         <li>Effettuare la registrazione all'applicazione come cliente</li>
 *     </ul>
 * </p>
 * @author ...
 * @see Utenti
 * @version 1.0
 */
public class Guest extends Utenti{

    // COSTRUTTORI
    /**
     * Costruisce un nuovo oggetto Guest con i parametri ereditati
     * dalla classe padre Utenti
     * @param nome
     * @param cognome
     * @param username
     * @param password
     * @param data_di_nascita
     * @param luogoDomicilio
     * @param ruolo
     */
    public Guest(String nome, String cognome, String username, String password, Date data_di_nascita, String luogoDomicilio, String ruolo) {
        super(nome, cognome, username, password, data_di_nascita, luogoDomicilio, ruolo);
    }
}
