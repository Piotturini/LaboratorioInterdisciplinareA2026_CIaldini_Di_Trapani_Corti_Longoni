package cinemax;

public class Film {
    private String titolo;
    private String genere;
    private String regista;
    private int anno;
    private int durata;
    private int eta_minima;
    private double prezzo;

    public Film(String titolo, String genere, String regista, int anno, int durata, int eta_minima, double prezzo) {
        this.titolo = titolo;
        this.genere = genere;
        this.regista = regista;
        this.anno = anno;
        this.durata = durata;
        this.eta_minima = eta_minima;
        this.prezzo = prezzo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setEta_minima(int eta_minima) {
        this.eta_minima = eta_minima;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }


    public String getTitolo() {
        return titolo;
    }

    public String getGenere() {
        return genere;
    }

    public String getRegista() {
        return regista;
    }

    public int getAnno() {
        return anno;
    }

    public int getDurata() {
        return durata;
    }

    public int getEta_minima() {
        return eta_minima;
    }

    public double getPrezzo() {
        return prezzo;
    }
}


