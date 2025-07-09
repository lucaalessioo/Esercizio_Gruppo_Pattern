import java.util.ArrayList;

// Classe Singleton
public class NotificationManager implements Subject {

    private static NotificationManager istanza;                                // istanza
    private ArrayList<Osservatore> listaUtenti = new ArrayList<>();            // lista di osservatori

    public ArrayList<Osservatore> getListaUtenti() {                           // getter per la lista utenti
        return listaUtenti;
    }

    public void setListaUtenti(ArrayList<Osservatore> listaUtenti) {           // Setter per la lista utenti
        this.listaUtenti = listaUtenti;
    }

    private NotificationManager() {                                            // Costruttore privato

    }

    public static NotificationManager getInstanza() {                          // Metodo per creare l istanza se non esistente
        if (istanza == null) {
            istanza = new NotificationManager();
        }
        return istanza;
    }

    public String inviaMessaggio(String messaggio) {                           // Metodo per inviare il messaggio
        return messaggio;
    }

    @Override
    public void notifyOsservatore(String message) {                            // Metodo per vedere i messaggi
        for (Osservatore o : listaUtenti) {
            System.out.println(o.update(message));
        }
    }

    @Override
    public void registerOsservatore(Osservatore o) {                           // Metodo per aggiungere l osservatore
        listaUtenti.add(o);
    }

    @Override   
    public void removeOsservatore(Osservatore o) {                             // Metodo per rimuovere l osservatore
        listaUtenti.remove(o);
    }
}