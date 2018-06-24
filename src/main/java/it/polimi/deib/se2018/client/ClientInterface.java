package it.polimi.deib.se2018.client;

import it.polimi.deib.se2018.server.ServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    void notify(ClientStringMessage stringMessage) throws RemoteException;

    void setPlayerNickName(String n) throws RemoteException;

    String getPlayerNickName() throws RemoteException;

    void setView(View v) throws RemoteException;

    View getView() throws RemoteException;

    ClientStringMessage pingServer() throws RemoteException;

    void setConnectionCheckerTimer(final ServerInterface server, final ClientInterface client)throws RemoteException;

}
