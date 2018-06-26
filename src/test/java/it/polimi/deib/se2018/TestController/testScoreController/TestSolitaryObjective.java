package it.polimi.deib.se2018.TestController.testScoreController;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.ScoreController;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 *Class used to test the calculation of the solitary objective points (for single player)
 * @author Simone Mariani
 */
public class TestSolitaryObjective {

    private RoundsTrack roundsTrack;
    private ScoreController scoreController;
    private Model model;
    private RemoteView view;

    /**
     * Method used to set up a simulated rounds track, to calculate points
     */
    @Before
    public void setUp(){
        roundsTrack=RoundsTrack.getSingletonRoundsTrack();
        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),roundsTrack);
        scoreController=new ScoreController(model,view);
        for(int i=0;i<10;i++){
            Dice d= new Dice(DiceColor.RED);
            d.setValue(1);
            roundsTrack.insertDice(d);
        }
    }

    /**
     * Real point calculation test
     */
    @Test
    public void testSolitaryObjective() {
        assertEquals(10,scoreController.solitaryObjectivePoint());
    }

}
