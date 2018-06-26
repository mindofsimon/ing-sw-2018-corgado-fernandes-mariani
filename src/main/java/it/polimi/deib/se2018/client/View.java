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
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.*;

import java.rmi.RemoteException;
import java.util.Scanner;

public class View  extends Observable<Event> implements Observer<Message>,Runnable,ViewInterface {//Classe abstract?

    private String playerNickName;
    private boolean gameOver=false;
    private Scanner s;
    private int category;//categoria a cui appartiene la carta
    private int error=0;//tipo di errore
    private int num;//numero di dadi a cui si applica l'effetto della carta
    private Dice diceC;//dado a cui applicare effetto carta
    private int gameMode;//singlePlayer o multiplayer

    public View(String n,int gameMod) throws RemoteException {
        gameMode=gameMod;
        playerNickName=n;
    }//Valutare protected

    public String getPlayerNickName(){
        return playerNickName;
    }//Valutare protected

    private void handleDicePlacement(int r, int c, Dice d)throws RemoteException {
        notify(new DicePlacement(playerNickName,r,c,d));
    }

    private void handleCardActivation(int n,Dice dice)throws RemoteException{
        notify(new CardActivation(playerNickName,n,dice));
    }
    private void handleChangeDice(Dice dice,String action,int num)throws RemoteException {
        notify(new ChangeDice(playerNickName,dice,action,num));
    }
    private void handleMoveDice(int dr,int dc,int row,int column,int num)throws RemoteException {
        notify(new MoveDice(playerNickName,dr,dc,row,column,num));
    }
    private void handleChangeDiceDR(Dice dice,Dice diceR,int num)throws RemoteException {
        notify(new ChangeDiceDR(playerNickName,dice,diceR,num));
    }
    private void handleChangeAndPlace(Dice dice,int num)throws RemoteException {
        notify(new ChangeAndPlace(playerNickName,dice,num));
    }
    private void handleDicePlacementCard(int r, int c, Dice d,int num)throws RemoteException {
        notify(new DicePlacementCard(playerNickName,r,c,d,num));
    }

    public void showMessage(StringMessage message)throws RemoteException{//Valutare protected
        System.out.printf(message.getMessage());
    }

    public void reportError(Message message)throws RemoteException{

        showMessage(message.getMessage()+"\n\n");
    }

