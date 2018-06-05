package it.polimi.deib.se2018.TestModel.testPlayer;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *Classe Test del colore del giocatore
 * @author fernandes
 */
public class TestAbbreviation {
    Player player;
    Dice dice;
    @Before
    public void setUp(){
        //creo giocatore e dado e assegno colore
        player=new Player("sirlan",0,PlayerColor.GREEN);
        dice=new Dice(DiceColor.GREEN);

    }

    /**
     * metodo che testa se ritorna l'abbreviazione giusta del colore
     * @author fernandes
     */
    @Test
    public void testToString(){
        assertEquals("G",player.getPlayerColor().toString());
        assertEquals("G",dice.getColor().toString());

    }
}
