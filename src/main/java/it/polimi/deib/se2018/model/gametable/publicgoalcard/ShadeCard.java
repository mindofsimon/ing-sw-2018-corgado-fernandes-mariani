package it.polimi.deib.se2018.model.gametable.publicgoalcard;

import it.polimi.deib.se2018.model.player.Player;

public class ShadeCard implements PublicGoalCard {

    private final ShadeType type;
    private final int points=2;
    private final String name;

    public ShadeCard(ShadeType t,String n){
        type=t;
        name=n;
    }

    public int calculateVictoryPoints(Player p){
        switch (type){
            case LIGHT:{
                return calculateShades(p,1,2);
            }
            case MEDIUM:{
                return calculateShades(p,3,4);
            }
            case DARK:{
                return calculateShades(p,5,6);
            }
            default:return 0;
        }
    }

    private int calculateShades(Player p, int val1, int val2){
        int cont=0;
        for(int i=0;i<p.getPlayerScheme().getROWS();i++){
            for(int j=0;j<p.getPlayerScheme().getCOLS();j++){
                if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null&&p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==val1){
                    cont=cont+countSet(p,i,j,val2);//COUNT SET SARA' UN INTERO
                }
            }
        }
        return cont*points;
    }

    private int countSet(Player p,int r,int c,int val){
        if ((r == 1 || r == 2) && (c == 1 || c == 2 || c == 3)) {
            return contA(p,r,c,val);
        } else if ((r == 0) && (c == 1 || c == 2 || c == 3)) {
            return contB(p,r,c,val);
        } else if ((r == 3) && (c == 1 || c == 2 || c == 3)) {
            return contC(p,r,c,val);
        } else if (c == 0 && (r == 1 || r == 2)) {
            return contD(p,r,c,val);
        } else if (c == 4 && (r == 1 || r == 2)) {
            return contE(p,r,c,val);
        } else if (c == 0 && r == 0) {
            return contF(p,r,c,val);
        } else if (c == 0 && r == 3) {
            return contG(p,r,c,val);
        } else if (c == 4 && r == 0) {
            return contH(p,r,c,val);
        } else if (c == 4 && r == 3) {
            return contI(p,r,c,val);
        }
        return 0;
    }

    private int contA(Player p,int r,int c,int val){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r - 1][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r - 1][c - 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r - 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r - 1][c +1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r - 1][c + 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c + 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c - 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c + 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c + 1].getDice().getValue()==val))cont++;
        return cont;
    }

    private int contB(Player p,int r,int c,int val){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c + 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r+1][c - 1].getDice() != null)&&(p.getPlayerScheme().getScheme()[r+1][c - 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r+1][c + 1].getDice() != null)&&(p.getPlayerScheme().getScheme()[r+1][c + 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue()==val))cont++;
        return cont;
    }

    private int contC(Player p,int r,int c,int val){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c + 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r-1][c - 1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r-1][c - 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r+1][c + 1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r+1][c + 1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r - 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue()==val))cont++;
        return cont;
    }

    private int contD(Player p,int r,int c,int val){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r - 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r - 1][c+1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r - 1][c+1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c+1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r + 1][c+1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c + 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue()==val))cont++;
        return cont;
    }

    private int contE(Player p,int r,int c,int val){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r - 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r - 1][c-1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r - 1][c-1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c-1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r + 1][c-1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue()==val))cont++;
        return cont;
    }

    private int contF(Player p,int r,int c,int val){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r + 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c+1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r + 1][c+1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c + 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue()==val))cont++;
        return cont;
    }

    private int contG(Player p,int r,int c,int val){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r - 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r - 1][c+1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r - 1][c+1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c + 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue()==val))cont++;
        return cont;
    }

    private int contH(Player p,int r,int c,int val){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r + 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r + 1][c-1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r + 1][c-1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue()==val))cont++;
        return cont;
    }

    private int contI(Player p,int r,int c,int val){
        int cont=0;
        if((p.getPlayerScheme().getScheme()[r - 1][c].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r - 1][c-1].getDice() != null) &&(p.getPlayerScheme().getScheme()[r - 1][c-1].getDice().getValue()==val))cont++;
        if((p.getPlayerScheme().getScheme()[r][c - 1].getDice()!=null)&&(p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue()==val))cont++;
        return cont;
    }

    @Override
    public String toString() {
        return name;
    }
}

