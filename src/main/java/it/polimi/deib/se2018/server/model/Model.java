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
 * @author Simone Mariani,Sirlan Fernandes
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
     * Gets player list
     * @return player list
     */
    public ArrayList<Player> getPlayerList(){return playerList;}

    /**
     * Gets public goal cards
     * @return public goal cards
     */
    public ArrayList<PublicGoalCard>getPublicGoalCards(){return publicGoalCards;}

    /**
     * Gets dice bag
     * @return dice bag
     */
    public DiceBag getDiceBag(){return diceBag;}

    /**
     * Gets dice stock
     * @return dice stock
     */
    public DiceStock getDiceStock(){return diceStock;}

    /**
     * Gets rounds track
     * @return rounds track
     */
    public RoundsTrack getRoundsTrack() {return roundsTrack;}

    /**
     * Gets round
     * @return round
     */
    public int getRound(){return round;}

    /**
     * Gets turm
     * @return turn
     */
    public int getTurn(){return turn;}

    //"Setters" methods
    /**
     * Adds player
     * @param p player
     */
    public void addPlayer(Player p){
        playerList.add(p);
    }

    /**
     * Adds public goal card
     * @param c public goal card
     */
    public void addPublicGoalCard(PublicGoalCard c){publicGoalCards.add(c);}

    /**
     * Increases round
     */
    public void incrRound(){
        round++;
    }

    /**
     * Increases turn
     */
    public void incrTurn(){
        turn++;
    }

    /**
     * Decreases turn
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
     * Notifies round update
     * @param p player
     * @throws RemoteException
     */
    public void notifyTurnAndRoundUpdate(Player p) throws RemoteException {
        notify(new EndTurnMessage(p,this));
    }

    /**
     * Notifies placement
     * @param p dice placement
     * @throws RemoteException
     */
    public void notifyPlacement(Player p)throws RemoteException{
        notify(new DicePlacementMessage(findPlayerByName(p.getNickname()),this));
    }

    /**
     * Performs card activation
     * @param p player
     * @throws RemoteException
     */
    public void notifyCardActivation(Player p)throws RemoteException{
        notify(new CardActivationMessage(p,this));
    }

    /**
     * Sets game over for single player
     * @param solitaryObjective Solitary objective
     * @throws RemoteException
     */
    public void setGameOver1P(int solitaryObjective)throws RemoteException {
        notify(new GameOverMessage(getPlayerList().get(0),printScores(solitaryObjective),false));
    }

    /**
     * Sets game over for multiplayer
     * @param p player
     * @throws RemoteException
     */
    public void setGameOverMP(Player p)throws RemoteException{
        notify(new GameOverMessage(p,printScores(),true));
    }

    /**
     * Finds player by name
     * @param name name
     * @return player based on the given name
     */
    public Player findPlayerByName(String name){
        for(int i=0;i<getPlayerList().size();i++){
            if(getPlayerList().get(i).getNickname().equals(name)) return getPlayerList().get(i);
        }
        return getPlayerList().get(0);
    }

    /**
     * Finds player by order
     * @param order order
     * @return player based on the given order
     */
    public Player findPlayerByOrder(int order){
        for(int i=0;i<getPlayerList().size();i++){
            if(getPlayerList().get(i).getOrder()==order) return getPlayerList().get(i);
        }
        return getPlayerList().get(0);
    }

    /**
     * Prints scores for single player game
     * @param solitaryObjective solitary objective
     * @return string that prints final score
     */
    public String printScores(int solitaryObjective){//SINGLE-PLAYER
        StringBuilder builder = new StringBuilder();
        builder.append("Your score: "+playerList.get(0).getVictoryPoints()+"\nSolitary objective (Rounds Track points): "+solitaryObjective+"\n");
        if(playerList.get(0).getVictoryPoints()>solitaryObjective) builder.append("WINNER!");
        else builder.append("LOSER!");
        return builder.toString();
    }


    /**
     * Prints scores for multiplayer game
     * @return string that prints final score
     */
    public String printScores(){//MULTI-PLAYER (Faccio una specie di classifica)...magari posso usare java funzionale per ordinamento(?)
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<getPlayerList().size();i++){
            if(!playerList.get(i).isOut()){
                builder.append("Player: "+getPlayerList().get(i).getNickname()+" Points: "+getPlayerList().get(i).getVictoryPoints()+"\n");
            }
        }
        return builder.toString();
    }

    /**
     * Prints public goal cards
     * @return string that prints all public goal cards
     */
    public String printPublicGoalCards(){
        StringBuilder builder = new StringBuilder();
        builder.append("Public Goal Cards:\n");
        for(int i=0;i<getPublicGoalCards().size();i++){
            builder.append("Card "+(i+1)+": "+getPublicGoalCards().get(i).toString()+"\n");
        }
        return builder.toString();
    }

    /**
     * Suspends player
     * @param p player
     * @throws RemoteException
     */
    public void suspendPlayer(Player p)throws RemoteException{
        p.suspend();
    }

    /**
     * Notifies player's suspension
     * @param p player
     * @throws RemoteException
     */
    public void notifyPlayerSuspension(Player p)throws RemoteException{
        notify(new PlayerSuspendedMessage(p,this));
    }

    /**
     * Sets player list
     * @param list player list
     */
    public void setPlayerList(ArrayList<Player> list){
        this.playerList=list;
    }

    /**
     * Notifies that player has quit
     * @param p player
     * @throws RemoteException
     */
    public void notifyPlayerQuit(Player p)throws RemoteException{
        notify(new QuitPlayerMessage(p));
    }

    /**
     * Gets first active player
     * @return first active player
     */
    public Player getFirstActive(){//In realtà lo userò per trovare l'unico giocatore rimasto attivo
        for(int i=0;i<playerList.size();i++){
            if(!playerList.get(i).isOut()&&!playerList.get(i).isSuspended()){
                return playerList.get(i);
            }
        }
        return null;
    }
}
