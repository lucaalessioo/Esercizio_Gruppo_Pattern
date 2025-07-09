import java.util.ArrayList;
import java.util.Observer;

public class NotificationManager implements Subject {

    private static NotificationManager istanza;
    ArrayList<Observer> listaUtenti;

    private NotificationManager() {
        this.listaUtenti = new ArrayList<>();
    }

    public static NotificationManager getInstanza() {
        if(istanza == null) {
            istanza = new NotificationManager();
        }
        return istanza;
    }

    public String inviaMessaggio(String messaggio) {
        return messaggio;
    }
    
    @Override
    public void registerObserver(Observer o) {
        listaUtenti.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        listaUtenti.remove(o);
    }



}