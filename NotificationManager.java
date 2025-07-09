import java.util.ArrayList;

// Classe singleton
public class NotificationManager implements Subject {

    
    private static NotificationManager istanza;                // istanza 
    ArrayList<Osservatore> listaUtenti = new ArrayList<>();    // Lista di osservatori

    private NotificationManager() {                            // Costruttore

    }

    public static NotificationManager getInstanza() {          // Metodo per creare l istanza se non è gia presente
        if (istanza == null) {
            istanza = new NotificationManager();
        }
        return istanza;
    }

    public String inviaMessaggio(String messaggio) {           // Metodo per inviare il messaggio
        return messaggio;
    }

    @Override
    public void notifyOsservatore(String message) {            // Metodo per stampare i messaggi
        for (Osservatore o : listaUtenti) {
            System.out.println(o.update(message));
        }
    }

    @Override
    public void registerOsservatore(Osservatore o) {           // Metodo aggiungere gli osservatori
        listaUtenti.add(o);
    }

    @Override
    public void removeOsservatore(Osservatore o) {             // Metodo per rimuovere gli osservatori
        listaUtenti.remove(o);
    }
}