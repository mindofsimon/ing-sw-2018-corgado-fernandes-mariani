package it.polimi.deib.se2018.TestController.testToolCards;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.CardActivationController;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.DicePlacementController;
import it.polimi.deib.se2018.server.controller.toolcard.ChangeDices;
import it.polimi.deib.se2018.server.controller.toolcard.MoveDices;
import it.polimi.deib.se2018.server.controller.toolcard.Restriction;
import it.polimi.deib.se2018.server.controller.toolcard.ToolCard;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.ElementType;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.LineType;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.RowAndColCard;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import it.polimi.deib.se2018.server.model.player.PrivateGoalCard;
import it.polimi.deib.se2018.server.model.player.schemecard.Box;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.tools.Tool;

import  static  org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * Class used to test set true or false the activeted of toolcards
 * @author Sirlan Fernandes
 */
public class TestSetToolCards {
    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1;
    private Player p2;
    private ArrayList<ToolCard> toolCardList=new ArrayList<ToolCard>();
    private CardActivationController cardActivationController;
    private DicePlacementController dicePlacementController=new DicePlacementController();


    @Before
    public void setUp(){
        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),RoundsTrack.getSingletonRoundsTrack());
        view=new RemoteView();
        controller=new Controller(model,view);
        p1=new Player("sirlan",1,PlayerColor.RED);
        p2=new Player("mari",2,PlayerColor.RED);
        //aggiungo qualche public card
        model.addPublicGoalCard(new RowAndColCard(LineType.ROW,ElementType.COLOR,"Colored Rows"));
        model.addPublicGoalCard(new RowAndColCard(LineType.COLUMN,ElementType.COLOR,"Colored Columns"));
        model.addPublicGoalCard(new RowAndColCard(LineType.ROW,ElementType.SHADE,"Shades Rows"));
        //creo uno schema tutto bianco solo per la prova
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        SchemeCard schema=new SchemeCard("schema1",3,tabella);
        p1.setPlayerScheme(schema);
        p1.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        p2.setPlayerScheme(schema);
        p2.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        controller.getGameRoundController().setTimer(p1.getnMoves(),p1.getOrder());
        controller.getGameRoundController().setTimer(p2.getnMoves(),p2.getOrder());


    }

    /**
     * test the set for the ChangeDices cards
     * @author fernandes
     */
    @Test
    public void testSetForChangeDices(){
        ToolCard card=new ChangeDices("Pinza  Sgrossatrice",1,DiceColor.VIOLET);
        toolCardList.add(card);
        cardActivationController=new CardActivationController(model,toolCardList,dicePlacementController);
        //aggiungo 1 dado viola e uno a caso nel diceStock
        Dice dice=new Dice(DiceColor.VIOLET);
        dice.setValue(1);
        model.getDiceStock().insertDice(dice);
        Dice dice1=new Dice(DiceColor.BLUE);
        dice.setValue(2);
        model.getDiceStock().insertDice(dice1);
        //setto per il single player
        try {
            this.cardActivationController.setActivated(p1,true);
        } catch (RemoteException e) {
            fail();
        }
        //dev'essere settato a true perche ce un dado viola e ci sono almeno 2 dadi nel dice stock
        assertEquals(true,toolCardList.get(0).getActivated());
        //rimuovo il dado viola
        model.getDiceStock().extractDice(dice);
        try {
            this.cardActivationController.setActivated(p1,true);
        } catch (RemoteException e) {
            fail();
        }
        //dev'essere settato a false perche la carta non puo essere attivata visto che non c'è piu il dado viola e ci sono meno di 2 dadi nel dice stock
        assertEquals(false,toolCardList.get(0).getActivated());

        //lo rifaccio per multiplayer assegnando al giocatore 2 segnalini
        p1.setFavorMarkers(2);
        try {
            this.cardActivationController.setActivated(p1,false);
        } catch (RemoteException e) {
            fail();
        }
        //a questo punto dev'essere true perche non controlla piu i colori dei dadi nel dicestock ma si il numero di segnalini
        assertEquals(true,toolCardList.get(0).getActivated());
        //ora assegno 0 segnalini al giocatore e la carta non puo essere piu attivata
        p1.setFavorMarkers(0);
        try {
            this.cardActivationController.setActivated(p1,false);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(false,toolCardList.get(0).getActivated());
    }

    /**
     * test the set for the ChangeDices cards
     * @author fernandes
     */
    @Test
    public void testSetForMoveDices(){
        ToolCard card=new MoveDices("Pennello  per  Eglomise",2,Restriction.COLOR,DiceColor.BLUE,1);
        toolCardList.add(card);
        cardActivationController=new CardActivationController(model,toolCardList,dicePlacementController);
        //in questo caso single player e multiplayer testano le stesse cose,perche segnalini e colori sono gia stati testati prima
        p1.setFavorMarkers(2);
        try {
            this.cardActivationController.setActivated(p1,false);
        } catch (RemoteException e) {
            fail();
        }
        //dovrebbe essere a false perche non ci sn dadi da muovere nello schema
        assertEquals(false,toolCardList.get(0).getActivated());
        //aggiungo un dado per poterlo muovere
        Dice dice=new Dice(DiceColor.VIOLET);
        dice.setValue(1);
        p2.getPlayerScheme().getScheme()[0][0].setDice(dice);
        try {
            this.cardActivationController.setActivated(p1,false);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(true,toolCardList.get(0).getActivated());
    }

    /**
     * Method used to clean some resources used during tests
     */
    @After
    public void clean(){
        controller.getGameRoundController().stopTimer();
        controller.cleanToolCards();
        toolCardList.clear();
        model.getDiceStock().clear();
        model.getPlayerList().clear();
        model.getDiceBag().clear();


    }

}
