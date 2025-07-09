import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NotificationManager notMan = NotificationManager.getInstanza();

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
                    Observer utente = new ConcreteObserver(nome);

                    // Scegli i decorator da applicare
                    utente = scegliDecoratori(scanner, utente);

                    notMan.registerObserver(utente);
                    System.out.println("Utente registrato con successo!");
                    break;

                case "2":
                    if (notMan.listaUtenti.isEmpty()) {
                        System.out.println("Nessun utente da rimuovere.");
                        break;
                    }
                    System.out.print("Nome utente da rimuovere: ");
                    String nomeDaRimuovere = scanner.nextLine();

                    Observer daRimuovere = null;
                    for (Observer o : notMan.listaUtenti) {
                        // Prova a recuperare il nome (ricorsivo se decorato)
                        if (recuperaNome(o).equalsIgnoreCase(nomeDaRimuovere)) {
                            daRimuovere = o;
                            break;
                        }
                    }
                    if (daRimuovere != null) {
                        notMan.removeObserver(daRimuovere);
                        System.out.println("Utente " + nomeDaRimuovere + " rimosso.");
                    } else {
                        System.out.println("Utente non trovato.");
                    }
                    break;

                case "3":
                    if (notMan.listaUtenti.isEmpty()) {
                        System.out.println("Nessun utente registrato! Registrane uno prima.");
                        break;
                    }
                    System.out.print("Scrivi il messaggio da inviare: ");
                    String messaggio = scanner.nextLine();

                    notMan.notifyObservers(messaggio);
                    break;

                case "4":
                    if (notMan.listaUtenti.isEmpty()) {
                        System.out.println("Nessun utente registrato.");
                    } else {
                        System.out.println("Utenti attualmente registrati:");
                        for (Observer o : notMan.listaUtenti) {
                            System.out.println("- " + recuperaNome(o));
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

    // Funzione per permettere all'utente di selezionare quali decoratori applicare
    private static Observer scegliDecoratori(Scanner scanner, Observer utente) {
        Observer result = utente;
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
                    System.out.print("Prefisso da aggiungere: ");
                    String prefisso = scanner.nextLine();
                    result = new Base_Notifica(result, prefisso);
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

    // Funzione che "scava" ricorsivamente tra i decorator per recuperare il nome base
    private static String recuperaNome(Observer observer) {
        if (observer instanceof Ab_UserNotificationDecorator) {
            // Scava ancora
            return recuperaNome(((Ab_UserNotificationDecorator) observer).osservatore);
        }
        if (observer instanceof ConcreteObserver) {
            // Caso base: ritorna il nome
            return ((ConcreteObserver) observer).getName();
        }
        
    }
}

