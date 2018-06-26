package it.polimi.deib.se2018.TestController.testScoreController;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.ScoreController;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import it.polimi.deib.se2018.server.model.player.PrivateGoalCard;
import it.polimi.deib.se2018.server.model.player.schemecard.Box;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * Class used to calculate the winner of the game
 * @author Simone Mariani
 */
public class TestCalculateWinner {

    private Controller controller;
    private ScoreController scoreController;
    private Model model;
    private RemoteView view;

    /**
     * Method used to set up a simulated situation with 4 players
     */
    private void setUp1(){
        ArrayList<Player> list=new ArrayList<Player>();
        for(int i=0;i<4;i++){
            list.add(new Player("abc",i+1,PlayerColor.RED));//Possono essere anche tutti uguali...qui contano solo i punti
            list.get(i).setVictoryPoints(i*10);
        }
        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),RoundsTrack.getSingletonRoundsTrack());
        model.setPlayerList(list);
        view=new RemoteView();
        controller=new Controller(model,view);
        controller.getGameRoundController().setTimer(1,1);
        scoreController=new ScoreController(model,view);
    }

    private void setUp2(){
        Player p1,p2,p3,p4;
        p1=new Player("a",1,PlayerColor.RED);
        p2=new Player("b",2,PlayerColor.GREEN);
        p3=new Player("c",3,PlayerColor.BLUE);
        p4=new Player("d",4,PlayerColor.VIOLET);
        p1.setVictoryPoints(30);
        p2.setVictoryPoints(30);
        p3.setVictoryPoints(30);
        p4.setVictoryPoints(30);
        p1.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        p2.setPrivateGoalCard(new PrivateGoalCard(DiceColor.GREEN,"Private Goal Green"));
        p3.setPrivateGoalCard(new PrivateGoalCard(DiceColor.BLUE,"Private Goal Blue"));
        p4.setPrivateGoalCard(new PrivateGoalCard(DiceColor.BLUE,"Private Goal Blue"));
        p1.setFavorMarkers(1);
        p2.setFavorMarkers(1);
        p3.setFavorMarkers(2);
        p4.setFavorMarkers(3);

        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        tabella[0][0].setDice(new Dice(DiceColor.BLUE));
        tabella[0][0].getDice().setValue(1);

        p1.setPlayerScheme(new SchemeCard("a",1,tabella));
        p2.setPlayerScheme(new SchemeCard("b",1,tabella));
        p3.setPlayerScheme(new SchemeCard("c",2,tabella));
        p4.setPlayerScheme(new SchemeCard("d",3,tabella));

        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),RoundsTrack.getSingletonRoundsTrack());
        model.getPlayerList().add(p1);
        model.getPlayerList().add(p2);
        model.getPlayerList().add(p3);
        model.getPlayerList().add(p4);
        view=new RemoteView();
        controller=new Controller(model,view);
        controller.getGameRoundController().setTimer(1,1);
        scoreController=new ScoreController(model,view);

    }



    /**
     * Winner calculation test 1 (Different Victory Points)
     */
    @Test
    public void testCalculateWinner1()
    {   setUp1();
        assertEquals(model.getPlayerList().get(3),scoreController.calculateWinner());
    }

    /**
     * Winner calculation test 2 (Other cases)
     */
    @Test
    public void testCalculateWinner2(){
        setUp2();
        assertEquals(model.getPlayerList().get(3),scoreController.calculateWinner());
    }

    @After
    public void clean(){
        controller.getGameRoundController().stopTimer();
        model.getPlayerList().clear();
    }
}
