package it.polimi.deib.se2018.TestController.testDicePlacement;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.DicePlacementController;
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
import it.polimi.deib.se2018.server.model.player.schemecard.ColoredBox;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import it.polimi.deib.se2018.server.model.player.schemecard.ValueBox;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * Here we test simple dice placements controls...es: player turn, player suspended, illegal row, etc...
 * @author Simone Mariani
 */
public class TestDicePlacement {

    private Controller controller;
    private Model model;
    private RemoteView view;
    private ArrayList<Player> playerList;
    private Dice dice1;
    private Dice dice2;
    private Dice dice3;
    private DicePlacementController dicePlacementController;

    /**
     *Here we set some players and their schemes to test dice placements
     */
    @Before
    public void setUp() {
        dicePlacementController=new DicePlacementController();
        model = new Model(DiceBag.getSingletonDiceBag(), DiceStock.getSingletonDiceStock(), RoundsTrack.getSingletonRoundsTrack());
        Player p1 = new Player("abc", 1, PlayerColor.RED);
        Player p2 = new Player("def", 2, PlayerColor.BLUE);
        Player p3 = new Player("ghi", 3, PlayerColor.GREEN);
        p1.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        p2.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        p3.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        p3.suspend();
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        tabella[0][1]=new ColoredBox(DiceColor.BLUE);
        tabella[0][2]=new ValueBox(2);
        p1.setPlayerScheme(new SchemeCard("schema1", 3, tabella));
        p2.setPlayerScheme(new SchemeCard("schema1", 3, tabella));
        p3.setPlayerScheme(new SchemeCard("schema1", 3, tabella));
        playerList=new ArrayList<Player>();
        playerList.add(p1);
        playerList.add(p2);
        playerList.add(p3);
        view=new RemoteView();
        model.setPlayerList(playerList);
        controller = new Controller(model, view);
        controller.getGameRoundController().setTimer(p1.getnMoves(), p1.getOrder());
        dice1 = new Dice(DiceColor.RED);
        dice1.setValue(1);
        model.getDiceStock().insertDice(dice1);
        dice2 = new Dice(DiceColor.BLUE);
        dice2.setValue(2);
        model.getDiceStock().insertDice(dice2);
        dice3=new Dice(DiceColor.RED);
        dice3.setValue(3);
    }

    /**
     * Here we test the placements
     * @throws RemoteException
     */
    @Test
    public void testDicePlacement() throws RemoteException{

        //Giocatore sospeso
        controller.update(new DicePlacement(model.getPlayerList().get(2).getNickname(),1,1,dice1));
        assertEquals(null,model.getPlayerList().get(2).getPlayerScheme().getScheme()[1][1].getDice());

        //Giocatore fuori turno
        controller.update(new DicePlacement(model.getPlayerList().get(1).getNickname(),1,1,dice1));
        assertEquals(null,model.getPlayerList().get(1).getPlayerScheme().getScheme()[1][1].getDice());

        //Colonna-Riga errate
        assertEquals(false,dicePlacementController.isRowColOk(7,1));
        assertEquals(false,dicePlacementController.isRowColOk(1,7));
        assertEquals(false,dicePlacementController.isRowColOk(7,7));
        assertEquals(false,dicePlacementController.isRowColOk(-1,-1));
        assertEquals(false,dicePlacementController.isRowColOk(7,7));
        assertEquals(true,dicePlacementController.isRowColOk(1,1));

        //Primo dado fuori bordi
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),1,1,dice1));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[1][1].getDice());

        //Primo dado ok ma in casella con colore diverso
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,1,dice1));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][1].getDice());

        //Primo dado ok ma in casella con valore diverso
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,2,dice1));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][2].getDice());

        //Primo dado ok ma dado non in riserva
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,0,dice3));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][0].getDice());

        //Piazzamento Ok
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,0,dice1));
        assertEquals(dice1,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][0].getDice());

        //Controllo incremento mosse
        assertEquals(1,model.getPlayerList().get(0).getnMoves());

        //Controllo settaggio del dado piazzato nel giocatore
        assertEquals(true,model.getPlayerList().get(0).dicePlaced());

        //Dado già piazzato, non posso ripiazzarne un altro nello stesso turno
        controller.update(new DicePlacement(model.getPlayerList().get(0).getNickname(),0,2,dice2));
        assertEquals(null,model.getPlayerList().get(0).getPlayerScheme().getScheme()[0][2].getDice());

    }

    /**
     * Method used to clean some resources used during tests
     */
    @After
    public void clean(){
        controller.getGameRoundController().stopTimer();
        model.getDiceStock().clear();
        model.getPlayerList().clear();
    }
}
