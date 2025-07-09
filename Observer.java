interface Subject{
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String message);
}

interface Observer{
    String update(String message);
}

class ConcreteObserver implements Observer{

    private String name;

    public ConcreteObserver(String name){
        this.name = name;
    }
 
    @Override
    public String update(String message) {
        return name + " ha ricevuto aggiornamento: " + message;
    }

}