    @Override
    public void update(Message message) throws RemoteException {
        if ((message instanceof StringMessage) && playerNickName.equals(message.getPlayer().getNickname())) {
            error = 0;
            showMessage(((StringMessage) message).getMessage());//POTREI FARLO ANCHE CON GLI ALTRI
        }
        if ((message instanceof StringMessageError) && playerNickName.equals(message.getPlayer().getNickname())) {
            error = message.getType();
            reportError(message.getMessage());//POTREI FARLO ANCHE CON GLI ALTRI

        }
        if (message instanceof EndTurnMessage) {
            if (message.getPlayer().getNickname().equals(playerNickName)) {
                showMessage(message.getPlayer().getPlayerScheme().toString() + message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
                if (gameMode == 1) {
                    showMessage(" FAVOR MARKS: " + message.getModel().findPlayerByName(playerNickName).getFavorMarkers());
                }
            } else {
                showMessage("Another player has passed the turn...\n" + message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
                if (gameMode == 1) {
                    showMessage(" FAVOR MARKS: " + message.getModel().findPlayerByName(playerNickName).getFavorMarkers());
                }
            }
        }
        if (message instanceof DicePlacementMessage) {
            if (message.getPlayer().getNickname().equals(playerNickName)){
                showMessage(message.getPlayer().getPlayerScheme().toString() + message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                if (gameMode == 1) {
                    showMessage(" FAVOR MARKS: " + message.getModel().findPlayerByName(playerNickName).getFavorMarkers());
                }
            }
            else {showMessage("Another player has placed a dice...\n"+message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
            }
        }
        if (message instanceof CardActivationMessage) {
            if(message.getPlayer().getNickname().equals(playerNickName)) {
                showMessage(message.getPlayer().getPlayerScheme().toString() + message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                if (gameMode == 1) {
                    showMessage(" FAVOR MARKS: " + message.getModel().findPlayerByName(playerNickName).getFavorMarkers());
                }
            }else{ showMessage("Another player has activated a card...\n"+message.getModel().getDiceBag().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
            }
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
        if((message instanceof SelectDiceView)&& playerNickName.equals(message.getPlayer().getNickname())){
            error=0;
            category=1;
        }
        if((message instanceof SelectDiceToMoveView)&& playerNickName.equals(message.getPlayer().getNickname())){
            error=0;
            category=0;
            num= message.getNumberOfDices();

        }
        if((message instanceof SelectDicesView)&& playerNickName.equals(message.getPlayer().getNickname())){
            error=0;
            category=2;
        }
        if((message instanceof ChooseAndPlaceView)&& playerNickName.equals(message.getPlayer().getNickname())){
            error=0;
            category=3;
            num=message.getNumberOfDices();
        }
        if((message instanceof DiceChange)&& playerNickName.equals(message.getPlayer().getNickname())){
            error=0;
            diceC= ((DiceChange) message).getDice();
            showMessage(message.getMessage());
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
        //nel caso del singleplayer si chiede la difficolta con cui vuole giocare
        if(gameMode==0) {
            int difficult;
            do {
                showMessage("CHOOSE A DIFFICULT: \n1)EXTREME CHALLENGE\n5)EASY CHALLENGE");
                difficult = s.nextInt();

            } while (difficult != 1 && difficult != 5);
            try {
                notify(new SetDiff(playerNickName,difficult));//mando al controller il settaggio della difficolta nel player
            } catch (RemoteException e) {
                System.out.println("Remote Exception!\n");
            }
        }
        while(!gameOver) {
            try{
                showMessage("CHOOSE AN OPTION: \n1)PLACE A DICE\n2)ACTIVATE A CARD\n3)PASS TURN\n0)EXIT\n");
                String choice = s.next();
                switch (choice) {
                    case "1": {
                        placeDice();break;
                    }
                    case "2":{
                        activateCard();
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

    private void activateCard()throws RemoteException{
        int number;
        do {
            if (gameMode == 1) {
                Dice dice = new Dice(DiceColor.RED);//assegno un dado generico che non verra mai utilizzato
                showMessage("TOOL CARD NUMBER: \n");
                number = s.nextInt();
                handleCardActivation(number, dice);
                showMessage("ERROR:"+error);

            } else {
                showMessage("SELECT A DICE TO USE THE TOOL CARD: \n");
                Dice dice = selectDice();

                showMessage("TOOL CARD NUMBER: \n");
                number = s.nextInt();
                handleCardActivation(number, dice);
                showMessage("ERROR:"+error);
            }

            if(error==1){
                showMessage("DO YOU WANNA CHOOSE ANOTHER CARD:(YES OR NO) \n");
                String choose = s.next().toUpperCase();
                if(choose.equals("NO")){
                    error=2;
                }

            }
        }while(error==1);

        showMessage("cateogoria:"+category);

        switch (category){
            case 0:
                if(number==12){
                    do {
                        showMessage("CHOOSE THE COLOR OF THE DICE IN ROUND TRACK");
                        showMessage("DICE COLOR (B/G/R/V/Y): ");
                        DiceColor color = convert(s.next().toUpperCase());
                        notify(new ColorDice(playerNickName,color,number));
                    }while(error==1);
                }

                for(int i=0;i<num;i++) {
                    do{
                        selectDiceToMove(number);
                        showMessage("ERROR:"+error);
                    }while(error==1);
                }

                break;

            case 1:
                do{
                    Dice dice;
                    if(number==1){
                        dice=selectDice();
                        handleChangeDice(dice,increment(),number);
                        showMessage("ERROR:"+error);
                    }
                    else{
                        dice=selectDice();
                        handleChangeDice(dice,"N",number);
                        showMessage("ERROR:"+error);
                    }

                }while(error==1);
                break;
            case 2:
                do{
                    showMessage("SELECT DICE IN DICE STOCK");
                    Dice diceS=selectDice();
                    showMessage("SELECT DICE IN ROUND TRACK");
                    Dice diceR=selectDice();
                    handleChangeDiceDR(diceS,diceR,number);
                    showMessage("ERROR:"+error);
                }while(error==1);
                break;
            case 3:

                if(number==11){
                    int value;
                    do{
                        Dice dice = selectDice();
                        handleChangeAndPlace(dice, number);
                        showMessage("ERROR:"+error);
                    }while(error==1);
                    do{
                        showMessage("CHOOSE A VALUE FOR THE DICE: \n");
                        value=s.nextInt();
                        if(value>=1&&value<=6){
                            notify(new SetDice(diceC,value));
                        }else{showMessage("THE DICE VALUE MUST BE BETWEEN 1 AND 6 ");}
                    }while(value<1&&value>6);
                    diceC.setValue(value);
                    showMessage("THE DICE NOW HAVE VALUE "+diceC.getValue());
                    do{
                        place(diceC,number);
                    }while(error==1);


                }else {
                    for (int i = 0; i < num; i++) {
                        do {
                            Dice dice = selectDice();
                            handleChangeAndPlace(dice, number);
                            showMessage("ERROR:"+error);
                        }while(error==1);
                        if(error!=3){
                            do{
                                place(diceC,number);
                                showMessage("ERROR:"+error);
                            }while(error==1);
                        }

                    }
                }

                break;


            default: break;
        }

        if(error==0||error==3){
            notify(new EndCardActivation(playerNickName,number));
        }


        error=0;
        category=-1;


    }

    //chiedo dove vuole piazzare il dado dopo averlo modificato e mando al controller
    private void place(Dice dice,int num)throws RemoteException{

        showMessage("WHERE DO YOU WANNA PLACE THE DICE?");
        showMessage("ROW (A/B/C/D): ");
        String row = s.next().toUpperCase();
        showMessage("COLUMN (1/2/3/4/5): ");
        int column = s.nextInt();
        handleDicePlacementCard(rowConversion(row), column - 1, dice,num);

    }

    //seleziona un dado
    private Dice selectDice() throws RemoteException{

        showMessage("DICE COLOR (B/G/R/V/Y): ");
        String color = s.next().toUpperCase();
        showMessage("DICE VALUE (1/2/3/4/5/6): ");
        int value = s.nextInt();
        Dice dice = new Dice(convert(color));
        dice.setValue(value);
        return dice;
    }

    //nel caso della carta 1 seleziona se vuoi incrementare o decrementare il dado
    public String increment()throws RemoteException{
        showMessage("INCREMENT(I) OR DECREMENT(D): ");
        return s.next().toUpperCase();

    }

    //nel caso della categoria di carte dove si muovono dadi nello schema
    // si sceglie la posizione del dado da muovere e dove lo si vuole spostare
    public void selectDiceToMove(int number)throws RemoteException{
        showMessage("DICE'S ROW (A/B/C/D):");
        int dr = rowConversion(s.next().toUpperCase());
        showMessage("DICE'S COLUMN (1/2/3/4/5)): ");
        int dc = s.nextInt();
        showMessage("WHERE DO YOU WANNA TO MOVE THE DICE");
        showMessage("ROW (A/B/C/D): ");
        int row = rowConversion(s.next().toUpperCase());
        showMessage("COLUMN (1/2/3/4/5): ");
        int column = s.nextInt();

        handleMoveDice(dr,dc-1,row,column-1,number);

    }



}
