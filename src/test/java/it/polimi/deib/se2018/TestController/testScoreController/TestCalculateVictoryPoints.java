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
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.ElementType;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.LineType;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.RowAndColCard;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import it.polimi.deib.se2018.server.model.player.PrivateGoalCard;
import it.polimi.deib.se2018.server.model.player.schemecard.Box;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Class used to test victory points calculation
 * @author Simone Mariani
 */
public class TestCalculateVictoryPoints {

    private Controller controller;
    private ScoreController scoreController;
    private Model model;
    private RemoteView view;
    private Player p;

    /**
     * Method used to create a player that will be used to calculate victory points
     */
    @Before
    public void setUp(){
        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),RoundsTrack.getSingletonRoundsTrack());
        model.addPublicGoalCard(new RowAndColCard(LineType.ROW,ElementType.COLOR,"Colored Rows"));
        model.addPublicGoalCard(new RowAndColCard(LineType.COLUMN,ElementType.COLOR,"Colored Columns"));
        model.addPublicGoalCard(new RowAndColCard(LineType.ROW,ElementType.SHADE,"Shades Rows"));
        view=new RemoteView();
        controller=new Controller(model,view);
        p=new Player("abc",1,PlayerColor.RED);
        p.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        p.setPlayerScheme(new SchemeCard("schema1",3,tabella));
        p.getPlayerScheme().getScheme()[0][0].setDice(new Dice(DiceColor.RED));
        p.getPlayerScheme().getScheme()[0][0].getDice().setValue(1);
        p.setFavorMarkers(3);
        model.addPlayer(p);
        controller.getGameRoundController().setTimer(p.getnMoves(),p.getOrder());
        scoreController=new ScoreController(model,view);

    }

    /**
     * Tests if victory points are correctly calculated
     */
    @Test
    public void testCalculateVictoryPoints(){
        assertEquals(-53,scoreController.calculateVictoryPoints(p));
    }
}
