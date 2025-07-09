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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConcreteOsservatore(String name) {
        this.name = name;
    }

    @Override
    public String update(String message) {
        return name + " ha ricevuto aggiornamento: " + message;
    }

}
