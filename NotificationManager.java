import java.util.ArrayList;

public class NotificationManager implements Subject {

    private static NotificationManager istanza;
    private ArrayList<Osservatore> listaUtenti = new ArrayList<>();

    public ArrayList<Osservatore> getListaUtenti() {
        return listaUtenti;
    }

    public void setListaUtenti(ArrayList<Osservatore> listaUtenti) {
        this.listaUtenti = listaUtenti;
    }

    private NotificationManager() {

    }

    public static NotificationManager getInstanza() {
        if (istanza == null) {
            istanza = new NotificationManager();
        }
        return istanza;
    }

    public String inviaMessaggio(String messaggio) {
        return messaggio;
    }

    @Override
    public void notifyOsservatore(String message) {
        for (Osservatore o : listaUtenti) {
            System.out.println(o.update(message));
        }
    }

    @Override
    public void registerOsservatore(Osservatore o) {
        listaUtenti.add(o);
    }

    @Override
    public void removeOsservatore(Osservatore o) {
        listaUtenti.remove(o);
    }
}