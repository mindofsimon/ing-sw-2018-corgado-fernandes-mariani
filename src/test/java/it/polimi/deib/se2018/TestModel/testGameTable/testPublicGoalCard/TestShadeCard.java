package it.polimi.deib.se2018.TestModel.testGameTable.testPublicGoalCard;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.*;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.model.player.PlayerColor;
import it.polimi.deib.se2018.model.player.schemecard.Box;
import it.polimi.deib.se2018.model.player.schemecard.SchemeCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 *Classe Test delle Carte effetto publico che cambiano il punteggio in base alla combinazione delle sfumature
 * @author fernandes
 */

public class TestShadeCard {
    SchemeCard scheme;
    Player player;
    ShadeCard card1;
    ShadeCard card2;
    ShadeCard card3;


    @Before
    public void setUp() {
        //inizializzo la carta 'Sfumature Chiare'
        card1=new ShadeCard(ShadeType.LIGHT,"sfumature chiare");
        //inizializzo la carta 'Sfumature medie'
        card2=new ShadeCard(ShadeType.MEDIUM,"sfumature medie");
        //inizializzo la carta 'Sfumature Scure'
        card3=new ShadeCard(ShadeType.DARK,"sfumature scure");

        //creo una carta schema generica tutta bianca solo per i test
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        SchemeCard retro = null;
        scheme = new SchemeCard("schema1", 3, retro, tabella);
        //inizializzo il giocatore con lo schema creato
        player = new Player("sirlan", 0, PlayerColor.GREEN);
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
        //inserisco altri dadi da coprire tutte le condizioni per il test
        Dice d=new Dice(DiceColor.BLU);
        d.setValue(1);
        player.getPlayerScheme().getScheme()[1][4].setDice(d);
        d=new Dice(DiceColor.BLU);
        d.setValue(4);
        player.getPlayerScheme().getScheme()[3][4].setDice(d);
        d=new Dice(DiceColor.BLU);
        d.setValue(6);
        player.getPlayerScheme().getScheme()[2][2].setDice(d);



    }

    /**
     * metodo che testa l'assegnamento dei punti per i vari tipi di sfumatura
     * @author fernandes
     */
    @Test
    public void testCalculateVictoryPoints(){
        assertEquals(2,card1.calculateVictoryPoints(player));//controllo il punteggio
        assertEquals(2,card2.calculateVictoryPoints(player));//controllo il punteggio
        assertEquals(2,card3.calculateVictoryPoints(player));//controllo il punteggio

    }

    @Test
    public void testToString(){
        assertEquals("sfumature chiare",card1.toString());

    }
}
