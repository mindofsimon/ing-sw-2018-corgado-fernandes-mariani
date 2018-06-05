package it.polimi.deib.se2018.TestModel.testGameTable.testPublicGoalCard;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.ElementType;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.VarietyCard;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import it.polimi.deib.se2018.server.model.player.schemecard.Box;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *Classe Test delle Carte effetto publico che cambiano il punteggio in base alla variazione di sfumature e colori
 * @author fernandes
 */
public class TestVarietyCard {
    SchemeCard scheme;
    SchemeCard scheme1;
    Player player;
    Player player2;


    @Before
    public void setUp() {
        //creo una carta schema generica tutta bianca solo per i test
        Box[][] tabella = new Box[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                tabella[i][j] = new Box();
            }
        }
        scheme = new SchemeCard("schema1", 3, tabella);
        scheme1 = new SchemeCard("schema2", 3, tabella);
        //inizializzo il giocatore con lo schema creato
        player = new Player("sirlan", 0, PlayerColor.GREEN);
        player.setPlayerScheme(scheme);

    }

    //svuoto lo schema
        @After
        public void tearDown(){
            Box[][] tabella = new Box[4][5];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    tabella[i][j] = new Box();
                }
            }

        }




    /**
     * metodo che testa l'assegnamento dei punti per le varieta di colori e diverse sfumature con lo schema vuoto
     * @author fernandes
     */
    @Test
    public void testCalculateVictoryPointsSchemaVuoto(){

        //inizializzo la carta 'Varieta di colori'
        VarietyCard card1=new VarietyCard(ElementType.COLOR,"Varieta colori");
        assertEquals(0,card1.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Sfumature diverse'
        VarietyCard card2=new VarietyCard(ElementType.SHADE,"Sfumature Diverse");
        assertEquals(0,card2.calculateVictoryPoints(player));

    }

    /**
     * metodo che testa l'assegnamento dei punti per le varieta di colori e diverse sfumature
     * @author fernandes
     */
    @Test
    public void testCalculateVictoryPoints(){
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

        //inizializzo la carta 'Varieta di colori'
        VarietyCard card1=new VarietyCard(ElementType.COLOR,"Varieta colori");
        assertEquals(4,card1.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Sfumature diverse'
        VarietyCard card2=new VarietyCard(ElementType.SHADE,"Sfumature Diverse");
        assertEquals(0,card2.calculateVictoryPoints(player));//controllo il punteggio,o perche non arrivo fino a 6 nello schema
        //aggiungo dado di valore 6
        Dice d=new Dice(DiceColor.BLUE);
        d.setValue(6);
        player.getPlayerScheme().getScheme()[1][0].setDice(d);
        assertEquals(5,card2.calculateVictoryPoints(player));//controllo il punteggio,ora ho anche il dado 6
    }



    @Test
    public void testToString(){
        VarietyCard card=new VarietyCard(ElementType.COLOR,"Varieta colori");
        assertEquals("Varieta colori",card.toString());

    }
}
