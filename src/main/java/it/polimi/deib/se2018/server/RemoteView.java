package it.polimi.deib.se2018.server;

import it.polimi.deib.se2018.client.NetworkHandlerInterface;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.common.utils.Observable;
import it.polimi.deib.se2018.common.view.ViewInterface;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.common.utils.Observer;
import it.polimi.deib.se2018.server.model.events.StringMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RemoteView  extends Observable<Event> implements Observer<Message>,ViewInterface {//Classe abstract?

    private String playerNickName;
    private ArrayList<NetworkHandlerInterface> networkHandlers=new ArrayList<NetworkHandlerInterface>();

    public String getPlayerNickName(){
        return playerNickName;
    }//Valutare protected

    @Override
    public void update(Message message)throws RemoteException {
        for(int i=0;i<networkHandlers.size();i++){
            networkHandlers.get(i).notifyView(message);
        }
    }

    public void notifyController(Event event)throws RemoteException{
        notify(event);
    }

    public void registerNetworkHandlers(ArrayList<NetworkHandlerInterface> networkHandlers)throws RemoteException{
        this.networkHandlers=networkHandlers;
    }

    public void reportError(StringMessage message)throws RemoteException{
        for(int i=0;i<networkHandlers.size();i++){
            networkHandlers.get(i).notifyView(message);
        }
    }

    public void showMessage(StringMessage message)throws RemoteException{
        for(int i=0;i<networkHandlers.size();i++){
            networkHandlers.get(i).notifyView(message);
        }
    }

    public Dice selectDice(){
        return null;
    }


    public String increment(){
        return null;
    }

    public void setGameOver(){

    }

    public void run(){

    }
}
