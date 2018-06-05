package it.polimi.deib.se2018.client;

import it.polimi.deib.se2018.server.model.events.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NetworkHandlerInterface extends Remote {

    void notifyView(Message message) throws RemoteException;
}
