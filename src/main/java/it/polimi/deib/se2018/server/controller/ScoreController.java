package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

/**
 * Score controller class
 * @author Simone Marian, Sirlan Fernandes
 */
public class ScoreController {//Implements methods related to Victory Points and Game Score
    private Model model;
    private RemoteView view;

    /**
     * Constructor, initializes score controller class
     * @param m model
     * @param v remote view
     */
    public ScoreController(Model m,RemoteView v){
        model=m;
        view=v;
    }


    /**
     * Calculates which player has most points
     * @return winner
     */
    private Player calculateMax() {
        Player winner=model.getPlayerList().get(0);
        for(int i=1;i<model.getPlayerList().size();i++){
            if(model.getPlayerList().get(i).getVictoryPoints()>winner.getVictoryPoints()){
                winner=model.getPlayerList().get(i);
            }
            else if(model.getPlayerList().get(i).getVictoryPoints()==winner.getVictoryPoints()){
                if(calculatePrivateGoal(model.getPlayerList().get(i))>calculatePrivateGoal(winner)){
                    winner=model.getPlayerList().get(i);
                }
                else if(calculatePrivateGoal(model.getPlayerList().get(i))==calculatePrivateGoal(winner)){
                    if(model.getPlayerList().get(i).getFavorMarkers()>winner.getFavorMarkers()){
                        winner=model.getPlayerList().get(i);
                    }
                    else if(model.getPlayerList().get(i).getFavorMarkers()==winner.getFavorMarkers()){
                        if(model.getPlayerList().get(i).getOrder()>winner.getOrder()){//Come posizione più bassa intendo l'ultimo ad eseguire il primo turno (es: il quarto per quattro giocatori)
                            winner=model.getPlayerList().get(i);
                        }
                    }
                }
            }
        }
        return winner;
    }

    /**
     * Counts solitary objective points
     * @return solitary objective points
     */
    public int solitaryObjectivePoint() {
        int points=0;
        for(int i=0;i<model.getRoundsTrack().getDiceList().size();i++){
            points=points+model.getRoundsTrack().getDiceList().get(i).getValue();
        }
        return points;
    }

    /**
     * Calculates the winner
     * @return winner
     */
    public Player calculateWinner() {
        return calculateMax();
    }

    /**
     * Calculates victory points
     * @param p player
     * @return victory points
     */
    public int calculateVictoryPoints(Player p){
        return calculatePrivateGoal(p)+calculateEmptyBoxes(p)+calculatePublicGoal(p)+p.getFavorMarkers();
    }

    /**
     * Calculates empty boxes and minus points if empty
     * @param p player
     * @return points
     */
    private int calculateEmptyBoxes(Player p){
        int points=0;
        for(int i=0;i<p.getPlayerScheme().getROWS();i++){
            for(int j=0;j<p.getPlayerScheme().getCOLS();j++){
                if(p.getPlayerScheme().getScheme()[i][j].getDice()== null ){
                    if(model.getPlayerList().size()==1){
                        points=points-3;
                    }
                    else{
                        points=points-1;
                    }
                }
            }
        }
        return points;
    }

    /**
     * Calculates private goal points
     * @param p player
     * @return private goal points
     */
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

    /**
     * Calculate public goal points
     * @param p player
     * @return public goal points
     */
    private int calculatePublicGoal(Player p){
        int points=0;
        for(int i=0;i<model.getPublicGoalCards().size();i++){
            points=points+model.getPublicGoalCards().get(i).calculateVictoryPoints(p);
        }
        return points;
    }
}
