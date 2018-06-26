package it.polimi.deib.se2018.common.view;

import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.server.model.events.StringMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ViewInterface extends Remote {

    String getPlayerNickName() throws RemoteException;

    void showMessage(StringMessage message) throws RemoteException;

    void reportError(Message message)throws RemoteException;

    void run()throws RemoteException;

    void setGameOver()throws RemoteException;




}