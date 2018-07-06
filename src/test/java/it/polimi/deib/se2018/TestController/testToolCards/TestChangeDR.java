package it.polimi.deib.se2018.TestController.testToolCards;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.CardActivationController;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.DicePlacementController;
import it.polimi.deib.se2018.server.controller.toolcard.Taglierina;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.ChangeDiceDR;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class used to test the activation of card 5
 * @author Sirlan Fernandes
 */
public class TestChangeDR {
    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1;
    private CardActivationController cardActivationController;
    private DicePlacementController dicePlacementController=new DicePlacementController();
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
        controller.addToolCard(new Taglierina("Taglierina  circolare",5,DiceColor.GREEN,"Dopo  aver  scelto  un  dado,\n  scambia  quel  dado  con  un  dado\n  sul  Tracciato  dei  Round"));
        cardActivationController=new CardActivationController(model,controller.getToolCardsList(),dicePlacementController);
        //aggiiungo 1 dado nel dice stock
        dice=new Dice(DiceColor.VIOLET);
        dice.setValue(2);
        model.getDiceStock().insertDice(dice);
        //aggiungo 1 dado nel round track
        dice1=new Dice(DiceColor.BLUE);
        dice1.setValue(3);
        model.getRoundsTrack().insertDice(dice1);
    }

    /**
     * test control that the effect of cards 5 is ok
     * @author fernandes
     */
    @Test
    public void testDiceChangeDROk() {
        model.addPlayer(p1);
        //attivo l'effetto della carta 5
        Event event = new ChangeDiceDR(p1.getNickname(),model.getDiceStock().getDice(0), model.getRoundsTrack().getDice(0), 5);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(dice1, model.getDiceStock().getDice(0));
        assertEquals(dice, model.getRoundsTrack().getDice(0));
    }

    /**
     * test control that the effect of cards 5 is Ko
     * @author fernandes
     */
    @Test
    public void testDiceChangeDRKo() {
        model.addPlayer(p1);
        Dice d=new Dice(DiceColor.VIOLET);
        d.setValue(4);
        //attivo l'effetto della carta con un dado che non è nel dice stock
        Event event = new ChangeDiceDR(p1.getNickname(),d, model.getRoundsTrack().getDice(0), 5);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-2, controller.getCategory());
        //attivo l'effetto della carta con un dado che non è nel Round track
        event = new ChangeDiceDR(p1.getNickname(),model.getDiceStock().getDice(0), d, 5);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(-3, controller.getCategory());

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
