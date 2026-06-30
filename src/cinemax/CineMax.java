package cinemax;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe principale del progetto Cinemax
 * Gestisce l'avvio dell'applicazione...
 * @author Cialdini Daniele
 * @version 3.1.2
 */
public class CineMax {
    public static void main(String[] args) {
        String fileCsv = "data/proiezioni.csv";
        List<Proiezioni> elenco = Proiezioni.caricaDaCSV(fileCsv);

        String filePrenotazioni = "data/prenotazioni.txt";
        java.util.Map<String, Prenotazione> mappaPrenotazioni = Prenotazione.caricaMappaPrenotazioni(filePrenotazioni, elenco);

        String fileUtenti = "data/Utenti.txt";

        //carichiamo le liste in memoria leggendo i file txt all'avvio
        ArrayList<Registrati> listaUtenti = Registrati.caricaTutti(fileUtenti);
        Scanner in = new Scanner(System.in);
        boolean ciclo = true;
        while (ciclo){
            System.out.println("BENVENUTO NEL NOSTRO SISTEMA CINEMAX");
            System.out.println("1. Login");
            System.out.println("2. Registrati come nuovo cliente");
            System.out.println("3. Accedi come guest (Utente non registrato)");
            System.out.println("0: Chiudo l'applicazione");
            System.out.print("Scegli un'opzione: ");

            int scelta;
            try {
                scelta = in.nextInt();
                in.nextLine(); //pulizia del buffer obbligatoria dopo  un nextInt()
            } catch (InputMismatchException e){
                scelta = 0;
            }


            switch (scelta) {
                case 1:
                    //LOGIN UTENTE
                    System.out.println("\nLOGIN");
                    System.out.println("1. Cliente");
                    System.out.println("2. Bigliettaio");
                    System.out.println("3. Proiezionista");
                    System.out.print("Seleziona il ruolo con cui vorresti accedere: ");
                    int ruoloScelto;
                    try {
                        ruoloScelto = in.nextInt();
                        in.nextLine();
                    } catch (InputMismatchException e)
                    {
                        ruoloScelto = -1;
                    }

                    String ruoloString = "";

                    switch (ruoloScelto) {
                        case 1:
                            ruoloString = "Cliente";
                            break;
                        case 2:
                            ruoloString = "Bigliettaio";
                            break;
                        case 3:
                            ruoloString = "Proiezionista";
                            break;
                        default:
                            ruoloString = "Sconosciuto";
                            break;
                    }

                    if (ruoloString.equals("Sconosciuto")) {
                        System.out.println("Ruolo non valido");
                        break;
                    }

                    System.out.print("Username: ");
                    String user = in.nextLine();
                    System.out.print("Password: ");
                    String pass = in.nextLine();

                    Utenti utenteTemp = new Registrati("","",user, pass, new Date(), "", "cliente");
                    Password gestoreVerifica = new Password(utenteTemp);
                    String passCifrataDigitata = String.valueOf(gestoreVerifica.hash());

                    Registrati utenteAutenticato = null;
                    for (Registrati u : listaUtenti) {
                        if (u.getUsername().equals(user) && u.getPassword().equals(passCifrataDigitata) && u.getRuolo().equalsIgnoreCase(ruoloString)) {
                            utenteAutenticato = u;
                            break;
                        }
                    }

                    if (utenteAutenticato != null) {
                        System.out.println("Login Utente riuscito! Benvenuto, " + utenteAutenticato.getUsername());

                        switch (utenteAutenticato.getRuolo().toLowerCase()) {
                            case "cliente":
                                menuClienteRegistrato(in, filePrenotazioni, elenco, mappaPrenotazioni, fileCsv);
                                break;
                            case "bigliettaio":
                                menuBigliettaio(in, filePrenotazioni, elenco, mappaPrenotazioni);
                                break;
                            case "proiezionista":
                                menuProiezionista(in, fileCsv, elenco);
                                break;
                            default:
                                System.out.println("Errore nel caricamente del menu profilo. ");
                                break;
                        }
                    } else {
                        System.out.println("Username o Password errati ");
                    }
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
                    Password gestoreCifratura = new Password(nuovo);

                    nuovo.setPassword(String.valueOf(gestoreCifratura.hash()));
                    //lo salviamo fisicamente nel file txt
                    nuovo.salvaSuFile(fileUtenti);
                    listaUtenti.add(nuovo);
                    System.out.println("Registrazione completata con successo");
                    break;
                case 3:
                    menuGuest(in);
                    break;
                case 0:
                    System.out.println("Grazie per aver usato Cinemax! ");
                    ciclo = false;
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
        in.close();
    }

    //Sottomenu

    private static void menuGuest(Scanner in){
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nMenu guest");
            System.out.println("1. cercare proiezioni");
            System.out.println("2. visualizzare i dettagli delle proiezioni");
            System.out.println("3. registrati all'applicazione come cliente");
            System.out.println("4. Torna al menu principale (Esci) ");
            System.out.println("Scegli un'opzione: ");
            int scelta = in.nextInt();
            in.nextLine();

            switch (scelta) {
                case 1:
                    System.out.println("Ricerca proiezioni... ");
                    break;
                case 2:
                    System.out.println("visualizzazione dettagli proiezioni...");
                    break;
                case 3:
                    System.out.println("Uscire dal menu guest e selezionare l'opzione 2 del menu principale per registrarsi. ");
                    inMenu = false;
                    break;
                case 4:
                    System.out.println("Logout effettuato.");
                    inMenu = false;
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
    }

    private static void menuClienteRegistrato(Scanner in, String filePrenotazioni, List<Proiezioni> elenco, java.util.Map<String, Prenotazione> mappaPrenotazioni, String fileCsv){
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nMenu cliente registrato");
            System.out.println("1. inserire una prenotazione");
            System.out.println("2. visualizzare le proprie prenotazioni");
            System.out.println("3. modificare e cancellare le proprie prenotazioni");
            System.out.println("4. Logout");
            System.out.println("Scegli un'opzione: ");
            int scelta = in.nextInt();
            in.nextLine();

            switch (scelta) {
                case 1:
                    Prenotazione.creaPrenotazione(in, filePrenotazioni, elenco, mappaPrenotazioni, fileCsv);
                    break;
                case 2:
                    System.out.println("Visualizzazione delle tue prenotazioni... ");
                break;
                case 3:
                    System.out.println("Modifica/cancellazione prenotazioni...");
                case 4:
                    System.out.println("Logout effettuato.");
                    inMenu = false;
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
    }

    private static void menuProiezionista(Scanner in, String fileCsv, List<Proiezioni> elenco) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nMenu proiezionista");
            System.out.println("1. inserire un film e i dettagli della proiezione");
            System.out.println("2. modificare una proiezione");
            System.out.println("3. eliminare una proiezione");
            System.out.println("4. Logout");
            System.out.println("Scegli un'opzione: ");
            int scelta = in.nextInt();
            in.nextLine();

            switch (scelta) {
                case 1:
                    Proiezioni.aggiungiProiezione(in, fileCsv, elenco);
                    break;
                case 2:
                    Proiezioni.modificaProiezione(fileCsv);
                    break;
                case 3:
                    Proiezioni.eliminaProiezione(fileCsv);

                case 4:
                    System.out.println("Logout effettuato.");
                    inMenu = false;
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
    }

    private static void menuBigliettaio(Scanner in, String filePrenotazioni, List<Proiezioni> elenco, java.util.Map<String, Prenotazione> mappaPrenotazioni) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nMenu bigliettaio");
            System.out.println("1. visuallizare le prenotazioni nella data odierna");
            System.out.println("2. Cercare una prenotazione");
            System.out.println("3. Logout");
            System.out.println("Scegli un'opzione: ");
            int scelta = in.nextInt();
            in.nextLine();

            switch (scelta) {
                case 1:
                    System.out.println("Visualizzazione delle prenotazioni di oggi... ");
                    break;
                case 2:
                    System.out.println("Ricerca di una prenotazione... ");
                    break;
                case 3:
                    System.out.println("Logout effettuato.");
                    inMenu = false;
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
    }
}

