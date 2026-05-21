package cinemax;

public class Password {
    private static final int DIM_ALFA = 31;
    private static final int DIM_TAB = 1007;
    private Utenti utente;

    public Password( Utenti utente){
        this.utente = utente;
    }

    public int hash() {
        String v = this.utente.getPassword();
        int l = v.length();
        int hasRisultato = 0;
        for (int h = 0; h < l; h++) {
            char c = v.charAt(h);
            hasRisultato= (DIM_ALFA *  hasRisultato + Character.getNumericValue(c)) % DIM_TAB;
        }
        if (hasRisultato <0){
            hasRisultato += DIM_TAB;
        }
        return hasRisultato ;
    }
}
