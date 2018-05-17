package it.polimi.deib.se2018.model;

import it.polimi.deib.se2018.model.events.DicePlacement;
import it.polimi.deib.se2018.model.events.DicePlacementMessage;
import it.polimi.deib.se2018.model.events.EndTurnMessage;
import it.polimi.deib.se2018.model.gametable.DiceBag;
import it.polimi.deib.se2018.model.gametable.DiceStock;
import it.polimi.deib.se2018.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.PublicGoalCard;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.utils.Observable;

import java.util.ArrayList;

/**
 * Model class
 * @author Simone Mariani
 */
public class Model extends Observable{

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
    public void notifyRoundUpdate(Player p) {
        notify(new EndTurnMessage(p,this));
    }

    /**
     * Notify placement
     * @param p dice placement
     */
    public void notifyPlacement(DicePlacement p){
        notify(new DicePlacementMessage(p.getPlayer(),this));
    }

    public void performCardActivation(Player p, int n){
        //VERDREMO POI
    }

}
