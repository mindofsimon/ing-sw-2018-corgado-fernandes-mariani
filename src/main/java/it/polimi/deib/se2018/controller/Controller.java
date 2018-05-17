package it.polimi.deib.se2018.controller;

import it.polimi.deib.se2018.controller.toolcard.ToolCard;
import it.polimi.deib.se2018.model.Model;
import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.events.DicePlacement;
import it.polimi.deib.se2018.model.events.EndTurn;
import it.polimi.deib.se2018.model.events.Event;
import it.polimi.deib.se2018.model.gametable.publicgoalcard.*;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.model.player.PrivateGoalCard;
import it.polimi.deib.se2018.utils.Observer;
import it.polimi.deib.se2018.view.View;

import java.util.ArrayList;

public class Controller implements Observer<Event> {

    private Model model;
    private View view;
    private ArrayList <ToolCard> toolCardList;

    public Controller(Model m, View v){
        view=v;
        model=m;
        toolCardList=new ArrayList<ToolCard>(3);
    }

    public ArrayList<ToolCard> getToolCardsList() { return toolCardList; }

    public void addToolCard(ToolCard c){ toolCardList.add(c); }


    //Point calculation and winner methods
    private Player calculateMax(){
        Player winner=null;
        for(int i=1;i<model.getPlayerList().size();i++){
            if(model.getPlayerList().get(i).getVictoryPoints()>model.getPlayerList().get(i-1).getVictoryPoints()){
                winner=model.getPlayerList().get(i);
            }
        }
        return winner;
    }

    private int solitaryObjectivePoint(){
        int points=0;
        for(int i=0;i<model.getRoundsTrack().getDiceList().size();i++){
            points=points+model.getRoundsTrack().getDiceList().get(i).getValue();
        }
        return points;
    }


    private void calculateWinner(){
        if(model.getPlayerList().size()==1){
            if(model.getPlayerList().get(0).getVictoryPoints()>solitaryObjectivePoint()){
                view.showMessage("Your points:"+model.getPlayerList().get(0).getVictoryPoints()+"\nRounds Track points:"+solitaryObjectivePoint()+"\nYOU ARE THE WINNER!");
            }
            else{
                view.showMessage("Your points:"+model.getPlayerList().get(0).getVictoryPoints()+"\nRounds Track points:"+solitaryObjectivePoint()+"\nYOU ARE THE LOSER!");

            }
            if(model.getPlayerList().size()>1){
                Player winner=calculateMax();
                if (view.getPlayer()!=winner) {
                    view.showMessage("Your points:" + model.getPlayerList().get(0).getVictoryPoints() + "\nRounds Track points:" + solitaryObjectivePoint() + "\nYOU ARE THE WINNER!");
                }
                else {
                    view.showMessage("Your points:" + model.getPlayerList().get(0).getVictoryPoints() + "\nRounds Track points:" + solitaryObjectivePoint() + "\nYOU ARE THE LOSER!");
                }
            }
        }
    }

    private int calculateVictoryPoints(Player p){
        return calculatePrivateGoal(p)+calculateEmptyBoxes(p)+calculatePublicGoal(p)+p.getFavorMarkers();
    }

    private int calculateEmptyBoxes(Player p){
        int points=0;
        for(int i=0;i<p.getPlayerScheme().getROWS();i++){
            for(int j=0;j<p.getPlayerScheme().getCOLS();j++){
                if(p.getPlayerScheme().getScheme()[i][j].getDice()== null ){
                    points=points-1;
                }
            }
        }
        return points;
    }

