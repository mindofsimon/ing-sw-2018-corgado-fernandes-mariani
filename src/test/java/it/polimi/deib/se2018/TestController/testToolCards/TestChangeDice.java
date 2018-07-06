package it.polimi.deib.se2018.TestController.testToolCards;


import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.CardActivationController;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.DicePlacementController;
import it.polimi.deib.se2018.server.controller.toolcard.*;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.ChangeDice;
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

import java.rmi.RemoteException;

import static org.junit.Assert.*;

/**
 * Class used to test the activation of cards 1 and 10
 * @author Sirlan Fernandes
 */
public class TestChangeDice {

    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1;
    private CardActivationController cardActivationController;
    private DicePlacementController dicePlacementController=new DicePlacementController();


    @Before
    public void setUp(){
        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),RoundsTrack.getSingletonRoundsTrack());
        view=new RemoteView();
        controller=new Controller(model,view);
        p1=new Player("sirlan",1,PlayerColor.RED);
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
        controller.getGameRoundController().setTimer(p1.getnMoves(),p1.getOrder());
        p1.setFavorMarkers(4);
        //carico una carta per ogni categoria
        controller.addToolCard(new ChangeDices("Pinza  Sgrossatrice",1,DiceColor.VIOLET,"Dopo  aver  scelto  un  dado,\n  aumenta  o  dominuisci  il  valore\n del  dado  scelto  di  1"));
        controller.addToolCard(new ChangeDices("Tampone  Diamantato",10,DiceColor.GREEN,"Dopo  aver  scelto  un  dado,\n  giralo  sulla  faccia  opposta"));
        cardActivationController=new CardActivationController(model,controller.getToolCardsList(),dicePlacementController);
        //aggiiungo 1 dado nel dice stock
        Dice dice=new Dice(DiceColor.VIOLET);
        dice.setValue(2);
        model.getDiceStock().insertDice(dice);
    }

    /**
     * test control that the effect of cards 1 and 10 is ok
     * @author fernandes
     */
    @Test
    public void testDiceChangeOk(){
        model.addPlayer(p1);
        //attivo l'effetto della carta 1(incremento)
        Event event= new ChangeDice(p1.getNickname(),model.getDiceStock().getDice(0),"I",1);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(3,model.getDiceStock().getDice(0).getValue());
        //attivo l'effetto della carta 1(Decremento)
        event= new ChangeDice(p1.getNickname(),model.getDiceStock().getDice(0),"D",1);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(2,model.getDiceStock().getDice(0).getValue());
        //attivo l'effetto della carta 10
        event= new ChangeDice(p1.getNickname(),model.getDiceStock().getDice(0),"N",10);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(5,model.getDiceStock().getDice(0).getValue());


    }

    /**
     * test control that the effect of cards 1 and 10 is Ko
     * @author fernandes
     */
    @Test
    public void testDiceChangeKo(){
        model.addPlayer(p1);
        //attivo l'effetto della carta 1 con un dado non presente nel dice stock
        Dice dice=new Dice(DiceColor.BLUE);
        dice.setValue(1);
        Event event= new ChangeDice(p1.getNickname(),dice,"I",1);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-2,controller.getCategory());
        //inserisco il dado di valore 1 nel dice stock e attivo l'effetto della carta 1(Decremento)
        model.getDiceStock().insertDice(dice);
        event= new ChangeDice(p1.getNickname(),model.getDiceStock().getDice(1),"D",1);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-3,controller.getCategory());
        //inserisco il dado di valore 6 nel dice stock e attivo l'effetto della carta 1(incremento)
        model.getDiceStock().getDice(1).setValue(6);
        event= new ChangeDice(p1.getNickname(),model.getDiceStock().getDice(1),"I",1);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-4,controller.getCategory());


    }

    /**
     * Method used to clean some resources used during tests
     */
    @After
    public void clean(){
        controller.getGameRoundController().stopTimer();
        controller.cleanToolCards();
        model.getDiceStock().clear();
        model.getPlayerList().clear();
        model.getDiceBag().clear();
        model.getRoundsTrack().clear();
        p1.setFavorMarkers(4);

    }
}
