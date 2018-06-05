package it.polimi.deib.se2018.TestModel.testPlayer;

import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.PrivateGoalCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *Classe Test dela Private Goal Card
 * @author fernandes
 */
public class TestPrivateGoalCard {
    PrivateGoalCard card;

    //inizializzo la carta
    @Before
    public void setUp() {
        card = new PrivateGoalCard(DiceColor.BLUE, "Sfumature Blu");
    }

    /**
     * Metodo che testa se la carta viene iniazzializata bene
     * @author fernandes
     */
    @Test
    public void TestPrivateGoalCard(){
        assertEquals(DiceColor.BLUE,card.getColor());
        assertEquals("Sfumature Blu",card.toString());
    }

}