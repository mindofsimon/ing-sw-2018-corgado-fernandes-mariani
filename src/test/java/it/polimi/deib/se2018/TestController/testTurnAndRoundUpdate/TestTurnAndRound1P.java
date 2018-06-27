package it.polimi.deib.se2018.TestController.testTurnAndRoundUpdate;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.EndTurn;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
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
import static org.junit.Assert.assertFalse;

/**
 * Class used to test turn and round updates for the single player side
 * @author Simone Mariani
 */
public class TestTurnAndRound1P {

    private Controller controller;
    private Model model;
    private RemoteView view;

    @Before
    /**
     * Setting up a simulated game with one player
     */
    public void setUp(){
        model = new Model(DiceBag.getSingletonDiceBag(), DiceStock.getSingletonDiceStock(), RoundsTrack.getSingletonRoundsTrack());
        view=new RemoteView();
        controller=new Controller(model,view);
        Player p=new Player("abc",1,PlayerColor.RED);
        p.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        p.setnMoves(1);
        controller.getGameRoundController().setTimer(p.getnMoves(),p.getOrder());
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        p.setPlayerScheme(new SchemeCard("abc",1,tabella));
        controller.getGameRoundController().updateDiceStock();
        model.getPlayerList().add(p);
    }

    /**
     * Testing some combinations
     * @throws RemoteException
     */
    @Test
    public void testTurnAndRound1P()throws RemoteException {
        controller.getGameRoundController().stopTimer();
        controller.getGameRoundController().updateTurn(model.getPlayerList().get(0));
        assertEquals(1,model.getPlayerList().get(0).getnTurns());
        assertEquals(1,model.getPlayerList().get(0).getnMoves());
        assertEquals(model.getPlayerList().get(0).getOrder(),model.getTurn());
        assertEquals(1,model.getRound());
        assertFalse(model.getPlayerList().get(0).isDicePlacedByCard());
        assertFalse(model.getPlayerList().get(0).cardActivated());
        assertFalse(model.getPlayerList().get(0).dicePlaced());


        controller.update(new EndTurn(model.getPlayerList().get(0).getNickname()));
        assertEquals(2,model.getPlayerList().get(0).getnTurns());
        assertEquals(0,model.getPlayerList().get(0).getnMoves());
        assertEquals(model.getPlayerList().get(0).getOrder(),model.getTurn());
        assertEquals(1,model.getRound());
        assertFalse(model.getPlayerList().get(0).isDicePlacedByCard());
        assertFalse(model.getPlayerList().get(0).cardActivated());
        assertFalse(model.getPlayerList().get(0).dicePlaced());

        controller.update(new EndTurn(model.getPlayerList().get(0).getNickname()));
        assertEquals(1,model.getPlayerList().get(0).getnTurns());
        assertEquals(0,model.getPlayerList().get(0).getnMoves());
        assertEquals(model.getPlayerList().get(0).getOrder(),model.getTurn());
        assertEquals(2,model.getRound());
        assertFalse(model.getPlayerList().get(0).isDicePlacedByCard());
        assertFalse(model.getPlayerList().get(0).cardActivated());
        assertFalse(model.getPlayerList().get(0).dicePlaced());

        for(int i=0;i<8;i++){
            model.incrRound();
        }
        model.getPlayerList().get(0).setnTurns(2);

        assertEquals(10,model.getRound());

        controller.update(new EndTurn(model.getPlayerList().get(0).getNickname()));
        assertEquals(1,model.getPlayerList().get(0).getnTurns());
        assertEquals(0,model.getPlayerList().get(0).getnMoves());
        assertEquals(model.getPlayerList().get(0).getOrder(),model.getTurn());
        assertEquals(10,model.getRound());
        assertFalse(model.getPlayerList().get(0).isDicePlacedByCard());
        assertFalse(model.getPlayerList().get(0).cardActivated());
        assertFalse(model.getPlayerList().get(0).dicePlaced());


    }

    @After
    public void clean(){
        model.getPlayerList().clear();
        model.getDiceStock().clear();
        model.getDiceBag().clear();
        model.getRoundsTrack().clear();
    }
}
