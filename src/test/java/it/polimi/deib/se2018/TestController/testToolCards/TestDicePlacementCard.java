package it.polimi.deib.se2018.TestController.testToolCards;


import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.CardActivationController;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.DicePlacementController;
import it.polimi.deib.se2018.server.controller.toolcard.ChangeAndPlaceCard;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.DicePlacementCard;
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
import it.polimi.deib.se2018.server.model.player.schemecard.ValueBox;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class used to test the dices placed by a card
 * @author Sirlan Fernandes
 */
public class TestDicePlacementCard {
    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1;
    private CardActivationController cardActivationController;
    private DicePlacementController dicePlacementController=new DicePlacementController();
    Dice dice;


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
        //creo uno schema
        Box[][] tabella = new Box[4][5];
        tabella[0][0] = new ColoredBox(DiceColor.YELLOW);
        tabella[0][1] = new ColoredBox(DiceColor.BLUE);
        tabella[0][2] = new Box();
        tabella[0][3] = new Box();
        tabella[0][4] = new ValueBox(1);
        tabella[1][0] = new ColoredBox(DiceColor.GREEN);
        tabella[1][1] = new ValueBox(6);
        tabella[1][2] = new ValueBox(5);
        tabella[1][3] = new Box();
        tabella[1][4] = new ValueBox(4);
        tabella[2][0] = new ValueBox(3);
        tabella[2][1] = new Box();
        tabella[2][2] = new ColoredBox(DiceColor.RED);
        tabella[2][3] = new Box();
        tabella[2][4] = new ColoredBox(DiceColor.GREEN);
        tabella[3][0] = new ValueBox(2);
        tabella[3][1] = new Box();
        tabella[3][2] = new Box();
        tabella[3][3] = new ColoredBox(DiceColor.BLUE);
        tabella[3][4] = new ColoredBox(DiceColor.YELLOW);
        SchemeCard schema=new SchemeCard("schema1",3,tabella);
        p1.setPlayerScheme(schema);
        p1.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        controller.getGameRoundController().setTimer(p1.getnMoves(),p1.getOrder());
        p1.setFavorMarkers(4);
        //carico una carta per ogni categoria
        controller.addToolCard(new ChangeAndPlaceCard("Pennello  per  Pasta  Salda",6,DiceColor.VIOLET,1,"Dopo  aver  scelto  un  dado,\n  tira  nuovamente  quel  dado\nSe  non  puoi  piazzarlo,\n  riponilo  nella  Riserva"));
        controller.addToolCard(new ChangeAndPlaceCard("Tenaglia  a  Rotelle",8,DiceColor.RED,1,"Dopo  il  tuo  primo  turno\n  scegli  immediatamente  un  altro  dado\nSalta  il  tuo  secondo  turno  in  questo  round"));
        controller.addToolCard(new ChangeAndPlaceCard("Riga  in  Sughero",9,DiceColor.YELLOW,1,"Dopo  aver  scelto  un  dado,  piazzalo  in  una  casella  che  non  sia  adiacente  a  un  altro  dado"));
        controller.addToolCard(new ChangeAndPlaceCard("Diluente  per  Pasta  Salda",11,DiceColor.VIOLET,1,"Dopo  aver  scelto  un  dado,\n  riponilo  nel  Sacchetto,\n  poi  pescane  uno  dal  SacchettoScegli  il  valore  del  nuovo  dado  e  piazzalo,  rispettando  tutte  le  restrizioni  di  piazzamento"));
        cardActivationController=new CardActivationController(model,controller.getToolCardsList(),dicePlacementController);
        //aggiiungo 1 dado nel dice stock
        dice=new Dice(DiceColor.VIOLET);
        dice.setValue(2);
        model.getDiceStock().insertDice(dice);

    }

    /**
     * test control that the effect of placement by chnage and placed card is ok
     * @author fernandes
     */
    @Test
    public void testDicePlacementCardOk() {
        model.addPlayer(p1);
        //piazzo il dado usando la carta 6
        Event event = new DicePlacementCard(p1.getNickname(),0,2,model.getDiceStock().getDice(0), 6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(dice, p1.getPlayerScheme().getScheme()[0][2].getDice());

    }

    /**
     * test control that the effect of placement by chnage and placed card is Ko
     * @author fernandes
     */
    @Test
    public void testDicePlacementCardKo() {
        model.addPlayer(p1);
        //piazzo il dado usando la carta 6 con dei valori sbagliati di riga e collona
        Event event = new DicePlacementCard(p1.getNickname(),5,6,model.getDiceStock().getDice(0), 6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-2, controller.getCategory());
        //piazzo il dado usando la carta 6 piazzando in meszzo il primo dado
        event = new DicePlacementCard(p1.getNickname(),1,3,model.getDiceStock().getDice(0), 6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-3, controller.getCategory());
        //piazzo il dado usando la carta 6 piazzando in una casella non accetabile
        event = new DicePlacementCard(p1.getNickname(),0,0,model.getDiceStock().getDice(0), 6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-4, controller.getCategory());
        //piazzo un dado
        Dice d=new Dice(DiceColor.YELLOW);
        d.setValue(3);
        p1.getPlayerScheme().getScheme()[0][2].setDice(d);
        //provo a piazzare il dado con stesso valore e colore vicino
        event = new DicePlacementCard(p1.getNickname(),0,3,d, 6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-5, controller.getCategory());
        //provo a piazzare un dado lontano
        event = new DicePlacementCard(p1.getNickname(),2,1,model.getDiceStock().getDice(0), 6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-6, controller.getCategory());
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
