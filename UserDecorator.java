abstract class Ab_UserNotificationDecorator implements Observer {
    protected Observer osservatore;

    public Ab_userDecorator(Observer osservatore){
        this.osservatore = osservatore;
    }

}

class Time_Stamp_UpperCase extends Ab_UserNotificationDecorator {
    public Time_Stamp_UpperCase(Observer osservatore) {
        super(osservatore);
    }
    // Messaggio timestamp localDateTime.now() uppercase .toUppercase()
}

class Base_Notifica extends Ab_UserNotificationDecorator {
    public Time_Stamp_UpperCase(Observer osservatore) {
        super(osservatore);
    }
    // Messaggio Notifica : + messaggio

}