package it.polimi.deib.se2018.TestModel.testGameTable;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.gametable.DiceBag;
import it.polimi.deib.se2018.model.gametable.DiceStock;
import it.polimi.deib.se2018.model.gametable.RoundsTrack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
}
