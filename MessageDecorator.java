import java.time.LocalDateTime;

abstract class Ab_UserNotificationDecorator implements Osservatore {// classe astratta decoratore notifiche/messaggi
    protected Osservatore osservatore;// osservatore oggetto da decorare

    public Ab_UserNotificationDecorator(Osservatore osservatore) {
        this.osservatore = osservatore;// super costruttore gestisce le modalita di creazione degli oggetti figli di
                                       // questa classe
    }

    @Override // overraid del metodo update dell'interfaccia Osservatore
    public String update(String message) {
        return osservatore.update(message); // ritorna il metodo specializzato base dell' osservatore
    }

}

class Time_Stamp_UpperCase extends Ab_UserNotificationDecorator {
    public Time_Stamp_UpperCase(Osservatore osservatore) {
        super(osservatore);// costruttore classe concreta
    }
    // Messaggio timestamp localDateTime.now() uppercase .toUppercase()

    @Override
    public String update(String message) {

        return LocalDateTime.now() + " " + super.update(message).toUpperCase(); // richiamo il super.update(passando un
                                                                                // messaggio) aggiungo il
                                                                                // LocalDateTime.now() + metodo to
                                                                                // uppercase()
    }
}

class Base_Notifica extends Ab_UserNotificationDecorator {
    public Base_Notifica(Osservatore osservatore) {// costruttore classe concreta
        super(osservatore);
    }
    // Messaggio Notifica : + messaggio

    @Override
    public String update(String message) {
        return "Notifica : " + super.update(message); // aggiunge prefisso notifica al metodo super.update(message)
    }

}