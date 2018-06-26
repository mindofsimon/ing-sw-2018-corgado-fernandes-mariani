package it.polimi.deib.se2018.TestController.testDicePlacement;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.DicePlacement;
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

/**
 * Class used to test illegal dice placements (without already placed dices near our)
 * @author Simone Mariani
 */
public class TestAlreadyPlacedDicesKo {

    private Controller controller;
    private Model model;
    private RemoteView view;

    /**
     * Classic set up, to initialize resources...scheme creations, players, etc...
     */
    @Before
    public void setUp(){
        model = new Model(DiceBag.getSingletonDiceBag(), DiceStock.getSingletonDiceStock(), RoundsTrack.getSingletonRoundsTrack());
        view=new RemoteView();
        controller=new Controller(model,view);
        Player p=new Player("abc",1,PlayerColor.RED);
        p.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        model.getPlayerList().add(p);
        controller.getGameRoundController().setTimer(p.getnMoves(),p.getOrder());
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        tabella[1][2].setDice(new Dice(DiceColor.GREEN));
        tabella[1][2].getDice().setValue(3);
        p.setPlayerScheme(new SchemeCard("abc",1,tabella));
        Dice dice;
        for(int i=0;i<11;i++){
            dice=new Dice(DiceColor.YELLOW);
            dice.setValue(2);
            model.getDiceStock().insertDice(dice);
        }
    }

    /**
     * Here we test the placements
     * @throws RemoteException
     */
    @Test
    public void testAlreadyPlacedDices() throws RemoteException {
        Dice d1,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13;

        //Piazzamenti non consentiti
        d1=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,0,d1));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][0].getDice());
        d1=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();


        setUp();

        d4=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,4,d4));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][4].getDice());
        d4=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d5=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,0,d5));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][0].getDice());
        d5=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d6=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,4,d6));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][4].getDice());
        d6=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d7=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),2,0,d7));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[2][0].getDice());
        d7=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d8=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),2,4,d8));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[2][4].getDice());
        d8=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d9=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),3,0,d9));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[3][0].getDice());
        d9=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d10=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),3,1,d10));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[3][1].getDice());
        d10=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d11=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),3,2,d11));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[3][2].getDice());
        d11=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d12=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),3,3,d12));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[3][3].getDice());
        d12=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d13=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),3,4,d13));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[3][4].getDice());
        d13=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

    }

    /**
    Method used to clean resources after tests
     */
    @After
    public void clean(){
        controller.getGameRoundController().stopTimer();
        model.getDiceStock().clear();
        model.getPlayerList().clear();
    }
}

