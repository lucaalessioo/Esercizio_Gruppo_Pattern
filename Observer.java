interface Subject {
    public void registerOsservatore(Osservatore o);

    public void removeOsservatore(Osservatore o);

    public void notifyOsservatore(String message);
}

interface Osservatore {
    public String update(String message);
}

class ConcreteOsservatore implements Osservatore {

    private String name;

    public ConcreteOsservatore(String name) {
        this.name = name;
    }

    @Override
    public String update(String message) {
        return name + " ha ricevuto aggiornamento: " + message;
    }

}
