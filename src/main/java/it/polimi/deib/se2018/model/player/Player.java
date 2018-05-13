package it.polimi.deib.se2018.model.player;

import it.polimi.deib.se2018.model.player.schemecard.SchemeCard;

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
    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public int getOrder(){
        return order;
    }

    public int getFavorMarkers(){
        return favorMarkers;
    }

    public int getVictoryPoints(){
        return victoryPoints;
    }

    public String getNickname(){
        return nickname;
    }

    public SchemeCard getPlayerScheme() {
        return playerScheme;
    }

    public PrivateGoalCard getPrivateGoalCard(){return privateGoalCard;}

    public int getnMoves() { return nMoves; }

    public int getnTurns(){ return nTurns; }

    //"Setters" methods
    public void setVictoryPoints(int points){
        victoryPoints=points;
    }

    public void setFavorMarkers(int markers){
        favorMarkers=markers;
    }

    public void setOrder(int order){
        this.order=order;
    }

    public void setPrivateGoalCard(PrivateGoalCard c){privateGoalCard=c;}

    public void setPlayerScheme(SchemeCard s){playerScheme=s;}

    public void setnMoves(int n){ nMoves=n; }

    public void setnTurns(int n){ nTurns=n;}
}

