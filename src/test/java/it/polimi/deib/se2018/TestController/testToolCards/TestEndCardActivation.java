package it.polimi.deib.se2018.TestController.testToolCards;


import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.CardActivationController;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.DicePlacementController;
import it.polimi.deib.se2018.server.controller.toolcard.*;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.EndCardActivation;
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
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Class used to test the end activation of a card
 * @author Sirlan Fernandes
 */
public class TestEndCardActivation {
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

    }

    /**
     * test control that the end card for multiplayer is ok
     * @author fernandes
     */
    @Test
    public void testEndCardActivationMP(){
        model.addPlayer(p1);
        model.addPlayer(p2);
        //provo ad attivare l'end card activation con una carta e vedo se sono state applicate le modifiche
        Event event=new EndCardActivation(p1.getNickname(),6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }

        assertTrue(controller.getToolCardsList().get(3).isAlreadyUsed());
        assertTrue(p1.cardActivated());
        assertEquals(1, p1.getnMoves());
        assertEquals(3, p1.getFavorMarkers());
        assertTrue(p1.isDicePlacedByCard());
        //provo ad riattivare la carta ormai utilizzata e vedo se i segnalini sono a -2
        event=new EndCardActivation(p1.getNickname(),6);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(1, p1.getFavorMarkers());


    }

    /**
     * test control that the end card for single player is ok
     * @author fernandes
     */
    @Test
    public void testEndCardActivationSP(){
        model.addPlayer(p1);
        //provo ad attivare l'end card activation con una carta e vedo se la carta esiste ancora
        Event event=new EndCardActivation(p1.getNickname(),1);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }

        assertEquals(null,cardActivationController.findCard(1));


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
