package it.polimi.deib.se2018.TestModel.testGameTable.testPublicGoalCard;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.ElementType;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.LineType;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.RowAndColCard;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.model.player.PlayerColor;
import it.polimi.deib.se2018.model.player.schemecard.Box;
import it.polimi.deib.se2018.model.player.schemecard.SchemeCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 *Classe Test delle Carte effetto publico che cambiano il punteggio in base alla combinazione delle righe e collone
 * @author fernandes
 */

public class TestRowAndColCard {
    SchemeCard scheme;
    Player player;
    RowAndColCard card;

    @Before
    public void setUp(){
        //creo una carta schema generica tutta bianca solo per i test
        Box[][] tabella = new Box[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                tabella[i][j] = new Box();
            }
        }
        SchemeCard retro=null;
        scheme = new SchemeCard("schema1",3,retro,tabella);
        //inizializzo il giocatore con lo schema creato
        player= new Player("sirlan",0,PlayerColor.GREEN);
        player.setPlayerScheme(scheme);
        //riempio lo schema con una colonna di colori diversi e valori diversi
        int cont=1;
        int i=0;
        for(DiceColor c: DiceColor.values()) {
            Dice dice= new Dice(c);
            dice.setValue(cont);
            player.getPlayerScheme().getScheme()[0][i].setDice(dice);
            cont++;
            i++;
        }
        //riempio lo schema con una riga di colori diversi e valori diversi
        int cont1=1;
        ArrayList<DiceColor> colorList=new ArrayList<DiceColor>();

        for(DiceColor c: DiceColor.values()) {
            colorList.add(c);
        }
        for(int j=0;j<4;j++){
            Dice dice= new Dice(colorList.get(j));
            dice.setValue(cont1);
            player.getPlayerScheme().getScheme()[j][0].setDice(dice);
            cont1++;
        }

        //piazzo un dado vicino a un'altro dello stesso colore e valore per coprire tutte le condizione di test
        Dice d= new Dice(DiceColor.GREEN);
        d.setValue(2);
        player.getPlayerScheme().getScheme()[1][1].setDice(d);


    }

    /**
     * metodo che testa l'assegnamento dei punti in base al tipo di carta inizializzato
     * @author fernandes
     */
    @Test
    public void testRowAndColCard(){
        //inizializzo la carta 'Colori Diversi Riga'
        card=new RowAndColCard(LineType.ROW,ElementType.COLOR,"colore Diversi Riga");
        assertEquals(6,card.getPoints());//controllo il punteggio assegnato
        //inizializzo la carta 'Colori Diversi Colonna'
        card=new RowAndColCard(LineType.COLUMN,ElementType.COLOR,"colore Diversi Colonna");
        assertEquals(5,card.getPoints());//controllo il punteggio assegnato
        //inizializzo la carta 'Sfumature Diverse Riga'
        card=new RowAndColCard(LineType.ROW,ElementType.SHADE,"sfumature Diverse Riga");
        assertEquals(5,card.getPoints());//controllo il punteggio assegnato
        //inizializzo la carta 'Sfumature Diverse Colonna'
        card=new RowAndColCard(LineType.COLUMN,ElementType.SHADE,"Sfumature Diverse Colonna");
        assertEquals(4,card.getPoints());//controllo il punteggio assegnato
    }

    /**
     * metodo che testa l'assegnamento dei punti per le righe o collone colorate
     * @author fernandes
     */
    @Test
    public void testCalculateVictoryPoints(){
        //inizializzo la carta 'Colori Diversi Riga'
        card=new RowAndColCard(LineType.ROW,ElementType.COLOR,"colore Diversi Riga");
        assertEquals(6,card.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Colori Diversi Colonna'
        card=new RowAndColCard(LineType.COLUMN,ElementType.COLOR,"colore Diversi Colonna");
        assertEquals(5,card.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Sfumature Diverse Riga'
        card=new RowAndColCard(LineType.ROW,ElementType.SHADE,"sfumature Diverse Riga");
        assertEquals(5,card.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Sfumature Diverse Colonna'
        card=new RowAndColCard(LineType.COLUMN,ElementType.SHADE,"Sfumature Diverse Colonna");
        assertEquals(4,card.calculateVictoryPoints(player));//controllo il punteggio
        //piazzo il dado in mezzo alla riga e alla colonna per non avere colori e valori diversi e controllo che il punteggio ora sia 0
        Dice d1= new Dice(DiceColor.GREEN);
        d1.setValue(2);
        player.getPlayerScheme().getScheme()[3][0].setDice(d1);
        player.getPlayerScheme().getScheme()[0][4].setDice(d1);

        //inizializzo la carta 'Colori Diversi Riga'
        card=new RowAndColCard(LineType.ROW,ElementType.COLOR,"colore Diversi Riga");
        assertEquals(0,card.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Colori Diversi Colonna'
        card=new RowAndColCard(LineType.COLUMN,ElementType.COLOR,"colore Diversi Colonna");
        assertEquals(0,card.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Sfumature Diverse Riga'
        card=new RowAndColCard(LineType.ROW,ElementType.SHADE,"sfumature Diverse Riga");
        assertEquals(0,card.calculateVictoryPoints(player));//controllo il punteggio
        //inizializzo la carta 'Sfumature Diverse Colonna'
        card=new RowAndColCard(LineType.COLUMN,ElementType.SHADE,"Sfumature Diverse Colonna");
        assertEquals(0,card.calculateVictoryPoints(player));//controllo il punteggio


    }

    @Test
    public void testToString(){
        //inizializzo la carta 'Colori Diversi Riga'
        card=new RowAndColCard(LineType.ROW,ElementType.COLOR,"colore Diversi Riga");
        assertEquals("colore Diversi Riga",card.toString());

    }




}
