package it.polimi.deib.se2018.client;

import it.polimi.deib.se2018.server.model.events.DicePlacementMessage;
import it.polimi.deib.se2018.server.model.events.EndTurnMessage;
import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.common.utils.Observable;
import it.polimi.deib.se2018.common.view.ViewInterface;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.common.utils.Observer;
import it.polimi.deib.se2018.server.model.events.*;

import java.rmi.RemoteException;
import java.util.Scanner;

public class View  extends Observable<Event> implements Observer<Message>,Runnable,ViewInterface {//Classe abstract?

    private String playerNickName;
    private boolean gameOver=false;
    private Scanner s;

    public View(String n) throws RemoteException { playerNickName=n; }//Valutare protected

    public String getPlayerNickName(){
        return playerNickName;
    }//Valutare protected

    private void handleDicePlacement(int r, int c, Dice d)throws RemoteException {
        notify(new DicePlacement(playerNickName,r,c,d));
    }

    public void showMessage(StringMessage message)throws RemoteException{//Valutare protected
        System.out.printf(message.getMessage());
    }

    public void reportError(StringMessage message)throws RemoteException{
        showMessage(message.getMessage()+"\n\n");
    }

    @Override
    public void update(Message message) throws RemoteException {
        if((message instanceof StringMessage)&&playerNickName.equals(message.getPlayer().getNickname())){
            showMessage(((StringMessage) message).getMessage());//POTREI FARLO ANCHE CON GLI ALTRI
        }
        if (message instanceof EndTurnMessage) {
            if (message.getPlayer().getNickname().equals(playerNickName)) {
                showMessage(message.getPlayer().getPlayerScheme().toString() + message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
            }
            else {
                showMessage("Another player has passed the turn...\n" + message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
            }
        }
        if (message instanceof DicePlacementMessage) {
            if(message.getPlayer().getNickname().equals(playerNickName))
                showMessage(message.getPlayer().getPlayerScheme().toString() + message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
            else
                showMessage("Another player has placed a dice...\n"+message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
        }
        if (message instanceof CardActivationMessage) {
            if(message.getPlayer().getNickname().equals(playerNickName))
                showMessage(message.getPlayer().getPlayerScheme().toString() + message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
            else
                showMessage("Another player has activated a card...\n"+message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
        }
        if(message instanceof GameOverMessage){
            showMessage(message.getFinalScores());
            if(((GameOverMessage) message).isMultiPlayer()){
                if(message.getPlayer().getNickname().equals(playerNickName)) showMessage("WINNER!\n");
                else showMessage("LOSER!\n");
            }
            setGameOver();
            showMessage("\nGAME OVER\n");
        }
        if (message instanceof PlayerSuspendedMessage) {
            if (message.getPlayer().getNickname().equals(playerNickName)) {
                showMessage("You have been suspended!\n");
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
            }
            else{
                showMessage("Player: "+message.getPlayer().getNickname()+" has been suspended!\n");
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
            }
        }
        if(message instanceof QuitPlayerMessage){
            if (message.getPlayer().getNickname().equals(playerNickName)) {
                System.out.println("You are out of the game\n");
            }
            else{
                System.out.println("Another player has left the game...\n");
            }
        }
    }

    private void showMessage(String message){
        System.out.println(message+"\n");
    }

    private void reportError(String message){
        System.out.println(message+"\n");
    }

    @Override
    public void run() {
        s=new Scanner(System.in);
        int n=s.nextInt();
        try{
            schemeSelection(n);
        }catch (RemoteException e){System.out.println("You are disconnected!\n");}
        while(!gameOver) {
            try{
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
                    case "0":{
                        quitGame();
                        return;
                    }
                    default: {showMessage("Insert a valid option!\n"); break;}

                }
            }catch (RemoteException e){System.out.println("You are disconnected!\n");}}
    }

    private void schemeSelection(int n) throws RemoteException{
        notify(new SchemeSelection(playerNickName,n));
    }

    public void setGameOver()throws RemoteException{
        gameOver=true;
    }

    private DiceColor convert(String c)throws RemoteException{
        switch (c){
            case "B": return DiceColor.BLUE;
            case "G": return DiceColor.GREEN;
            case "R": return DiceColor.RED;
            case "V": return DiceColor.VIOLET;
            case "Y": return DiceColor.YELLOW;
            default: return null;
        }
    }

    private int rowConversion(String rowLetter)throws RemoteException{
        switch (rowLetter){
            case "A": return 0;
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            default: return -1;
        }
    }

    private void endTurn()throws RemoteException{//FORSE VA RIVISTA LA REGISTRAZIONE DEGLI OSSERVATORI PER VIEW E REMOTE VIEW
        notify(new EndTurn(playerNickName));
    }

    private void quitGame()throws RemoteException{
        notify(new QuitPlayerEvent(playerNickName));
    }

    private void placeDice()throws RemoteException{
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
