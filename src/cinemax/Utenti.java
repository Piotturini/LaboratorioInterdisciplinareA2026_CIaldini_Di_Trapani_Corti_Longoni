    package cinemax;

    import java.util.Date;

    /**
     * Classe base che rappresenta un utente generico all'interno del sistema Cinemax
     * <p>
     *     Questa classe funge da superclasse per tutte le tipologie di Utenti (Bigliettai, Proiezionisti, Clienti registrati e Clienti non registrati
     *     raggrupando le infomazioni anagrafiche e le credenziali di accesso comune
     * </p>
     * @author ...
     * @version 1.0.1
     */
    public abstract class Utenti {

        // CAMPI
        private String nome;
        private String cognome;
        private String username;
        private String password;
        private Date dataDiNascita;
        private String luogoDomicilio;
        private String ruolo;

        // COSTRUTTORI
        /**
         * Costruisce un oggetto che rappresenta l'Utente inizializzando
         * tutti i campi anagrafici e di accesso
         * @param nome Il nome dell'utente
         * @param cognome Il cognome dell'utente
         * @param username Username scelta per il login
         * @param password Password scelta per il login
         * @param dataDiNascita Data di nascita dell'utente espresso come oggetto Date
         * @param luogoDomicilio Stringa contenente il domicilio
         * @param ruolo Stringa che definisce il livello di accesso (Registrato, non Registrato)
         */
        public Utenti(String nome, String cognome, String username, String password, Date dataDiNascita, String luogoDomicilio, String ruolo) {
            this.nome = nome;
            this.cognome = cognome;
            this.username = username;
            this.password = password;
            this.dataDiNascita = dataDiNascita;
            this.luogoDomicilio = luogoDomicilio;
            this.ruolo = ruolo;
        }

        public Utenti(){
            
        }

        // METODI SETTER

        /**
         * Aggiorna il nome dell'utente
         * @param nome Nuovo nome da assegnare
         */
        public void setNome(String nome) {
            this.nome = nome;
        }

        /**
         * Aggiorna il cognome dell'utente
         * @param cognome Nuovo cognome da assegnare
         */
        public void setCognome(String cognome) {
            this.cognome = cognome;
        }

        /**
         * Imposta il nuovo username per il login
         * @param username Nuovo username
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * Imposta la nuova password per il login
         * @param password Nuova password di accesso
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * Imposta o modifica la data di nascita
         * @param dataDiNascita Oggetto Date con la nuova data
         */
        public void setDataDiNascita(Date dataDiNascita) {
            this.dataDiNascita = dataDiNascita;
        }

        /**
         * Aggiorna le informazioni sul domicilio
         * @param luogoDomicilio Nuova stringa del domicilio
         */
        public void setLuogoDomicilio(String luogoDomicilio) {
            this.luogoDomicilio = luogoDomicilio;
        }

        /**
         * Modifica il ruolo dell'utente all'interno del sistema
         * @param ruolo Nuovo ruolo assegnato
         */
        public void setRuolo(String ruolo) {
            this.ruolo = ruolo;
        }

        // METODI GETTER

        /**
         * Restituisce il nome dell'utente
         * @return Stringa del nome
         */
        public String getNome() {
            return nome;
        }

        /**
         * Restituisce il cognome dell'utente
         * @return Stringa del ccognome
         */
        public String getCognome() {
            return cognome;
        }

        /**
         * Restituisce l'username utilizzato per l'accesso
         * @return Lo username dell'account
         */
        public String getUsername() {
            return username;
        }

        /**
         * Restituisce la password dell'utente
         * @return La password corrente
         */
        public String getPassword() {
            return password;
        }

        /**
         * Restituisce la data di nascita dell'utente
         * @return Un oggetto Date contenente la data di nascita
         */
        public Date getDataDiNascita() {
            return dataDiNascita;
        }

        /**
         * Restituisce il luogo di domicilio dell'utente
         * @return Stringa del domicilio
         */
        public String getLuogoDomicilio() {
            return luogoDomicilio;
        }

        /**
         * Restituisce il ruolo dell'utente
         * @return Il ruolo dell'utente nel sistema
         */
        public String getRuolo() {
            return ruolo;
        }
    }
