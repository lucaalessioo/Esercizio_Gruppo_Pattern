import java.time.LocalDateTime;

abstract class Ab_UserNotificationDecorator implements Osservatore {
    protected Osservatore osservatore;

    public Ab_UserNotificationDecorator(Osservatore osservatore) {
        this.osservatore = osservatore;
    }

    @Override
    public String update(String message) {
        return osservatore.update(message);
    }

}

class Time_Stamp_UpperCase extends Ab_UserNotificationDecorator {
    public Time_Stamp_UpperCase(Osservatore osservatore) {
        super(osservatore);
    }
    // Messaggio timestamp localDateTime.now() uppercase .toUppercase()

    @Override
    public String update(String message) {

        return LocalDateTime.now() + " " + super.update(message).toUpperCase();
    }
}

class Base_Notifica extends Ab_UserNotificationDecorator {
    public Base_Notifica(Osservatore osservatore) {
        super(osservatore);
    }
    // Messaggio Notifica : + messaggio

    @Override
    public String update(String message) {
        return "Notifica : " + super.update(message);
    }

}