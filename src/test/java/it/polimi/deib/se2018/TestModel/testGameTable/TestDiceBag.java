package it.polimi.deib.se2018.TestModel.testGameTable;
import static org.junit.Assert.*;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *Classe Test del Dice Bag
 * @author fernandes
 */

public class TestDiceBag {

    private  DiceBag bag;

    @Before
    public void setUp(){
        bag= DiceBag.getSingletonDiceBag();
    }

    //cancella l'istanziamento del sachetto creato nel test
    @After
    public void tearDown(){
        bag.clear();
    }



    /**
     * metodo che testa l'inizializzazione del sachetto
     * si controlla che ci siano 90 dadi e 18 per ogni colore
     * @author fernandes
     */

    @Test
    public void testInit(){

        assertEquals(90,bag.size());
        for(DiceColor c: DiceColor.values()) {
            assertEquals(18,bag.numColor(c));
        }

    }
    /**
     * metodo che testa l'estrazione di un dado dal sacchetto
     * si controlla che i numeri di dadi rimasti dopo l'estrazione sia di -1 in generale e per quel colore
     * @author fernandes
     */
    @Test
    public void testExtractDice(){

        Dice dice=bag.extractRandomDice();
        assertEquals(89,bag.size());
        DiceColor c=dice.getColor();
        assertEquals(17,bag.numColor(c));

    }

    /**
     * metodo che testa l'inserimento di un dado dal sacchetto
     * si controlla che i numeri di dadi rimasti dopo l'inserimento sia di +1 in generale e per quel colore
     * @author fernandes
     */
    @Test
    public void testInserttDice(){

        Dice dice=new Dice(DiceColor.BLUE);
        bag.insertDice(dice);
        assertEquals(91,bag.size());
        assertEquals(19,bag.numColor(DiceColor.BLUE));

    }



    @Test
    public void testToString(){
        Dice dice=new Dice(DiceColor.BLUE);
        bag.insertDice(dice);
        assertTrue(bag.toString().contains("COLOR: B"));
        assertTrue(bag.toString().contains("LEFT: 19"));


    }





}
