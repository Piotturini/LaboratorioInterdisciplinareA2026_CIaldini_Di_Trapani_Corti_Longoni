package cinemax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Proiezioni {
    private String dataOra;
    private String titolo;
    private String genere;
    private String regista;
    private int anno;
    private int durata;
    private int etaMinima;
    private double prezzo;

    public Proiezioni(String dataOra, String titolo, String genere, String regista, int anno, int durata, int etaMinima, double prezzo) {
        this.dataOra = dataOra;
        this.titolo = titolo;
        this.genere = genere;
        this.regista = regista;
        this.anno = anno;
        this.durata = durata;
        this.etaMinima = etaMinima;
        this.prezzo = prezzo;
    }

    public static List<Proiezioni> caricaDaCSV(String percorsoFile) {
        List<Proiezioni> listaP = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String riga;
            br.readLine();

            while ((riga = br.readLine()) != null) {
                String[] d = riga.split(",");

                if (d.length >= 0) {
                    Proiezioni p = new Proiezioni(
                            d[0],
                            d[1],
                            d[2],
                            d[3],
                            Integer.parseInt(d[4].trim()),
                            Integer.parseInt(d[5].trim()),
                            Integer.parseInt(d[6].trim()),
                            Double.parseDouble(d[7].trim().replace(",", "."))
                    );

                    listaP.add(p);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Errore nel caricamento dati: " + e.getMessage());
        }
        return listaP;
    }

    @Override
    public String toString() {
        // Usiamo il simbolo "|" per separare i dati in modo ordinato
        return "DATA: " + dataOra +
                " | FILM: " + titolo +
                " | GENERE: " + genere +
                " | PREZZO: " + prezzo + "€";
    }
}
