package it.polimi.deib.se2018.TestModel.testPlayer.testSchemeCard;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.schemecard.Box;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *Classe Test della generica Box bianca della Scheme Card
 * @author fernandes
 */

public class TestBox {
    private Dice dice;
    private Box box;

    //inizializza una box e un dado blu di valore 1
    @Before
    public void setUp(){
        box=new Box();
        dice=new Dice(DiceColor.BLUE);
        dice.setValue(1);

    }

    /**
     * metodo che testa l'inserimento del dado nella box
     * si controlla che il dado inserito sia quello nella box
     * @author fernandes
     */
    @Test
    public void TestSetDice(){
        box.setDice(dice);
        assertEquals(dice,box.getDice());

    }

    /**
     * metodo che testa il get dice
     * si controlla che il dado tornato abbia gli stessi valori di quello inserito
     * @author fernandes
     */
    @Test
    public void TestGetDice(){
        box.setDice(dice);
        assertEquals(DiceColor.BLUE,box.getDice().getColor());
        assertEquals(1,box.getDice().getValue());

    }

    /**
     * metodo che testa il colore della Box
     * si controlla che colore sia null,visto che una Box non ha colori
     * @author fernandes
     */

    @Test
    public void TestGetColor(){
        assertEquals(null,box.getColor());

    }

    /**
     * metodo che testa il valore della Box
     * si controlla che il valore sia 0,visto che una Box non ha valore
     * @author fernandes
     */

    @Test
    public void TestGetValue(){
        assertEquals(0,box.getValue());

    }




}
