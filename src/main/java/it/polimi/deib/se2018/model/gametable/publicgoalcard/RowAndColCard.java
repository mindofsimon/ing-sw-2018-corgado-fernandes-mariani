package it.polimi.deib.se2018.model.gametable.publicgoalcard;

import it.polimi.deib.se2018.model.player.Player;

/**
 * Rows and columns card class: public goal card
 * @author Simone Mariani
 */
public class RowAndColCard implements PublicGoalCard {

    private final LineType lineType;
    private final ElementType elementType;
    private final int points;
    private final String name;

    /**
     * Constructor, initializes rows and columns card class
     * Identifies which public goal card is given
     * @param lt line type
     * @param et element type
     * @param n name
     */
    public RowAndColCard(LineType lt, ElementType et,String n) {
        lineType = lt;
        elementType=et;
        name=n;
        if(lineType.equals(LineType.ROW)&&elementType.equals(ElementType.COLOR)){points=6;}
        else if(lineType.equals(LineType.COLUMN)&&elementType.equals(ElementType.COLOR)){points=5;}
        else if(lineType.equals(LineType.ROW)&&elementType.equals(ElementType.SHADE)){points=5;}
        else points=4;
    }

    /**
     * Calculates victory points depending on element type
     * @param p player
     * @return total victory points depending on element type: color or shade
     */
    public int calculateVictoryPoints(Player p) {
        if (elementType.equals(ElementType.COLOR)) {
            return calculateColors(p);
        } else {
            return calculateShades(p);
        }

    }

    /**
     * Checks if the colors of a row or a column are all different and calculates number of rows or columns that have different colors
     * @param p player
     * @return number of rows or columns that have different colors * points
     */
    private int calculateColors(Player p) {
        int cont = 0;
        boolean cond;
        if (lineType.equals(LineType.ROW)) {
            for (int i = 0; i < p.getPlayerScheme().getROWS(); i++) {
                cond = true;
                for (int j = 1; j < p.getPlayerScheme().getCOLS(); j++) {
                    if (!(p.getPlayerScheme().getScheme()[i][j - 1].getDice() != null && p.getPlayerScheme().getScheme()[i][j].getDice() != null && !p.getPlayerScheme().getScheme()[i][j - 1].getDice().getColor().equals(p.getPlayerScheme().getScheme()[i][j].getDice().getColor())))
                        cond = false;
                }
                if (cond) cont++;
            }
            return cont * points;
        } else {
            for (int i = 0; i < p.getPlayerScheme().getCOLS(); i++) {
                cond = true;
                for (int j = 1; j < p.getPlayerScheme().getROWS(); j++) {
                    if (!(p.getPlayerScheme().getScheme()[j-1][i].getDice() != null && p.getPlayerScheme().getScheme()[j][i].getDice() != null && !p.getPlayerScheme().getScheme()[j-1][i].getDice().getColor().equals(p.getPlayerScheme().getScheme()[j][i].getDice().getColor())))
                        cond = false;
                }
                if (cond) cont++;
            }
            return cont * points;
        }
    }

    /**
     * Checks if the shades of a row or a column are all different and calculates number of rows or columns that have different shades
     * @param p player
     * @return number of rows or columns that have different shades * points
     */
    private int calculateShades(Player p){
        int cont = 0;
        boolean cond;
        if (lineType.equals(LineType.ROW)) {
            for (int i = 0; i < p.getPlayerScheme().getROWS(); i++) {
                cond = true;
                for (int j = 1; j < p.getPlayerScheme().getCOLS(); j++) {
                    if (!(p.getPlayerScheme().getScheme()[i][j - 1].getDice() != null && p.getPlayerScheme().getScheme()[i][j].getDice() != null && p.getPlayerScheme().getScheme()[i][j - 1].getDice().getValue()!=p.getPlayerScheme().getScheme()[i][j].getDice().getValue()))
                        cond = false;
                }
                if (cond) cont++;
            }
            return cont * points;
        }
        else {
            for (int i = 0; i < p.getPlayerScheme().getCOLS(); i++) {
                cond = true;
                for (int j = 1; j < p.getPlayerScheme().getROWS(); j++) {
                    if (!(p.getPlayerScheme().getScheme()[j-1][i].getDice() != null && p.getPlayerScheme().getScheme()[j][i].getDice() != null && p.getPlayerScheme().getScheme()[j-1][i].getDice().getValue()!=p.getPlayerScheme().getScheme()[j][i].getDice().getValue()))
                        cond = false;
                }
                if (cond) cont++;
            }
            return cont * points;

        }
    }

    /**
     * return the points assigneted on the card
     * @return points
     */
    //ritorna il numero di punti assegnato alla carta
    public int getPoints(){
        return this.points;
    }

    /**
     * Object text representation
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}


