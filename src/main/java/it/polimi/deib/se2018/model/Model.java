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

public class Model extends Observable{

    private ArrayList <Player> playerList;
    private ArrayList<PublicGoalCard> publicGoalCards;
    private DiceBag diceBag;
    private DiceStock diceStock;
    private RoundsTrack roundsTrack;
    private int round;
    private int turn;//per la prova con un giocatore non serve...potrebbe essere utile gestirlo con i colori?

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
    public ArrayList<Player> getPlayerList(){return playerList;}

    public ArrayList<PublicGoalCard>getPublicGoalCards(){return publicGoalCards;}

    public DiceBag getDiceBag(){return diceBag;}

    public DiceStock getDiceStock(){return diceStock;}

    public RoundsTrack getRoundsTrack() {return roundsTrack;}

    public int getRound(){return round;}

    public int getTurn(){return turn;}

    //"Setters" methods
    public void addPlayer(Player p){
        playerList.add(p);
    }

    public void addPublicGoalCard(PublicGoalCard c){publicGoalCards.add(c);}

    public void incrRound(){
        round++;
    }

    public void incrTurn(){
        turn++;
    }

    public void decrTurn(){
        turn--;
    }

    public Model copy(){//faccio una copia del model...il clone non funziona...
        Model model=new Model(this.diceBag,this.diceStock,this.roundsTrack);
        model.playerList=playerList;
        model.round=round;
        model.turn=turn;
        return model;
    }

    public void notifyRoundUpdate(Player p) {
        notify(new EndTurnMessage(p,this));
    }

    public void notifyPlacement(DicePlacement p){
        notify(new DicePlacementMessage(p.getPlayer(),this));
    }

    public void performCardActivation(Player p, int n){
        //VERDREMO POI
    }

}
