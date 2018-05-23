package it.polimi.deib.se2018.TestModel.testPlayer.testSchemeCard;

import it.polimi.deib.se2018.model.player.schemecard.Box;
import it.polimi.deib.se2018.model.player.schemecard.SchemeCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        SchemeCard retro=null;
        scheme = new SchemeCard("schema1",3,retro,tabella);
    }

    /**
     * Metodo che testa se la scheme card viene iniazzializata bene
     * @author fernandes
     */
    @Test
    public void testSchemeCard(){
        assertEquals("schema1",scheme.getSchemeName());
        assertEquals(3,scheme.getDifficulty());
        assertEquals(null,scheme.getRetro());
        assertEquals(tabella,scheme.getScheme());

    }


}
