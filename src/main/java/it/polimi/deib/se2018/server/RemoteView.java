package it.polimi.deib.se2018.server;

import it.polimi.deib.se2018.client.NetworkHandlerInterface;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.common.utils.Observable;
import it.polimi.deib.se2018.common.view.ViewInterface;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.common.utils.Observer;
import it.polimi.deib.se2018.server.model.events.StringMessage;
import it.polimi.deib.se2018.server.model.events.StringMessageError;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Remote view class
 * @author Simone Mariani
 */
public class RemoteView  extends Observable<Event> implements Observer<Message>,ViewInterface {//Classe abstract?

    private String playerNickName;
    private ArrayList<NetworkHandlerInterface> networkHandlers=new ArrayList<NetworkHandlerInterface>();

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName(){
        return playerNickName;
    }//Valutare protected

    /**
     * Updates message
     * @param message message
     * @throws RemoteException
     */
    @Override
    public void update(Message message)throws RemoteException {
        for(int i=0;i<networkHandlers.size();i++){
            networkHandlers.get(i).notifyView(message);
        }
    }

    /**
     * Notifies controller of an event
     * @param event event
     * @throws RemoteException
     */
    public void notifyController(Event event)throws RemoteException{
        notify(event);
    }

    /**
     * Registers network handlers
     * @param networkHandlers network handlers
     * @throws RemoteException
     */
    public void registerNetworkHandlers(ArrayList<NetworkHandlerInterface> networkHandlers)throws RemoteException{
        this.networkHandlers=networkHandlers;
    }

    /**
     * Reports error
     * @param message message
     * @throws RemoteException
     */
    public void reportError(Message message)throws RemoteException{
        for(int i=0;i<networkHandlers.size();i++){
            networkHandlers.get(i).notifyView(message);
        }
    }

    /**
     * Shows message
     * @param message message
     * @throws RemoteException
     */
    public void showMessage(StringMessage message)throws RemoteException{
        for(int i=0;i<networkHandlers.size();i++){
            networkHandlers.get(i).notifyView(message);
        }
    }

    /**
     * Notifies view
     * @param message message
     * @throws RemoteException
     */
    public void notifyView(Message message)throws RemoteException{
        for(int i=0;i<networkHandlers.size();i++){
            networkHandlers.get(i).notifyView(message);
        }
    }

    /**
     * Selects dice
     * @return null
     */
    public Dice selectDice(){
        return null;
    }

    /**
     * Increments
     * @return null
     */
    public String increment(){
        return null;
    }

    /**
     * Sets game over
     */
    public void setGameOver(){

    }

    /**
     * Runs
     */
    public void run(){

    }
}
