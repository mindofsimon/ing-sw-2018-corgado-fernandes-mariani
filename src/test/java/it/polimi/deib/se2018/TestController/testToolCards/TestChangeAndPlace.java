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
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.ChangeAndPlace;
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.SetDice;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class used to test the changes of the Change and place cards
 * @author Sirlan Fernandes
 */
public class TestChangeAndPlace {
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
     * test control that the changes of changes and place cards is ok
     * @author fernandes
     */
    @Test
    public void testDiceChangeAndPlaceOk() {
        model.addPlayer(p1);
        //provo l'effetto della carta 6
        Event event = new ChangeAndPlace(p1.getNickname(),model.getDiceStock().getDice(0), 6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(1, controller.getCategory());
        //provo l'effetto della carta 8
        event = new ChangeAndPlace(p1.getNickname(),model.getDiceStock().getDice(0), 8);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(2, controller.getCategory());
        //provo l'effetto della carta 9
        event = new ChangeAndPlace(p1.getNickname(),model.getDiceStock().getDice(0), 9);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(3, controller.getCategory());
        //provo l'effetto della carta 11
        event = new ChangeAndPlace(p1.getNickname(),model.getDiceStock().getDice(0), 11);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(0, model.getDiceStock().getDice(0).getValue());
    }

    /**
     * test control that the changes of changes and place cards is ko
     * @author fernandes
     */
    @Test
    public void testDiceChangeAndPlaceKo() {
        model.addPlayer(p1);
        //provo l'effetto della carta 6 con un dado non presente nel dice stock
        Dice d=new Dice(DiceColor.RED);
        d.setValue(4);
        Event event = new ChangeAndPlace(p1.getNickname(),d, 6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-2, controller.getCategory());
        //creo uno schema diverso per provare l'errore di questa carta
        Box[][] tabella1 = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella1[i][j] = new ColoredBox(DiceColor.RED);
            }
        }
        SchemeCard schema1=new SchemeCard("schema1",3,tabella1);
        p1.setPlayerScheme(schema1);
        p1.getPlayerScheme().getScheme()[0][0].setDice(d);
        //provo con un dado non piazzabile
        event = new ChangeAndPlace(p1.getNickname(),model.getDiceStock().getDice(0), 6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-3, controller.getCategory());

    }
    /**
     * test control that the changes of set dice is ok
     * @author fernandes
     */
    @Test
    public void testSetDice() {
        model.addPlayer(p1);
        Event event = new SetDice(model.getDiceStock().getDice(0), 4);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(4, model.getDiceStock().getDice(0).getValue());

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
