package it.polimi.deib.se2018.TestModel.testGameTable.testPublicGoalCard;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.DiagonalCard;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.model.player.PlayerColor;
import it.polimi.deib.se2018.model.player.schemecard.Box;
import it.polimi.deib.se2018.model.player.schemecard.SchemeCard;
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
        //creo una carta schema generica tutta bianca solo per i test
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        SchemeCard retro=null;
        SchemeCard scheme = new SchemeCard("schema1",3,retro,tabella);
        //creo giocatore
        player=new Player("sirlan",0,PlayerColor.GREEN);
        player.setPlayerScheme(scheme);
        //piazzo i dadi in modo da avere 7 sette punti con la carta diagonali colorate
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
        Dice d6=new Dice(DiceColor.YELLOW);
        d6.setValue(3);
        player.getPlayerScheme().getScheme()[3][2].setDice(d6);
        Dice d7=new Dice(DiceColor.BLU);
        d7.setValue(2);
        player.getPlayerScheme().getScheme()[3][3].setDice(d7);

    }
    /**
     * metodo che testa il calcolo dei punti avendo come carta effetto publico Diagonali colorate
     * @author fernandes
     */
   @Test
    public void testCalculateVictoryPoints(){
       assertEquals(7,card.calculateVictoryPoints(player));
   }

}
