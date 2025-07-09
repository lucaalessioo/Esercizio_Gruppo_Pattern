Piattaforma di Notifiche: Notifiche Personalizzabili
Questo progetto implementa un sistema di notifica flessibile dove gli utenti possono iscriversi per ricevere aggiornamenti, e questi aggiornamenti possono essere dinamicamente personalizzati con funzionalità aggiuntive. Utilizza diversi pattern di progettazione fondamentali: Observer, Decorator e Singleton.

Pattern di Progettazione Utilizzati
Pattern Observer: Questo pattern definisce una dipendenza uno-a-molti tra gli oggetti, in modo che quando un oggetto cambia stato, tutti i suoi dipendenti vengano notificati e aggiornati automaticamente.

Subject: Interfaccia Subject NotificationManager

Observer: Interfaccia Osservatore e classe ConcreteOsservatore

Pattern Decorator: Questo pattern permette di aggiungere comportamenti a un singolo oggetto, sia staticamente che dinamicamente, senza influenzare il comportamento di altri oggetti della stessa classe.

Componente: Interfaccia Osservatore

Decoratore Base: Ab_UserNotificationDecorator

Decoratori Concreti: Time_Stamp_UpperCase e Base_Notifica

Pattern Singleton: Questo pattern assicura che una classe abbia una sola istanza e fornisce un punto di accesso globale a essa.

Classe Singleton: NotificationManager

Struttura del Progetto e Suddivisione dei File
Observer.java (Interfacce e Observer Concreto)
Questo file contiene i componenti centrali per il pattern Observer.

Interfaccia Subject:

Java

interface Subject {
public void registerOsservatore(Osservatore o); // Metodo per registrare un observer
public void removeOsservatore(Osservatore o); // Metodo per rimuovere un observer
public void notifyOsservatore(String message); // Metodo per notificare la lista degli observer
}
Definisce il contratto per gli oggetti che possono essere osservati. Qualsiasi classe che implementa Subject permetterà agli oggetti Osservatore di registrarsi, deregistrarsi ed essere notificati dei cambiamenti.

Interfaccia Osservatore:

Java

interface Osservatore {
public String update(String message); // Metodo per stampare/elaborare la notifica
}
Definisce il contratto per gli oggetti che vogliono essere notificati dei cambiamenti da un Subject. Il metodo update viene chiamato quando si verifica una notifica.

Classe ConcreteOsservatore:

Java

class ConcreteOsservatore implements Osservatore {
private String name;

    public String getName() { /* ... */ }
    public void setName(String name) { /* ... */ }

    public ConcreteOsservatore(String name) {
        this.name = name;
    }

    @Override
    public String update(String message) {
        return name + " ha ricevuto aggiornamento: " + message;
    }

}
Questa è un'implementazione concreta dell'interfaccia Osservatore. Ogni ConcreteOsservatore rappresenta un utente specifico che può ricevere notifiche. Il suo metodo update si limita a formattare e restituire il messaggio ricevuto, indicando quale utente lo ha ricevuto.

NotificationManager.java (Implementazione Subject e Singleton)
Questo file contiene il componente centrale responsabile della gestione degli observer e dell'invio delle notifiche.

Java

import java.util.ArrayList;

public class NotificationManager implements Subject {

    private static NotificationManager istanza; // Istanza Singleton
    private ArrayList<Osservatore> listaUtenti = new ArrayList<>(); // Lista degli osservatori registrati

    public ArrayList<Osservatore> getListaUtenti() { /* ... */ }
    public void setListaUtenti(ArrayList<Osservatore> listaUtenti) { /* ... */ }

    private NotificationManager() { /* Costruttore privato per Singleton */ }

    public static NotificationManager getInstanza() { // Metodo per ottenere l'istanza singleton
        if (istanza == null) {
            istanza = new NotificationManager();
        }
        return istanza;
    }

