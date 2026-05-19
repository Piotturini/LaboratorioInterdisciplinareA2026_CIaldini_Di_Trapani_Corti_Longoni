package cinemax;

/**
 * Rappresenta un film all'interno del sistema di gestione Cinemax.
 * La classe memorizza informazioni dettagliate come titolo, genere,
 * dettagli tecnici e registrazioni sull'età
 * @author ...
 * @version 1.0
 */

public class Film {

    // CAMPI
    private String titolo;
    private String genere;
    private String regista;
    private int anno;
    private int durata;
    private int etaMinima;
    private double prezzo;

    // COSTRUTTORI
    /**
     * Costruisce un oggetto che rappresenta la struttura dei Film
     * con tutti i parametri specificati
     * @param titolo Il titolo del film
     * @param genere Il genere del film
     * @param regista Il nome del regista
     * @param anno L'anno di pubblicazione
     * @param durata La durata in minuti
     * @param etaMinima L'età minima per la visione
     * @param prezzo Il costo del biglietto
     */
    public Film(String titolo, String genere, String regista, int anno, int durata, int etaMinima, double prezzo) {
        this.titolo = titolo;
        this.genere = genere;
        this.regista = regista;
        this.anno = anno;
        this.durata = durata;
        this.etaMinima = etaMinima;
        this.prezzo = prezzo;
    }

    // METODI SETTER

    /**
     * Imposta il titolo del film
     * @param titolo Stringa contenente il titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Imposta il genere del film
     * @param genere Stringa contenente il genere
     */
    public void setGenere(String genere) {
        this.genere = genere;
    }

    /**
     * Imposta il nome del regista
     * @param regista Stringa contenente il nome del regista
     */
    public void setRegista(String regista) {
        this.regista = regista;
    }

    /**
     * Imposta l'anno del film
     * @param anno L'anno espresso come intero
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }

    /**
     * Imposta la durata del film
     * @param durata Durata espressa in minuti
     */
    public void setDurata(int durata) {
        this.durata = durata;
    }

    /**
     * Imposta l'età minima per la visione
     * @param etaMinima Età minima (es. 0, 14, 18)
     */
    public void setEtaMinima(int etaMinima) {
        this.etaMinima = etaMinima;
    }

    /**
     * Imposta il prezzo del biglietto
     * @param prezzo Valore espresso il decimale del prezzo
     */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    // METODI GETTER

    /**
     * Restituisce il titolo del film
     * @return Il titolo corrente
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Restituisce il genere del film
     * @return Il genere corrente
     */
    public String getGenere() {
        return genere;
    }

    /**
     * Restituisce il nome del regista
     * @return Il nome del regista
     */
    public String getRegista() {
        return regista;
    }

    /**
     * Restituisce l'anno di uscita del film
     * @return L'anno di pubblicazione
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Restituisce la durata del film
     * @return Durata in minuti
     */
    public int getDurata() {
        return durata;
    }

    /**
     * Restituisce l'età minima per la visione
     * @return L'età minima richiesta
     */
    public int getEtaMinima() {
        return etaMinima;
    }

    /**
     * Restituisce il prezzo del biglietto
     * @return Il costo del biglietto
     */
    public double getPrezzo() {
        return prezzo;
    }
}


