package cinemax;

import java.util.Date;

/**
 * Rappresenta il profilo di un Bigliettaio all'interno del sistema Cinemax
 * <p>
 *     Questa classe estende la classe Utenti e definisce le operazioni specifiche che
 *     un operatore di biglietteria può compiere, tra cui:
 *     <ul>
 *         <li>Visualizzare le prenotazioni per la giornata odierna</li>
 *         <li>Effettuare la ricerca di una prenotazione</li>
 *         <li>Eseguire il Logout</li>
 *     </ul>
 * </p>
 * @author ...
 * @see Utenti
 * @version 1.0
 */

public  class Bigliettai extends Utenti{

    // COSTRUTTORI
    /**
     * Costruisce un nuovo oggetto Bigliettaio con i parametri ereditati
     * dalla classe padre Utenti
     * @param nome
     * @param cognome
     * @param username
     * @param password
     * @param data_di_nascita
     * @param luogoDomicilio
     * @param ruolo
     */
    public Bigliettai(String nome, String cognome, String username, String password, Date data_di_nascita, String luogoDomicilio, String ruolo) {
        super(nome, cognome, username, password, data_di_nascita, luogoDomicilio, ruolo);
    }

}
