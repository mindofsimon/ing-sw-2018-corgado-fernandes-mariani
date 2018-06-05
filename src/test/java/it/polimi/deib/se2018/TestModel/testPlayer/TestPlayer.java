package it.polimi.deib.se2018.TestModel.testPlayer;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.ShadeCard;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.ShadeType;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import it.polimi.deib.se2018.server.model.player.PrivateGoalCard;
import it.polimi.deib.se2018.server.model.player.schemecard.Box;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *Classe Test del player
 * @author fernandes
 */
public class TestPlayer {
    Player player;
    SchemeCard scheme;

    //inizializza un giocatore
    @Before
    public void setUp(){
        player=new Player("sirlan",1,PlayerColor.GREEN);

        //creo una carta schema generica tutta bianca solo per i test
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        scheme = new SchemeCard("schema1",3,tabella);
        player.setPlayerScheme(scheme);
        //riempio lo schema con una colonna di valori diversi,cosi abbiamo i 3 tipi di sfumature da fare il test
        int cont = 1;
        int i = 0;
        for (DiceColor c : DiceColor.values()) {
            Dice dice = new Dice(c);
            dice.setValue(cont);
            player.getPlayerScheme().getScheme()[0][i].setDice(dice);
            cont++;
            i++;
        }



    }

    /**
     * Metodo che testa se il giocatore viene iniazzializato bene
     * @author fernandes
     */
    @Test
    public void testPlayer(){
        assertEquals("sirlan",player.getNickname());
        assertEquals(1,player.getOrder());
        assertEquals(PlayerColor.GREEN,player.getPlayerColor());
        assertEquals(0,player.getVictoryPoints());
        assertEquals(0,player.getFavorMarkers());
        assertEquals(0,player.getnMoves());
    }

    /**
     * Metodo che testa se lo schema viene assegnato giusto
     * @author fernandes
     */
    @Test
    public void testSetScheme(){
        assertEquals(scheme,player.getPlayerScheme());//controllo se è lo schema giusto
    }

    /**
     * Metodo che testa il set e reset della cardActivaded e dicePlacement
     * @author fernandes
     */
    @Test
    public void testSetReset(){
        //attivo la carta e piazzo il dado
        player.setDicePlacement();
        player.setCardActivated();
        //controllo se si sono attivati
        assertTrue(player.dicePlaced());
        assertTrue(player.cardActivated());
        //reseto
        player.resetDicePlacement();
        player.resetCardActivated();
        //controllo se si sono resetati
        assertFalse(player.dicePlaced());
        assertFalse(player.cardActivated());
    }

    /**
     * Metodo che testa se i punti assegnati sono giusti
     * @author fernandes
     */
    @Test
    public void testSetVictoryPoints(){
        ShadeCard card= new ShadeCard(ShadeType.LIGHT,"sfumature chiare");
        player.setVictoryPoints(card.calculateVictoryPoints(player));
        assertEquals(2,player.getVictoryPoints());
    }

    /**
     * Metodo che testa se i numeri di segnalini assegnati sono giusti
     * @author fernandes
     */
    @Test
    public void testSetFavorMakers(){
        player.setFavorMarkers(scheme.getDifficulty());
        assertEquals(3,player.getFavorMarkers());
    }

    /**
     * Metodo che testa se l'ordine assegnato è giusto
     * @author fernandes
     */
    @Test
    public void testSetOrder(){
        player.setOrder(2);
        assertEquals(2,player.getOrder());
    }

    /**
     * Metodo che testa se i numeri di segnalini assegnati sono giusti
     * @author fernandes
     */
    @Test
    public void testSetPrivateGoalCard(){
        PrivateGoalCard card=new PrivateGoalCard(DiceColor.BLUE,"dadi blu");
        player.setPrivateGoalCard(card);
        assertEquals(card,player.getPrivateGoalCard());
    }

    /**
     * Metodo che testa se il numero di mosse assegnate è giusto
     * @author fernandes
     */
    @Test
    public void testSetnMoves(){
        player.setnMoves(2);
        assertEquals(2,player.getnMoves());
    }

    /**
     * Metodo che testa se il numero di turni assegnati è giusto
     * @author fernandes
     */
    @Test
    public void testSetnTurns(){
        player.setnTurns(2);
        assertEquals(2,player.getnTurns());
    }

}
