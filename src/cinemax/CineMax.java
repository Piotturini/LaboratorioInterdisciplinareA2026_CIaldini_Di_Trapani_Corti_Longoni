package cinemax;

import java.util.List;

/**
 * Classe principale del progetto Cinemax
 * Gestisce l'avvio dell'applicazione...
 * @author ...
 * @version 1.0
 */
public class CineMax {
    public static void main(String[] args) {
        String fileCsv = "data/proiezioni.csv";
        List<Proiezioni> elenco = Proiezioni.caricaDaCSV(fileCsv);

        System.out.println("ELENCO PROIEZIONI CARICATE");
        for (Proiezioni p : elenco) {
            System.out.println(p);
        }

        System.out.println("Totale proiezioni in memoria: " + elenco.size());
    }
}
