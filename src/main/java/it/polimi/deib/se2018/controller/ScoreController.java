package it.polimi.deib.se2018.controller;

import it.polimi.deib.se2018.model.Model;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.view.View;

public class ScoreController {//Implements methods related to Victory Points and Game Score
    private Model model;
    private View view;

    public ScoreController(Model m,View v){
        model=m;
        view=v;
    }



    private Player calculateMax(){
        Player winner=null;
        for(int i=1;i<model.getPlayerList().size();i++){
            if(model.getPlayerList().get(i).getVictoryPoints()>model.getPlayerList().get(i-1).getVictoryPoints()){
                winner=model.getPlayerList().get(i);
            }
        }
        return winner;
    }

    private int solitaryObjectivePoint(){
        int points=0;
        for(int i=0;i<model.getRoundsTrack().getDiceList().size();i++){
            points=points+model.getRoundsTrack().getDiceList().get(i).getValue();
        }
        return points;
    }


    public void calculateWinner(){
        if(model.getPlayerList().size()==1){
            if(model.getPlayerList().get(0).getVictoryPoints()>solitaryObjectivePoint()){
                view.showMessage("Your points:"+model.getPlayerList().get(0).getVictoryPoints()+"\nRounds Track points:"+solitaryObjectivePoint()+"\nYOU ARE THE WINNER!");
            }
            else{
                view.showMessage("Your points:"+model.getPlayerList().get(0).getVictoryPoints()+"\nRounds Track points:"+solitaryObjectivePoint()+"\nYOU ARE THE LOSER!");

            }
            if(model.getPlayerList().size()>1){
                Player winner=calculateMax();
                if (view.getPlayer()!=winner) {
                    view.showMessage("Your points:" + model.getPlayerList().get(0).getVictoryPoints() + "\nRounds Track points:" + solitaryObjectivePoint() + "\nYOU ARE THE WINNER!");
                }
                else {
                    view.showMessage("Your points:" + model.getPlayerList().get(0).getVictoryPoints() + "\nRounds Track points:" + solitaryObjectivePoint() + "\nYOU ARE THE LOSER!");
                }
            }
        }
    }

    public int calculateVictoryPoints(Player p){
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

    private int calculatePublicGoal(Player p){
        int points=0;
        for(int i=0;i<model.getPublicGoalCards().size();i++){
            points=points+model.getPublicGoalCards().get(i).calculateVictoryPoints(p);
        }
        return points;
    }
}

