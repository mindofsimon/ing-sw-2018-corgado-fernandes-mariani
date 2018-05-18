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
    ShadeCard card;

    @Before
    public void setUp() {
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



    }

    /**
     * metodo che testa l'assegnamento dei punti per i vari tipi di sfumatura
     * @author fernandes
     */
    @Test
    public void testCalculateVictoryPoints(){
        //inizializzo la carta 'Sfumature Chiare'
        card=new ShadeCard(ShadeType.LIGHT,"sfumature chiare");
        assertEquals(2,card.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Sfumature medie'
        card=new ShadeCard(ShadeType.MEDIUM,"sfumature medie");
        assertEquals(2,card.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Sfumature Scure'
        card=new ShadeCard(ShadeType.DARK,"sfumature scure");
        assertEquals(0,card.calculateVictoryPoints(player));//controllo il punteggio,0 perche nello schema non c'erano sfumature scure
        //aggiungo un dado di valore 6 vicino al 5
        Dice d=new Dice(DiceColor.BLU);
        d.setValue(6);
        player.getPlayerScheme().getScheme()[1][4].setDice(d);
        assertEquals(2,card.calculateVictoryPoints(player));//controllo il punteggio,2 ora ce la sfumatura scura

    }
}
