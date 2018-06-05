package it.polimi.deib.se2018.common.view;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.StringMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ViewInterface extends Remote {

    String getPlayerNickName() throws RemoteException;

    void showMessage(StringMessage message) throws RemoteException;


    String increment()throws RemoteException;

    Dice selectDice()throws RemoteException;

    void reportError(StringMessage message)throws RemoteException;

    void run()throws RemoteException;

    void setGameOver()throws RemoteException;




}