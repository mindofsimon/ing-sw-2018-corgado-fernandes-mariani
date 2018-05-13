package it.polimi.deib.se2018.model.gametable.publicgoalcard;

import it.polimi.deib.se2018.model.player.Player;

public class RowAndColCard implements PublicGoalCard {

    private final LineType lineType;
    private final ElementType elementType;
    private final int points;
    private final String name;

    public RowAndColCard(LineType lt, ElementType et,String n) {
        lineType = lt;
        elementType=et;
        name=n;
        if(lineType.equals(LineType.ROW)&&elementType.equals(ElementType.COLOR)){points=6;}
        else if(lineType.equals(LineType.COLUMN)&&elementType.equals(ElementType.COLOR)){points=5;}
        else if(lineType.equals(LineType.ROW)&&elementType.equals(ElementType.SHADE)){points=5;}
        else points=4;
    }

    public int calculateVictoryPoints(Player p) {
        if (elementType.equals(ElementType.COLOR)) {
            return calculateColors(p);
        } else {
            return calculateShades(p);
        }

    }

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
                    if (!(p.getPlayerScheme().getScheme()[i][j - 1].getDice() != null && p.getPlayerScheme().getScheme()[i][j].getDice() != null && p.getPlayerScheme().getScheme()[i][j - 1].getDice().getValue()!=p.getPlayerScheme().getScheme()[i][j].getDice().getValue()))
                        cond = false;
                }
                if (cond) cont++;
            }
            return cont * points;

        }
    }

    @Override
    public String toString() {
        return name;
    }
}


