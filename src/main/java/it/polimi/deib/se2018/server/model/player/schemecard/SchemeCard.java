package it.polimi.deib.se2018.server.model.player.schemecard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Scheme card class
 * @author Simone Mariani
 */
public class SchemeCard implements Serializable {
    private String schemeName;
    private int difficulty;
    private SchemeCard retro;
    private Box[][] scheme;
    private final int ROWS=4;//Rows
    private final int COLS=5;//Columns

    /**
     * Constructor
     * @param name scheme card name
     * @param diff scheme card difficulty
     * @param table scheme card table
     */
    //Constructor
    public SchemeCard(String name, int diff,Box [][] table){
        schemeName=name;
        setDifficulty(diff);
        scheme=new Box[ROWS][COLS];
        scheme=table.clone();
    }

    //"Getters" methods
    /**
     * Gets scheme card name
     * @return scheme card name
     */
    public String getSchemeName() {
        return schemeName;
    }

    /**
     * Gets scheme card difficulty
     * @return scheme card difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Gets scheme card retro
     * @return scheme card retro
     */
    public SchemeCard getRetro() {
        return retro;
    }

    /**
     * Gets scheme
     * @return scheme
     */
    public Box[][] getScheme(){return scheme;}

    /**
     * Gets scheme card rows
     * @return scheme card rows
     */
    public int getROWS(){ return ROWS; }

    /**
     * Gets scheme card columns
     * @return scheme card columns
     */
    public int getCOLS() { return COLS; }

    //"Setters" methods
    /**
     * Sets difficulty
     * @param diff difficulty
     */
    public void setDifficulty(int diff){
        difficulty=diff;
    }

    /**
     * Shows scheme card
     * @return string that describes the object
     */
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
        builder.append("  1   2   3   4   5");
        for(int i = 0; i < ROWS; i++){
            builder.append("\n" + (stringa.get(i)));
            for(int j=0;j<COLS;j++) {
                if (scheme[i][j].getDice() != null)
                {builder.append("|" + scheme[i][j].getDice().getColor() + scheme[i][j].getDice().getValue() + "|");}
                else { if(scheme[i][j] instanceof ColoredBox) builder.append("| "+scheme[i][j].getColor()+"|");
                else if(scheme[i][j] instanceof ValueBox) builder.append("| "+scheme[i][j].getValue()+"|");
                else builder.append(("| W|"));}
            }
        }
        builder.append("\n\n");
        return builder.toString();
    }

    /**
     * Sets scheme card retro
     * @param retro scheme card retro
     */
    public void setRetro(SchemeCard retro) {
        this.retro = retro;
    }

    /**
     * Sets scheme card
     * @param schemeCard scheme card
     */
    public void setScheme(SchemeCard schemeCard){this.scheme=schemeCard.getScheme();}

    /**
     * Basic visualization
     * @return string that prints initial scheme card
     */
    public String basicVisualization(){
        ArrayList stringa=new ArrayList<String>();
        stringa.add(0,"A");
        stringa.add(1,"B");
        stringa.add(2,"C");
        stringa.add(3,"D");
        StringBuilder builder = new StringBuilder();
        builder.append("SCHEME: "+schemeName+"\nDIFFICULTY: "+difficulty+"\n");
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
        return builder.toString();
    }




}

