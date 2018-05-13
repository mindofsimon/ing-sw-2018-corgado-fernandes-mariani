package it.polimi.deib.se2018.model.gametable.publicgoalcard;

import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.player.Player;

public class VarietyCard implements PublicGoalCard {

    private final ElementType elementType;
    private final int points;
    private final String name;

    public VarietyCard(ElementType et,String n){
        elementType=et;
        name=n;
        if(elementType.equals(ElementType.COLOR))points=4;
        else points=5;
    }

    public int calculateVictoryPoints(Player p){
        if(elementType.equals(ElementType.COLOR)) return calculateColorsVariety(p);
        else return calculateShadesVariety(p);
    }

    private int calculateColorsVariety(Player p){//LO SWITCH NEL CICLO DA PROBLEMI
        int varietyCounter=0;
        int contR=0;
        int contY=0;
        int contB=0;
        int contV=0;
        int contG=0;
        boolean stop=false;
        for(int i=0;i<p.getPlayerScheme().getROWS();i++) {
            for (int j = 0; j < p.getPlayerScheme().getCOLS(); j++) {
                if (p.getPlayerScheme().getScheme()[i][j].getDice() != null) {
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(DiceColor.BLU))contB++;
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(DiceColor.RED))contR++;
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(DiceColor.YELLOW))contY++;
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(DiceColor.GREEN))contG++;
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(DiceColor.VIOLET))contV++;

                }
            }
        }
        if(contB==0||contG==0||contR==0||contV==0||contY==0) stop=true;
        while(!stop){
            contB--;
            contG--;
            contR--;
            contV--;
            contY--;
            varietyCounter++;
            if(contB==0||contG==0||contR==0||contV==0||contY==0) stop=true;
        }
        return varietyCounter*points;
    }

    private int calculateShadesVariety(Player p){//LO SWITCH NEL CICLO DA PROBLEMI
        int varietyCounter=0;
        int cont1=0;
        int cont2=0;
        int cont3=0;
        int cont4=0;
        int cont5=0;
        int cont6=0;
        boolean stop=false;
        for(int i=0;i<p.getPlayerScheme().getROWS();i++) {
            for (int j = 0; j < p.getPlayerScheme().getCOLS(); j++) {
                if (p.getPlayerScheme().getScheme()[i][j].getDice() != null) {
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==1)cont1++;
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==2)cont2++;
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==3)cont3++;
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==4)cont4++;
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==5)cont5++;
                    if (p.getPlayerScheme().getScheme()[i][j].getDice().getValue()==6)cont6++;
                }
            }
        }
        if(cont1==0||cont2==0||cont3==0||cont4==0||cont5==0||cont6==0) stop=true;
        while(!stop){
            cont1--;
            cont2--;
            cont3--;
            cont4--;
            cont5--;
            cont6--;
            varietyCounter++;
            if(cont1==0||cont2==0||cont3==0||cont4==0||cont5==0||cont6==0) stop=true;
        }
        return varietyCounter*points;
    }

    @Override
    public String toString() {
        return name;
    }
}

