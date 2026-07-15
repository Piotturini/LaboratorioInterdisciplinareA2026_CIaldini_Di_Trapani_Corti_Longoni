package cinemax;

/**
 * Classe che gestisce la generazione di un codice hash per le password degli utenti
 * Utilizza un algoritmo basato su una base alfabetica e una dimensione della tabella hash
 * per minimizzare le collisioni e distribuire i valori
 * @author Di Trapani Daniele
 * @version 1.0
 */
public class Password {
    private static final int DIM_ALFA = 31;
    private static final int DIM_TAB = 1007;
    private  Utenti utente;

    /**
     * Costruisce un oggetto Password associandolo a uno specifico utente
     * @param utente L'oggetto {@link Utenti} contenente la password da elaborare
     */
    public Password(Utenti utente) {
        this.utente = utente;
    }

    /**
     * Calcola il valore hash della password dell'utente
     * <p>
     *     L'algoritmo scorre ogni carattere della stringa, applicando una formula polinomiale iterativa
     *     Viene poi garantito che il risultato sia sempre un intero positivo
     * </p>
     * @return Un intero che rappresenta l'hash della password
     */
    public int hash() {
        String v = this.utente.getPassword();
        int l = v.length();
        int hasRisultato = 0;
        for (int h = 0; h < l; h++) {
            char c = v.charAt(h);
            hasRisultato= (DIM_ALFA *  hasRisultato + Character.getNumericValue(c)) % DIM_TAB;
        }
        if (hasRisultato <0) {
            hasRisultato += DIM_TAB;
        }
        return hasRisultato;
    }
}
