package it.polimi.deib.se2018.client;

import it.polimi.deib.se2018.common.utils.Observable;
import it.polimi.deib.se2018.common.utils.Observer;
import it.polimi.deib.se2018.server.ServerInterface;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.events.Message;

import java.rmi.RemoteException;

/**
 * Network handler class
 * @author Simone Mariani
 */
public class NetworkHandler extends Observable<Message> implements Observer<Event>,NetworkHandlerInterface {

    private ServerInterface server;

    /**
     * Constructor, initializes network handler class
     * @param server server
     */
    public NetworkHandler(ServerInterface server){
        this.server=server;
    }

    /**
     * Gets activated when a message arrives from the remote view
     * @param event event
     * @throws RemoteException
     */
    public void update(Event event) throws RemoteException {
        server.notifyRemoteView(event);
    }

    /**
     * Notifies view
     * @param message message
     * @throws RemoteException
     */
    public void notifyView(Message message)throws RemoteException{
        notify(message);
    }

}