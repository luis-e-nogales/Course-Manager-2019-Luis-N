package weather;

import java.util.ArrayList;

public abstract class Subject {
    protected ArrayList<Observer> observers;

    public Subject() {
        observers = new ArrayList<>();
    }

    //Effects: notifies all observers of the change
    public void notifyObservers(Weather weather) {
        for (Observer observer: observers) {
            observer.update(weather);
        }
    }

    //Modifies: this
    //Effects: adds an observer to the list
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

}
