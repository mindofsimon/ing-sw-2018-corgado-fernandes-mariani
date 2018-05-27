package it.polimi.deib.se2018.TestModel.testGameTable.testPublicGoalCard;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.DiagonalCard;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.ElementType;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.LineType;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.RowAndColCard;
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
        //piazzo i dadi in modo da avere una copertura completa dei test
        Dice dB=new Dice(DiceColor.BLU);
        dB.setValue(1);
        Dice dR=new Dice(DiceColor.RED);
        dR.setValue(1);
        Dice dY=new Dice(DiceColor.YELLOW);
        dY.setValue(1);
        Dice dG=new Dice(DiceColor.GREEN);
        dG.setValue(1);
        Dice dV=new Dice(DiceColor.VIOLET);
        dV.setValue(1);
        player.getPlayerScheme().getScheme()[0][0].setDice(dB);
        player.getPlayerScheme().getScheme()[1][1].setDice(dB);
        player.getPlayerScheme().getScheme()[2][0].setDice(dB);
        player.getPlayerScheme().getScheme()[2][2].setDice(dB);
        player.getPlayerScheme().getScheme()[3][3].setDice(dB);
        player.getPlayerScheme().getScheme()[1][4].setDice(dY);
        player.getPlayerScheme().getScheme()[2][3].setDice(dY);
        player.getPlayerScheme().getScheme()[3][2].setDice(dY);
        player.getPlayerScheme().getScheme()[3][4].setDice(dY);
        player.getPlayerScheme().getScheme()[0][1].setDice(dR);
        player.getPlayerScheme().getScheme()[1][0].setDice(dR);
        player.getPlayerScheme().getScheme()[1][3].setDice(dV);
        player.getPlayerScheme().getScheme()[0][2].setDice(dV);
        player.getPlayerScheme().getScheme()[0][3].setDice(dG);



    }
    /**
     * metodo che testa il calcolo dei punti avendo come carta effetto publico Diagonali colorate
     * @author fernandes
     */
   @Test
    public void testCalculateVictoryPoints(){
       assertEquals(13,card.calculateVictoryPoints(player));
   }

    @Test
    public void testToString(){

        assertEquals("diagonali colorate",card.toString());

    }

}


