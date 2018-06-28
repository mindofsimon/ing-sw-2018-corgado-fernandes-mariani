package it.polimi.deib.se2018.TestController.testTurnAndRoundUpdate;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.EndTurn;
import it.polimi.deib.se2018.server.model.events.QuitPlayerEvent;
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

import static junit.framework.TestCase.assertEquals;

/**
 * Class used to test the update of turns and round for the multi-player side
 * @author Simone Mariani
 */
public class TestTurnAndRoundMP {

    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1,p2,p3,p4;

    /**
     * Setting up a simulated game with four players
     */
    @Before
    public void setUp(){
        p1=new Player("a",1,PlayerColor.RED);
        p2=new Player("b",2,PlayerColor.GREEN);
        p3=new Player("c",3,PlayerColor.BLUE);
        p4=new Player("d",4,PlayerColor.VIOLET);
        p1.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        p2.setPrivateGoalCard(new PrivateGoalCard(DiceColor.GREEN,"Private Goal Green"));
        p3.setPrivateGoalCard(new PrivateGoalCard(DiceColor.BLUE,"Private Goal Blue"));
        p4.setPrivateGoalCard(new PrivateGoalCard(DiceColor.BLUE,"Private Goal Blue"));
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        p1.setPlayerScheme(new SchemeCard("a",1,tabella));
        p2.setPlayerScheme(new SchemeCard("b",2,tabella));
        p3.setPlayerScheme(new SchemeCard("c",3,tabella));
        p4.setPlayerScheme(new SchemeCard("d",4,tabella));
        p1.setFavorMarkers(1);
        p2.setFavorMarkers(2);
        p3.setFavorMarkers(3);
        p4.setFavorMarkers(4);

        model = new Model(DiceBag.getSingletonDiceBag(), DiceStock.getSingletonDiceStock(), RoundsTrack.getSingletonRoundsTrack());
        model.addPublicGoalCard(new RowAndColCard(LineType.ROW,ElementType.COLOR,"Colored Rows"));
        model.addPublicGoalCard(new RowAndColCard(LineType.COLUMN,ElementType.COLOR,"Colored Columns"));
        model.addPublicGoalCard(new RowAndColCard(LineType.ROW,ElementType.SHADE,"Shades Rows"));
        model.getPlayerList().add(p1);
        model.getPlayerList().add(p2);
        model.getPlayerList().add(p3);
        model.getPlayerList().add(p4);
        view=new RemoteView();
        controller=new Controller(model,view);

        controller.getGameRoundController().setSuspensionTimerInterval(60);
        controller.getGameRoundController().updateDiceStock();
        controller.getGameRoundController().setTimer(p1.getnMoves(),p1.getOrder());
    }

    /**
     * Testing on normal turn and round update
     * @throws RemoteException
     */
    @Test
    public void testTurnAndRoundMP()throws RemoteException {
        controller.update(new EndTurn(p1.getNickname()));
        assertEquals(2,p1.getnTurns());
        assertEquals(0,p1.getnMoves());
        assertEquals(p2.getOrder(),model.getTurn());
        assertEquals(p2,model.findPlayerByOrder(model.getTurn()));
        assertEquals(1,model.getRound());

        //Update turn made by "out of turn players" make no changes
        controller.update(new EndTurn(p3.getNickname()));
        assertEquals(1,p3.getnTurns());
        assertEquals(0,p3.getnMoves());
        assertEquals(p2.getOrder(),model.getTurn());
        assertEquals(p2,model.findPlayerByOrder(model.getTurn()));
        assertEquals(1,model.getRound());


        controller.update(new EndTurn(p2.getNickname()));
        assertEquals(2,p2.getnTurns());
        assertEquals(0,p2.getnMoves());
        assertEquals(p3.getOrder(),model.getTurn());
        assertEquals(p3,model.findPlayerByOrder(model.getTurn()));
        assertEquals(1,model.getRound());

        controller.update(new EndTurn(p3.getNickname()));
        assertEquals(2,p3.getnTurns());
        assertEquals(0,p3.getnMoves());
        assertEquals(p4.getOrder(),model.getTurn());
        assertEquals(p4,model.findPlayerByOrder(model.getTurn()));
        assertEquals(1,model.getRound());

        controller.update(new EndTurn(p4.getNickname()));
        assertEquals(2,p4.getnTurns());
        assertEquals(0,p4.getnMoves());
        assertEquals(p4.getOrder(),model.getTurn());
        assertEquals(p4,model.findPlayerByOrder(model.getTurn()));
        assertEquals(1,model.getRound());

        controller.update(new EndTurn(p4.getNickname()));
        assertEquals(1,p4.getnTurns());
        assertEquals(0,p4.getnMoves());
        assertEquals(p3.getOrder(),model.getTurn());
        assertEquals(p3,model.findPlayerByOrder(model.getTurn()));
        assertEquals(1,model.getRound());

        controller.update(new EndTurn(p3.getNickname()));
        assertEquals(1,p3.getnTurns());
        assertEquals(0,p3.getnMoves());
        assertEquals(p2.getOrder(),model.getTurn());
        assertEquals(p2,model.findPlayerByOrder(model.getTurn()));
        assertEquals(1,model.getRound());

        controller.update(new EndTurn(p2.getNickname()));
        assertEquals(1,p2.getnTurns());
        assertEquals(0,p2.getnMoves());
        assertEquals(p1.getOrder(),model.getTurn());
        assertEquals(p1,model.findPlayerByOrder(model.getTurn()));
        assertEquals(1,model.getRound());

        controller.update(new EndTurn(p1.getNickname()));
        assertEquals(1,p1.getnTurns());
        assertEquals(0,p1.getnMoves());

        //At the end of the round players order changes
        assertEquals(4,p1.getOrder());
        assertEquals(1,p2.getOrder());
        assertEquals(2,p3.getOrder());
        assertEquals(3,p4.getOrder());

        assertEquals(2,model.getRound());
        assertEquals(p2.getOrder(),model.getTurn());

        for(int i=0;i<8;i++){
            model.incrRound();
        }
        assertEquals(10,model.getRound());

        p2.setnTurns(2);
        controller.update(new EndTurn(p2.getNickname()));//End Turn by last player...
        assertEquals(10,model.getRound());

    }

