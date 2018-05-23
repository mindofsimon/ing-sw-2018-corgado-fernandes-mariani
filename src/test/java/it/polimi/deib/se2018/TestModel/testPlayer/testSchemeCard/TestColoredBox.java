package it.polimi.deib.se2018.TestModel.testPlayer.testSchemeCard;

import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.player.schemecard.ColoredBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *Classe Test della Box Colorata della Scheme Card
 * @author fernandes
 */
public class TestColoredBox {
    ColoredBox box;

    //inizializzo una box Blu
    @Before
    public void setUp(){
        box=new ColoredBox(DiceColor.BLU);
    }

    /**
     * Metodo che testa se la Box colorata viene iniazzializata bene
     * @author fernandes
     */
    @Test
    public void testColoredBox(){
        assertEquals(DiceColor.BLU,box.getColor());
    }

}
