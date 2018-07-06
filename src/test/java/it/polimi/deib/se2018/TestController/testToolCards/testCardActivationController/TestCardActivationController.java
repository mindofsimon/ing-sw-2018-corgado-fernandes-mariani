package it.polimi.deib.se2018.TestController.testToolCards.testCardActivationController;


import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.CardActivationController;
import it.polimi.deib.se2018.server.controller.Controller;
import it.polimi.deib.se2018.server.controller.DicePlacementController;
import it.polimi.deib.se2018.server.controller.toolcard.ChangeAndPlaceCard;
import it.polimi.deib.se2018.server.controller.toolcard.MoveDices;
import it.polimi.deib.se2018.server.controller.toolcard.Restriction;
import it.polimi.deib.se2018.server.controller.toolcard.ToolCard;
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
import it.polimi.deib.se2018.server.model.player.schemecard.ColoredBox;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import it.polimi.deib.se2018.server.model.player.schemecard.ValueBox;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class used to test other methods of CardActivationController
 * @author Sirlan Fernandes
 */
public class TestCardActivationController {
    private Controller controller;
    private Model model;
    private RemoteView view;
    private Player p1;
    private Player p2;
    private ArrayList<ToolCard> toolCardList = new ArrayList<ToolCard>();
    private CardActivationController cardActivationController;
    private DicePlacementController dicePlacementController = new DicePlacementController();


    @Before
    public void setUp() {
        model = new Model(DiceBag.getSingletonDiceBag(), DiceStock.getSingletonDiceStock(), RoundsTrack.getSingletonRoundsTrack());
        view = new RemoteView();
        controller = new Controller(model, view);
        p1 = new Player("sirlan", 1, PlayerColor.RED);
        p2 = new Player("mari", 2, PlayerColor.RED);
        //aggiungo qualche public card
        model.addPublicGoalCard(new RowAndColCard(LineType.ROW, ElementType.COLOR, "Colored Rows"));
        model.addPublicGoalCard(new RowAndColCard(LineType.COLUMN, ElementType.COLOR, "Colored Columns"));
        model.addPublicGoalCard(new RowAndColCard(LineType.ROW, ElementType.SHADE, "Shades Rows"));
        //creo uno schema
        Box[][] tabella = new Box[4][5];
        tabella[0][0] = new ColoredBox(DiceColor.YELLOW);
        tabella[0][1] = new ColoredBox(DiceColor.BLUE);
        tabella[0][2] = new Box();
        tabella[0][3] = new Box();
        tabella[0][4] = new ValueBox(1);
        tabella[1][0] = new ColoredBox(DiceColor.GREEN);
        tabella[1][1] = new ValueBox(6);
        tabella[1][2] = new ValueBox(5);
        tabella[1][3] = new Box();
        tabella[1][4] = new ValueBox(4);
        tabella[2][0] = new ValueBox(3);
        tabella[2][1] = new Box();
        tabella[2][2] = new ColoredBox(DiceColor.RED);
        tabella[2][3] = new Box();
        tabella[2][4] = new ColoredBox(DiceColor.GREEN);
        tabella[3][0] = new ValueBox(2);
        tabella[3][1] = new Box();
        tabella[3][2] = new Box();
        tabella[3][3] = new ColoredBox(DiceColor.BLUE);
        tabella[3][4] = new ColoredBox(DiceColor.YELLOW);
        SchemeCard schema = new SchemeCard("schema1", 3, tabella);
        p1.setPlayerScheme(schema);
        p1.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED, "Private Goal Red"));
        p2.setPlayerScheme(schema);
        p2.setPrivateGoalCard(new PrivateGoalCard(DiceColor.RED, "Private Goal Red"));
        controller.getGameRoundController().setTimer(p1.getnMoves(), p1.getOrder());
        controller.getGameRoundController().setTimer(p2.getnMoves(), p2.getOrder());
        p1.setFavorMarkers(3);

    }

    /**
     * test the controller that no one cards can be activated
     * @author fernandes
     */
    @Test
    public void testnoOneCardsActivated(){
        model.addPlayer(p1);
        toolCardList.add(new MoveDices("Pennello  per  Eglomise",2,Restriction.COLOR,DiceColor.BLUE,1,"Muovi  un  qualsiasi  dado  nella  tua\n  vetrata  ignorando  le  restrizioni  di  colore"));
        toolCardList.add(new MoveDices("Alesatore  per  lamina  di  rame",3,Restriction.SHADE,DiceColor.RED,1,"Muovi  un  qualsiasi  dado  nella  tua  vetrata  ignorando  le  restrizioni\n  di  valore"));
        toolCardList.add(new ChangeAndPlaceCard("Riga  in  Sughero",9,DiceColor.YELLOW,1,"Dopo  aver  scelto  un  dado,  piazzalo  in  una  casella  che  non  sia  adiacente  a  un  altro  dado"));
        toolCardList.add(new ChangeAndPlaceCard("Pennello  per  Pasta  Salda",6,DiceColor.VIOLET,1,"Dopo  aver  scelto  un  dado,\n  tira  nuovamente  quel  dado\nSe  non  puoi  piazzarlo,\n  riponilo  nella  Riserva"));
        cardActivationController=new CardActivationController(model,toolCardList,dicePlacementController);
        Dice dice=new Dice(DiceColor.VIOLET);
        dice.setValue(1);
        model.getDiceStock().insertDice(dice);
        try {
            this.cardActivationController.setActivated(p1,false);
        } catch (RemoteException e) {
            fail();
        }
        //ora a false perche almeno una carta puo essere utiizzata
        assertEquals(false,cardActivationController.noOneCardsActivated());
        //setto che è stata usata una carta e quindi vanno tutte a false e quindi è true
        p1.setCardActivated();
        try {
            this.cardActivationController.setActivated(p1,false);
        } catch (RemoteException e) {
            fail();
        }
        assertEquals(true,cardActivationController.noOneCardsActivated());



    }

    /**
     * test Find a Card
     * @author fernandes
     */
    @Test
    public void testFindCard(){
        model.addPlayer(p1);
        ToolCard card=new MoveDices("Pennello  per  Eglomise",2,Restriction.COLOR,DiceColor.BLUE,1,"Muovi  un  qualsiasi  dado  nella  tua\n  vetrata  ignorando  le  restrizioni  di  colore");
        toolCardList.add(card);
        cardActivationController=new CardActivationController(model,toolCardList,dicePlacementController);
        assertEquals(card,cardActivationController.findCard(2));
        assertEquals(null,cardActivationController.findCard(3));




    }

    /**
     * Method used to clean some resources used during tests
     */
    @After
    public void clean(){
        controller.getGameRoundController().stopTimer();
        controller.cleanToolCards();
        toolCardList.clear();
        model.getDiceStock().clear();
        model.getPlayerList().clear();
        model.getDiceBag().clear();
        model.getRoundsTrack().clear();
        p1.setFavorMarkers(4);

    }

}
