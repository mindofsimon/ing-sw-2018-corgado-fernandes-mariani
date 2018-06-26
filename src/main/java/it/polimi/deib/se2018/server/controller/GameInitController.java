package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.gametable.publicgoalcard.*;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PrivateGoalCard;
import it.polimi.deib.se2018.server.model.player.schemecard.Box;
import it.polimi.deib.se2018.server.model.player.schemecard.ColoredBox;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;
import it.polimi.deib.se2018.server.model.player.schemecard.ValueBox;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameInitController {
    private Model model;

    public GameInitController(Model m){
        model=m;
    }

    public void init()throws RemoteException{
        loadSchemeCards();
        loadPrivateGoalCards();
        loadPublicGoalCards();
    }

    public boolean isSinglePlayer(){
        if(model.getPlayerList().size()==1){
            return true;
        }
        return false;

    }

    public void initFavorMarkers(Player p) throws RemoteException{
        //Never used in Single-Player
        if(model.getPlayerList().size()==1) {
            p.setFavorMarkers(0);
        }
        if(model.getPlayerList().size()>1){
            p.setFavorMarkers((p.getPlayerScheme().getDifficulty()));
        }
    }


    private void loadPrivateGoalCards()throws RemoteException {
        ArrayList<PrivateGoalCard> privateGoalCards=createPrivateGoalCards();
        for(int i=0; i<model.getPlayerList().size();i++){
            int index = (int) ((Math.random() * privateGoalCards.size()));
            model.getPlayerList().get(i).setPrivateGoalCard(privateGoalCards.remove(index));
        }
    }

    private void loadPublicGoalCards()throws RemoteException{
        ArrayList<PublicGoalCard> publicGoalCards=createPublicGoalCards();
        if(model.getPlayerList().size()==1){
            for(int i=0;i<4;i++){
                int index = (int) ((Math.random() * publicGoalCards.size()));
                model.addPublicGoalCard(publicGoalCards.remove(index));
            }
        }
        if(model.getPlayerList().size()>1){
            for(int i=0;i<3;i++){
                int index = (int) ((Math.random() * publicGoalCards.size()));
                model.addPublicGoalCard(publicGoalCards.remove(index));
            }
        }

    }

    private void loadSchemeCards(){
        ArrayList<SchemeCard>schemeCards=createSchemeCards();
        for(int i=0;i<model.getPlayerList().size();i++){
            for(int cont=0;cont<2;cont++) {
                int index = (int) ((Math.random() * schemeCards.size()));
                model.getPlayerList().get(i).setOfferedSchemeCards(schemeCards.remove(index));
            }
        }
    }

    private ArrayList<PrivateGoalCard> createPrivateGoalCards(){
        ArrayList<PrivateGoalCard> privateGoalCards=new ArrayList<PrivateGoalCard>();
        privateGoalCards.add(new PrivateGoalCard(DiceColor.BLUE,"Private Goal Blu"));
        privateGoalCards.add(new PrivateGoalCard(DiceColor.GREEN,"Private Goal Green"));
        privateGoalCards.add(new PrivateGoalCard(DiceColor.RED,"Private Goal Red"));
        privateGoalCards.add(new PrivateGoalCard(DiceColor.VIOLET,"Private Goal Violet"));
        privateGoalCards.add(new PrivateGoalCard(DiceColor.YELLOW,"Private Goal Yellow"));
        return privateGoalCards;
    }

    private ArrayList<PublicGoalCard> createPublicGoalCards(){
        ArrayList<PublicGoalCard> publicGoalCards=new ArrayList<PublicGoalCard>();
        publicGoalCards.add(new RowAndColCard(LineType.ROW,ElementType.COLOR,"Colored Rows"));
        publicGoalCards.add(new RowAndColCard(LineType.COLUMN,ElementType.COLOR,"Colored Columns"));
        publicGoalCards.add(new RowAndColCard(LineType.ROW,ElementType.SHADE,"Shades Rows"));
        publicGoalCards.add(new RowAndColCard(LineType.COLUMN,ElementType.SHADE,"Shades Coulmns"));
        publicGoalCards.add(new ShadeCard(ShadeType.LIGHT,"Light Shades"));
        publicGoalCards.add(new ShadeCard(ShadeType.MEDIUM,"Medium Shades"));
        publicGoalCards.add(new ShadeCard(ShadeType.DARK,"Dark Shades"));
        publicGoalCards.add(new VarietyCard(ElementType.COLOR,"Color Variety"));
        publicGoalCards.add(new VarietyCard(ElementType.SHADE,"Shades Variety"));
        publicGoalCards.add(new DiagonalCard("Colored Diagonals"));
        return publicGoalCards;
    }

    //Da qui in avanti è tutto creazione dei 24 schemi...
    private ArrayList<SchemeCard> createSchemeCards(){
        ArrayList<SchemeCard>schemeCards=new ArrayList<SchemeCard>();

        //Creo 24 tabelle perchè il clone su una sola sembra non funzionare...
        Box[][] table1=new Box[4][5];
        Box[][] table2=new Box[4][5];
        Box[][] table3=new Box[4][5];
        Box[][] table4=new Box[4][5];
        Box[][] table5=new Box[4][5];
        Box[][] table6=new Box[4][5];
        Box[][] table7=new Box[4][5];
        Box[][] table8=new Box[4][5];
        Box[][] table9=new Box[4][5];
        Box[][] table10=new Box[4][5];
        Box[][] table11=new Box[4][5];
        Box[][] table12=new Box[4][5];
        Box[][] table13=new Box[4][5];
        Box[][] table14=new Box[4][5];
        Box[][] table15=new Box[4][5];
        Box[][] table16=new Box[4][5];
        Box[][] table17=new Box[4][5];
        Box[][] table18=new Box[4][5];
        Box[][] table19=new Box[4][5];
        Box[][] table20=new Box[4][5];
        Box[][] table21=new Box[4][5];
        Box[][] table22=new Box[4][5];
        Box[][] table23=new Box[4][5];
        Box[][] table24=new Box[4][5];






        table1[0][0] = new ColoredBox(DiceColor.YELLOW);
        table1[0][1] = new ColoredBox(DiceColor.BLUE);
        table1[0][2] = new Box();
        table1[0][3] = new Box();
        table1[0][4] = new ValueBox(1);
        table1[1][0] = new ColoredBox(DiceColor.GREEN);
        table1[1][1] = new Box();
        table1[1][2] = new ValueBox(5);
        table1[1][3] = new Box();
        table1[1][4] = new ValueBox(4);
        table1[2][0] = new ValueBox(3);
        table1[2][1] = new Box();
        table1[2][2] = new ColoredBox(DiceColor.RED);
        table1[2][3] = new Box();
        table1[2][4] = new ColoredBox(DiceColor.GREEN);
        table1[3][0] = new ValueBox(2);
        table1[3][1] = new Box();
        table1[3][2] = new Box();
        table1[3][3] = new ColoredBox(DiceColor.BLUE);
        table1[3][4] = new ColoredBox(DiceColor.YELLOW);
        SchemeCard card1=new SchemeCard("Kaleidoscopic Dream",4,table1);

        table2[0][0] = new ColoredBox(DiceColor.VIOLET);
        table2[0][1] = new ValueBox(6);
        table2[0][2] = new Box();
        table2[0][3] = new Box();
        table2[0][4] = new ValueBox(3);
        table2[1][0] = new ValueBox(5);
        table2[1][1] = new ColoredBox(DiceColor.VIOLET);
        table2[1][2] = new ValueBox(3);
        table2[1][3] = new Box();
        table2[1][4] = new Box();
        table2[2][0] = new Box();
        table2[2][1] = new ValueBox(2);
        table2[2][2] = new ColoredBox(DiceColor.VIOLET);
        table2[2][3] = new ValueBox(1);
        table2[2][4] = new Box();
        table2[3][0] = new Box();
        table2[3][1] = new ValueBox(1);
        table2[3][2] = new ValueBox(5);
        table2[3][3] = new ColoredBox(DiceColor.VIOLET);
        table2[3][4] = new ValueBox(4);
        SchemeCard card2=new SchemeCard("Firmitas",5,table2);

        card1.setRetro(card2);
        card1.setRetro(card1);

        table3[0][0] = new Box();
        table3[0][1] = new ValueBox(4);
        table3[0][2] = new Box();
        table3[0][3] = new ColoredBox(DiceColor.YELLOW);
        table3[0][4] = new ValueBox(6);
        table3[1][0] = new ColoredBox(DiceColor.RED);
        table3[1][1] = new Box();
        table3[1][2] = new ValueBox(2);
        table3[1][3] = new Box();
        table3[1][4] = new Box();
        table3[2][0] = new Box();
        table3[2][1] = new Box();
        table3[2][2] = new ColoredBox(DiceColor.RED);
        table3[2][3] = new ColoredBox(DiceColor.VIOLET);
        table3[2][4] = new ValueBox(1);
        table3[3][0] = new ColoredBox(DiceColor.BLUE);
        table3[3][1] = new ColoredBox(DiceColor.YELLOW);
        table3[3][2] = new Box();
        table3[3][3] = new Box();
        table3[3][4] = new Box();
        SchemeCard card3=new SchemeCard("Fractal Drops",3,table3);

        table4[0][0] = new Box();
        table4[0][1] = new Box();
        table4[0][2] = new Box();
        table4[0][3] = new ColoredBox(DiceColor.RED);
        table4[0][4] = new ValueBox(5);
        table4[1][0] = new Box();
        table4[1][1] = new Box();
        table4[1][2] = new ColoredBox(DiceColor.VIOLET);
        table4[1][3] = new ValueBox(4);
        table4[1][4] = new ColoredBox(DiceColor.BLUE);
        table4[2][0] = new Box();
        table4[2][1] = new ColoredBox(DiceColor.BLUE);
        table4[2][2] = new ValueBox(3);
        table4[2][3] = new ColoredBox(DiceColor.YELLOW);
        table4[2][4] = new ValueBox(6);
        table4[3][0] = new ColoredBox(DiceColor.YELLOW);
        table4[3][1] = new ValueBox(2);
        table4[3][2] = new ColoredBox(DiceColor.GREEN);
        table4[3][3] = new ValueBox(1);
        table4[3][4] = new ColoredBox(DiceColor.RED);
        SchemeCard card4=new SchemeCard("Ripples of Life",5,table4);

        card3.setRetro(card4);
        card4.setRetro(card3);

        table5[0][0] = new Box();
        table5[0][1] = new Box();
        table5[0][2] = new ValueBox(1);
        table5[0][3] = new Box();
        table5[0][4] = new Box();
        table5[1][0] = new ValueBox(1);
        table5[1][1] = new ColoredBox(DiceColor.GREEN);
        table5[1][2] = new ValueBox(3);
        table5[1][3] = new ColoredBox(DiceColor.BLUE);
        table5[1][4] = new ValueBox(2);
        table5[2][0] = new ColoredBox(DiceColor.BLUE);
        table5[2][1] = new ValueBox(5);
        table5[2][2] = new ValueBox(4);
        table5[2][3] = new ValueBox(6);
        table5[2][4] = new ColoredBox(DiceColor.GREEN);
        table5[3][0] = new Box();
        table5[3][1] = new ColoredBox(DiceColor.BLUE);
        table5[3][2] = new ValueBox(5);
        table5[3][3] = new ColoredBox(DiceColor.GREEN);
        table5[3][4] = new Box();
        SchemeCard card5=new SchemeCard("Lux Mundi",6,table5);

        table6[0][0] = new Box();
        table6[0][1] = new ValueBox(1);
        table6[0][2] = new ColoredBox(DiceColor.GREEN);
        table6[0][3] = new ColoredBox(DiceColor.VIOLET);
        table6[0][4] = new ValueBox(4);
        table6[1][0] = new ValueBox(6);
        table6[1][1] = new ColoredBox(DiceColor.VIOLET);
        table6[1][2] = new ValueBox(2);
        table6[1][3] = new ValueBox(5);
        table6[1][4] = new ColoredBox(DiceColor.GREEN);
        table6[2][0] = new ValueBox(1);
        table6[2][1] = new ColoredBox(DiceColor.GREEN);
        table6[2][2] = new ValueBox(5);
        table6[2][3] = new ValueBox(3);
        table6[2][4] = new ColoredBox(DiceColor.VIOLET);
        table6[3][0] = new Box();
        table6[3][1] = new Box();
        table6[3][2] = new Box();
        table6[3][3] = new Box();
        table6[3][4] = new Box();
        SchemeCard card6=new SchemeCard("Lux Astram",5,table6);

        card5.setRetro(card6);
        card6.setRetro(card5);

        table7[0][0] = new ValueBox(1);
        table7[0][1] = new Box();
        table7[0][2] = new ValueBox(3);
        table7[0][3] = new ColoredBox(DiceColor.BLUE);
        table7[0][4] = new Box();
        table7[1][0] = new Box();
        table7[1][1] = new ValueBox(2);
        table7[1][2] = new ColoredBox(DiceColor.BLUE);
        table7[1][3] = new Box();
        table7[1][4] = new Box();
        table7[2][0] = new ValueBox(6);
        table7[2][1] = new ColoredBox(DiceColor.BLUE);
        table7[2][2] = new Box();
        table7[2][3] = new ValueBox(4);
        table7[2][4] = new Box();
        table7[3][0] = new ColoredBox(DiceColor.BLUE);
        table7[3][1] = new ValueBox(5);
        table7[3][2] = new ValueBox(2);
        table7[3][3] = new Box();
        table7[3][4] = new ValueBox(1);
        SchemeCard card7=new SchemeCard("Gravitas",5,table7);

        table8[0][0] = new ValueBox(6);
        table8[0][1] = new ColoredBox(DiceColor.BLUE);
        table8[0][2] = new Box();
        table8[0][3] = new Box();
        table8[0][4] = new ValueBox(1);
        table8[1][0] = new Box();
        table8[1][1] = new ValueBox(5);
        table8[1][2] = new ColoredBox(DiceColor.BLUE);
        table8[1][3] = new Box();
        table8[1][4] = new Box();
        table8[2][0] = new ValueBox(4);
        table8[2][1] = new ColoredBox(DiceColor.RED);
        table8[2][2] = new ValueBox(2);
        table8[2][3] = new ColoredBox(DiceColor.BLUE);
        table8[2][4] = new Box();
        table8[3][0] = new ColoredBox(DiceColor.GREEN);
        table8[3][1] = new ValueBox(6);
        table8[3][2] = new ColoredBox(DiceColor.YELLOW);
        table8[3][3] = new ValueBox(3);
        table8[3][4] = new ColoredBox(DiceColor.VIOLET);
        SchemeCard card8=new SchemeCard("Water of Life",6,table8);

        card7.setRetro(card8);
        card8.setRetro(card7);

        table9[0][0] = new Box();
        table9[0][1] = new ColoredBox(DiceColor.BLUE);
        table9[0][2] = new ValueBox(2);
        table9[0][3] = new Box();
        table9[0][4] = new ColoredBox(DiceColor.YELLOW);
        table9[1][0] = new Box();
        table9[1][1] = new ValueBox(4);
        table9[1][2] = new Box();
        table9[1][3] = new ColoredBox(DiceColor.RED);
        table9[1][4] = new Box();
        table9[2][0] = new Box();
        table9[2][1] = new Box();
        table9[2][2] = new ValueBox(5);
        table9[2][3] = new ColoredBox(DiceColor.YELLOW);
        table9[2][4] = new Box();
        table9[3][0] = new ColoredBox(DiceColor.GREEN);
        table9[3][1] = new ValueBox(3);
        table9[3][2] = new Box();
        table9[3][3] = new Box();
        table9[3][4] = new ColoredBox(DiceColor.VIOLET);
        SchemeCard card9=new SchemeCard("Sun Catcher",3,table9);

        table10[0][0] = new ValueBox(6);
        table10[0][1] = new ColoredBox(DiceColor.VIOLET);
        table10[0][2] = new Box();
        table10[0][3] = new Box();
        table10[0][4] = new ValueBox(5);
        table10[1][0] = new ValueBox(5);
        table10[1][1] = new Box();
        table10[1][2] = new ColoredBox(DiceColor.VIOLET);
        table10[1][3] = new Box();
        table10[1][4] = new Box();
        table10[2][0] = new ColoredBox(DiceColor.RED);
        table10[2][1] = new ValueBox(6);
        table10[2][2] = new Box();
        table10[2][3] = new ColoredBox(DiceColor.VIOLET);
        table10[2][4] = new Box();
        table10[3][0] = new ColoredBox(DiceColor.YELLOW);
        table10[3][1] = new ColoredBox(DiceColor.RED);
        table10[3][2] = new ValueBox(5);
        table10[3][3] = new ValueBox(4);
        table10[3][4] = new ValueBox(3);
        SchemeCard card10=new SchemeCard("Shadow Thief",5,table10);

        card9.setRetro(card10);
        card10.setRetro(card9);

        table11[0][0] = new ValueBox(5);
        table11[0][1] = new Box();
        table11[0][2] = new ColoredBox(DiceColor.GREEN);
        table11[0][3] = new ColoredBox(DiceColor.BLUE);
        table11[0][4] = new ColoredBox(DiceColor.VIOLET);
        table11[1][0] = new ValueBox(2);
        table11[1][1] = new ColoredBox(DiceColor.VIOLET);
        table11[1][2] = new Box();
        table11[1][3] = new Box();
        table11[1][4] = new Box();
        table11[2][0] = new ColoredBox(DiceColor.YELLOW);
        table11[2][1] = new Box();
        table11[2][2] = new ValueBox(6);
        table11[2][3] = new Box();
        table11[2][4] = new ColoredBox(DiceColor.VIOLET);
        table11[3][0] = new ValueBox(1);
        table11[3][1] = new Box();
        table11[3][2] = new Box();
        table11[3][3] = new ColoredBox(DiceColor.GREEN);
        table11[3][4] = new ValueBox(4);
        SchemeCard card11=new SchemeCard("Aurorae Magnificus",5,table11);

        table12[0][0] = new ColoredBox(DiceColor.RED);
        table12[0][1] = new Box();
        table12[0][2] = new ColoredBox(DiceColor.BLUE);
        table12[0][3] = new Box();
        table12[0][4] = new ColoredBox(DiceColor.YELLOW);
        table12[1][0] = new ValueBox(4);
        table12[1][1] = new ColoredBox(DiceColor.VIOLET);
        table12[1][2] = new ValueBox(3);
        table12[1][3] = new ColoredBox(DiceColor.GREEN);
        table12[1][4] = new ValueBox(2);
        table12[2][0] = new Box();
        table12[2][1] = new ValueBox(1);
        table12[2][2] = new Box();
        table12[2][3] = new ValueBox(5);
        table12[2][4] = new Box();
        table12[3][0] = new Box();
        table12[3][1] = new Box();
        table12[3][2] = new ValueBox(6);
        table12[3][3] = new Box();
        table12[3][4] = new Box();
        SchemeCard card12=new SchemeCard("Aurora Sagradis",4,table12);

        card11.setRetro(card12);
        card12.setRetro(card11);

        table13[0][0] = new ValueBox(2);
        table13[0][1] = new Box();
        table13[0][2] = new ValueBox(5);
        table13[0][3] = new Box();
        table13[0][4] = new ValueBox(1);
        table13[1][0] = new ColoredBox(DiceColor.YELLOW);
        table13[1][1] = new ValueBox(6);
        table13[1][2] = new ColoredBox(DiceColor.VIOLET);
        table13[1][3] = new ValueBox(2);
        table13[1][4] = new ColoredBox(DiceColor.RED);
        table13[2][0] = new Box();
        table13[2][1] = new ColoredBox(DiceColor.BLUE);
        table13[2][2] = new ValueBox(4);
        table13[2][3] = new ColoredBox(DiceColor.GREEN);
        table13[2][4] = new Box();
        table13[3][0] = new Box();
        table13[3][1] = new ValueBox(3);
        table13[3][2] = new Box();
        table13[3][3] = new ValueBox(5);
        table13[3][4] = new Box();
        SchemeCard card13=new SchemeCard("Symphony of Light",6,table13);

        table14[0][0] = new ValueBox(4);
        table14[0][1] = new Box();
        table14[0][2] = new ValueBox(2);
        table14[0][3] = new ValueBox(5);
        table14[0][4] = new ColoredBox(DiceColor.GREEN);
        table14[1][0] = new ColoredBox(DiceColor.GREEN);
        table14[1][1] = new Box();
        table14[1][2] = new Box();
        table14[1][3] = new ValueBox(6);
        table14[1][4] = new ColoredBox(DiceColor.GREEN);
        table14[2][0] = new ValueBox(2);
        table14[2][1] = new Box();
        table14[2][2] = new ValueBox(3);
        table14[2][3] = new ColoredBox(DiceColor.GREEN);
        table14[2][4] = new ValueBox(4);
        table14[3][0] = new ValueBox(5);
        table14[3][1] = new ColoredBox(DiceColor.GREEN);
        table14[3][2] = new ValueBox(1);
        table14[3][3] = new Box();
        table14[3][4] = new Box();
        SchemeCard card14=new SchemeCard("Virtus",5,table14);

        card13.setRetro(card14);
        card14.setRetro(card13);

        table15[0][0] = new ValueBox(3);
        table15[0][1] = new ValueBox(4);
        table15[0][2] = new ValueBox(1);
        table15[0][3] = new ValueBox(5);
        table15[0][4] = new Box();
        table15[1][0] = new Box();
        table15[1][1] = new ValueBox(6);
        table15[1][2] = new ValueBox(2);
        table15[1][3] = new Box();
        table15[1][4] = new ColoredBox(DiceColor.YELLOW);
        table15[2][0] = new Box();
        table15[2][1] = new Box();
        table15[2][2] = new Box();
        table15[2][3] = new ColoredBox(DiceColor.YELLOW);
        table15[2][4] = new ColoredBox(DiceColor.RED);
        table15[3][0] = new ValueBox(5);
        table15[3][1] = new Box();
        table15[3][2] = new ColoredBox(DiceColor.YELLOW);
        table15[3][3] = new ColoredBox(DiceColor.RED);
        table15[3][4] = new ValueBox(6);
        SchemeCard card15=new SchemeCard("Firelight",5,table15);

        table16[0][0] = new ValueBox(1);
        table16[0][1] = new ColoredBox(DiceColor.VIOLET);
        table16[0][2] = new ColoredBox(DiceColor.YELLOW);
        table16[0][3] = new Box();
        table16[0][4] = new ValueBox(4);
        table16[1][0] = new ColoredBox(DiceColor.VIOLET);
        table16[1][1] = new ColoredBox(DiceColor.YELLOW);
        table16[1][2] = new Box();
        table16[1][3] = new Box();
        table16[1][4] = new ValueBox(6);
        table16[2][0] = new ColoredBox(DiceColor.YELLOW);
        table16[2][1] = new Box();
        table16[2][2] = new Box();
        table16[2][3] = new ValueBox(5);
        table16[2][4] = new ValueBox(3);
        table16[3][0] = new Box();
        table16[3][1] = new ValueBox(5);
        table16[3][2] = new ValueBox(4);
        table16[3][3] = new ValueBox(2);
        table16[3][4] = new ValueBox(1);
        SchemeCard card16=new SchemeCard("Sun's Glory",6,table16);

        card15.setRetro(card16);
        card16.setRetro(card15);

        table17[0][0] = new Box();
        table17[0][1] = new Box();
        table17[0][2] = new ValueBox(6);
        table17[0][3] = new Box();
        table17[0][4] = new Box();
        table17[1][0] = new Box();
        table17[1][1] = new ValueBox(5);
        table17[1][2] = new ColoredBox(DiceColor.BLUE);
        table17[1][3] = new ValueBox(4);
        table17[1][4] = new Box();
        table17[2][0] = new ValueBox(3);
        table17[2][1] = new ColoredBox(DiceColor.GREEN);
        table17[2][2] = new ColoredBox(DiceColor.YELLOW);
        table17[2][3] = new ColoredBox(DiceColor.VIOLET);
        table17[2][4] = new ValueBox(2);
        table17[3][0] = new ValueBox(1);
        table17[3][1] = new ValueBox(4);
        table17[3][2] = new ColoredBox(DiceColor.RED);
        table17[3][3] = new ValueBox(5);
        table17[3][4] = new ValueBox(3);
        SchemeCard card17=new SchemeCard("Battlo",5,table17);

        table18[0][0] = new ColoredBox(DiceColor.BLUE);
        table18[0][1] = new ValueBox(6);
        table18[0][2] = new Box();
        table18[0][3] = new Box();
        table18[0][4] = new ColoredBox(DiceColor.YELLOW);
        table18[1][0] = new Box();
        table18[1][1] = new ValueBox(3);
        table18[1][2] = new ColoredBox(DiceColor.RED);
        table18[1][3] = new Box();
        table18[1][4] = new Box();
        table18[2][0] = new Box();
        table18[2][1] = new ValueBox(5);
        table18[2][2] = new ValueBox(6);
        table18[2][3] = new ValueBox(2);
        table18[2][4] = new Box();
        table18[3][0] = new Box();
        table18[3][1] = new ValueBox(4);
        table18[3][2] = new Box();
        table18[3][3] = new ValueBox(1);
        table18[3][4] = new ColoredBox(DiceColor.GREEN);
        SchemeCard card18=new SchemeCard("Bellesguard",3,table18);

        card17.setRetro(card18);
        card18.setRetro(card17);

        table19[0][0] = new Box();
        table19[0][1] = new ColoredBox(DiceColor.BLUE);
        table19[0][2] = new ColoredBox(DiceColor.RED);
        table19[0][3] = new Box();
        table19[0][4] = new Box();
        table19[1][0] = new Box();
        table19[1][1] = new ValueBox(4);
        table19[1][2] = new ValueBox(5);
        table19[1][3] = new Box();
        table19[1][4] = new ColoredBox(DiceColor.BLUE);
        table19[2][0] = new ColoredBox(DiceColor.BLUE);
        table19[2][1] = new ValueBox(2);
        table19[2][2] = new Box();
        table19[2][3] = new ColoredBox(DiceColor.RED);
        table19[2][4] = new ValueBox(5);
        table19[3][0] = new ValueBox(6);
        table19[3][1] = new ColoredBox(DiceColor.RED);
        table19[3][2] = new ValueBox(3);
        table19[3][3] = new ValueBox(1);
        table19[3][4] = new Box();
        SchemeCard card19=new SchemeCard("Fulgor del Cielo",5,table19);

        table20[0][0] = new Box();
        table20[0][1] = new Box();
        table20[0][2] = new ColoredBox(DiceColor.RED);
        table20[0][3] = new ValueBox(5);
        table20[0][4] = new Box();
        table20[1][0] = new ColoredBox(DiceColor.VIOLET);
        table20[1][1] = new ValueBox(4);
        table20[1][2] = new Box();
        table20[1][3] = new ColoredBox(DiceColor.GREEN);
        table20[1][4] = new ValueBox(3);
        table20[2][0] = new ValueBox(6);
        table20[2][1] = new Box();
        table20[2][2] = new Box();
        table20[2][3] = new ColoredBox(DiceColor.BLUE);
        table20[2][4] = new Box();
        table20[3][0] = new Box();
        table20[3][1] = new ColoredBox(DiceColor.YELLOW);
        table20[3][2] = new ValueBox(2);
        table20[3][3] = new Box();
        table20[3][4] = new Box();
        SchemeCard card20=new SchemeCard("Luz Celestial",3,table20);

        card19.setRetro(card20);
        card20.setRetro(card19);


        table21[0][0] = new Box();
        table21[0][1] = new Box();
        table21[0][2] = new ColoredBox(DiceColor.GREEN);
        table21[0][3] = new Box();
        table21[0][4] = new Box();
        table21[1][0] = new ValueBox(2);
        table21[1][1] = new ColoredBox(DiceColor.YELLOW);
        table21[1][2] = new ValueBox(5);
        table21[1][3] = new ColoredBox(DiceColor.BLUE);
        table21[1][4] = new ValueBox(1);
        table21[2][0] = new Box();
        table21[2][1] = new ColoredBox(DiceColor.RED);
        table21[2][2] = new ValueBox(3);
        table21[2][3] = new ColoredBox(DiceColor.VIOLET);
        table21[2][4] = new Box();
        table21[3][0] = new ValueBox(1);
        table21[3][1] = new Box();
        table21[3][2] = new ValueBox(6);
        table21[3][3] = new Box();
        table21[3][4] = new ValueBox(4);
        SchemeCard card21=new SchemeCard("Chromatic Splendor",5,table21);

        table22[0][0] = new ColoredBox(DiceColor.YELLOW);
        table22[0][1] = new Box();
        table22[0][2] = new ValueBox(2);
        table22[0][3] = new Box();
        table22[0][4] = new ValueBox(6);
        table22[1][0] = new Box();
        table22[1][1] = new ValueBox(4);
        table22[1][2] = new Box();
        table22[1][3] = new ValueBox(5);
        table22[1][4] = new ColoredBox(DiceColor.YELLOW);
        table22[2][0] = new Box();
        table22[2][1] = new Box();
        table22[2][2] = new Box();
        table22[2][3] = new ColoredBox(DiceColor.YELLOW);
        table22[2][4] = new ValueBox(5);
        table22[3][0] = new ValueBox(1);
        table22[3][1] = new ValueBox(2);
        table22[3][2] = new ColoredBox(DiceColor.YELLOW);
        table22[3][3] = new ValueBox(3);
        table22[3][4] = new Box();
        SchemeCard card22=new SchemeCard("Comitas",5,table22);

        card21.setRetro(card22);
        card22.setRetro(card21);


        table23[0][0] = new ColoredBox(DiceColor.YELLOW);
        table23[0][1] = new Box();
        table23[0][2] = new ValueBox(6);
        table23[0][3] = new Box();
        table23[0][4] = new Box();
        table23[1][0] = new Box();
        table23[1][1] = new ValueBox(1);
        table23[1][2] = new ValueBox(5);
        table23[1][3  ] = new Box();
        table23[1][4] = new ValueBox(2);
        table23[2][0] = new ValueBox(3);
        table23[2][1] = new ColoredBox(DiceColor.YELLOW);
        table23[2][2] = new ColoredBox(DiceColor.RED);
        table23[2][3] = new ColoredBox(DiceColor.VIOLET);
        table23[2][4] = new Box();
        table23[3][0] = new Box();
        table23[3][1] = new Box();
        table23[3][2] = new ValueBox(4);
        table23[3][3] = new ValueBox(3);
        table23[3][4] = new ColoredBox(DiceColor.RED);
        SchemeCard card23=new SchemeCard("Via Lux",4,table23);

        table24[0][0] = new ValueBox(1);
        table24[0][1] = new ColoredBox(DiceColor.RED);
        table24[0][2] = new ValueBox(3);
        table24[0][3] = new Box();
        table24[0][4] = new ValueBox(6);
        table24[1][0] = new ValueBox(5);
        table24[1][1] = new ValueBox(4);
        table24[1][2] = new ColoredBox(DiceColor.RED);
        table24[1][3] = new ValueBox(2);
        table24[1][4] = new Box();
        table24[2][0] = new Box();
        table24[2][1] = new Box();
        table24[2][2] = new ValueBox(5);
        table24[2][3] = new ColoredBox(DiceColor.RED);
        table24[2][4] = new ValueBox(1);
        table24[3][0] = new Box();
        table24[3][1] = new Box();
        table24[3][2] = new Box();
        table24[3][3] = new ValueBox(3);
        table24[3][4] = new ColoredBox(DiceColor.RED);
        SchemeCard card24=new SchemeCard("Industria",5,table24);

        card23.setRetro(card24);
        card24.setRetro(card23);

        schemeCards.add(card1);
        schemeCards.add(card3);
        schemeCards.add(card5);
        schemeCards.add(card7);
        schemeCards.add(card9);
        schemeCards.add(card11);
        schemeCards.add(card13);
        schemeCards.add(card15);
        schemeCards.add(card17);
        schemeCards.add(card19);
        schemeCards.add(card21);
        schemeCards.add(card23);

        return schemeCards;
    }
}
