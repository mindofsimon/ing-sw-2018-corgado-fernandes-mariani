package it.polimi.deib.se2018.TestController.testToolCards;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.toolcard.ChangeDices;
import it.polimi.deib.se2018.server.controller.toolcard.MoveDices;
import it.polimi.deib.se2018.server.controller.toolcard.Restriction;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.ColorDice;
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.MoveDice;
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
 * Class used to test rhe effects of MoveDiceCards
 * @author Sirlan Fernandes
 */
public class TestMoveDice {
    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1;
    Dice dice;
    Dice dice1;


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
        p1.setFavorMarkers(3);
        //aggiungo le carte
        controller.addToolCard(new MoveDices("Pennello  per  Eglomise",2,Restriction.COLOR,DiceColor.BLUE,1,"Muovi  un  qualsiasi  dado  nella  tua\n  vetrata  ignorando  le  restrizioni  di  colore"));
        controller.addToolCard(new MoveDices("Alesatore  per  lamina  di  rame",3,Restriction.SHADE,DiceColor.RED,1,"Muovi  un  qualsiasi  dado  nella  tua  vetrata  ignorando  le  restrizioni\n  di  valore"));
        controller.addToolCard(new MoveDices("Lathekin",4,Restriction.NULL,DiceColor.YELLOW,2,"Muovi  esattamente  due  dadi,\n  rispettando  tutte  le  restrizioni  di\n  piazzamento"));
        controller.addToolCard(new MoveDices("Taglierina  Manuale",12,Restriction.NULL,DiceColor.BLUE,2,"Muovi  fino  a  due  dadi  dello  stesso  colore \n di  un  solo  dado  sul  Tracciato  dei  Round"));
        //aggiungo un dado nello schema
        dice1=new Dice(DiceColor.YELLOW);
        dice1.setValue(2);
        p1.getPlayerScheme().getScheme()[0][0].setDice(dice1);
        //aggiungo un dado nel Rounds Track
        dice=new Dice(DiceColor.BLUE);
        dice.setValue(2);
        model.getRoundsTrack().insertDice(dice);

    }

    /**
     * test control that the effect of MoveDices card is ok
     * @author fernandes
     */
    @Test
    public void testMoveDiceOk(){
        model.addPlayer(p1);
        //attivo l'effetto della carta 2 muovendo il dado senza rispettare le restrizioni di colore
        Event event= new MoveDice(p1.getNickname(),0,0,0,1,2);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(dice1,p1.getPlayerScheme().getScheme()[0][1].getDice());
        assertEquals(null,p1.getPlayerScheme().getScheme()[0][0].getDice());
    }

    /**
     * test control that the effect of MoveDices card is Ko
     * @author fernandes
     */
    @Test
    public void testMoveDiceKo(){
        model.addPlayer(p1);
        //attivo l'effetto della carta 2 passando dei valori sbagliati delle righe e collone
        Event event= new MoveDice(p1.getNickname(),6,7,0,1,2);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-2, controller.getCategory());
        //attivo l'effetto della carta 2 passando una riga e una collona senza dado
        event= new MoveDice(p1.getNickname(),0,3,0,1,2);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-3, controller.getCategory());
        //attivo l'effetto della carta 2 provando a muovere un primo dado in mezzo allo schema
        event= new MoveDice(p1.getNickname(),0,0,1,1,2);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-4, controller.getCategory());
        //attivo l'effetto della carta 2 provando a muovere il dado in una posizione col valore diverso
        event= new MoveDice(p1.getNickname(),0,0,0,4,2);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-6, controller.getCategory());
        //attivo l'effetto della carta 3 provando a muovere il dado in una posizione col colore diverso
        event= new MoveDice(p1.getNickname(),0,0,0,1,3);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-5, controller.getCategory());
        //attivo l'effetto della carta 4 provando a muovere il dado in una che non rispetta le regole di piazzamento
        event= new MoveDice(p1.getNickname(),0,0,0,1,4);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-7, controller.getCategory());
        //piazzo un'altro dado
        Dice dice1=new Dice(DiceColor.BLUE);
        dice1.setValue(2);
        p1.getPlayerScheme().getScheme()[0][1].setDice(dice);
        //provo a muovere il dado lontano da un dado piazzato
        event= new MoveDice(p1.getNickname(),0,0,0,3,4);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-9, controller.getCategory());
        //provo a muovere il dado vicino a uno con lo stesso valore
        event= new MoveDice(p1.getNickname(),0,0,0,2,4);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-8, controller.getCategory());
        //provo a muovere il dado che non rispetta il colore del dado scelto nel round track
        controller.getToolCardsList().get(3).setColorDice(DiceColor.VIOLET);
        event= new MoveDice(p1.getNickname(),0,1,1,1,12);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-10, controller.getCategory());

    }

    /**
     * test control that the effect of MoveDices card is Ko
     * @author fernandes
     */
    @Test
    public void testSetColorDice(){
        model.addPlayer(p1);
        Event event=new ColorDice(p1.getNickname(),DiceColor.BLUE,12);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(DiceColor.BLUE,controller.getToolCardsList().get(3).getColorDice());
        //testo con un colore che non è presente nel Rounds Track
        event=new ColorDice(p1.getNickname(),DiceColor.RED,12);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-2,controller.getCategory());

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
