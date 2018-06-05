package it.polimi.deib.se2018.TestModel.testDice;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *Classe Test del player
 * @author fernandes
 */
public class TestDice {
    Dice dice;
    //inizializza un dado blu
    @Before
    public void setUp(){
        dice= new Dice(DiceColor.BLUE);
    }

    /**
     * Metodo che testa se il dado viene iniazzializato bene
     * @author fernandes
     */
    @Test
    public void testDice(){
        assertEquals(DiceColor.BLUE,dice.getColor());
        assertEquals(0,dice.getValue());//valore a 0 perche quando viene inizializzato il dado ha valore 0

    }

    /**
     * Metodo che testa se il valore assegnato al dado è giusto
     * @author fernandes
     */
    @Test
    public void testSetValue(){
        dice.setValue(6);//assegno il valore 6 al dado
        assertEquals(6,dice.getValue());

    }

    /**
     * Metodo che testa se il colore assegnato al dado è giusto
     * @author fernandes
     */
    @Test
    public void testSetColor(){
        dice.setColor(DiceColor.RED);//assegno il valore rosso
        assertEquals(DiceColor.RED,dice.getColor());

    }



}
