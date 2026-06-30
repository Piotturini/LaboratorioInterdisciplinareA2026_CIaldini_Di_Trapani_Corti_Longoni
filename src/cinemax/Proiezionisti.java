package cinemax;

import java.util.Date;

/**
 * Rappresenta il profilo di un Proiezionista all'interno del sistema Cinemax
 * <p>
 *     Questa classe estende la classe Utenti e definisce le operazioni specifiche che
 *     un operatore proiezionista può compiere, tra cui:
 *     <ul>
 *        <li>Inserimento di un film che, successiamente, fa comparire a schermo la data ed il costo del biglietto per ogni proiezione</li>
 *        <li>Eseguire la modifica delle data di una proiezione</li>
 *        <li>Effettuare l'eliminazione di una proiezione</li>
 *        <li>Eseguire il Logout</li>
 *     </ul>
 * </p>
 * @author Corti Matteo
 * @see Utenti
 * @version 1.0
 */

public class Proiezionisti extends Utenti {

    // COSTRUTTORI

    /**
     * Costruisce un nuovo oggetto Proiezionista con i parametri ereditati
     * dalla classe padre Utenti
     * @param nome
     * @param cognome
     * @param username
     * @param password
     * @param data_di_nascita
     * @param luogoDomicilio
     * @param ruolo
     */
    public Proiezionisti(String nome, String cognome, String username, String password, Date data_di_nascita, String luogoDomicilio, String ruolo) {
        super(nome, cognome, username, password, data_di_nascita, luogoDomicilio, ruolo);
    }
}
