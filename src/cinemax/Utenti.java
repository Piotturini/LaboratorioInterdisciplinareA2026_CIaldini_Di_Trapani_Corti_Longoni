package cinemax;

import java.util.Date;

public class Utenti {

    private String nome;
    private String cognome;
    private String username;
    private String password;
    private Date dataDiNascita;
    private String luogoDomicilio;
    private String ruolo;

    public Utenti(String nome, String cognome, String username, String password, Date dataDiNascita, String luogoDomicilio, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.dataDiNascita = dataDiNascita;
        this.luogoDomicilio = luogoDomicilio;
        this.ruolo = ruolo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public void setLuogoDomicilio(String luogoDomicilio) {
        this.luogoDomicilio = luogoDomicilio;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public String getLuogoDomicilio() {
        return luogoDomicilio;
    }

    public String getRuolo() {
        return ruolo;
    }
}
