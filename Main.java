import java.util.List;
// Corretto sarebbe: import tuo.pacchetto.Observer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // === SINGLETON: Ottieni l'istanza centrale di NotificationManager (una sola per tutta l'app) ===
        NotificationManager notMan = NotificationManager.getInstance();

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
                // ===== REGISTRA NUOVO UTENTE (Observer + Decorator) =====
                case "1":
                    System.out.print("Nome utente: ");
                    String nome = scanner.nextLine();

                    // Observer: crei un utente base che implementa l'interfaccia Observer custom
                    UtenteBase utenteBase = new UtenteBase(nome);

                    // Decorator: permetti di decorare l'utente con varie personalizzazioni
                    Observer decorato = scegliDecoratori(scanner, utenteBase);

                    // Observer: aggiungi l'utente (observer) al NotificationManager (subject/observable)
                    notMan.registerObserver(decorato);

                    System.out.println("Utente registrato con successo!");
                    break;

                // ===== RIMUOVI UTENTE (Observer) =====
                case "2":
                    if (notMan.getObservers().isEmpty()) {
                        System.out.println("Nessun utente da rimuovere.");
                        break;
                    }
                    System.out.print("Nome utente da rimuovere: ");
                    String nomeDaRimuovere = scanner.nextLine();

                    // Observer: cerchi l'observer (utente/decoratore) tramite il suo nome
                    Observer daRimuovere = null;
                    for (Observer o : notMan.getObservers()) {
                        if (o.getNome().equalsIgnoreCase(nomeDaRimuovere)) {
                            daRimuovere = o;
                            break;
                        }
                    }
                    if (daRimuovere != null) {
                        // Observer: rimuovi l'observer dal NotificationManager
                        notMan.removeObserver(daRimuovere);
                        System.out.println("Utente " + nomeDaRimuovere + " rimosso.");
                    } else {
                        System.out.println("Utente non trovato.");
                    }
                    break;

                // ===== INVIA NOTIFICA (Singleton + Observer + Decorator) =====
                case "3":
                    if (notMan.getObservers().isEmpty()) {
                        System.out.println("Nessun utente registrato! Registrane uno prima.");
                        break;
                    }
                    System.out.print("Scrivi il messaggio da inviare: ");
                    String messaggio = scanner.nextLine();

                    // Singleton + Observer: NotificationManager (Singleton) invia notifica a tutti gli observer registrati,
                    // che sono potenzialmente decorati (Decorator)
                    notMan.inviaNotifica(messaggio);
                    break;

                // ===== VISUALIZZA UTENTI (Observer) =====
                case "4":
                    List<Observer> observers = notMan.getObservers();
                    if (observers.isEmpty()) {
                        System.out.println("Nessun utente registrato.");
                    } else {
                        System.out.println("Utenti attualmente registrati:");
                        for (Observer o : observers) {
                            // Observer (puoi accedere al nome perché propagato anche dai decorator)
                            System.out.println("- " + o.getNome());
                        }
                    }
                    break;

                // ===== USCITA =====
                case "5":
                    System.out.println("Arrivederci!");
                    return;

                // ===== SCELTA NON VALIDA =====
                default:
                    System.out.println("Scelta non valida, riprova.");
            }
        }
    }

    // ===== DECORATOR: Permette di comporre la catena di decoratori per personalizzare le notifiche dell'utente =====
    private static Observer scegliDecoratori(Scanner scanner, UtenteBase utenteBase) {
        Observer result = utenteBase;
        boolean continua = true;

        while (continua) {
            System.out.println("\nPersonalizza le notifiche dell'utente:");
            System.out.println("1. Aggiungi timestamp");
            System.out.println("2. Trasforma in MAIUSCOLO");
            System.out.println("3. Aggiungi prefisso/emoji");
            System.out.println("4. Nessun altro decorator (continua)");

            System.out.print("Scegli (puoi applicare più decoratori in ordine, uno alla volta): ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    result = new TimestampDecorator(result); // Decorator: aggiunge timestamp
                    System.out.println("Aggiunto timestamp.");
                    break;
                case "2":
                    result = new MaiuscoloDecorator(result); // Decorator: trasforma in maiuscolo
                    System.out.println("Aggiunto maiuscolo.");
                    break;
                case "3":
                    System.out.print("Prefisso da aggiungere (es: 'Notifica: ' oppure '🚨 '): ");
                    String prefisso = scanner.nextLine();
                    result = new PrefissoDecorator(result, prefisso); // Decorator: aggiunge prefisso
                    System.out.println("Aggiunto prefisso.");
                    break;
                case "4":
                    continua = false;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
        return result;
    }
}
