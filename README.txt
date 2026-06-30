CineMax - Laboratorio Interdisciplinare A
a.a. 2025/2026
Versione 1.0

REQUISITI DI SISTEMA
--------------------
* Java Development Kit (JDK) installato.
* Sistema operativo compatibile con Java (Windows, Linux, macOS).
* Nessuna dipendenza da database o server esterni.

INSTALLAZIONE
-------------
1. Scaricare o clonare la repository da Github.
2. Verificare che la cartella 'data/' sia presente nella directory radice con i file:
   proiezioni.csv, Utenti.txt, prenotazioni.txt.

STRUTTURA DELLA REPOSITORY
--------------------------
* src/       : Contiene il codice sorgente dell'applicazione (package cinemax).
* bin/       : Contiene il file eseguibile .jar.
* data/      : Contiene i file di dati (proiezioni.csv, Utenti.txt, prenotazioni.txt).
* doc/       : Contiene la documentazione (Manuale Utente, Manuale Tecnico, JavaDoc).
* lib/       : Contiene eventuali librerie esterne.
* autori.txt : Contiene i dati anagrafici e le matricole dei membri del gruppo.

COMPILAZIONE (da riga di comando)
---------------------------------
1. Aprire il terminale e posizionarsi nella cartella radice del progetto.
2. Assicurarsi che esista la cartella 'bin' (in caso contrario, crearla).
3. Compilare i file .java eseguendo il comando:
   javac -d bin src/cinemax/*.java

CREAZIONE DELL'ESEGUIBILE (.jar)
--------------------------------
Dalla cartella radice del progetto, eseguire:
    jar cfe bin/CineMax.jar cinemax.CineMax -C bin .

ESECUZIONE
----------
Dalla cartella radice del progetto, eseguire:
    java -jar bin/CineMax.jar

NOTA: il programma legge e scrive i dati nella cartella 'data/'.
Eseguire sempre il .jar dalla directory radice affinché i percorsi
relativi vengano risolti correttamente.