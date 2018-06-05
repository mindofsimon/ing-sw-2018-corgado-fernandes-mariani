package it.polimi.deib.se2018.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    public void notify(ClientStringMessage stringMessage) throws RemoteException;

    public void setPlayerNickName(String n) throws RemoteException;

    public String getPlayerNickName() throws RemoteException;

    public void setView(View v) throws RemoteException;

    public View getView() throws RemoteException;

}
