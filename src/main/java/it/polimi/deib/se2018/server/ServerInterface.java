package it.polimi.deib.se2018.server;

import it.polimi.deib.se2018.client.ClientStringMessage;
import it.polimi.deib.se2018.client.NetworkHandlerInterface;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import it.polimi.deib.se2018.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    int assignOrder() throws RemoteException;

    PlayerColor assignColor() throws RemoteException;

    void createGame() throws RemoteException;

    void initGame()throws RemoteException;

    int getNPlayers() throws RemoteException;

    boolean isGameFull() throws RemoteException;

    void setNPlayers(int n) throws RemoteException;

    int getClientsNumber() throws RemoteException;

    boolean isNameAccepted(String name,ClientInterface client) throws RemoteException;

    boolean isGameModeAccepted(int n,ClientInterface client) throws RemoteException;

    void addClient(ClientInterface client) throws RemoteException;

    void addNetworkHandler(NetworkHandlerInterface networkHandler) throws RemoteException;

    void send(ClientStringMessage stringMessage) throws RemoteException;

    void setEnoughPlayersTimer() throws RemoteException;

    void stopEnoughPlayersTimer() throws RemoteException;

    void notifyRemoteView(Event event) throws RemoteException;

    void checkClients()throws RemoteException;

    ClientStringMessage pingClient(ClientInterface client) throws RemoteException;

    void setEnoughPlayersTimerInterval(int interval) throws RemoteException;

    void setSuspensionTimerInterval(int interval) throws RemoteException;

    void reconnectClient(ClientInterface client)throws RemoteException;

}


