package it.polimi.deib.se2018.server;

import it.polimi.deib.se2018.client.ClientStringMessage;
import it.polimi.deib.se2018.client.NetworkHandlerInterface;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import it.polimi.deib.se2018.server.modelview.ModelView;
import it.polimi.deib.se2018.client.ClientInterface;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Server implementation class
 * @author Simone Mariani
 */
public class ServerImplementation extends UnicastRemoteObject implements
        ServerInterface {

    private ArrayList<ClientInterface> clients = new ArrayList<ClientInterface>();
    private ArrayList<NetworkHandlerInterface> networkHandlers=new ArrayList<NetworkHandlerInterface>();
    private int nPlayers;
    private Model model;
    private Controller controller;
    private RemoteView remoteView;
    private ModelView modelView;
    private Timer enoughPlayersTimer;
    private Timer connectionCheckerTimer;
    private TimerTask waitingConnection;
    private TimerTask clientChecker;
    private int enoughPlayersTimerInterval;
    private int suspensionTimerInterval;
    private boolean gameStarted=false;
    private ArrayList<ClientInterface> disconnectedClients = new ArrayList<ClientInterface>();
    private ArrayList<NetworkHandlerInterface> disconnectedNetworkHandlers=new ArrayList<NetworkHandlerInterface>();

    /**
     * Constructor, initializes server implementation class
     * @throws RemoteException
     */
    protected ServerImplementation() throws RemoteException {
        super(0);
    }

    private static final long serialVersionUID = -7098548671967083832L;

    /**
     * Adds client
     * @param client client
     * @throws RemoteException
     */
    public void addClient(ClientInterface client) throws RemoteException {
        clients.add(client);
        System.out.println("Player: "+ (client.getPlayerNickName())+ " connected!");
        if(clients.size()<nPlayers) client.notify(new ClientStringMessage("Waiting for other players...\n"));
        if(clients.size()==nPlayers) {
            if(nPlayers>1){
                stopEnoughPlayersTimer();
            }
            initGame();
        }
    }

    /**
     * Initiates game
     * @throws RemoteException
     */
    public void initGame()throws RemoteException{
        gameStarted=true;
        for(int i=0;i<clients.size();i++){
            model.addPlayer(new Player(clients.get(i).getPlayerNickName(),assignOrder(),assignColor()));
        }
        remoteView.registerNetworkHandlers(networkHandlers);
        controller.initGame(suspensionTimerInterval);
    }

    /**
     * Creates game
     * @throws RemoteException
     */
    public void createGame()throws RemoteException {
        checkClients();//Faccio partire il controllo sul mantenimento della connessione da qui...cioè dopo che è stato aggiunto il primo client
        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),RoundsTrack.getSingletonRoundsTrack());
        remoteView=new RemoteView();
        controller=new Controller(model,remoteView);
        modelView=new ModelView();
        remoteView.register(controller);
        model.register(modelView);
        modelView.register(remoteView);
    }

    /**
     * Checks if the game mode is accepted
     * @param n mode
     * @param client client
     * @return true if accepted, else false
     * @throws RemoteException
     */
    public boolean isGameModeAccepted(int n, ClientInterface client) throws RemoteException{
        if(n==0||n==1) return true;
        else {client.notify(new ClientStringMessage("ERROR! Insert a valid option")); return false;}
    }

    /**
     * Checks if inserted name is accepted
     * @param name name
     * @param client client
     * @return true if accepted, else false
     * @throws RemoteException
     */
    public boolean isNameAccepted(String name,ClientInterface client)throws RemoteException{
        for(int i=0;i<clients.size();i++){
            if(clients.get(i).getPlayerNickName().equals(name)){client.notify(new ClientStringMessage("ERROR! Name already taken\n")); return false;}
        }
        return true;
    }

    /**
     * Gets number of players
     * @return number of players
     * @throws RemoteException
     */
    public int getNPlayers()throws RemoteException {
        return nPlayers;
    }

    /**
     * Sets number of players
     * @param n number of players
     * @throws RemoteException
     */
    public void setNPlayers(int n)throws RemoteException{
        if(n==0)
            nPlayers=1;
        else
            nPlayers=4;
    }

    /**
     * Gets clients number
     * @return clients number
     * @throws RemoteException
     */
    public int getClientsNumber() throws RemoteException{
        return clients.size();
    }

    /**
     * Checks if game is full
     * @return true if number of players is equal to clients number, else false
     * @throws RemoteException
     */
    public boolean isGameFull()throws RemoteException{
        return nPlayers==clients.size();
    }

    /**
     * Assigns order to players
     * @return player's order when player is created
     * @throws RemoteException
     */
    public int assignOrder()throws RemoteException{
        if(model.getPlayerList().size()==0){
            return 1;
        }
        else{
            return model.getPlayerList().get(model.getPlayerList().size()-1).getOrder()+1;
        }
    }


    /**
     * Stops enough players timer
     */
    public void stopEnoughPlayersTimer(){
        waitingConnection.cancel();
        enoughPlayersTimer.cancel();
        enoughPlayersTimer.purge();
    }

    /**
     * Checks clients
     * @throws RemoteException
     */
    public void checkClients()throws RemoteException {
        clientChecker = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < clients.size(); i++) {
                    try {
                        ClientStringMessage response=pingClient(clients.get(i));
                    }
                    catch (RemoteException e) {
                        removeClient(clients.get(i),i);
                    }
                }
            }
        };
        connectionCheckerTimer=new Timer();
        connectionCheckerTimer.schedule(clientChecker,0,1000);//La esegue ogni secondo
    }

    /**
     * Pings client
     * @param client client
     * @return message from client
     * @throws RemoteException
     */
    public ClientStringMessage pingClient(ClientInterface client) throws RemoteException{
        return client.pingServer();
    }

    /**
     * Sets enough players timer
     */
    public void setEnoughPlayersTimer(){
        waitingConnection=new TimerTask() {
            @Override
            public void run() {
                if(clients.size()>=2&&clients.size()<4) {
                    try{ nPlayers=clients.size();
                        initGame();}catch (RemoteException e){System.out.println("Error! Remote Exception.");}
                }
            }
        };
        enoughPlayersTimer=new Timer();
        enoughPlayersTimer.schedule(waitingConnection,(long)enoughPlayersTimerInterval*1000);//La esegue una sola volta
    }

    /**
     * Removes client
     * @param client client
     * @param i client index in client list
     */
    private void removeClient(ClientInterface client,int i){
        //Dovrò dividere tra partita iniziata e partita in fase di avvio?
        if(!gameStarted){//Fase di avvio
            if(clients.size()>1) {
                clients.remove(client);//Valutare anche l'eliminazione del Player dal model
                networkHandlers.remove(networkHandlers.get(i));
                if (clients.size() < 2) {
                    stopEnoughPlayersTimer();//Stoppo il timer solo se la disconnessione produce un solo giocatore in attesa...
                }
                System.out.println("Player disconnected!");//Cercare di stampare anche il nome...problema RemoteException
            }
            else{
                clients.remove(client);
                networkHandlers.remove(networkHandlers.get(i));
                System.out.println("Player disconnected!");//Cercare di stampare anche il nome...problema RemoteException
            }
        }
        else{//Partita iniziata
            disconnectClient(client,i);
        }
    }

    /**
     * Disconnects client
     * @param client client
     * @param i client index in client list
     */
    private void disconnectClient(ClientInterface client,int i){
        disconnectedClients.add(client);
        disconnectedNetworkHandlers.add(networkHandlers.get(i));
        networkHandlers.remove(networkHandlers.get(i));
        clients.remove(client);
    }

    /**
     * Reconnects client
     * @param client client
     */
    public void reconnectClient(ClientInterface client){
        for(int i=0;i<disconnectedClients.size();i++){
            clients.add(client);
            disconnectedClients.remove(client);
            networkHandlers.add(disconnectedNetworkHandlers.get(i));
            disconnectedNetworkHandlers.remove(disconnectedNetworkHandlers.get(i));
        }
    }

    /**
     * Assigns color to players
     * @return color index
     * @throws RemoteException
     */
    public PlayerColor assignColor() throws RemoteException{
        ArrayList<PlayerColor> colorList=new ArrayList<PlayerColor>();
        for(PlayerColor c: PlayerColor.values()){
            colorList.add(c);
        }
        for(int i=0;i<model.getPlayerList().size();i++){
            colorList.remove(model.getPlayerList().get(i).getPlayerColor());
        }
        int index = (int) ((Math.random() * colorList.size()));
        return colorList.get(index);
    }


    /**
     * Adds network handlers
     * @param networkHandler network handler
     * @throws RemoteException
     */
    public void addNetworkHandler(NetworkHandlerInterface networkHandler) throws RemoteException{
        networkHandlers.add(networkHandler);
    }

    /**
     * Notifies remote view of an event
     * @param event event
     * @throws RemoteException
     */
    public void notifyRemoteView(Event event) throws RemoteException{
        remoteView.notifyController(event);
    }

    /**
     * Sets enough players timer interval
     * @param interval interval
     * @throws RemoteException
     */
    public void setEnoughPlayersTimerInterval(int interval)throws RemoteException{
        enoughPlayersTimerInterval=interval;
    }

    /**
     * Sets enough suspension timer interval
     * @param interval interval
     * @throws RemoteException
     */
    public void setSuspensionTimerInterval(int interval)throws RemoteException{
        suspensionTimerInterval=interval;
    }

}