    public String inviaMessaggio(String messaggio) {
        return messaggio; // Questo metodo sembra solo restituire il messaggio, non inviarlo.
                           // Il suo ruolo principale è probabilmente per la gestione interna del messaggio prima della notifica.
    }

    @Override
    public void notifyOsservatore(String message) {
        for (Osservatore o : listaUtenti) {
            System.out.println(o.update(message)); // Notifica ogni observer
        }
    }

    @Override
    public void registerOsservatore(Osservatore o) {
        listaUtenti.add(o); // Aggiunge un observer alla lista
    }

    @Override
    public void removeOsservatore(Osservatore o) {
        listaUtenti.remove(o); // Rimuove un observer dalla lista
    }

}
NotificationManager ha un duplice scopo:

Subject: Implementa l'interfaccia Subject, permettendo di registrare, rimuovere e notificare gli observer (oggetti Osservatore).

Singleton: Il suo costruttore privato e il metodo statico getInstanza() assicurano che possa esistere solo un'istanza di NotificationManager nell'applicazione, fornendo un punto di accesso globale per la gestione delle notifiche.
Il metodo notifyOsservatore itera su tutti gli observer registrati e chiama il loro metodo update con il messaggio fornito.

MessageDecorator.java (Implementazione del Pattern Decorator)
Questo file contiene il decoratore astratto e le sue implementazioni concrete, permettendo la modifica dinamica dei messaggi di notifica.

Java

import java.time.LocalDateTime;

abstract class Ab_UserNotificationDecorator implements Osservatore {
protected Osservatore osservatore; // L'oggetto observer da decorare

    public Ab_UserNotificationDecorator(Osservatore osservatore) {
        this.osservatore = osservatore;
    }

    @Override
    public String update(String message) {
        return osservatore.update(message); // Delega la chiamata all'observer incapsulato
    }

}

class Time_Stamp_UpperCase extends Ab_UserNotificationDecorator {
public Time_Stamp_UpperCase(Osservatore osservatore) {
super(osservatore);
}

    @Override
    public String update(String message) {
        // Aggiunge un timestamp e converte il messaggio in maiuscolo
        return LocalDateTime.now() + " " + super.update(message).toUpperCase();
    }

}

class Base_Notifica extends Ab_UserNotificationDecorator {
public Base_Notifica(Osservatore osservatore) {
super(osservatore);
}

    @Override
    public String update(String message) {
        // Aggiunge un prefisso "Notifica :" al messaggio
        return "Notifica : " + super.update(message);
    }

}
Ab_UserNotificationDecorator (Decoratore Astratto):
Questa classe astratta implementa l'interfaccia Osservatore e mantiene un riferimento a un oggetto Osservatore. Il suo metodo update si limita a delegare la chiamata all'Osservatore incapsulato. Questa classe funge da base per tutti i decoratori concreti.

Time_Stamp_UpperCase (Decoratore Concreto):
Questo decoratore aggiunge un timestamp al messaggio di notifica e converte l'intero messaggio in maiuscolo. Lo fa chiamando super.update(message) (che chiama il metodo update dell'Osservatore incapsulato o di un altro decoratore) e quindi premettendo l'ora corrente e convertendo il risultato in maiuscolo.

Base_Notifica (Decoratore Concreto):
Questo decoratore aggiunge un prefisso "Notifica :" al messaggio di notifica. Similmente a Time_Stamp_UpperCase, chiama super.update(message) e poi aggiunge la sua specifica formattazione.

Main.java (Classe Principale e Interfaccia Utente)
Questo file contiene la logica principale dell'applicazione, gestendo l'interazione con l'utente tramite un menu a riga di comando e orchestrando le operazioni tra i diversi pattern.

