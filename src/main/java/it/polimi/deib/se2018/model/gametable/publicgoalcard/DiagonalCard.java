package it.polimi.deib.se2018.model.gametable.publicgoalcard;

import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.player.Player;

public class DiagonalCard implements PublicGoalCard {

    private final String name;

    public DiagonalCard(String n){
        name=n;
    }

    @Override
    public int calculateVictoryPoints(Player p) {
        int countTotal=0;
        int cont=0;
        int contNext=0;
        for(DiceColor c: DiceColor.values()) {
            for (int i = 0; i < p.getPlayerScheme().getROWS()-1; i++) {
                for (int j = 0; j < p.getPlayerScheme().getCOLS(); j++) {
                    if (p.getPlayerScheme().getScheme()[i][j].getDice() != null && p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(c)) {
                        cont = countNearDiagonalDices(p, i, j, c);
                        if(cont>0&&j==4){
                            contNext= countNearDiagonalDices(p, i+1, j-1, c);
                        }
                        if(cont>0&&j==0){
                            contNext= countNearDiagonalDices(p, i+1, j+1, c);
                        }
                        if(cont>0&&j!=0&&j!=4){
                            contNext=countNearDiagonalDices(p, i+1, j+1, c);
                            contNext=contNext+countNearDiagonalDices(p, i+1, j-1, c);
                        }
                        if(contNext==0&&cont!=0) cont++;
                        countTotal=countTotal+cont;
                    }
                }
            }
        }
        return countTotal;
    }

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

    private int contA(Player p,int r,int c,DiceColor color){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r + 1][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c - 1].getDice().getColor().equals(color)))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c + 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c + 1].getDice().getColor().equals(color)))cont++;
        return cont;
    }

    private int contB(Player p,int r,int c,DiceColor color){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r+1][c + 1].getDice() != null)&&(p.getPlayerScheme().getScheme()[r+1][c + 1].getDice().getColor().equals(color)))cont++;
        return cont;
    }

    private int contC(Player p,int r,int c,DiceColor color){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r+1][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r+1][c - 1].getDice().getColor().equals(color)))cont++;
        return cont;
    }


    @Override
    public String toString() {
        return name;
    }
}
