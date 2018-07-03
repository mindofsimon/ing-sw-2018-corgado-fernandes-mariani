package it.polimi.deib.se2018.TestController.testToolCards;

import it.polimi.deib.se2018.client.NetworkHandler;
import it.polimi.deib.se2018.client.NetworkHandlerInterface;
import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.ServerInterface;
import it.polimi.deib.se2018.server.controller.CardActivationController;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.DicePlacementController;
import it.polimi.deib.se2018.server.controller.toolcard.*;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.CardActivation;
import it.polimi.deib.se2018.server.model.events.Event;
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
import it.polimi.deib.se2018.server.model.player.schemecard.ColoredBox;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import it.polimi.deib.se2018.server.modelview.ModelView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;


import static org.junit.Assert.*;

/**
 * Class used to test the activation of a card
 * @author Sirlan Fernandes
 */
public class TestCardActivation {
    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1;
    private Player p2;
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
        p1.setFavorMarkers(4);
        //carico una carta per ogni categoria
        controller.addToolCard(new ChangeDices("Pinza  Sgrossatrice",1,DiceColor.VIOLET));
        controller.addToolCard(new MoveDices("Pennello  per  Eglomise",2,Restriction.COLOR,DiceColor.BLUE,1));
        controller.addToolCard(new Taglierina("Taglierina  circolare",5,DiceColor.GREEN));
        controller.addToolCard(new ChangeAndPlaceCard("Pennello  per  Pasta  Salda",6,DiceColor.VIOLET,1));
        controller.addToolCard(new Marteletto("Marteletto",7,DiceColor.BLUE));
        controller.addToolCard(new ChangeAndPlaceCard("Tenaglia  a  Rotelle",8,DiceColor.RED,2));
        controller.addToolCard(new ChangeAndPlaceCard("Riga  in  Sughero",9,DiceColor.YELLOW,1));
        cardActivationController=new CardActivationController(model,controller.getToolCardsList(),dicePlacementController);
        //aggiiungo 1 dado nel dice stock
        Dice dice=new Dice(DiceColor.VIOLET);
        dice.setValue(2);
        model.getDiceStock().insertDice(dice);
        dice=new Dice(DiceColor.BLUE);
        dice.setValue(2);
        model.getDiceStock().insertDice(dice);
        p1.getPlayerScheme().getScheme()[0][0].setDice(dice);
        model.getRoundsTrack().insertDice(dice);
        p1.setnMoves(1);
    }


    /**
     * test control that the category of the card is ok
     * @author fernandes
     */
    @Test
    public void testCardActivationOk(){
        model.addPlayer(p1);
        model.addPlayer(p2);
        try {
            this.cardActivationController.setActivated(p1,false);
        } catch (RemoteException e) {
            fail();
        }
        Dice dice=new Dice(DiceColor.VIOLET);
        dice.setValue(2);
        Event event=new CardActivation(p1.getNickname(),1,dice);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(1,controller.getCategory());
        event=new CardActivation(p1.getNickname(),2,dice);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(0,controller.getCategory());
        event=new CardActivation(p1.getNickname(),5,dice);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(2,controller.getCategory());
        event=new CardActivation(p1.getNickname(),7,dice);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(4,controller.getCategory());
        event=new CardActivation(p1.getNickname(),6,dice);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(3,controller.getCategory());

    }

    /**
     * test control the errors of the activation of a card
     * @author fernandes
     */
    @Test
    public void testCardActivationKo() {
        model.addPlayer(p1);
        model.addPlayer(p2);
        try {
            this.cardActivationController.setActivated(p1, false);
        } catch (RemoteException e) {
            fail();
        }
        Dice dice = new Dice(DiceColor.VIOLET);
        dice.setValue(2);
        Event event = new CardActivation(p1.getNickname(), 1, dice);
        //non è il suo turno quindi errore di turno
        model.incrTurn();
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-3,controller.getCategory());
        model.decrTurn();
        //attivo una carta quindi tutte le carte non sono piu attivabili,errore
        p1.setCardActivated();
        try {
            this.cardActivationController.setActivated(p1, false);
        } catch (RemoteException e) {
            fail();
        }
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-4,controller.getCategory());
        p1.resetCardActivated();
        try {
            this.cardActivationController.setActivated(p1, false);
        } catch (RemoteException e) {
            fail();
        }
        //attivo una carta inesistente
        event = new CardActivation(p1.getNickname(), 3, dice);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-5,controller.getCategory());
        //passo alla mossa due del giocatore e provo ad attivare la carta 7,errore
        p1.setnMoves(2);
        try {
            this.cardActivationController.setActivated(p1, false);
        } catch (RemoteException e) {
            fail();
        }
        event = new CardActivation(p1.getNickname(), 7, dice);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-6,controller.getCategory());
        //segno 0 segnalini al giocatore e provo ad attivare la carta
        p1.setFavorMarkers(0);

        //sospendo il giocatore e vedo che torni l'errore di sospensione
        p1.suspend();
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-2,controller.getCategory());

    }

    /**
     * test control the errors of the activation of a card for single player
     * @author fernandes
     */
    @Test
    public void testCardActivationKoSP() {
        model.addPlayer(p1);
        try {
            this.cardActivationController.setActivated(p1, true);
        } catch (RemoteException e) {
            fail();
        }
        //provo ad attivare una carta con un dado non presente nel dice stock
        Dice dice = new Dice(DiceColor.RED);
        dice.setValue(2);
        Event event = new CardActivation(p1.getNickname(), 1, dice);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-8,controller.getCategory());
        //provo ad attivare una carta con un dado di colore diverso della carta
        event = new CardActivation(p1.getNickname(), 1, model.getDiceStock().getDice(1));
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-9,controller.getCategory());
    }

    /**
     * test control the errors of the activation of card 6
     * @author fernandes
     */
    @Test
    public void testCardActivationKoSPCard6() {
        //creo uno schema diverso per provare l'errore di questa carta
        Box[][] tabella1 = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella1[i][j] = new ColoredBox(DiceColor.YELLOW);
            }
        }
        SchemeCard schema1=new SchemeCard("schema1",3,tabella1);
        p1.setPlayerScheme(schema1);
        model.addPlayer(p1);
        model.getDiceStock().extractRandomDice();
        model.getDiceStock().extractRandomDice();
        Dice dice1=new Dice(DiceColor.YELLOW);
        dice1.setValue(2);
        model.getDiceStock().insertDice(dice1);
        Dice dice2=new Dice(DiceColor.BLUE);
        dice2.setValue(2);
        model.getDiceStock().insertDice(dice2);
        p1.getPlayerScheme().getScheme()[0][0].setDice(dice2);

        try {
            this.cardActivationController.setActivated(p1, true);
        } catch (RemoteException e) {
            fail();
        }
        Event event = new CardActivation(p1.getNickname(), 9, model.getDiceStock().getDice(0));
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-10,controller.getCategory());
        assertEquals(dice1,model.getDiceStock().getDice(0));

    }

    /**
     * test control the errors of the activation of card 8
     * @author fernandes
     */
    @Test
    public void testCardActivationKoSPCard8() {
        //creo uno schema diverso per provare l'errore di questa carta
        Box[][] tabella1 = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella1[i][j] = new ColoredBox(DiceColor.RED);
            }
        }
        SchemeCard schema1=new SchemeCard("schema1",3,tabella1);
        p1.setPlayerScheme(schema1);
        model.addPlayer(p1);
        model.getDiceStock().extractRandomDice();
        model.getDiceStock().extractRandomDice();
        Dice dice1=new Dice(DiceColor.RED);
        dice1.setValue(2);
        model.getDiceStock().insertDice(dice1);
        Dice d=new Dice(DiceColor.RED);
        d.setValue(2);
        model.getDiceStock().insertDice(d);
        Dice dice2=new Dice(DiceColor.BLUE);
        dice2.setValue(2);
        model.getDiceStock().insertDice(dice2);
        p1.getPlayerScheme().getScheme()[0][0].setDice(dice2);

        try {
            this.cardActivationController.setActivated(p1, true);
        } catch (RemoteException e) {
            fail();
        }
        Event event = new CardActivation(p1.getNickname(), 8, model.getDiceStock().getDice(0));
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-11,controller.getCategory());
        assertEquals(dice1,model.getDiceStock().getDice(0));

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
