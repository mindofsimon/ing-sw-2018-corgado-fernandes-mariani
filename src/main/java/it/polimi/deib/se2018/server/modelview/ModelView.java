package it.polimi.deib.se2018.server.modelview;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.events.*;
import it.polimi.deib.se2018.common.utils.Observable;
import it.polimi.deib.se2018.common.utils.Observer;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ModelView extends Observable<Message> implements Observer<Message>,Serializable {

    private Model modelCopy;

    @Override
    public void update(Message message)throws RemoteException {
        if (message instanceof DicePlacementMessage) {
            modelCopy = (message.getModel()).copy();
            notify(new DicePlacementMessage(message.getPlayer(), modelCopy));
        }
        if(message instanceof EndTurnMessage){
            modelCopy = (message.getModel()).copy();
            notify(new EndTurnMessage(message.getPlayer(),modelCopy));
        }
        if(message instanceof CardActivationMessage){
            modelCopy = (message.getModel()).copy();
            notify(new CardActivationMessage(message.getPlayer(), modelCopy));
        }
        if(message instanceof GameOverMessage){
            notify(message);
        }
        if(message instanceof QuitPlayerMessage){
            notify(message);
        }
        if(message instanceof PlayerSuspendedMessage){
            modelCopy = (message.getModel()).copy();
            notify(new PlayerSuspendedMessage(message.getPlayer(),modelCopy));
        }

    }

}