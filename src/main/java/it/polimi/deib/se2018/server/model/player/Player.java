package it.polimi.deib.se2018.server.model.player;

import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Player class
 * @author Simone Mariani
 */
public class Player implements Serializable {
    private PlayerColor playerColor;
    private int order;
    private int favorMarkers;
    private int victoryPoints;
    private String nickname;
    private SchemeCard playerScheme;
    private PrivateGoalCard privateGoalCard;
    private int nMoves;
    private int nTurns;
    private boolean dicePlaced;
    private boolean cardActivated;
    private ArrayList<SchemeCard> offeredSchemeCards=new ArrayList<SchemeCard>();
    private boolean suspended;
    private boolean out;

    /**
     * Constructor, initializes player class
     * @param name name
     * @param order order
     * @param color player's color
     */
    //Constructor
    public Player(String name, int order, PlayerColor color){
        playerColor=color;
        setOrder(order);
        nickname=name;
        setVictoryPoints(0);
        setFavorMarkers(0);//Assigned when player'll choose scheme card
        setnTurns(1);
        setnMoves(0);
        dicePlaced=false;
        cardActivated=false;
        suspended=false;
        out=false;

    }

    //"Getters" methods
    /**
     * Gets player's color
     * @return player's color
     */
    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    /**
     * Gets order of players
     * @return order of players
     */
    public int getOrder(){
        return order;
    }

    /**
     * Gets favor markers
     * @return favor markers
     */
    public int getFavorMarkers(){
        return favorMarkers;
    }

    /**
     * Gets victory points
     * @return victory points
     */
    public int getVictoryPoints(){
        return victoryPoints;
    }

    /**
     * Gets nickname
     * @return nickname
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * Gets player scheme
     * @return player scheme
     */
    public SchemeCard getPlayerScheme() {
        return playerScheme;
    }

    /**
     * Gets private goal card
     * @return private goal card
     */
    public PrivateGoalCard getPrivateGoalCard(){return privateGoalCard;}

    /**
     * Gets number of moves
     * @return number of moves
     */
    public int getnMoves() { return nMoves; }

    /**
     * Gets number of turns
     * @return number of turns
     */
    public int getnTurns(){ return nTurns; }

    /**
     * Tells if a card was already activated by this player during this turn
     * @return true if the card was activated, else returns false
     */
    public boolean cardActivated() { return cardActivated; }

    /**
     * Tells if a dice was already placed by this player during this turn
     * @return true if the dice was placed, else returns false
     */
    public boolean dicePlaced() { return dicePlaced; }

    /**
     * Gets offered scheme cards
     * @return offered scheme cards
     */
    public ArrayList<SchemeCard> getOfferedSchemeCards() { return offeredSchemeCards; }

    public boolean isSuspended() {
        return suspended;
    }

    public boolean isOut(){return out;}

    //"Setters" methods
    /**
     * Sets victory points
     * @param points victory points
     */
    public void setVictoryPoints(int points){
        victoryPoints=points;
    }

    /**
     * Sets favor markers
     * @param markers favor markers
     */
    public void setFavorMarkers(int markers){
        favorMarkers=markers;
    }

    /**
     * Sets order
     * @param order order
     */
    public void setOrder(int order){
        this.order=order;
    }

    /**
     * Sets private goal card
     * @param c private goal card
     */
    public void setPrivateGoalCard(PrivateGoalCard c){privateGoalCard=c;}

    /**
     * Sets player scheme
     * @param s player scheme
     */
    public void setPlayerScheme(SchemeCard s){playerScheme=s;}

    /**
     * Sets number of moves
     * @param n number of moves
     */
    public void setnMoves(int n){ nMoves=n; }

    /**
     * Sets number of turns
     * @param n number of turns
     */
    public void setnTurns(int n){ nTurns=n;}

    /**
     * Sets the dice placement for this turn
     */
    public void setDicePlacement(){
        dicePlaced=true;
    }

    /**
     * Sets the card activation for this turn
     */
    public void setCardActivated(){
        cardActivated=true;
    }

    /**
     * Resets the dice placement for this turn
     */
    public void resetDicePlacement(){
        dicePlaced=false;
    }

    /**
     * Resets the card activation for this turn
     */
    public void resetCardActivated(){
        cardActivated=false;
    }

    /**
     * Sets offered scheme cards
     * @param s scheme card
     */
    public void setOfferedSchemeCards(SchemeCard s){ offeredSchemeCards.add(s); }

    public void suspend(){suspended=true;}

    public void escape(){out=true;}

}