    /**
     * Testing on round and turn updates caused by player suspensions
     * @throws RemoteException
     */
    @Test
    public void testTurnAndRoundSuspensionMP()throws RemoteException{

        p1.setnMoves(2);
        p1.suspend();
        controller.getGameRoundController().stopTimer();
        controller.getGameRoundController().updateTurn(p1);
        assertEquals(1,model.getRound());
        assertEquals(1,p2.getOrder());
        assertEquals(2,p3.getOrder());
        assertEquals(3,p4.getOrder());
        assertEquals(4,p1.getOrder());//è stato sospeso
        assertEquals(p2.getOrder(),model.getTurn());

        //Update turn made by "suspended players" make no changes
        controller.update(new EndTurn(p1.getNickname()));
        assertEquals(1,model.getRound());
        assertEquals(1,p2.getOrder());
        assertEquals(2,p3.getOrder());
        assertEquals(3,p4.getOrder());
        assertEquals(4,p1.getOrder());//è stato sospeso
        assertEquals(p2.getOrder(),model.getTurn());

        controller.update(new EndTurn(p2.getNickname()));
        controller.update(new EndTurn(p3.getNickname()));

        p4.setnMoves(2);
        p4.suspend();
        controller.getGameRoundController().stopTimer();
        controller.getGameRoundController().updateTurn(p4);
        assertEquals(1,model.getRound());
        assertEquals(1,p2.getOrder());
        assertEquals(2,p3.getOrder());
        assertEquals(3,p4.getOrder());//è stato sospeso
        assertEquals(4,p1.getOrder());//è stato sospeso
        assertEquals(p3.getOrder(),model.getTurn());

        controller.update(new EndTurn(p3.getNickname()));
        p2.setnMoves(2);
        p2.suspend();
        controller.getGameRoundController().stopTimer();
        controller.getGameRoundController().updateTurn(p2);
        assertEquals(2,model.getRound());
        assertEquals(1,p3.getOrder());
        assertEquals(2,p2.getOrder());//è stato sospeso
        assertEquals(3,p4.getOrder());//è stato sospeso
        assertEquals(4,p1.getOrder());//è stato sospeso
        assertEquals(p3.getOrder(),model.getTurn());

    }

    /**
     * Testing on round and turn updates caused by escaping players
     * @throws RemoteException
     */
    @Test
    public void testTurnAndRoundQuitMP()throws RemoteException{

        controller.update(new EndTurn(p1.getNickname()));
        controller.update(new QuitPlayerEvent(p2.getNickname()));
        assertEquals(1,model.getRound());
        assertEquals(1,p1.getOrder());
        assertEquals(2,p3.getOrder());
        assertEquals(3,p4.getOrder());
        assertEquals(4,p2.getOrder());//è uscito
        assertEquals(p3.getOrder(),model.getTurn());

        //Update turn by "quitting players" make no changes
        controller.update(new EndTurn(p2.getNickname()));
        assertEquals(1,model.getRound());
        assertEquals(1,p1.getOrder());
        assertEquals(2,p3.getOrder());
        assertEquals(3,p4.getOrder());
        assertEquals(4,p2.getOrder());//è uscito
        assertEquals(p3.getOrder(),model.getTurn());


        controller.update(new EndTurn(p3.getNickname()));
        assertEquals(p4.getOrder(),model.getTurn());
        controller.update(new EndTurn(p4.getNickname()));
        assertEquals(p4.getOrder(),model.getTurn());
        controller.update(new EndTurn(p4.getNickname()));
        assertEquals(p3.getOrder(),model.getTurn());
        controller.update(new EndTurn(p3.getNickname()));

        controller.update(new QuitPlayerEvent(p1.getNickname()));
        assertEquals(2,model.getRound());
        assertEquals(1,p3.getOrder());
        assertEquals(2,p4.getOrder());
        assertEquals(3,p1.getOrder());//è uscito
        assertEquals(4,p2.getOrder());//è uscito
        assertEquals(p3.getOrder(),model.getTurn());

        controller.update(new QuitPlayerEvent(p4.getNickname()));
        assertEquals(false,p4.isOut());
        assertEquals(p3.getOrder(),model.getTurn());

        controller.update(new QuitPlayerEvent(p3.getNickname()));
        assertEquals(p4,model.getFirstActive());



    }

    /**
     * Cleaning up some resources
     */
    @After
    public void clean(){
        controller.getGameRoundController().stopTimer();
        model.getPlayerList().clear();
        model.getDiceStock().clear();
        model.getDiceBag().clear();
    }
}
