package cinemax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce le proiezioni all'interno del sistema Cinemax
 * @author ...
 * @version 1.0.1
 */

public class Proiezioni {

    // CAMPI
    private String dataOra;
    private String titolo;
    private String genere;
    private String regista;
    private int anno;
    private int durata;
    private int etaMinima;
    private double prezzo;

    // COSTRUTTORI
    /**
     * Costruisce un oggetto che rappresenta tutte le Proiezioni inizializzando
     * tutti i campi di informazione relativi ai film
     * @param dataOra La data e l'ora del film
     * @param titolo Il titolo del film
     * @param genere Il genere del film
     * @param regista Il nome del regista
     * @param anno L'anno di pubblicazione
     * @param durata La durata in minuti
     * @param etaMinima L'età minima per la visione
     * @param prezzo Il costo del biglietto
     */
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

    // METODI

    /**
     * Carica un eleneco di proiezioni di un film in formato CSV
     * <p>
     *    Il metodo legge il file riga per riga, ignora l'intestazione
     *    e utilizza un espressione ottimizzata per gestire lo le virgole
     *    con lo split
     * </p>
     * <strong>Logica di eleborazione:</strong>
     * <ul>
     *     <li>Salto della riga</li>
     *     <li>Parsing dei campi con gestione dei delimitatori</li>
     *     <li>Conversione dei tipi di dato</li>
     *     <li>Gestione degli errori: se una riga non è corretta, viene saltata senza interrompere
     *     il caricamento del file</li>
     * </ul>
     * @param percorsoFile
     * @return Una {@link List} di oggetti {@link Proiezioni} caricati correttamente
     * @throws IOException Se si verifica un errore di accesso al file
     */
    public static List<Proiezioni> caricaDaCSV(String percorsoFile) {
        List<Proiezioni> listaP = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String riga;
            br.readLine();

            while ((riga = br.readLine()) != null) {
                try {
                    if (riga.trim().isEmpty()) continue;
                    String[] d = riga.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                    if (d.length >= 8) {
                        for (int i = 0; i < d.length; i++) {
                            d[i] = d[i].replace("^\"|\"$", "").trim();
                        }

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
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Errore riga ignorata: " + riga + " -> " + e.getMessage());
                }
            }
        } catch (IOException e){
            System.out.println("errore nella lettura del file" + e.getMessage());
        }
        return listaP;
    }

    /**
     * Restiuisce una rappresentazione testuale formattata della proiezione
     * @return Una stringa contenente data/ora, titolo, genere e prezzo (formattati)
     */
    @Override
    public String toString() {
        // Usiamo il simbolo "|" per separare i dati in modo ordinato
        return "DATA: " + dataOra +
                " | FILM: " + titolo +
                " | GENERE: " + genere +
                " | PREZZO: " + prezzo + "€";
    }
}
