package it.polimi.deib.se2018.TestController.testSchemeSelection;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.SchemeSelection;
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
 * Class used to test the scheme card selection logic
 * @author Simone Mariani
 */
public class TestSchemeSelection {

    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1;

    /**
     * Setting up the offered scheme cards for a player
     */
    @Before
    public void setUp(){
        p1=new Player("a",1,PlayerColor.RED);

        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        p1.setOfferedSchemeCards(new SchemeCard("fronte1",1,tabella));
        p1.setOfferedSchemeCards(new SchemeCard("fronte2",3,tabella));
        p1.getOfferedSchemeCards().get(0).setRetro(new SchemeCard("retro1",2,tabella));
        p1.getOfferedSchemeCards().get(1).setRetro(new SchemeCard("retro2",4,tabella));
        p1.getOfferedSchemeCards().get(0).getRetro().setRetro(p1.getOfferedSchemeCards().get(0));
        p1.getOfferedSchemeCards().get(1).getRetro().setRetro(p1.getOfferedSchemeCards().get(1));


        model = new Model(DiceBag.getSingletonDiceBag(), DiceStock.getSingletonDiceStock(), RoundsTrack.getSingletonRoundsTrack());
        model.getPlayerList().add(p1);

        view=new RemoteView();
        controller=new Controller(model,view);

        p1.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));

        model.addPublicGoalCard(new RowAndColCard(LineType.ROW,ElementType.COLOR,"Colored Rows"));
        model.addPublicGoalCard(new RowAndColCard(LineType.COLUMN,ElementType.COLOR,"Colored Columns"));
        model.addPublicGoalCard(new RowAndColCard(LineType.ROW,ElementType.SHADE,"Shades Rows"));
    }

    /**
     * Testing first card front
     * @throws RemoteException
     */
    @Test
    public void testSchemeSelection0()throws RemoteException {

        controller.update(new SchemeSelection(p1.getNickname(),0));
        assertEquals("fronte1",p1.getPlayerScheme().getSchemeName());
        assertEquals(1,p1.getPlayerScheme().getDifficulty());
        assertEquals("retro1",p1.getPlayerScheme().getRetro().getSchemeName());

    }

    /**
     * Testing first card retro
     * @throws RemoteException
     */
    @Test
    public void testSchemeSelection1()throws RemoteException {

        controller.update(new SchemeSelection(p1.getNickname(),1));
        assertEquals("retro1",p1.getPlayerScheme().getSchemeName());
        assertEquals(2,p1.getPlayerScheme().getDifficulty());
        assertEquals("fronte1",p1.getPlayerScheme().getRetro().getSchemeName());

    }

    /**
     * Testing second card front
     * @throws RemoteException
     */
    @Test
    public void testSchemeSelection2()throws RemoteException {

        controller.update(new SchemeSelection(p1.getNickname(),2));
        assertEquals("fronte2",p1.getPlayerScheme().getSchemeName());
        assertEquals(3,p1.getPlayerScheme().getDifficulty());
        assertEquals("retro2",p1.getPlayerScheme().getRetro().getSchemeName());

    }

    /**
     * Testing second card retro
     * @throws RemoteException
     */
    @Test
    public void testSchemeSelection3()throws RemoteException {

        controller.update(new SchemeSelection(p1.getNickname(),3));
        assertEquals("retro2",p1.getPlayerScheme().getSchemeName());
        assertEquals(4,p1.getPlayerScheme().getDifficulty());
        assertEquals("fronte2",p1.getPlayerScheme().getRetro().getSchemeName());

    }

    /**
     * Cleaning resources
     */
    @After
    public void clean(){
        controller.getGameRoundController().stopTimer();
        p1.getOfferedSchemeCards().clear();
        p1=null;
        model.getPlayerList().clear();
        model.getDiceStock().clear();
        model.getDiceBag().clear();
    }

}
