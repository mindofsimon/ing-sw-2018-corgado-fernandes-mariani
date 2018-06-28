package it.polimi.deib.se2018.TestController.testToolCards;


import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;
import it.polimi.deib.se2018.server.model.events.SetDiff;
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
import  static  org.junit.Assert.*;

import java.rmi.RemoteException;


/**
 * Class used to test Load Tool Cards
 * @author Sirlan Fernandes
 */
public class TestLoadCards {
    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1;
    private Player p2;


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
     * test that the number of load toolcards for single player is 1 for easy difficult and 5 for extreme difficult
     * @author fernandes
     */
    @Test
    public void testLoadSinglePlayer(){
        model.addPlayer(p1);
        //caso in cui il giocatore sceglie difficulta 1
        Event event=new SetDiff(p1.getNickname(),1);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(1,controller.getToolCardsList().size());//controllo che siano state caricate 1 toolcard
        //pulisco la lista delle tool
        controller.cleanToolCards();
        //caso in cui il giocatore scelga difficulta 5
        event=new SetDiff(p1.getNickname(),5);
        try {
            controller.update(event);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(5,controller.getToolCardsList().size());//controllo che siano state caricate 5 toolcards
    }

    /**
     * test that the number of load toolcards for multi player is 3
     * @author fernandes
     */
    @Test
    public void testLoadMultiPlayer(){
        model.addPlayer(p1);
        model.addPlayer(p2);

        try {
            controller.initGame(60);
        } catch (RemoteException e) {
           fail();
        }
        assertEquals(3,controller.getToolCardsList().size());//controllo che siano state caricate 3 toolcards
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


    }
}
