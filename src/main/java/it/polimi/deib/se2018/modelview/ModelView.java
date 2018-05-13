package it.polimi.deib.se2018.modelview;

import it.polimi.deib.se2018.model.Model;
import it.polimi.deib.se2018.model.events.DicePlacementMessage;
import it.polimi.deib.se2018.model.events.EndTurnMessage;
import it.polimi.deib.se2018.model.events.Message;
import it.polimi.deib.se2018.utils.Observable;
import it.polimi.deib.se2018.utils.Observer;

public class ModelView extends Observable<Message> implements Observer<Message> {

    private Model modelCopy;

    @Override
    public void update(Message message) {
        modelCopy = (message.getModel()).copy();
        if (message instanceof DicePlacementMessage) {
            notify(new DicePlacementMessage(message.getPlayer(), modelCopy));
        }
        if(message instanceof EndTurnMessage){
            notify(new EndTurnMessage(message.getPlayer(),modelCopy));
        }

    }

}
