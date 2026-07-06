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

            int scelta = leggiIntero(in, "Scegli un'opzione");


            switch (scelta) {
                case 1:
                    //LOGIN UTENTE
                    System.out.println("\nLOGIN");
                    System.out.println("1. Cliente");
                    System.out.println("2. Bigliettaio");
                    System.out.println("3. Proiezionista");
                    int ruoloScelto = leggiIntero(in, "Seleziona il ruolo con cui vorresti accedere: ");
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
                                menuClienteRegistrato(in, filePrenotazioni, elenco, mappaPrenotazioni, fileCsv, utenteAutenticato);
                                break;
                            case "bigliettaio":
                                menuBigliettaio(in, filePrenotazioni, elenco, mappaPrenotazioni, fileCsv, listaUtenti);
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
                    registraNuovoCliente(in, fileUtenti, listaUtenti);
                    break;
                case 3:
                    menuGuest(in, fileCsv, elenco, fileUtenti, listaUtenti);
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

    public static int leggiIntero(Scanner in, String prompt){
        while (true) {
            System.out.print(prompt);
            try{
                return Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e){
                System.out.println("Errore: inserisci un numero intero valido.");
            }
        }
    }

    private static void registraNuovoCliente(Scanner in, String fileUtenti, List<Registrati> listaUtenti) {
        System.out.println("\nRegistrazione nuovo cliente");
        System.out.print("Nome ");
        String nome = in.nextLine().trim();
        System.out.print("Cognome ");
        String cognome = in.nextLine().trim();

        String nuovoUser = "";
        while (true) {
            System.out.print("Scegli Username");
            nuovoUser = in.nextLine().trim();
            if (nuovoUser.isEmpty()){
                System.out.println("Errore: lo username non può essere vuoto. ");
                continue;
            }
            boolean duplicato = false;
            for (Registrati u: listaUtenti) {
                if (u.getUsername().equalsIgnoreCase(nuovoUser)) {
                    duplicato = true;
                    break;
                }
            }
            if (duplicato){
                System.out.println("Errore: questo username è già registrato nel sistema!");
            } else {
                break;
            }
        }
        String nuovaPass = "";
        while (true) {
            System.out.print("Scegli Password: ");
            nuovaPass = in.nextLine().trim();
            if (nuovaPass.isEmpty()){
                System.out.println("Errore: la password non può essere vuota.");
                continue;
            }
            break;
        }

        System.out.print("Data di nascita (gg/mm/aaaa, premi INVIO per saltare): ");
        String dataInput = in.nextLine().trim();

        Date dataNascitaUtente = null;
        if (!dataInput.isEmpty()) {
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dataNascitaUtente = sdf.parse(dataInput);
            } catch (Exception e) {
                System.out.println("Formato data non valido. Verrà impostata la data odierna");
                dataNascitaUtente = new Date();
            }
        } else {
            dataNascitaUtente = new Date();
        }

        System.out.print("Città di domicilio: ");
        String citta = in.nextLine().trim();

        Registrati nuovo = new Registrati(nome, cognome, nuovoUser, nuovaPass, dataNascitaUtente, citta, "Cliente");
        Password gestoreCifratura = new Password(nuovo);

        nuovo.setPassword(String.valueOf(gestoreCifratura.hash()));

        nuovo.salvaSuFile(fileUtenti);
        listaUtenti.add(nuovo);
        System.out.println("Registrazione completata con successo! Ora puoi effettuare il login");
    }

    private static void menuGuest(Scanner in, String fileCsv, List<Proiezioni> elenco, String fileUtenti, List<Registrati> listaUtenti) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nMenu guest");
            System.out.println("1. cercare proiezioni");
            System.out.println("2. visualizzare i dettagli di una proiezione");
            System.out.println("3. registrati all'applicazione come cliente");
            System.out.println("4. Torna al menu principale ");

            int scelta = leggiIntero(in, "Scegli un'opzione: ");

            switch (scelta) {
                case 1:
                    System.out.print("Inserisci il titolo del film (anche parziale): ");
                    String titoloCercato = in.nextLine().trim().toLowerCase();
                    System.out.println("\nProiezioni in programmazione per \"" + titoloCercato + "\":");
                    int ris = 0;
                    for (int i=0; i < elenco.size(); i++){
                        Proiezioni pr = elenco.get(i);
                        if (pr.getTitolo().toLowerCase().contains(titoloCercato)){
                            System.out.println((i + 1) + ". DATA: " + pr.getDataOra() + " | FILM: " + pr.getTitolo() + " | GENERE: " + pr.getGenere() + " | PREZZO: " + pr.getPrezzo() + " €");
                            ris++;
                        }
                    }
                    if (ris == 0) {
                        System.out.println("Nessun film in programmazione corrisponde alla tua ricerca.");
                    }
                    break;

                case 2:
                    System.out.print("Inserisci il numero progressivo della proiezion vista a schermo: ");
                    int prog = leggiIntero(in, "Numero proiezione: ");
                    if (prog > 0 && prog <= elenco.size()){
                        Proiezioni.visualizzaProiezione(elenco.get(prog - 1));
                    } else {
                        System.out.println("Scelta non valida.");
                    }
                    break;
                case 3:
                    registraNuovoCliente(in, fileUtenti, listaUtenti);
                    inMenu = false;
                    break;
                case 4:
                    System.out.println("Ritorno al menu principale");
                    inMenu = false;
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
    }

    private static void menuClienteRegistrato(Scanner in, String filePrenotazioni, List<Proiezioni> elenco, java.util.Map<String, Prenotazione> mappaPrenotazioni, String fileCsv, Registrati utenteLoggato){
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nMenu cliente registrato (" + utenteLoggato.getUsername() + ")");
            System.out.println("1. Effettuare una prenotazione");
            System.out.println("2. visualizzare le proprie prenotazioni");
            System.out.println("3. modificare e cancellare le proprie prenotazioni");
            System.out.println("4. Logout");
            System.out.println("Scegli un'opzione: ");

            int scelta = leggiIntero(in, "Scegli un'opzione: ");

            switch (scelta) {
                case 1:
                    Prenotazione.creaPrenotazione(in, filePrenotazioni, elenco, mappaPrenotazioni, fileCsv, utenteLoggato.getUsername());
                    break;
                case 2:
                    System.out.println("\nLE TUE PRENOTAZIONI:");
                    int count = 0;
                    for (Prenotazione p: mappaPrenotazioni.values()) {
                        if (p.getUsernamenCliente().equalsIgnoreCase(utenteLoggato.getUsername())) {
                            System.out.println("- Codice: " + p.getCodice() + " | Film: \"" + p.getFilm().getTitolo() + "\" | Data: " + p.getProiezione().getDataOra() + " | Posti: " + p.getNumBiglietti() + " | Costo totale: " + String.format(Locale.US, "%.2f", p.getCostoTotale()) + " €");
                            count++;
                        }
                    }
                    if (count == 0) {
                        System.out.println(" Non hai effettuato alcuna prenotazione.");
                    }
                    break;
                case 3:
                    System.out.println("\nGESTIONE PRENOTAZIONE");
                    System.out.println("1. Modifica prenotazione (Cambio film/posti)");
                    System.out.println("2. Cancella prenotazione (Rimborso)");
                    System.out.println("3. Indietro");
                    int sottoscelta = leggiIntero(in, "Scegli un'opzione");
                    if (sottoscelta == 1) {
                        Prenotazione.modificaPrenotazione(in, filePrenotazioni, elenco, mappaPrenotazioni, fileCsv);
                    } else if (sottoscelta == 2) {
                        Prenotazione.eliminaPrenotazione(in, filePrenotazioni, elenco, mappaPrenotazioni, utenteLoggato.getUsername());
                    }
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

    private static void menuProiezionista(Scanner in, String fileCsv, List<Proiezioni> elenco) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nMenu proiezionista");
            System.out.println("1. inserire un film e i dettagli della proiezione");
            System.out.println("2. modificare una proiezione");
            System.out.println("3. eliminare una proiezione");
            System.out.println("4. Logout");

            int scelta = leggiIntero(in, "Scegli un'opzione: ");

            switch (scelta) {
                case 1:
                    Proiezioni.aggiungiProiezione(in, fileCsv, elenco);
                    break;
                case 2:
                    Proiezioni.modificaProiezione(fileCsv, elenco);
                    break;
                case 3:
                    Proiezioni.eliminaProiezione(fileCsv, elenco);

                case 4:
                    System.out.println("Logout effettuato.");
                    inMenu = false;
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
    }

    private static void menuBigliettaio(Scanner in, String filePrenotazioni, List<Proiezioni> elenco, java.util.Map<String, Prenotazione> mappaPrenotazioni, String fileCsv, List<Registrati> listaUtenti) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\nMenu bigliettaio");
            System.out.println("1. visuallizare le prenotazioni nella data odierna");
            System.out.println("2. Cercare una prenotazione");
            System.out.println("3. Logout");

            int scelta = leggiIntero(in, "Scegli un'opzione: ");

            switch (scelta) {
                case 1:
                     System.out.println("\nPRENOTAZIONI DATA ODIERNA: ");
                     java.time.LocalDate oggi = java.time.LocalDate.now();
                     java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                     int count = 0;
                     for (Prenotazione p: mappaPrenotazioni.values()) {
                         try {
                             String dataPulita = p.getProiezione().getDataOra().replace("\"", "").trim();
                             java.time.LocalDateTime dt = java.time.LocalDateTime.parse(dataPulita, formatter);
                             if (dt.toLocalDate().equals(oggi)) {
                                 visualizzaDettaglioPrenotazione(p, listaUtenti);
                                 count++;
                             }
                         } catch (Exception e) {

                         }
                         if (count == 0) {
                             System.out.println("Nessuna prenotazione programmata per oggi.");
                         }
                     }
                    break;
                case 2:
                    Prenotazione.CercaPrenotazione(in, filePrenotazioni, elenco, mappaPrenotazioni, fileCsv, listaUtenti);
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

    public static Registrati trovaUtentePerUsername (String username, List<Registrati> listaUtenti) {
        for (Registrati u: listaUtenti) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public static void visualizzaDettaglioPrenotazione(Prenotazione p, List<Registrati> listaUtenti){
        Registrati u = trovaUtentePerUsername(p.getUsernamenCliente(), listaUtenti);
        String intestatario = (u != null) ? (u.getNome() + " " + u.getCognome()) : p.getUsernamenCliente();
        System.out.println("------------------------------------------------");
        System.out.println("Codice Prenotazione: " + p.getCodice());
        System.out.println("Cliente:             " + intestatario + " (" + p.getUsernamenCliente() + ")");
        System.out.println("Film:                " + p.getFilm().getTitolo());
        System.out.println("Data e ora show:     " + p.getProiezione().getDataOra());
        System.out.println("Blietti Prenotati:   " + p.getNumBiglietti());
        System.out.println("Costo Unitario:      " + String.format(Locale.US, "%.2f", p.getFilm().getGenere() + " €"));
        System.out.println("Costo Totale:        " + String.format(Locale.US, "%.2f", p.getCostoTotale() + " €"));
        System.out.println("------------------------------------------------");
    }
}

