package cinemax;

import java.util.Date;

public class Registrati extends Utenti{
    public Registrati(String nome, String cognome, String username, String password, Date data_di_nascita, String luogoDomicilio, String ruolo) {
        super(nome, cognome, username, password, data_di_nascita, luogoDomicilio, ruolo);
    }
}
