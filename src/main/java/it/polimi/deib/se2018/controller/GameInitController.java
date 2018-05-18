package it.polimi.deib.se2018.controller;

import it.polimi.deib.se2018.model.Model;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.*;
import it.polimi.deib.se2018.model.player.PrivateGoalCard;

import java.util.ArrayList;

public class GameInitController {
    private Model model;

    public GameInitController(Model m){
        model=m;
    }

    public void init(){
        initFavorMarkers();
        loadPrivateGoalCards();
        loadPublicGoalCards();
    }

    private void loadPrivateGoalCards(){
        ArrayList<PrivateGoalCard> privateGoalCards=createPrivateGoalCards();
        for(int i=0; i<model.getPlayerList().size();i++){
            int index = (int) ((Math.random() * privateGoalCards.size()));
            model.getPlayerList().get(i).setPrivateGoalCard(privateGoalCards.remove(index));
        }
    }

    private void loadPublicGoalCards(){
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

    private ArrayList<PrivateGoalCard> createPrivateGoalCards(){
        ArrayList<PrivateGoalCard> privateGoalCards=new ArrayList<PrivateGoalCard>();
        privateGoalCards.add(new PrivateGoalCard(DiceColor.BLU,"Private Goal Blu"));
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

    private void initFavorMarkers() {
        //Never used in Single-Player
        if(model.getPlayerList().size()==1) {
            model.getPlayerList().get(0).setFavorMarkers(0);
        }
        if(model.getPlayerList().size()>1){
            for(int i=0;i<model.getPlayerList().size();i++){
                model.getPlayerList().get(i).setFavorMarkers((model.getPlayerList().get(i).getPlayerScheme().getDifficulty()));
            }
        }
    }

}
