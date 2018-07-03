package it.polimi.deib.se2018.common.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Observable class
 * @param <T>
 * @author Simone Mariani
 */
public class Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();

    /**
     * Registers observer
     * @param observer observer
     */
    public void register(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Deregisters observer
     * @param observer observer
     */
    public void deregister(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * Receives messages from observers
     * @param message message
     * @throws RemoteException
     */
    protected void notify(T message)throws RemoteException {
        synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.update(message);
            }
        }
    }


}
