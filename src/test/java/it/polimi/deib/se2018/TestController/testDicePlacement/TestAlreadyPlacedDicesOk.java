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
 * Class used to test correct dice placements(with already placed dices near)
 * @author Simone Mariani
 */
public class TestAlreadyPlacedDicesOk {

    private Controller controller;
    private Model model;
    private RemoteView view;

    /**
     * An additional set up to try new placements
     */
    private void additionalSetUp(){
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
        tabella[0][0].setDice(new Dice(DiceColor.RED));
        tabella[0][0].getDice().setValue(1);
        tabella[0][1].setDice(new Dice(DiceColor.RED));
        tabella[0][1].getDice().setValue(1);
        tabella[0][2].setDice(new Dice(DiceColor.RED));
        tabella[0][2].getDice().setValue(1);
        tabella[0][3].setDice(new Dice(DiceColor.RED));
        tabella[0][3].getDice().setValue(1);
        tabella[0][4].setDice(new Dice(DiceColor.RED));
        tabella[0][4].getDice().setValue(1);
        tabella[1][0].setDice(new Dice(DiceColor.RED));
        tabella[1][0].getDice().setValue(1);
        tabella[1][4].setDice(new Dice(DiceColor.RED));
        tabella[1][4].getDice().setValue(1);
        tabella[2][0].setDice(new Dice(DiceColor.RED));
        tabella[2][0].getDice().setValue(1);
        tabella[2][4].setDice(new Dice(DiceColor.RED));
        tabella[2][4].getDice().setValue(1);
        tabella[3][0].setDice(new Dice(DiceColor.RED));
        tabella[3][0].getDice().setValue(1);
        tabella[3][1].setDice(new Dice(DiceColor.RED));
        tabella[3][1].getDice().setValue(1);
        tabella[3][2].setDice(new Dice(DiceColor.RED));
        tabella[3][2].getDice().setValue(1);
        tabella[3][3].setDice(new Dice(DiceColor.RED));
        tabella[3][3].getDice().setValue(1);
        p.setPlayerScheme(new SchemeCard("abc",1,tabella));
        Dice dice;
        for(int i=0;i<20;i++){
            dice=new Dice(DiceColor.YELLOW);
            dice.setValue(2);
            model.getDiceStock().insertDice(dice);
        }
    }

    /**
     * Main set up, to try principal combinations
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
        tabella[0][0].setDice(new Dice(DiceColor.RED));
        tabella[0][0].getDice().setValue(1);
        tabella[0][4].setDice(new Dice(DiceColor.RED));
        tabella[0][4].getDice().setValue(1);
        tabella[1][2].setDice(new Dice(DiceColor.GREEN));
        tabella[1][2].getDice().setValue(3);
        tabella[2][2].setDice(new Dice(DiceColor.RED));
        tabella[2][2].getDice().setValue(1);
        tabella[3][0].setDice(new Dice(DiceColor.RED));
        tabella[3][0].getDice().setValue(1);
        tabella[3][4].setDice(new Dice(DiceColor.RED));
        tabella[3][4].getDice().setValue(1);
        p.setPlayerScheme(new SchemeCard("abc",1,tabella));
        Dice dice;
        for(int i=0;i<14;i++){
            dice=new Dice(DiceColor.YELLOW);
            dice.setValue(2);
            model.getDiceStock().insertDice(dice);
        }
    }

    /**
     * Here we test placements
     * @throws RemoteException
     */
    @Test
    public void testAlreadyPlacedDices() throws RemoteException {
        Dice d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20;

        //Piazzamenti consentiti
        d1=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,1,d1));
        assertEquals(d1,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][1].getDice());
        d1=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d2=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,2,d2));
        assertEquals(d2,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][2].getDice());
        d2=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d3=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,3,d3));
        assertEquals(d3,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][3].getDice());
        d3=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d4=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,0,d4));
        assertEquals(d4,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][0].getDice());
        d4=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d5=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,1,d5));
        assertEquals(d5,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][1].getDice());
        d5=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d6=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,3,d6));
        assertEquals(d6,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][3].getDice());
        d6=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d7=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,4,d7));
        assertEquals(d7,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][4].getDice());
        d7=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d8=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),2,0,d8));
        assertEquals(d8,model.getPlayerList().get(0).getPlayerScheme().getScheme()[2][0].getDice());
        d8=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d9=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),2,1,d9));
        assertEquals(d9,model.getPlayerList().get(0).getPlayerScheme().getScheme()[2][1].getDice());
        d9=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d10=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),2,3,d10));
        assertEquals(d10,model.getPlayerList().get(0).getPlayerScheme().getScheme()[2][3].getDice());
        d10=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d11=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),2,4,d11));
        assertEquals(d11,model.getPlayerList().get(0).getPlayerScheme().getScheme()[2][4].getDice());
        d11=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d12=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),3,1,d12));
        assertEquals(d12,model.getPlayerList().get(0).getPlayerScheme().getScheme()[3][1].getDice());
        d12=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d13=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),3,2,d13));
        assertEquals(d13,model.getPlayerList().get(0).getPlayerScheme().getScheme()[3][2].getDice());
        d13=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        setUp();

        d14=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),3,3,d14));
        assertEquals(d14,model.getPlayerList().get(0).getPlayerScheme().getScheme()[3][3].getDice());
        d14=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        additionalSetUp();

        d15=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,1,d15));
        assertEquals(d15,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][1].getDice());
        d15=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        additionalSetUp();

        d16=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,2,d16));
        assertEquals(d16,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][2].getDice());
        d16=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        additionalSetUp();

        d17=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,3,d17));
        assertEquals(d17,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][3].getDice());
        d17=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        additionalSetUp();

        d18=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),2,1,d18));
        assertEquals(d18,model.getPlayerList().get(0).getPlayerScheme().getScheme()[2][1].getDice());
        d18=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        additionalSetUp();

        d19=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),2,2,d19));
        assertEquals(d19,model.getPlayerList().get(0).getPlayerScheme().getScheme()[2][2].getDice());
        d19=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

        additionalSetUp();

        d20=model.getDiceStock().extractRandomDice();
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),2,3,d20));
        assertEquals(d20,model.getPlayerList().get(0).getPlayerScheme().getScheme()[2][3].getDice());
        d20=null;
        model.getPlayerList().get(0).setnMoves(0);
        model.getPlayerList().get(0).resetDicePlacement();

    }

    /**
     * Used to clean resources
     */
    @After
    public void clean(){
        controller.getGameRoundController().stopTimer();
        model.getDiceStock().clear();
        model.getPlayerList().clear();
    }
}
