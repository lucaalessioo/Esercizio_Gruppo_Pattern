import java.util.Scanner;

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
                    Osservatore utente = new ConcreteOsservatore(nome);

                    // Scegli i decorator da applicare
                    utente = scegliDecoratori(scanner, utente);

                    notMan.registerOsservatore(utente);
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
                        // Prova a recuperare il nome (ricorsivo se decorato)
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

                    notMan.notifyOsservatore(messaggio);
                    break;

                case "4":
                    if (notMan.getListaUtenti().isEmpty()) {
                        System.out.println("Nessun utente registrato.");
                    } else {
                        System.out.println("Utenti attualmente registrati:");
                        for (Osservatore o : notMan.getListaUtenti()) {
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

    // Funzione che "scava" ricorsivamente tra i decorator per recuperare il nome
    // base
    private static String recuperaNome(Osservatore observer) {
        if (observer instanceof Ab_UserNotificationDecorator) {
            // Scava ancora
            return recuperaNome(((Ab_UserNotificationDecorator) observer).osservatore);
        }
        if (observer instanceof ConcreteOsservatore) {
            // Caso base: ritorna il nome
            return ((ConcreteOsservatore) observer).getName();
        }
        return null;

    }

}
