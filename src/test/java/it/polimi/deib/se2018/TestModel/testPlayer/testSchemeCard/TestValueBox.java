package it.polimi.deib.se2018.TestModel.testPlayer.testSchemeCard;

import it.polimi.deib.se2018.server.model.player.schemecard.ValueBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *Classe Test della Box Con un valore assegnato della Scheme Card
 * @author fernandes
 */
public class TestValueBox {
    ValueBox box;

    //inizializzo una box di valore 3
    @Before
    public void setUp(){
        box=new ValueBox(3);
    }

    /**
     * Metodo che testa se la Box viene iniazzializata bene
     * @author fernandes
     */
    @Test
    public void testColoredBox(){
        assertEquals(3,box.getValue());
    }
}
