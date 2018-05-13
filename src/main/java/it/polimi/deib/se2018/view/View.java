package it.polimi.deib.se2018.view;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.events.*;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.utils.Observable;
import it.polimi.deib.se2018.utils.Observer;

import java.util.Scanner;

public class View  extends Observable<Event>  implements Observer<Message>,Runnable {//Classe abstract?

    private Player player;
    private int nmove;
    private boolean gameover=false;
    private Scanner s;

    public View(Player p) { player=p; nmove=0; }//Valutare protected

    public Player getPlayer(){
        return player;
    }//Valutare protected

    private void handleDicePlacement(int r, int c, Dice d) {
        notify(new DicePlacement(player,r,c,d));
    }

    public void showMessage(String message){//Valutare protected
        System.out.printf(message);
    }

    public void reportError(String message){
        showMessage(message + "\nCHOOSE AN OPTION: \n1)PLACE A DICE\n2)ACTIVATE A CARD\n3)NOTHING");
    }

    @Override
    public void update(Message message){
        if(message instanceof EndTurnMessage){
            if(gameover){
                showMessage("\nGame Over\n");
            }
            else{
                showMessage("ROUND: "+message.getModel().getRound()+" TURN: "+message.getPlayer().getNickname()+"\n");
                showMessage( message.getPlayer().getPlayerScheme().toString() +message.getModel().getDiceBag().toString()+ message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());

            }
        }
        if(message instanceof DicePlacementMessage) {
            showMessage( message.getPlayer().getPlayerScheme().toString() +message.getModel().getDiceBag().toString()+ message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
            if(gameover) {
                showMessage("\nGame Over\n");
            }
        }
    }




    @Override
    public void run(){
        s=new Scanner(System.in);
        while(!gameover) {
            showMessage("CHOOSE AN OPTION: \n1)PLACE A DICE\n2)ACTIVATE A CARD\n3)PASS TURN\n0)EXIT\n");
            String choice = s.next();
            switch (choice) {
                case "1": {
                    placeDice();break;
                }
                case "2":{
                    break;
                }
                case "3":{
                    endTurn();break;
                }
                case "0": return;
                default: {showMessage("Insert a valid option!\n"); break;}

            }
        }
    }

    public void setGameOver(){
        gameover=true;
    }

    private DiceColor convert(String c){
        switch (c){
            case "B": return DiceColor.BLU;
            case "G": return DiceColor.GREEN;
            case "R": return DiceColor.RED;
            case "V": return DiceColor.VIOLET;
            case "Y": return DiceColor.YELLOW;
            default: return null;
        }
    }

    private int rowConversion(String rowLetter){
        switch (rowLetter){
            case "A": return 0;
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            default: return -1;
        }
    }

    private void endTurn(){
        notify(new EndTurn(player));
    }

    private void placeDice(){
        showMessage("DICE COLOR (B/G/R/V/Y): ");
        String color = s.next().toUpperCase();
        showMessage("DICE VALUE (1/2/3/4/5/6): ");
        int value = s.nextInt();
        Dice dice = new Dice(convert(color));
        dice.setValue(value);
        showMessage("ROW (A/B/C/D): ");
        String row = s.next().toUpperCase();
        showMessage("COLUMN (1/2/3/4/5): ");
        int column = s.nextInt();
        handleDicePlacement(rowConversion(row), column - 1, dice);
    }

}
