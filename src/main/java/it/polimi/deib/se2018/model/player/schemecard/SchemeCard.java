package it.polimi.deib.se2018.model.player.schemecard;

import it.polimi.deib.se2018.model.dice.DiceColor;

import java.util.ArrayList;

public class SchemeCard {
    private String schemeName;
    private int difficulty;
    private SchemeCard retro;
    private Box[][] scheme;
    private final int ROWS=4;//Rows
    private final int COLS=5;//Columns

    //Constructor
    public SchemeCard(String name, int diff, SchemeCard retro,Box [][] table){
        this.retro=retro;
        schemeName=name;
        setDifficulty(diff);
        scheme=new Box[ROWS][COLS];
        scheme=table.clone();
    }

    //"Getters" methods
    public String getSchemeName() {
        return schemeName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public SchemeCard getRetro() {
        return retro;
    }

    public Box[][] getScheme(){return scheme;}

    public int getROWS(){ return ROWS; }

    public int getCOLS() { return COLS; }

    //"Setters" methods
    public void setDifficulty(int diff){
        difficulty=diff;
    }

    //toString() to show the scheme card
    @Override
    public String toString() {
        ArrayList stringa=new ArrayList<String>();
        stringa.add(0,"A");
        stringa.add(1,"B");
        stringa.add(2,"C");
        stringa.add(3,"D");
        StringBuilder builder = new StringBuilder();
        builder.append("BOXES SCHEME: \n");
        builder.append("  1  2  3  4  5");
        for(int i = 0; i < ROWS; i++){
            builder.append("\n" + (stringa.get(i)));
            for(int j=0;j<COLS;j++){
                if(scheme[i][j] instanceof ColoredBox) builder.append("|"+scheme[i][j].getColor()+"|");
                else if(scheme[i][j] instanceof ValueBox) builder.append("|"+scheme[i][j].getValue()+"|");
                else builder.append(("|W|"));
            }
        }
        builder.append("\n\n");
        builder.append("PLACED DICES SCHEME (XX=Boxes with no dices): \n");
        builder.append("  1   2   3   4   5");
        for(int i = 0; i < ROWS; i++){
            builder.append("\n" + (stringa.get(i)));
            for(int j=0;j<COLS;j++) {
                if (scheme[i][j].getDice() != null)
                {builder.append("|" + scheme[i][j].getDice().getColor() + scheme[i][j].getDice().getValue() + "|");}
                else {builder.append(("|XX|"));}
            }
        }
        builder.append("\n\n");
        return builder.toString();
    }




}

