package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.controller.toolcard.ToolCard;
import it.polimi.deib.se2018.server.model.player.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class CardActivationController {

    private Model model;
    private ArrayList<ToolCard> toolCardList;

    public CardActivationController(Model m,ArrayList<ToolCard>list){
        model=m;
        toolCardList=list;
    }

    public ToolCard findCard(int n){
        for(int i=0;i<toolCardList.size();i++){
            if(toolCardList.get(i).getNumber()==n) return toolCardList.get(i);
        }
        return null;
    }

    public boolean canTakeCard(Player p, ToolCard c)throws RemoteException {
        if(model.getPlayerList().size()==1){
            if(model.getDiceStock().findDice(c.getSolitaryColor())){
                return true;
            }
            else return false;
        }
        if(model.getPlayerList().size()>1){
            if(c.isAlreadyUsed()&&p.getFavorMarkers()<2) return false;
            if(!c.isAlreadyUsed()&&p.getFavorMarkers()<1) return false;
            return true;
        }
        return false;
    }
}