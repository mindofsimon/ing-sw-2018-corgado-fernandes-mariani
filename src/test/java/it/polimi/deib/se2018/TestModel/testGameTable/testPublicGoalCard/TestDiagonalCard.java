package it.polimi.deib.se2018.TestModel.testGameTable.testPublicGoalCard;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.DiagonalCard;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.model.player.PlayerColor;
import it.polimi.deib.se2018.model.player.schemecard.Box;
import it.polimi.deib.se2018.model.player.schemecard.ColoredBox;
import it.polimi.deib.se2018.model.player.schemecard.SchemeCard;
import it.polimi.deib.se2018.model.player.schemecard.ValueBox;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 *Classe Test della Carta effetto publico Diagonali Colorate
 * @author fernandes
 */
public class TestDiagonalCard {
    DiagonalCard card;
    Player player;

    //creo schema con dadi piazzati e giocatore
    @Before
    public void setUp(){
        //creo carta pubblica
        card=new DiagonalCard("diagonali colorate");
        //creo schema
        Box[][] tabella = new Box[4][5];
        tabella[0][0] = new Box();
        tabella[0][1] = new Box();//è UNA CASELLA BIANCA (WHITE=W)!
        tabella[0][2] = new Box();
        tabella[0][3] = new Box();
        tabella[0][4] = new Box();
        tabella[1][0] = new ColoredBox(DiceColor.VIOLET);
        tabella[1][1] = new ColoredBox(DiceColor.BLU);
        tabella[1][2] = new ColoredBox(DiceColor.YELLOW);
        tabella[1][3] = new ValueBox(6);
        tabella[1][4] = new ColoredBox(DiceColor.YELLOW);
        tabella[2][0] = new ColoredBox(DiceColor.BLU);
        tabella[2][1] = new ColoredBox(DiceColor.RED);
        tabella[2][2] = new ValueBox(5);
        tabella[2][3] = new ColoredBox(DiceColor.YELLOW);
        tabella[2][4] = new ColoredBox(DiceColor.BLU);
        tabella[3][0] = new ValueBox(4);
        tabella[3][1] = new ColoredBox(DiceColor.YELLOW);
        tabella[3][2] = new ValueBox(3);
        tabella[3][3] = new ValueBox(2);
        tabella[3][4] = new ColoredBox(DiceColor.BLU);
        SchemeCard retro=null;
        SchemeCard scheme = new SchemeCard("schema1",3,retro,tabella);
        //creo giocatore
        player=new Player("sirlan",0,PlayerColor.GREEN);
        player.setPlayerScheme(scheme);
        //piazzo i dadi
        Dice d1=new Dice(DiceColor.BLU);
        d1.setValue(1);
        player.getPlayerScheme().getScheme()[1][1].setDice(d1);
        Dice d2=new Dice(DiceColor.YELLOW);
        d2.setValue(1);
        player.getPlayerScheme().getScheme()[1][4].setDice(d2);
        Dice d3=new Dice(DiceColor.BLU);
        d3.setValue(1);
        player.getPlayerScheme().getScheme()[2][0].setDice(d3);
        Dice d4=new Dice(DiceColor.BLU);
        d4.setValue(5);
        player.getPlayerScheme().getScheme()[2][2].setDice(d4);
        Dice d5=new Dice(DiceColor.YELLOW);
        d5.setValue(1);
        player.getPlayerScheme().getScheme()[2][3].setDice(d5);
        Dice d6=new Dice(DiceColor.BLU);
        d6.setValue(3);
        player.getPlayerScheme().getScheme()[3][2].setDice(d6);
        Dice d7=new Dice(DiceColor.YELLOW);
        d7.setValue(2);
        player.getPlayerScheme().getScheme()[3][3].setDice(d7);

    }
    /**
     * metodo che testa il calcolo dei punti avendo come carta effetto publico Diagonali colorate
     * @author fernandes
     */
   @Test
    public void testCalculateVictoryPoints(){
       assertEquals(5,card.calculateVictoryPoints(player));
   }

}
