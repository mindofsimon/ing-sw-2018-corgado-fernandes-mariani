package it.polimi.deib.se2018.TestModel.testGameTable;


import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.gametable.DiceBag;
import it.polimi.deib.se2018.model.gametable.DiceStock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 *Classe Test del Dice Stock
 * @author fernandes
 */
public class TestDiceStock {
    private DiceStock stock;
    private DiceBag bag;


    @Before
    public void setUp(){
        //inizializo la riserva dadi con 1 dado
        bag=DiceBag.getSingletonDiceBag();
        stock= DiceStock.getSingletonDiceStock();
        stock.insertDice(bag.extractRandomDice());
        stock.setDiceValue(0);
    }

    //cancella l'istanziamento del sachetto e della riserva creati nei test
    @After
    public void tearDown(){
        stock.clear();
        bag.clear();
    }

    /**
     * Metodo che testa se un dado viene trovato o meno
     * @author fernandes
     */
    @Test
    public void testFindDice(){
        Dice dice=stock.getDice(0);
        Dice d= new Dice(DiceColor.BLU);//dado non presente,perche inizializzato con valore 0
        assertEquals(0 ,stock.findDice(dice));
        assertEquals(-1,stock.findDice(d));

    }

    /**
     * Metodo che testa se un dado viene aggiunto corretamente alla riserva dadi
     * @author fernandes
     */
    @Test
    public void testInsertDice(){
        Dice d=bag.extractRandomDice();
        d.setValue(2);
        stock.insertDice(d);
        assertEquals(2,stock.size());//controlla se la lista ora ha 2 elementi
        assertNotEquals(-1,stock.findDice(d));//controlla che ci sia il dado

    }

    /**
     * Metodo che testa se un dado viene rimosso corretamente dalla riserva dadi
     * @author fernandes
     */
    @Test
    public void testExractDice(){
        Dice d=bag.extractRandomDice();
        d.setValue(2);
        stock.insertDice(d);//aggiunge dado
        stock.extractDice(d);//rimuove dado
        assertEquals(1,stock.size());//controlla se la lista ora ha 1 elemento

    }

    /**
     * Metodo che testa se un dado a caso viene rimosso corretamente dalla riserva dadi
     * @author fernandes
     */
    @Test
    public void testExractRandomDice(){
        Dice d1=bag.extractRandomDice();
        d1.setValue(2);
        stock.insertDice(d1);//aggiunge dado
        stock.extractRandomDice();//rimuove dado a caso
        assertEquals(1,stock.size());//controlla se la lista ora ha 1 elementi

    }

    /**
     * Metodo che testa se il valore del dado è stato setato bene
     * @author fernandes
     */
    @Test
    public void testSetDiceValue(){
        stock.setDiceValue(0);
        assertTrue(stock.getDice(0).getValue()>0&&stock.getDice(0).getValue()<7);

    }

    /**
     * Metodo che testa se nello stock esiste un dado di quel colore
     * @author fernandes
     */
    @Test
    public void testFindDiceColor(){
        stock.getDice(0).setColor(DiceColor.BLU);
        assertTrue(stock.findDice(DiceColor.BLU));
        assertFalse(stock.findDice(DiceColor.RED));

    }

    /**
     * Metodo che testa se lo stock torna la lista di dadi presenti
     * @author fernandes
     */
    @Test
    public void testGetDiceList(){
        ArrayList<Dice> lista=new ArrayList<Dice>();
        Dice d=stock.getDice(0);
        lista.add(d);

        assertEquals(lista,stock.getDiceList());

    }

    @Test
    public void testToString(){
        stock.getDice(0).setColor(DiceColor.BLU);
        stock.getDice(0).setValue(1);
        assertTrue(stock.toString().contains("B"));
        assertTrue(stock.toString().contains("1"));

    }

}