    private int calculatePrivateGoal(Player p){
        int points=0;
        for(int i=0;i<p.getPlayerScheme().getROWS();i++){
            for(int j=0;j<p.getPlayerScheme().getCOLS();j++){
                if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null &&p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(p.getPrivateGoalCard().getColor())){
                    points=points+p.getPlayerScheme().getScheme()[i][j].getDice().getValue();
                }
            }
        }
        return points;
    }

    private int calculatePublicGoal(Player p){
        int points=0;
        for(int i=0;i<model.getPublicGoalCards().size();i++){
            points=points+model.getPublicGoalCards().get(i).calculateVictoryPoints(p);
        }
        return points;
    }


    //We'll use this method just to reconvert the row, to show the letter instead of the number...
    private String convertRow(int r){
        switch (r){
            case 0:return "A";
            case 1:return "B";
            case 2:return "C";
            case 3:return "D";
            default:return null;
        }

    }

    //Game initialization methods
    public void initGame(){
        //MANCA LA CREAZIONE SCHEMI
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


    //Round and Turn update methods
    private void updateRoundsTrack(){
        if(model.getPlayerList().size()==1) {
            model.getRoundsTrack().getDiceList().addAll(model.getDiceStock().getDiceList());
        }
        if(model.getPlayerList().size()>1){
            model.getRoundsTrack().insertDice(model.getDiceStock().extractRandomDice());
        }
    }

    public void updateDiceStock(){//DEVO ELIMINARE TUTTI I DADI RIMASTI
        model.getDiceStock().getDiceList().removeAll(model.getDiceStock().getDiceList());
        if(model.getPlayerList().size()==1) {
            for (int i = 0; i < 4; i++) {
                model.getDiceStock().insertDice(model.getDiceBag().extractRandomDice());
                model.getDiceStock().setDiceValue(i);
            }
        }
        if(model.getPlayerList().size()>1){
            for (int i = 0; i < model.getPlayerList().size()*2; i++) {
                model.getDiceStock().insertDice(model.getDiceBag().extractRandomDice());
                model.getDiceStock().setDiceValue(i);
            }
        }
    }


    private void updateRound(Player p){
        if(model.getRound()==10){//End of the game
            p.setVictoryPoints(calculateVictoryPoints(p));
            view.setGameOver();
            calculateWinner();
            model.notifyRoundUpdate(p);
        }
        else {//DEVO ANCHE MODIFICARE L'ORDINE DEI GIOCATORI
            updateRoundsTrack();
            updateDiceStock();
            model.incrRound();
            model.notifyRoundUpdate(p);
        }
    }

    private void updateTurn(Player p){
        if(model.getPlayerList().size()==1){//Single Player
            if(p.getnMoves()==2){
                p.setnMoves(0);
                updateRound(p);
            }
        }
        if(model.getPlayerList().size()>1&&p.getnMoves()==2){//Multi-Player
            p.setnMoves(0);
            if(p.getnTurns()==1&&model.getTurn()!=model.getPlayerList().size()){
                p.setnTurns(p.getnTurns()+1);
                model.incrTurn();
            }
            if(p.getnTurns()==2&&model.getTurn()!=1){
                p.setnTurns(p.getnTurns()-1);
                model.decrTurn();
            }
            if(p.getnTurns()==2&&model.getTurn()==1){
                p.setnTurns(p.getnTurns()-1);
                updateRound(p);
            }
            if(p.getnTurns()==1&&model.getTurn()==model.getPlayerList().size()){
                p.setnTurns(p.getnTurns()+1);
            }


        }
    }



    //Control methods

    //Controls if row and column are alright
    private boolean isRowColOk(int r, int c){
        return((r>=0&&r<=3)&&(c>=0&&c<=4));
    }

    private boolean isPlayerTurn(Player p){return model.getTurn()==p.getOrder();}


    //Controls if there are no dices placed by a player
    private boolean firstDice(Player p){
        return (dicesPlaced(p)==0);
    }

    //Returns number of dices placed by a player
    private int dicesPlaced(Player p){
        int cont=0;
        for (int i = 0; i<p.getPlayerScheme().getROWS(); i++){
            for (int j=0;j<p.getPlayerScheme().getCOLS();j++){
                if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null) cont++;
            }
        }
        return cont;
    }

    //Controls if the choosen box is compatible with the selected dice
    private boolean isBoxOk(Player p, int r, int c, Dice d){
        return (p.getPlayerScheme().getScheme()[r][c].getDice()==null
                && (p.getPlayerScheme().getScheme()[r][c].getColor()==null||p.getPlayerScheme().getScheme()[r][c].getValue()==d.getValue()
                ||p.getPlayerScheme().getScheme()[r][c].getColor().equals(d.getColor())));

    }

    //Controls if the selected dice is not placed near similar dices (similar by value and color)
    private boolean similarDicesOk(Player p, int r, int c, Dice d){
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
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c + 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c + 1].getDice().getValue() != d.getValue()));

    }

    private boolean similarDicesControl5(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getColor() != d.getColor()) &&
                (p.getPlayerScheme().getScheme()[r][c - 1].getDice() == null || p.getPlayerScheme().getScheme()[r][c - 1].getDice().getValue() != d.getValue()));

    }

    private boolean similarDicesControl6(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c+1].getDice() == null || p.getPlayerScheme().getScheme()[r][c+1].getDice().getColor() != d.getColor()));

    }

    private boolean similarDicesControl7(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c+1].getDice() == null || p.getPlayerScheme().getScheme()[r][c+1].getDice().getColor() != d.getColor()));

    }

    private boolean similarDicesControl8(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r + 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r + 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c-1].getDice() == null || p.getPlayerScheme().getScheme()[r][c-1].getDice().getColor() != d.getColor()));

    }

    private boolean similarDicesControl9(Player p, int r, int c, Dice d){
        return ((p.getPlayerScheme().getScheme()[r - 1][c].getDice() == null || p.getPlayerScheme().getScheme()[r - 1][c].getDice().getValue() != d.getValue()) &&
                (p.getPlayerScheme().getScheme()[r][c-1].getDice() == null || p.getPlayerScheme().getScheme()[r][c-1].getDice().getColor() != d.getColor()));

    }


    //Controls if there are already placed dices near the box we want to place the dice in
    private boolean alreadyPlacedDicesOk(Player p, int r, int c) {
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



    //Perform actions and events methods
    private synchronized void performDicePlacement(Event placement){
        if(!isPlayerTurn(placement.getPlayer())){
            view.reportError("ERROR! Wait, it's not your turn.");
        }
        if(model.getDiceStock().findDice(placement.getDice())==-1){
            view.reportError("ERROR! Selected dice is not into dice stock!");
            return;
        }
        if(!isRowColOk(placement.getRow(),placement.getColumn())){
            view.reportError("ERROR! Insert a correct value for row and column!");
            return;
        }
        if(firstDice(placement.getPlayer())&&(placement.getRow()!=0&&placement.getRow()!=3&&placement.getColumn()!=0&&placement.getColumn()!=4)){
            view.reportError("ERROR! First dice must be placed on borders!");
            return;
        }
        if(!isBoxOk(placement.getPlayer(),placement.getRow(),placement.getColumn(),placement.getDice())){
            view.reportError(("ERROR! Selected box is not compatible with selected dice!"));
            return;
        }
        if(!firstDice(placement.getPlayer())&&!similarDicesOk(placement.getPlayer(),placement.getRow(),placement.getColumn(),placement.getDice())){
            view.reportError("ERROR! There are some dices similar to the one you've selected near selected box");
            return;
        }
        if(!firstDice(placement.getPlayer())&&!alreadyPlacedDicesOk(placement.getPlayer(),placement.getRow(),placement.getColumn())){
            view.reportError("ERROR! A dice must be placed near already placed dices");
            return;
        }
        //Once controls are done, the dice is setted
        placeDice((DicePlacement)placement);
        view.showMessage("\nDICE color: " + placement.getDice().getColor() + " value: " + placement.getDice().getValue() + " placed in " +convertRow(placement.getRow())+(placement.getColumn()+1) +"\n\n");
        placement.getPlayer().setnMoves(placement.getPlayer().getnMoves()+1);
        updateTurn(placement.getPlayer());
    }

    private void placeDice(DicePlacement p){
        model.getDiceStock().extractDice(p.getDice());
        p.getPlayer().getPlayerScheme().getScheme()[p.getRow()][p.getColumn()].setDice(p.getDice());
        model.notifyPlacement(p);
    }

    private synchronized void performEndTurn(Event e){
        if(!isPlayerTurn(e.getPlayer())){
            view.reportError("ERROR! Wait, it's not your turn.");
            return;
        }
        e.getPlayer().setnMoves(2);
        updateTurn(e.getPlayer());
    }

    @Override
    public void update(Event event) {
        if(event instanceof DicePlacement) {
            performDicePlacement(event);
        }
        if(event instanceof EndTurn){
            performEndTurn(event);
        }
    }

}

