package it.polimi.deib.se2018.server.model;

import it.polimi.deib.se2018.server.model.events.*;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.PublicGoalCard;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.common.utils.Observable;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Model class
 * @author Simone Mariani
 */
public class Model extends Observable implements Serializable {

    private ArrayList <Player> playerList;
    private ArrayList<PublicGoalCard> publicGoalCards;
    private DiceBag diceBag;
    private DiceStock diceStock;
    private RoundsTrack roundsTrack;
    private int round;
    private int turn;//per la prova con un giocatore non serve...potrebbe essere utile gestirlo con i colori?

    /**
     * Constructor, initializes model class
     * @param db dice bacg
     * @param ds dice stock
     * @param rt rounds track
     */
    public Model(DiceBag db, DiceStock ds, RoundsTrack rt){
        playerList=new ArrayList<Player>();
        publicGoalCards=new ArrayList<PublicGoalCard>();
        diceStock=ds;
        diceBag=db;
        roundsTrack=rt;
        round=1;
        turn=1;
    }

    //"Getters" methods
    /**
     * Get player list
     * @return player list
     */
    public ArrayList<Player> getPlayerList(){return playerList;}

    /**
     * Get public goal cards
     * @return public goal cards
     */
    public ArrayList<PublicGoalCard>getPublicGoalCards(){return publicGoalCards;}

    /**
     * Get dice bag
     * @return dice bag
     */
    public DiceBag getDiceBag(){return diceBag;}

    /**
     * Get dice stock
     * @return dice stock
     */
    public DiceStock getDiceStock(){return diceStock;}

    /**
     * Get rounds track
     * @return rounds track
     */
    public RoundsTrack getRoundsTrack() {return roundsTrack;}

    /**
     * Get round
     * @return round
     */
    public int getRound(){return round;}

    /**
     * Get turm
     * @return turn
     */
    public int getTurn(){return turn;}

    //"Setters" methods
    /**
     * Add player
     * @param p player
     */
    public void addPlayer(Player p){
        playerList.add(p);
    }

    /**
     * Add public goal card
     * @param c public goal card
     */
    public void addPublicGoalCard(PublicGoalCard c){publicGoalCards.add(c);}

    /**
     * Increase round
     */
    public void incrRound(){
        round++;
    }

    /**
     * Increase turn
     */
    public void incrTurn(){
        turn++;
    }

    /**
     * Decrease turn
     */
    public void decrTurn(){
        turn--;
    }

    /**
     * Copies model for the view
     * @return model
     */
    public Model copy(){//faccio una copia del model...il clone non funziona...
        Model model=new Model(this.diceBag,this.diceStock,this.roundsTrack);
        model.playerList=playerList;
        model.round=round;
        model.turn=turn;
        return model;
    }

    /**
     * Notify round update
     * @param p player
     */
    public void notifyTurnAndRoundUpdate(Player p) throws RemoteException {
        notify(new EndTurnMessage(p,this));
    }

    /**
     * Notify placement
     * @param p dice placement
     */
    public void notifyPlacement(DicePlacement p)throws RemoteException{
        notify(new DicePlacementMessage(findPlayerByName(p.getPlayerNickName()),this));
    }

    public void performCardActivation(Player p, int n)throws RemoteException{
        notify(new CardActivationMessage(p,this));
    }

    public void setGameOver1P(int solitaryObjective)throws RemoteException {
        notify(new GameOverMessage(getPlayerList().get(0),printScores(solitaryObjective),false));
    }

    public void setGameOverMP(Player p)throws RemoteException{
        notify(new GameOverMessage(p,printScores(),true));
    }

    private Player findPlayerByName(String name){
        for(int i=0;i<getPlayerList().size();i++){
            if(getPlayerList().get(i).getNickname().equals(name)) return getPlayerList().get(i);
        }
        return getPlayerList().get(0);
    }

    public Player findPlayerByOrder(int order){
        for(int i=0;i<getPlayerList().size();i++){
            if(getPlayerList().get(i).getOrder()==turn) return getPlayerList().get(i);
        }
        return getPlayerList().get(0);
    }

    public String printScores(int solitaryObjective){//SINGLE-PLAYER
        StringBuilder builder = new StringBuilder();
        builder.append("Your score: "+playerList.get(0).getVictoryPoints()+"\nSolitary objective (Rounds Track points): "+solitaryObjective+"\n");
        if(playerList.get(0).getVictoryPoints()>solitaryObjective) builder.append("WINNER!");
        else builder.append("LOSER!");
        return builder.toString();
    }


    public String printScores(){//MULTI-PLAYER (Faccio una specie di classifica)...magari posso usare java funzionale per ordinamento(?)
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<getPlayerList().size();i++){
            builder.append("Player: "+getPlayerList().get(i).getNickname()+" Points: "+getPlayerList().get(i).getVictoryPoints());
        }
        return builder.toString();
    }

    public String printPublicGoalCards(){
        StringBuilder builder = new StringBuilder();
        builder.append("Public Goal Cards:\n");
        for(int i=0;i<getPublicGoalCards().size();i++){
            builder.append("Card "+(i+1)+": "+getPublicGoalCards().get(i).toString()+"\n");
        }
        return builder.toString();
    }
}
