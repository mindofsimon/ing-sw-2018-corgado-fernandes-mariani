package it.polimi.deib.se2018.TestModel.testPlayer.testSchemeCard;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import it.polimi.deib.se2018.server.model.player.schemecard.Box;
import it.polimi.deib.se2018.server.model.player.schemecard.ColoredBox;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import it.polimi.deib.se2018.server.model.player.schemecard.ValueBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *Classe Test della della Scheme Card
 * @author fernandes
 */
public class TestSchemeCard {
    SchemeCard scheme;
    Box[][] tabella;

    //creo schema con dadi piazzati e giocatore
    @Before
    public void setUp(){

        //creo una carta schema generica tutta bianca solo per i test
        tabella = new Box[4][5];
        tabella[0][0] = new ColoredBox(DiceColor.BLUE);
        tabella[1][0] = new ValueBox(2);
        tabella[2][0] = new Box();
        tabella[3][0] = new Box();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        scheme = new SchemeCard("schema1",3,tabella);
        Player player=new Player("sirlan",0,PlayerColor.GREEN);
        player.setPlayerScheme(scheme);
        //piazzo i dadi in modo da avere una copertura completa dei test
        Dice dB=new Dice(DiceColor.BLUE);
        dB.setValue(1);
        player.getPlayerScheme().getScheme()[2][0].setDice(dB);
    }

    /**
     * Metodo che testa se la scheme card viene iniazzializata bene
     * @author fernandes
     */
    @Test
    public void testSchemeCard(){
        assertEquals("schema1",scheme.getSchemeName());
        assertEquals(3,scheme.getDifficulty());
        //assertEquals(tabella,scheme.getScheme());

    }

    @Test
    public void testToString(){
        assertTrue(scheme.toString().contains("B1"));
        assertTrue(scheme.toString().contains("XX"));
        assertTrue(scheme.toString().contains("2"));
        assertTrue(scheme.toString().contains("B"));


    }


}