Java

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NotificationManager notMan = NotificationManager.getInstanza(); // Singleton

        while (true) {
            System.out.println("\nBenvenuto, scegli una di queste operazioni:");
            System.out.println("1. Registra nuovo utente");
            System.out.println("2. Rimuovi utente per nome");
            System.out.println("3. Invia notifica a tutti");
            System.out.println("4. Visualizza utenti registrati");
            System.out.println("5. Esci");
            System.out.print("Scelta: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    System.out.print("Nome utente: ");
                    String nome = scanner.nextLine();
                    Osservatore utente = new ConcreteOsservatore(nome);

                    utente = scegliDecoratori(scanner, utente); // Applica decoratori (Decorator)
                    notMan.registerOsservatore(utente); // Registra l'utente (Observer)
                    System.out.println("Utente registrato con successo!");
                    break;

                case "2":
                    if (notMan.getListaUtenti().isEmpty()) {
                        System.out.println("Nessun utente da rimuovere.");
                        break;
                    }
                    System.out.print("Nome utente da rimuovere: ");
                    String nomeDaRimuovere = scanner.nextLine();

                    Osservatore daRimuovere = null;
                    for (Osservatore o : notMan.getListaUtenti()) {
                        // Recupera il nome effettivo (anche se è decorato)
                        if (recuperaNome(o).equalsIgnoreCase(nomeDaRimuovere)) {
                            daRimuovere = o;
                            break;
                        }
                    }
                    if (daRimuovere != null) {
                        notMan.removeOsservatore(daRimuovere);
                        System.out.println("Utente " + nomeDaRimuovere + " rimosso.");
                    } else {
                        System.out.println("Utente non trovato.");
                    }
                    break;

                case "3":
                    if (notMan.getListaUtenti().isEmpty()) {
                        System.out.println("Nessun utente registrato! Registrane uno prima.");
                        break;
                    }
                    System.out.print("Scrivi il messaggio da inviare: ");
                    String messaggio = scanner.nextLine();
                    notMan.notifyOsservatore(messaggio); // Notifica tutti (Observer)
                    break;

                case "4":
                    if (notMan.getListaUtenti().isEmpty()) {
                        System.out.println("Nessun utente registrato.");
                    } else {
                        System.out.println("Utenti attualmente registrati:");
                        for (Osservatore o : notMan.getListaUtenti()) {
                            System.out.println("- " + recuperaNome(o)); // Mostra il nome reale
                        }
                    }
                    break;

                case "5":
                    System.out.println("Arrivederci!");
                    return;

                default:
                    System.out.println("Scelta non valida, riprova.");
            }
        }
    }

    /**
     * Permette all'utente di selezionare (in modo iterativo) quali decoratori
     * applicare all'utente.
     * Si possono applicare più decoratori in ordine, uno dopo l'altro.
     * Pattern Decorator: permette di aggiungere comportamenti dinamicamente.
     */
    private static Osservatore scegliDecoratori(Scanner scanner, Osservatore utente) {
        Osservatore result = utente;
        boolean continua = true;

        while (continua) {
            System.out.println("\nPersonalizza le notifiche dell'utente:");
            System.out.println("1. Aggiungi timestamp + MAIUSCOLO");
            System.out.println("2. Aggiungi prefisso (tipo 'Notifica: ')");
            System.out.println("3. Nessun altro decorator (continua)");
            System.out.print("Scegli (puoi applicare più decoratori in ordine, uno alla volta): ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    result = new Time_Stamp_UpperCase(result);
                    System.out.println("Aggiunto timestamp+maiuscolo.");
                    break;
                case "2":
                    result = new Base_Notifica(result);
                    System.out.println("Aggiunto prefisso.");
                    break;
                case "3":
                    continua = false;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
        return result;
    }

    /**
     * Funzione ricorsiva che "scava" tra i decoratori fino a trovare il
     * ConcreteOsservatore e recupera il nome base dell'utente.
     * Serve per identificare realmente un utente anche se sono stati applicati più
     * livelli di decorazione.
     */
    private static String recuperaNome(Osservatore observer) {
        if (observer instanceof Ab_UserNotificationDecorator) {
            return recuperaNome(((Ab_UserNotificationDecorator) observer).osservatore);
        }
        if (observer instanceof ConcreteOsservatore) {
            return ((ConcreteOsservatore) observer).getName();
        }
        return null;
    }

}
Descrizione del Funzionamento del Main
La classe Main fornisce un'interfaccia a riga di comando per interagire con la piattaforma di notifiche.

Menu Principale: Presenta un menu all'utente con le seguenti opzioni:

1. Registra nuovo utente: Permette di creare un nuovo ConcreteOsservatore e di registrarlo con il NotificationManager. Durante la registrazione, viene invocato il metodo scegliDecoratori per consentire all'utente di personalizzare le notifiche del nuovo utente applicando uno o più Decorator.

2. Rimuovi utente per nome: Richiede il nome dell'utente da rimuovere. Utilizza il metodo recuperaNome per trovare l'Osservatore corretto nella lista del NotificationManager, anche se è stato decorato, e lo rimuove.

3. Invia notifica a tutti: Chiede un messaggio all'utente e lo invia a tutti gli Osservatore registrati tramite il NotificationManager. Ogni Osservatore riceverà il messaggio elaborato in base ai decoratori applicati.

4. Visualizza utenti registrati: Elenca i nomi di tutti gli utenti attualmente registrati, utilizzando recuperaNome per mostrare il nome base anche per gli utenti decorati.

5. Esci: Termina l'applicazione.

scegliDecoratori(Scanner scanner, Osservatore utente):
Questa funzione privata interattiva è il cuore dell'applicazione del Decorator Pattern. Chiede all'utente quali decoratori (Time_Stamp_UpperCase o Base_Notifica) vuole applicare all'Osservatore appena creato o a quello esistente. L'utente può applicare più decoratori in sequenza, costruendo una "catena" di funzionalità aggiuntive.

recuperaNome(Osservatore observer):
Questa funzione ricorsiva è fondamentale per il corretto funzionamento della rimozione e visualizzazione degli utenti. Dato che un ConcreteOsservatore può essere incapsulato da uno o più Ab_UserNotificationDecorator, questa funzione "scava" ricorsivamente all'interno della catena di decoratori fino a raggiungere l'istanza di ConcreteOsservatore e recuperarne il nome originale. Questo assicura che gli utenti possano essere identificati e gestiti per nome anche se le loro notifiche sono state personalizzate.

Come Funziona
Avvio: L'applicazione si avvia e ottiene l'unica istanza del NotificationManager (grazie al Singleton Pattern).

Interazione Utente: L'utente interagisce tramite il menu a riga di comando.

Registrazione Utente: Quando un nuovo utente viene registrato, il metodo scegliDecoratori permette di personalizzare la sua esperienza di notifica. Un ConcreteOsservatore può essere "avvolto" da decoratori come Time_Stamp_UpperCase o Base_Notifica, aggiungendo dinamicamente funzionalità al suo metodo update.

Invio Notifiche: Quando un messaggio viene inviato, il NotificationManager (il Subject) notifica tutti gli Osservatore registrati. Se un Osservatore è stato decorato, la chiamata al metodo update passerà attraverso la catena di decoratori, ciascuno aggiungendo la propria logica (ad esempio, timestamp, maiuscolo, prefisso) prima che il messaggio finale venga elaborato dall'ConcreteOsservatore sottostante.

Gestione Utenti: I metodi registerOsservatore e removeOsservatore del NotificationManager gestiscono la lista degli utenti. La funzione recuperaNome è cruciale per la gestione degli utenti decorati, permettendo di riferirsi a loro tramite il loro nome base, indipendentemente dalla personalizzazione applicata.

Questo Main dimostra in modo eccellente come i pattern Observer, Decorator e Singleton lavorino insieme per creare un'applicazione flessibile, estendibile e facile da usare.
