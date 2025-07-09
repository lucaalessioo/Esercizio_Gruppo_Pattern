// Classe principale che gestisce il menu e le funzionalità della piattaforma notifiche

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Crea uno Scanner per input da tastiera
        Scanner scanner = new Scanner(System.in);
        // Ottieni l'istanza Singleton del NotificationManager (gestore notifiche)
        NotificationManager notMan = NotificationManager.getInstanza();

        // Loop principale del menu
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
                    // REGISTRAZIONE NUOVO UTENTE
                    System.out.print("Nome utente: ");
                    String nome = scanner.nextLine();
                    Osservatore utente = new ConcreteOsservatore(nome);

                    // Applica decoratori scelti dall'utente (pattern Decorator)
                    utente = scegliDecoratori(scanner, utente);

                    // Registra l'utente (pattern Observer)
                    notMan.registerOsservatore(utente);
                    System.out.println("Utente registrato con successo!");
                    break;

                case "2":
                    // RIMOZIONE UTENTE PER NOME
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
                    // INVIO NOTIFICA A TUTTI GLI UTENTI REGISTRATI
                    if (notMan.getListaUtenti().isEmpty()) {
                        System.out.println("Nessun utente registrato! Registrane uno prima.");
                        break;
                    }
                    System.out.print("Scrivi il messaggio da inviare: ");
                    String messaggio = scanner.nextLine();

                    // Notifica tutti (Observer)
                    notMan.notifyOsservatore(messaggio);
                    break;

                case "4":
                    // VISUALIZZA TUTTI GLI UTENTI REGISTRATI
                    if (notMan.getListaUtenti().isEmpty()) {
                        System.out.println("Nessun utente registrato.");
                    } else {
                        System.out.println("Utenti attualmente registrati:");
                        for (Osservatore o : notMan.getListaUtenti()) {
                            // Mostra il nome reale (ignora i decoratori)
                            System.out.println("- " + recuperaNome(o));
                        }
                    }
                    break;

                case "5":
                    // USCITA DAL PROGRAMMA
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
            // Se è un decorator, scava ancora
            return recuperaNome(((Ab_UserNotificationDecorator) observer).osservatore);
        }
        if (observer instanceof ConcreteOsservatore) {
            // Se è il ConcreteOsservatore, torna il nome
            return ((ConcreteOsservatore) observer).getName();
        }
        return null;
    }
}
