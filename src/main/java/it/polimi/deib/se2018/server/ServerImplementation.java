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


public class ServerImplementation extends UnicastRemoteObject implements
        ServerInterface {

    private ArrayList<ClientInterface> clients = new ArrayList<ClientInterface>();
    private ArrayList<NetworkHandlerInterface> networkHandlers=new ArrayList<NetworkHandlerInterface>();
    private int nPlayers;
    private Model model;
    private Controller controller;
    private RemoteView remoteView;
    private ModelView modelView;
    private Timer timer;
    private TimerTask waitingConnection;


    protected ServerImplementation() throws RemoteException {
        super(0);
    }

    private static final long serialVersionUID = -7098548671967083832L;

    public void addClient(ClientInterface client) throws RemoteException {
        clients.add(client);
        System.out.println("Player: "+ (client.getPlayerNickName())+ " connected!");
        if(clients.size()<nPlayers) client.notify(new ClientStringMessage("Waiting for other players...\n"));
        if(clients.size()==nPlayers) initGame();
    }

    public void initGame()throws RemoteException{
        for(int i=0;i<clients.size();i++){
            model.addPlayer(new Player(clients.get(i).getPlayerNickName(),assignOrder(),assignColor()));
        }
        remoteView.registerNetworkHandlers(networkHandlers);
        controller.initGame();
    }

    public void createGame()throws RemoteException {
        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),RoundsTrack.getSingletonRoundsTrack());
        remoteView=new RemoteView();
        controller=new Controller(model,remoteView);
        modelView=new ModelView();
        remoteView.register(controller);
        model.register(modelView);
        modelView.register(remoteView);
    }



    public boolean isGameModeAccepted(int n, ClientInterface client) throws RemoteException{
        if(n==0||n==1) return true;
        else {client.notify(new ClientStringMessage("ERROR! Insert a valid option")); return false;}
    }


    public boolean isNameAccepted(String name,ClientInterface client)throws RemoteException{
        for(int i=0;i<clients.size();i++){
            if(clients.get(i).getPlayerNickName().equals(name)){client.notify(new ClientStringMessage("ERROR! Name already taken\n")); return false;}
        }
        return true;
    }

    public int getNPlayers()throws RemoteException {
        return nPlayers;
    }

    public void setNPlayers(int n)throws RemoteException{
        if(n==0)
            nPlayers=1;
        else
            nPlayers=4;
    }

    public int getClientsNumber() throws RemoteException{
        return clients.size();
    }

    public boolean isGameFull()throws RemoteException{
        return nPlayers==clients.size();
    }

    public int assignOrder()throws RemoteException{
        if(model.getPlayerList().size()==0){
            return 1;
        }
        else{
            return model.getPlayerList().get(model.getPlayerList().size()-1).getOrder()+1;
        }
    }


    public void stopTimer() throws RemoteException{
        timer.cancel();
    }

    public void setTimer()throws RemoteException{
        long interval=60000;//60 SECONDI
        waitingConnection=new TimerTask() {
            @Override
            public void run() {
                if(clients.size()>=2&&clients.size()<4) {
                    try{
                        nPlayers=clients.size();
                        initGame();}catch (RemoteException e){System.out.println("Error! Remote Exception.");}
                    timer.cancel();}
            }
        };
        timer=new Timer();
        timer.schedule(waitingConnection,interval,interval);
    }


    public void removeClient(ClientInterface client) throws RemoteException{
        clients.remove(client);
        System.out.println("Player: "+ client.getPlayerNickName() + " disconnected!");

    }

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


    public void send(ClientStringMessage stringMessage) throws RemoteException {
        Iterator<ClientInterface> clientIterator = clients.iterator();
        while(clientIterator.hasNext()){
            try{
                clientIterator.next().notify(stringMessage);
            }catch(ConnectException e) {
                clientIterator.remove();
                System.out.println("Client removed!");
            }
        }
    }

    public void addNetworkHandler(NetworkHandlerInterface networkHandler) throws RemoteException{
        networkHandlers.add(networkHandler);
    }

    public void notifyRemoteView(Event event) throws RemoteException{
        remoteView.notifyController(event);
    }



}