//interfaccia del Subject
interface Subject {
    // metodo per registrare observer
    public void registerOsservatore(Osservatore o);

    // metodo per rimuovere observer
    public void removeOsservatore(Osservatore o);

    // metodo per mandare notifica a lista observer
    public void notifyOsservatore(String message);
}

// interfaccia observer
interface Osservatore {
    // metodo per stampare notifica
    public String update(String message);
}

// classe Observer che implementa interfaccia dell'Observer
class ConcreteOsservatore implements Osservatore {

    // attributo privato proprio
    private String name;

    // getter e setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // costruttore con parametro
    public ConcreteOsservatore(String name) {
        this.name = name;
    }

    // override del metodo dell'interfaccia dell'observer con stampa del messaggio
    // base
    @Override
    public String update(String message) {
        return name + " ha ricevuto aggiornamento: " + message;
    }

}