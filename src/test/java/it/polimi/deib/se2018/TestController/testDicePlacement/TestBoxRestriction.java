package it.polimi.deib.se2018.TestController.testDicePlacement;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.DicePlacementController;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import it.polimi.deib.se2018.server.model.player.schemecard.Box;
import it.polimi.deib.se2018.server.model.player.schemecard.ColoredBox;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import it.polimi.deib.se2018.server.model.player.schemecard.ValueBox;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


/**
 * Class used to test Box Restrictions (value or color)
 * @author Simone Mariani
 */
public class TestBoxRestriction {

    private Controller controller;
    private Model model;
    private RemoteView view;
    private DicePlacementController dicePlacementController;

    /**
     * Method used to create a player and a particular scheme to test some combinations
     */
    @Before
    public void setUp(){
        model = new Model(DiceBag.getSingletonDiceBag(), DiceStock.getSingletonDiceStock(), RoundsTrack.getSingletonRoundsTrack());
        view=new RemoteView();
        controller=new Controller(model,view);
        dicePlacementController=new DicePlacementController();
        Player p=new Player("abc",1,PlayerColor.RED);
        model.getPlayerList().add(p);
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        tabella[0][1]=new ColoredBox(DiceColor.GREEN);
        tabella[0][2]=new ValueBox(3);
        tabella[0][3].setDice(new Dice(DiceColor.GREEN));
        tabella[0][4]=new ColoredBox(DiceColor.RED);
        tabella[1][0]=new ValueBox(2);
        tabella[1][1]=new ColoredBox(DiceColor.GREEN);
        tabella[1][1].setDice(new Dice(DiceColor.GREEN));
        tabella[1][2]=new ValueBox(3);
        tabella[1][2].setDice(new Dice(DiceColor.GREEN));
        p.setPlayerScheme(new SchemeCard("abc",1,tabella));

    }

    /**
     * Testing boxes restrictions
     */
    @Test
    public void testBoxRestriction(){
        Dice d=new Dice(DiceColor.GREEN);
        d.setValue(3);
        assertTrue(dicePlacementController.isBoxOkColor(model.getPlayerList().get(0),0,1,d));
        assertTrue(dicePlacementController.isBoxOkShade(model.getPlayerList().get(0),0,2,d));
        assertTrue(dicePlacementController.isBoxOkColor(model.getPlayerList().get(0),0,0,d));
        assertTrue(dicePlacementController.isBoxOkShade(model.getPlayerList().get(0),0,0,d));
        assertFalse(dicePlacementController.isBoxOkColor(model.getPlayerList().get(0),0,3,d));
        assertFalse(dicePlacementController.isBoxOkShade(model.getPlayerList().get(0),0,3,d));
        assertFalse(dicePlacementController.isBoxOkColor(model.getPlayerList().get(0),0,4,d));
        assertFalse(dicePlacementController.isBoxOkShade(model.getPlayerList().get(0),1,0,d));
        assertFalse(dicePlacementController.isBoxOkColor(model.getPlayerList().get(0),1,1,d));
        assertFalse(dicePlacementController.isBoxOkShade(model.getPlayerList().get(0),1,2,d));

    }

    /**
     * Cleaning resources after tests
     */
    @After
    public void clean(){
        model.getPlayerList().clear();
    }

}
