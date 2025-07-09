//interfaccia del Subject
interface Subject{
    //metodo per aggiungere Observer
    void registerObserver(Observer o);
    //metodo per rimuovere Observer
    void removeObserver(Observer o);
    //metodo per notificare Observer
    void notifyObservers(String message);
}

//interfaccia dell?Observer
interface Observer{
    String update(String message);
}

//classe Observer che implementa interfaccia Observer
class ConcreteObserver implements Observer{

    //attributo privato del nome
    private String name;

    //costruttore con parametro
    public ConcreteObserver(String name){
        this.name = name;
    }
 
    //Override del metodo update con messaggio
    @Override
    public String update(String message) {
        return name + " ha ricevuto aggiornamento: " + message;
    }

}
