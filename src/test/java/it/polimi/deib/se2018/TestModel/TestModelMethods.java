package it.polimi.deib.se2018.TestModel;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * Class used to test some Model methods
 * @author Simone Mariani
 */
public class TestModelMethods {

    private Model model,modelCopy;
    private Player p1,p2,p3,p4;

    /**
     * Creating a simulated Model
     */
    @Before
    public void setUp(){
        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),RoundsTrack.getSingletonRoundsTrack());
        p1=new Player("a",1,PlayerColor.RED);
        p2=new Player("b",2,PlayerColor.GREEN);
        p3=new Player("c",3,PlayerColor.BLUE);
        p4=new Player("d",4,PlayerColor.VIOLET);
        model.addPlayer(p1);
        model.addPlayer(p2);
        model.addPlayer(p3);
        model.addPlayer(p4);
        modelCopy=model.copy();

    }

    /**
     * Testing ModelCopy method
     */
    @Test
    public void testModelCopy(){
        assertEquals(modelCopy.getRound(),model.getRound());
        assertEquals(modelCopy.getTurn(),model.getTurn());

    }


    /**
     * Testing if there is an active player
     */
    @Test
    public void testFirstActive(){
        p1.suspend();
        assertEquals(p2,model.getFirstActive());

        p2.escape();
        assertEquals(p3,model.getFirstActive());

        p3.escape();
        p4.suspend();
        assertNull(model.getFirstActive());
    }

    /**
     *Cleaning used resources
     */
    @After
    public void clean(){
        model.getDiceBag().clear();
        model.getDiceStock().clear();
        model.getPlayerList().clear();
    }

}

