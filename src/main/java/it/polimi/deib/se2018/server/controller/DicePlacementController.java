package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.schemecard.ColoredBox;

public class DicePlacementController {


    public boolean isRowColOk(int r, int c){
        return((r>=0&&r<=3)&&(c>=0&&c<=4));
    }


    //Controls if there are no dices placed by a player
    public boolean firstDice(Player p){
        return (dicesPlaced(p)==0);
    }

    //Returns number of dices placed by a player
    public int dicesPlaced(Player p){
        int cont=0;
        for (int i = 0; i<p.getPlayerScheme().getROWS(); i++){
            for (int j=0;j<p.getPlayerScheme().getCOLS();j++){
                if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null) cont++;
            }
        }
        return cont;
    }

    //Controls if the choosen box is compatible with the selected dice
    public boolean isBoxOk(Player p, int r, int c, Dice d){
        return (p.getPlayerScheme().getScheme()[r][c].getDice()==null
                && ((p.getPlayerScheme().getScheme()[r][c].getColor()==null&&p.getPlayerScheme().getScheme()[r][c].getValue()==0)||p.getPlayerScheme().getScheme()[r][c].getValue()==d.getValue()
                ||(p.getPlayerScheme().getScheme()[r][c]instanceof ColoredBox &&p.getPlayerScheme().getScheme()[r][c].getColor().equals(d.getColor()))));

    }

    //Controls if the selected dice is not placed near similar dices (similar by value and color)
    public boolean similarDicesOk(Player p, int r, int c, Dice d){
        if ((r == 1 || r == 2) && (c == 1 || c == 2 || c == 3)) {
            return similarDicesControl1(p,r,c,d);
        }
        else if((r==0)&&(c==1||c==2||c==3)) {
            return similarDicesControl2(p,r,c,d);
        }
        else if((r==3)&&(c==1||c==2||c==3)) {
            return similarDicesControl3(p,r,c,d);
        }
        else if (c==0&&(r==1||r==2)) {
            return similarDicesControl4(p,r,c,d);
        }
        else if(c==4&&(r==1||r==2)){
            return similarDicesControl5(p,r,c,d);
        }
        else if(c==0&&r==0){
            return similarDicesControl6(p,r,c,d);
        }
        else if(c==0&&r==3){
            return similarDicesControl7(p,r,c,d);
        }
        else if(c==4&&r==0){
            return similarDicesControl8(p,r,c,d);
        }
        else if(c==4&&r==3){
            return similarDicesControl9(p,r,c,d);
        }
        return false;
    }

    //This methods are used to reduce similarDicesOk() method complexity
    private boolean similarDicesControl1(Player p, int r, int c, Dice d){
        return( (p.getPlayerScheme().getScheme()[r-1][c].getDice()==null||p.getPlayerScheme().getScheme()[r-1][c].getDice().getValue()!=d.getValue())&&
                (p.getPlayerScheme().getScheme()[r-1][c].getDice()==null||p.getPlayerScheme().getScheme()[r-1][c].getDice().getColor()!=d.getColor())&&
                (p.getPlayerScheme().getScheme()[r+1][c].getDice()==null||p.getPlayerScheme().getScheme()[r+1][c].getDice().getValue()!=d.getValue())&&
                (p.getPlayerScheme().getScheme()[r+1][c].getDice()==null||p.getPlayerScheme().getScheme()[r+1][c].getDice().getColor()!=d.getColor())&&
                (p.getPlayerScheme().getScheme()[r][c-1].getDice()==null||p.getPlayerScheme().getScheme()[r][c-1].getDice().getValue()!=d.getValue())&&
                (p.getPlayerScheme().getScheme()[r][c-1].getDice()==null||p.getPlayerScheme().getScheme()[r][c-1].getDice().getColor()!=d.getColor())&&
                (p.getPlayerScheme().getScheme()[r][c+1].getDice()==null||p.getPlayerScheme().getScheme()[r][c+1].getDice().getValue()!=d.getValue())&&
                (p.getPlayerScheme().getScheme()[r][c+1].getDice()==null||p.getPlayerScheme().getScheme()[r][c+1].getDice().getColor()!=d.getColor()));

    }

    private boolean similarDicesControl2(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r][c - 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c - 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c - 1].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c + 1].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getColor() != d.getColor()));

    }

    private boolean similarDicesControl3(Player p, int r, int c, Dice d){
        return  ((p.getPlayerScheme().getScheme()[r][c - 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c - 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c - 1].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c + 1].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getColor() != d.getColor()));


    }

    private boolean similarDicesControl4(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c + 1].getDice().getColor() != d.getColor()));

    }

    private boolean similarDicesControl5(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c - 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c - 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c - 1].getDice().getColor() != d.getColor()));

    }

    private boolean similarDicesControl6(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c+1].getDice() == null || p.getPlayerScheme().getScheme()[r][c+1].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c+1].getDice() == null || p.getPlayerScheme().getScheme()[r][c+1].getDice().getColor() != d.getColor()));

    }

    private boolean similarDicesControl7(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c+1].getDice() == null || p.getPlayerScheme().getScheme()[r][c+1].getDice().getValue() != d.getValue())&&
                (p.getPlayerScheme().getScheme()[r][c+1].getDice() == null || p.getPlayerScheme().getScheme()[r][c+1].getDice().getColor() != d.getColor()));

    }

    private boolean similarDicesControl8(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c-1].getDice() == null || p.getPlayerScheme().getScheme()[r][c-1].getDice().getValue() != d.getValue())&&
                (p.getPlayerScheme().getScheme()[r][c-1].getDice() == null || p.getPlayerScheme().getScheme()[r][c-1].getDice().getColor() != d.getColor()));

    }

    private boolean similarDicesControl9(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c-1].getDice() == null || p.getPlayerScheme().getScheme()[r][c-1].getDice().getValue() != d.getValue())&&
                (p.getPlayerScheme().getScheme()[r][c-1].getDice() == null || p.getPlayerScheme().getScheme()[r][c-1].getDice().getColor() != d.getColor()));

    }


    //Controls if there are already placed dices near the box we want to place the dice in
    public boolean alreadyPlacedDicesOk(Player p, int r, int c) {
        if ((r == 1 || r == 2) && (c == 1 || c == 2 || c == 3)) {
            return alreadyPlacedDicesControl1(p, r, c);
        } else if ((r == 0) && (c == 1 || c == 2 || c == 3)) {
            return alreadyPlacedDicesControl2(p, r, c);
        } else if ((r == 3) && (c == 1 || c == 2 || c == 3)) {
            return alreadyPlacedDicesControl3(p, r, c);
        } else if (c == 0 && (r == 1 || r == 2)) {
            return alreadyPlacedDicesControl4(p, r, c);
        } else if (c == 4 && (r == 1 || r == 2)) {
            return alreadyPlacedDicesControl5(p, r, c);
        } else if (c == 0 && r == 0) {
            return alreadyPlacedDicesControl6(p, r, c);
        } else if (c == 0 && r == 3) {
            return alreadyPlacedDicesControl7(p, r, c);
        } else if (c == 4 && r == 0) {
            return alreadyPlacedDicesControl8(p, r, c);
        } else if (c == 4 && r == 3) {
            return alreadyPlacedDicesControl9(p, r, c);
        }
        return false;
    }

    //This methods are used to reduce alreadyPlacedDicesOk() method complexity
    private boolean alreadyPlacedDicesControl1(Player p, int r, int c){
        return ((p.getPlayerScheme().getScheme()[r - 1][c - 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r - 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r - 1][c + 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c - 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c - 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c + 1].getDice() != null));
    }

    private boolean alreadyPlacedDicesControl2(Player p, int r, int c){
        return ((p.getPlayerScheme().getScheme()[r][c - 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r+1][c - 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r+1][c + 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() != null));
    }

    private boolean alreadyPlacedDicesControl3(Player p, int r, int c){
        return ((p.getPlayerScheme().getScheme()[r][c - 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r-1][c - 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r-1][c + 1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r - 1][c].getDice() != null));
    }

    private boolean alreadyPlacedDicesControl4(Player p, int r, int c){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r - 1][c+1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c+1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() != null));
    }

    private boolean alreadyPlacedDicesControl5(Player p, int r, int c){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r - 1][c-1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c-1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c - 1].getDice() != null));
    }

    private boolean alreadyPlacedDicesControl6(Player p, int r, int c){
        return ((p.getPlayerScheme().getScheme()[r + 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c+1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() != null));
    }

    private boolean alreadyPlacedDicesControl7(Player p, int r, int c){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r - 1][c+1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() != null));
    }

    private boolean alreadyPlacedDicesControl8(Player p, int r, int c){
        return ((p.getPlayerScheme().getScheme()[r + 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r + 1][c-1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c - 1].getDice() != null));
    }

    private boolean alreadyPlacedDicesControl9(Player p, int r, int c){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r - 1][c-1].getDice() != null) ||
                (p.getPlayerScheme().getScheme()[r][c - 1].getDice() != null));
    }



}