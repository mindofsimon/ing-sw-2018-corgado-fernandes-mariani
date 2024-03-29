package it.polimi.deib.se2018.server.model.gametable.publicgoalcard;

import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

/**
 * Diagonal card class: public goal card
 * @author Simone Mariani, Sirlan Fernandes
 */
public class DiagonalCard implements PublicGoalCard,Serializable {

    private final String name;
    //matrice che vede se quella casella è stata gia contata
    private boolean[][] contato= new boolean[4][5];

    /**
     * Constructor, initializes card activation message class
     * @param n name
     */
    public DiagonalCard(String n){
        name=n;
    }

    /**
     * Calculates total victory points
     * @param p player
     * @return total points counted
     */
    @Override
    public int calculateVictoryPoints(Player p) {
        int countTotal=0;
        int cont=0;
        for(DiceColor c: DiceColor.values()) {
            for (int i = 0; i < p.getPlayerScheme().getROWS(); i++) {
                for (int j = 0; j < p.getPlayerScheme().getCOLS(); j++) {
                    if (p.getPlayerScheme().getScheme()[i][j].getDice() != null && p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(c)) {
                        cont = countNearDiagonalDices(p, i, j, c);
                        countTotal=countTotal+cont;
                    }
                }
            }
        }
        return countTotal;
    }

    /**
     * If the box's row is 1, 2 or 3 and its column is 2, 3 or 4, returns contA method value
     * If the box's row is 1, 2 or 3 and its column is 1, returns contB method value
     * If the box's row is 1, 2 or 3 and its column is 5, returns contC method value
     * Else returns 0
     * @param p player
     * @param r box row
     * @param c box column
     * @param color dice color
     * @return
     */
    private int countNearDiagonalDices(Player p,int r, int c, DiceColor color){
        if ((r==0||r == 1 || r == 2) && (c == 1 || c == 2 || c == 3)) {
            return contA(p,r,c,color);
        } else if ((r == 0||r==1||r==2) && (c ==0)) {
            return contB(p,r,c,color);
        } else if (c == 4 && (r==0||r == 1 || r == 2)) {
            return contC(p,r,c,color);
        }
        return 0;
    }

    /**
     * Box's row is 1, 2 or 3 and its column is 2, 3 or 4. Checks if the boxes diagonal to the given box and the given box have the same color and counts them
     * @param p player
     * @param r box row
     * @param c box column
     * @param color dice color
     * @return number of boxes diagonal to the given box
     */
    private int contA(Player p,int r,int c,DiceColor color){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r + 1][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c - 1].getDice().getColor().equals(color))&&!contato[r+1][c-1]){
            if(!contato[r][c]){
                contato[r][c]=true;
                cont++;
            }

            contato[r+1][c-1]=true;
            cont++;


        }
        if((p.getPlayerScheme().getScheme()[r + 1][c + 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c + 1].getDice().getColor().equals(color))&&!contato[r+1][c+1]) {
            if(!contato[r][c]){
                contato[r][c]=true;
                cont++;
            }

            contato[r+1][c+1]=true;
            cont++;

        }
        return cont;
    }

    /**
     * Box's row is 1, 2 or 3 and its column is 1. Checks if the boxes diagonal to the given box and the given box have the same color and counts them
     * @param p player
     * @param r box row
     * @param c box column
     * @param color dice color
     * @return number of boxes diagonal to the given box
     */
    private int contB(Player p,int r,int c,DiceColor color){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r+1][c + 1].getDice() != null)&&(p.getPlayerScheme().getScheme()[r+1][c + 1].getDice().getColor().equals(color))&&!contato[r+1][c+1]){
            if(!contato[r][c]){
                contato[r][c]=true;
                cont++;
            }
            contato[r+1][c+1]=true;
            cont++;

        }
        return cont;
    }

    /**
     * Box's row is 1, 2 or 3 and its column is 5. Checks if the boxes diagonal to the given box and the given box have the same color and counts them
     * @param p player
     * @param r box row
     * @param c box column
     * @param color dice color
     * @return number of boxes diagonal to the given box
     */
    private int contC(Player p,int r,int c,DiceColor color){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r+1][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r+1][c - 1].getDice().getColor().equals(color))&&!contato[r+1][c-1]){
            if(!contato[r][c]){
                contato[r][c]=true;
                cont++;
            }

            contato[r+1][c-1]=true;
            cont++;

        }
        return cont;
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
