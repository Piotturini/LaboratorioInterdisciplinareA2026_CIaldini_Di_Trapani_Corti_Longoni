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
 * @author Cialdini Daniele
 * @see Utenti
 * @version 1.0
 */
public class Guest extends Utenti{
    
    public Guest() {
        super();
    }
}
