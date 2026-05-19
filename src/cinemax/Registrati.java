package cinemax;

import java.util.ArrayList;
import java.util.Date;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat; //serve per gestire la data in testo
/**
 * Rappresenta il profilo di un Utente registrato all'interno del sistema Cinemax
 * <p>
 *     Questa classe estende la classe Utenti e definisce i servizi che può effettuare, tra cui:
 *     <ul>
 *         <li>La possibilità di inserire una prenotazione</li>
 *         <li>Visualizzare le proprie prenotazioni</li>
 *         <li>Effetuare la modifica e la cancellazione delle proprie prenotazioni</li>
 *         <li>Effetuare il Logout</li>
 *     </ul>
 * </p>
 * @author ...
 * @see Utenti
 * @version 2.0.1
 */

public class Registrati extends Utenti{

    // COSTRUTTORI
    /**
     * Costruisce un nuovo oggetto Registrati con i parametri ereditati
     * dalla classe padre Utenti
     * @param nome
     * @param cognome
     * @param username
     * @param password
     * @param data_di_nascita
     * @param luogoDomicilio
     * @param ruolo
     */
    public Registrati(String nome, String cognome, String username, String password, Date data_di_nascita, String luogoDomicilio, String ruolo) {
        super(nome, cognome, username, password, data_di_nascita, luogoDomicilio, ruolo);
    }

    /**
     * Salva i dati dell'utente corrente su un file di testo
     * <p>
     *     Il metodo scrive una riga contenente i dati dell'utente separati da virgole
     *     Se il file esiste già, i nuovi dati vengono aggiunti alla fine senza sovrascrivere
     *     quelli esistenti. La data di nascita viene formattata nel pattern dd/MM/yyyy
     * </p>
     * @param filepath il percoso del file (data/utenti_registrati.txt) dove salvare le informazioni
     */
    public void salvaSuFile(String filepath){
        try {
            // Scrittore di file. Il "true" serve per agigungere testo senza cancellare il vecchio
            FileWriter fw = new FileWriter(filepath, true);
            PrintWriter pw = new PrintWriter(fw);
            SimpleDateFormat formattatore = new SimpleDateFormat("dd/MM/yyyy");
            String dataFormattata = "data nulla"; //Valore di default se la data fosse nulla

            if (getDataDiNascita() != null){
                dataFormattata = formattatore.format(getDataDiNascita());
            }

            // Scriviamo i dati separati da virgola
            pw.println(getNome() + "," + getCognome() + "," + getUsername() + "," +
                    getPassword() + "," + dataFormattata + "," + getLuogoDomicilio() + "," + getRuolo());

            pw.close();
        } catch (Exception e) {
            System.out.println("Errore durante il salvatagglio dell'utente");
        }
    }

    /**
     * Carica l'elenco degli utenti registrati da un file di testo
     * <p>
     *     Il metodo legge il file riga per riga, ingnorando le righe vuote o i commenti
     *     che iniziano con il carattere #. Per ogni riga valida, ricostruisce un oggetto
     *     Registrazioni effettuando il parsing della data e dei campi stringa
     * </p>
     * <b>NOTE TECNICHE</b>
     * <ul>
     *     <li>In caso di errore nel parsing della data di nascita viene assegnata automaticamente
     *     la data odierna
     *     </li>
     *     <li>Se il file non esiste, il metodo restituisce una lista vuota</li>
     * </ul>
     * @param filepath il percoso del file (data/utenti_registrati.txt) dove salvare le informazioni
     * @return Un ArrayList contenente tutti gli oggetti Registrati validi trovati nel file
     */
    public static ArrayList<Registrati> caricaTutti (String filepath) {
        ArrayList<Registrati> lista = new ArrayList<>();
        SimpleDateFormat formattatore = new SimpleDateFormat("dd/MM/yyyy");

        try {
            BufferedReader br  = new BufferedReader(new FileReader(filepath));
            String linea;

            while ((linea = br.readLine())!= null) {
                if (linea.trim().isEmpty() || linea.startsWith("#")){
                    continue;
                }

                String[] dati = linea.split(",");
                if (dati.length >= 7) {
                    // Convertiamo la stringa di testo letta (es. "19/05/2026") di nuovo in un oggetto Date
                    Date dataNascita;
                    try {
                        dataNascita = formattatore.parse(dati[4]);
                    } catch (Exception e) {
                        dataNascita = new Date(); // In caso di errore di lettura usa la data odierna
                    }

                    Registrati r = new Registrati(dati[0], dati[1], dati[2], dati[3], dataNascita, dati[5], dati[6]);
                    lista.add(r);
                }
            }
            br.close();
        } catch (Exception e) {
            //Se il file non esiste ancora (es. prima registrazione), restituisce la lista vuota
        }
        return lista;
    }
}
