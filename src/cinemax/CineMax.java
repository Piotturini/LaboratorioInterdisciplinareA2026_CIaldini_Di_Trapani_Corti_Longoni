package cinemax;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principale del progetto Cinemax
 * Gestisce l'avvio dell'applicazione...
 * @author ...
 * @version 2.0
 */
public class CineMax {
    public static void main(String[] args) {
        String fileCsv = "data/proiezioni.csv";
        List<Proiezioni> elenco = Proiezioni.caricaDaCSV(fileCsv);

        String fileUtenti = "data/utenti_registrati.txt";
        String fileBigliettai = "data/bigliettai.txt";

        //carichiamo le liste in memoria leggendo i file txt all'avvio
        ArrayList<Registrati> listaUtenti = Registrati.caricaTutti(fileUtenti);

        System.out.println("BENVENUTO NEL NOSTRO CINEMAX");
        System.out.println("Scegli un'opzione (1: login utente || 2: registrati || 3: login bigliettaio || 4: informazioni sulle proiezioni):");

        Scanner in = new Scanner(System.in);
        int scelta = in.nextInt();
        in.nextLine(); //pulizia del buffer obbligatoria dopo  un nextInt()

        switch (scelta) {
            case 1:
                //LOGIN UTENTE
                System.out.print("Username: ");
                String user = in.nextLine();
                System.out.print("Password: ");
                String pass = in.nextLine();

                boolean trovato = false;
                for (Registrati u : listaUtenti) {
                    if (u.getUsername().equals(user) && u.getPassword().equals(pass)) {
                        trovato = true;
                        break;
                    }
                }
                if (trovato) System.out.println("Login Utente riuscito!");
                else System.out.println("Username o Password errati.");
                break; //Termina il case 1
            case 2:
                //Registrazione utente
                System.out.print("Nome "); String nome = in.nextLine();
                System.out.print("Cognome "); String cognome = in.nextLine();
                System.out.print("Scegli Username: "); String nuovoUser = in.nextLine();
                System.out.print("Scegli Password: "); String nuovaPass = in.nextLine();

                //chiediamo la data di nascita all'utente in formato testo
                System.out.print("Data di nascita (gg/mm/aaaa): ");
                String dataInput = in.nextLine();
                System.out.print("Città di domicilio:  "); String citta = in.nextLine();

                //Convertiamo il testo inserito dall'utente in un vero oggetto Date
                Date dataNascitaUtente;
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    dataNascitaUtente = sdf.parse(dataInput);
                } catch (Exception e) {
                    System.out.println("Formato data non valido. Verrà impostata la data odierna");
                    dataNascitaUtente = new Date();
                }

                //creiamo l'oggetto usando i dati inseriti
                Registrati nuovo = new Registrati(nome, cognome, nuovoUser, nuovaPass, dataNascitaUtente, citta, "Cliente");

                //lo salviamo fisicamente nel file txt
                nuovo.salvaSuFile(fileUtenti);
                System.out.println("Registrazione completata con successo");
                break; //Termina il case 2
            case 3:
                System.out.println("Login Bigliettaio (da implementare)");
                break;
            case 4:
                //
                System.out.println("Accesso Guest: ricerca proiezioni");
                Proiezioni.cercaProiezione(fileCsv);
                break;

            default:
                System.out.println("Scelta non valida");
        }
        in.close();
    }
}
