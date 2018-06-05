package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.rmi.RemoteException;

public class ScoreController {//Implements methods related to Victory Points and Game Score
    private Model model;
    private RemoteView view;

    public ScoreController(Model m,RemoteView v){
        model=m;
        view=v;
    }



    private Player calculateMax() throws RemoteException{
        Player winner=model.getPlayerList().get(0);
        for(int i=1;i<model.getPlayerList().size();i++){
            if(model.getPlayerList().get(i).getVictoryPoints()>winner.getVictoryPoints()){
                winner=model.getPlayerList().get(i);
            }
        }
        return winner;
    }

    public int solitaryObjectivePoint()throws RemoteException{
        int points=0;
        for(int i=0;i<model.getRoundsTrack().getDiceList().size();i++){
            points=points+model.getRoundsTrack().getDiceList().get(i).getValue();
        }
        return points;
    }


    public Player calculateWinner()throws RemoteException {
        return calculateMax();
    }

    public int calculateVictoryPoints(Player p)throws RemoteException{
        return calculatePrivateGoal(p)+calculateEmptyBoxes(p)+calculatePublicGoal(p)+p.getFavorMarkers();
    }

    private int calculateEmptyBoxes(Player p){
        int points=0;
        for(int i=0;i<p.getPlayerScheme().getROWS();i++){
            for(int j=0;j<p.getPlayerScheme().getCOLS();j++){
                if(p.getPlayerScheme().getScheme()[i][j].getDice()== null ){
                    points=points-1;
                }
            }
        }
        return points;
    }

    private int calculatePrivateGoal(Player p){
        int points=0;
        for(int i=0;i<p.getPlayerScheme().getROWS();i++){
            for(int j=0;j<p.getPlayerScheme().getCOLS();j++){
                if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null &&p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(p.getPrivateGoalCard().getColor())){
                    points=points+p.getPlayerScheme().getScheme()[i][j].getDice().getValue();
                }
            }
        }
        return points;
    }

    private int calculatePublicGoal(Player p)throws RemoteException{
        int points=0;
        for(int i=0;i<model.getPublicGoalCards().size();i++){
            points=points+model.getPublicGoalCards().get(i).calculateVictoryPoints(p);
        }
        return points;
    }
}
