package it.polimi.deib.se2018.TestModel.testGameTable;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestRoundsTrack {
    private DiceStock stock;
    private DiceBag bag;
    private RoundsTrack track;


    @Before
    public void setUp(){
        //inizializo la riserva dadi con 1 dado
        bag=DiceBag.getSingletonDiceBag();
        stock= DiceStock.getSingletonDiceStock();
        stock.insertDice(bag.extractRandomDice());
        stock.setDiceValue(0);
        //inizializo il tracciato
        track=RoundsTrack.getSingletonRoundsTrack();
    }

    //cancella l'istanziamento del sachetto,della riserva e del tracciato creati nei test
    @After
    public void tearDown(){
        stock.clear();
        bag.clear();
        track.clear();
    }

    /**
     * Metodo che testa se un dado viene aggiunto corretamente alla riserva dadi
     * @author fernandes
     */
    @Test
    public void testInsertDice(){
        Dice d=stock.extractRandomDice();
        track.insertDice(d);
        assertEquals(1,track.size());//controlla se la lista ora ha 1 elementi
        assertNotEquals(-1,track.findDice(d));//controlla che ci sia il dado

    }

    /**
     * Metodo che testa se il tracciato dei round torna la lista dei dadi presenti
     * @author fernandes
     */
    @Test
    public void testGetDiceList(){
        ArrayList<Dice> lista=new ArrayList<Dice>();
        Dice d=stock.getDice(0);
        track.insertDice(d);
        lista.add(d);

        assertEquals(lista,track.getDiceList());

    }

    /**
     * Metodo che testa se un dado viene trovato o meno
     * @author fernandes
     */
    @Test
    public void testFindDice(){
        Dice dice=stock.getDice(0);
        track.insertDice(dice);
        Dice d= new Dice(DiceColor.BLUE);//dado non presente,perche inizializzato con valore 0
        assertEquals(0 ,track.findDice(dice));
        assertEquals(-1,track.findDice(d));

    }

    @Test
    public void testToString(){
        Dice d=new Dice(DiceColor.BLUE);
        d.setValue(1);
        track.insertDice(d);
        assertTrue(track.toString().contains("B"));
        assertTrue(track.toString().contains("1"));

    }
}
