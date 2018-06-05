package it.polimi.deib.se2018.client;

import it.polimi.deib.se2018.common.utils.Observable;
import it.polimi.deib.se2018.common.utils.Observer;
import it.polimi.deib.se2018.server.ServerInterface;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.events.Message;

import java.rmi.RemoteException;

public class NetworkHandler extends Observable<Message> implements Observer<Event>,NetworkHandlerInterface {

    private ServerInterface server;

    public NetworkHandler(ServerInterface server){
        this.server=server;
    }


    public void update(Event event) throws RemoteException {
        server.notifyRemoteView(event);
    }

    public void notifyView(Message message)throws RemoteException{
        notify(message);
    }

}