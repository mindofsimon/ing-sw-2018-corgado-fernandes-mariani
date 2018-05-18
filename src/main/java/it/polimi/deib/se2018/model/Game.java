package it.polimi.deib.se2018.model;

import it.polimi.deib.se2018.controller.Controller;
import it.polimi.deib.se2018.controller.GameRoundController;
import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.gametable.DiceBag;
import it.polimi.deib.se2018.model.gametable.DiceStock;
import it.polimi.deib.se2018.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.model.player.PlayerColor;
import it.polimi.deib.se2018.model.player.schemecard.Box;
import it.polimi.deib.se2018.model.player.schemecard.ColoredBox;
import it.polimi.deib.se2018.model.player.schemecard.SchemeCard;
import it.polimi.deib.se2018.model.player.schemecard.ValueBox;
import it.polimi.deib.se2018.modelview.ModelView;
import it.polimi.deib.se2018.view.View;

/**
 * Game class
 * @author Simone Mariani
 */
public class Game implements Runnable
{
    private Model model;
    private ModelView modelView;
    private View view;
    private Controller controller;


    public static void main( String[] args )
    {
        Game game=new Game();
        game.run();

    }

    /**
     * Constructor, initializes game class, loads a scheme card, show messages..
     */
    public Game(){
        //CARICO UNO SCHEMA
        Box[][] tabella = new Box[4][5];
        tabella[0][0] = new Box();
        tabella[0][1] = new Box();//è UNA CASELLA BIANCA (WHITE=W)!
        tabella[0][2] = new Box();
        tabella[0][3] = new Box();
        tabella[0][4] = new Box();
        tabella[1][0] = new ColoredBox(DiceColor.VIOLET);
        tabella[1][1] = new ColoredBox(DiceColor.GREEN);
        tabella[1][2] = new ColoredBox(DiceColor.YELLOW);
        tabella[1][3] = new ValueBox(6);
        tabella[1][4] = new ColoredBox(DiceColor.VIOLET);
        tabella[2][0] = new ColoredBox(DiceColor.BLU);
        tabella[2][1] = new ColoredBox(DiceColor.RED);
        tabella[2][2] = new ValueBox(5);
        tabella[2][3] = new ColoredBox(DiceColor.GREEN);
        tabella[2][4] = new ColoredBox(DiceColor.BLU);
        tabella[3][0] = new ValueBox(4);
        tabella[3][1] = new ColoredBox(DiceColor.YELLOW);
        tabella[3][2] = new ValueBox(3);
        tabella[3][3] = new ValueBox(2);
        tabella[3][4] = new ColoredBox(DiceColor.BLU);
        SchemeCard retro=null;
        SchemeCard scheme = new SchemeCard("schema1",3,retro,tabella);
        DiceBag diceBag= DiceBag.getSingletonDiceBag();
        DiceStock diceStock=DiceStock.getSingletonDiceStock();
        RoundsTrack roundsTrack=RoundsTrack.getSingletonRoundsTrack();


        //MANCA SPOSTARE INIZIALIZZAZIONE GIOCATORE E TAVOLO DI GIOCO NEL CONTROLLER

        //INIZIALIZZO COMPONENTI PRINCIPALI
        model=new Model(diceBag,diceStock,roundsTrack);
        modelView=new ModelView();
        Player player1=new Player("Simon",1,PlayerColor.RED);
        model.addPlayer(player1);
        view=new View(player1);
        controller=new Controller(model,view);
        player1.setPlayerScheme(scheme);
        model.register(modelView);
        modelView.register(view);
        view.register(controller);

        controller.initGame();
        //GameRoundController.updateDiceStock(); Bisogna metterlo a posto

        //VISUALIZZAZIONE INIZIALE (PER OGNI VIEW)...PER ORA DI UNA SOLA
        view.showMessage("PUBLIC GOAL CARDS: \n");
        for(int i=0;i<model.getPublicGoalCards().size();i++){
            view.showMessage(model.getPublicGoalCards().get(i).toString()+"\n");
        }
        view.showMessage("\nPRIVATE GOAL CARD: \n");
        view.showMessage(view.getPlayer().getPrivateGoalCard().toString()+"\n\n");//POI DOVRO' FARLO PER OGNI GIOCATORE
        view.showMessage("ROUND: 1 TURN: "+view.getPlayer().getNickname()+"\n");
        view.showMessage(view.getPlayer().getPlayerScheme().toString());
        view.showMessage(diceBag.toString()+diceStock.toString());
    }

    /**
     * Runs game
     */
    @Override
    public void run(){
        view.run();
    }

}
