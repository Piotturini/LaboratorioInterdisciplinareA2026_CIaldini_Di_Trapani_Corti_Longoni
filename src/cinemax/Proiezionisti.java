package cinemax;

import java.util.Date;

public class Proiezionisti extends Utenti {
    public Proiezionisti(String nome, String cognome, String username, String password, Date data_di_nascita, String luogoDomicilio, String ruolo) {
        super(nome, cognome, username, password, data_di_nascita, luogoDomicilio, ruolo);
    }
}
