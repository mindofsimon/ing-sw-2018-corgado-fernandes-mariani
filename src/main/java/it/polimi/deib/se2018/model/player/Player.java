package it.polimi.deib.se2018.model.player;

import it.polimi.deib.se2018.model.player.schemecard.SchemeCard;

/**
 * Player class
 * @author Simone Mariani
 */
public class Player {
    private PlayerColor playerColor;
    private int order;
    private int favorMarkers;
    private int victoryPoints;
    private String nickname;
    private SchemeCard playerScheme;
    private PrivateGoalCard privateGoalCard;
    private int nMoves;
    private int nTurns;


    /**
     * Constructor, initializes player class
     * @param name name
     * @param order order
     * @param color color
     */
    //Constructor
    public Player(String name, int order, PlayerColor color){
        playerColor=color;
        setOrder(order);
        nickname=name;
        setVictoryPoints(0);
        setFavorMarkers(0);//Assigned when player'll choose scheme card
        setnMoves(0);

    }

    //"Getters" methods
    /**
     * Get player color
     * @return player color
     */
    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    /**
     * Get order
     * @return order
     */
    public int getOrder(){
        return order;
    }

    /**
     * Get favor markers
     * @return favor markers
     */
    public int getFavorMarkers(){
        return favorMarkers;
    }

    /**
     * Get victory points
     * @return victory points
     */
    public int getVictoryPoints(){
        return victoryPoints;
    }

    /**
     * Get nickname
     * @return nickname
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * Get player scheme
     * @return player scheme
     */
    public SchemeCard getPlayerScheme() {
        return playerScheme;
    }

    /**
     * Get private goal card
     * @return private goal card
     */
    public PrivateGoalCard getPrivateGoalCard(){return privateGoalCard;}

    /**
     * Get number of moves
     * @return number of moves
     */
    public int getnMoves() { return nMoves; }

    /**
     * Get number of turns
     * @return number of turns
     */
    public int getnTurns(){ return nTurns; }

    //"Setters" methods
    /**
     * Set victory points
     * @param points victory points
     */
    public void setVictoryPoints(int points){
        victoryPoints=points;
    }

    /**
     * Set favor markers
     * @param markers favor markers
     */
    public void setFavorMarkers(int markers){
        favorMarkers=markers;
    }

    /**
     * Set order
     * @param order order
     */
    public void setOrder(int order){
        this.order=order;
    }

    /**
     * Set private goal card
     * @param c private goal card
     */
    public void setPrivateGoalCard(PrivateGoalCard c){privateGoalCard=c;}

    /**
     * Set player scheme
     * @param s player scheme
     */
    public void setPlayerScheme(SchemeCard s){playerScheme=s;}

    /**
     * Set number of moves
     * @param n number of moves
     */
    public void setnMoves(int n){ nMoves=n; }

    /**
     * Set number of turns
     * @param n number of turns
     */
    public void setnTurns(int n){ nTurns=n;}
}

