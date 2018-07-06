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

/**
 * View class
 * @author Simone Mariani, Sirlan Fernandes
 */
public class View  extends Observable<Event> implements Observer<Message>,Runnable,ViewInterface {//Classe abstract?

    private String playerNickName;
    private boolean gameOver=false;
    private Scanner s;
    private int category;//categoria a cui appartiene la carta
    private int error=0;//tipo di errore
    private int num;//numero di dadi a cui si applica l'effetto della carta
    private Dice diceC;//dado a cui applicare effetto carta
    private int gameMode;//singlePlayer o multiplayer

    /**
     * Constructor, intializes view class
     * @param n player's nickname
     * @param gameMod game mode
     * @throws RemoteException
     */
    public View(String n,int gameMod) throws RemoteException {
        gameMode=gameMod;
        playerNickName=n;
    }//Valutare protected

    /**
     * Gets player's nickname
     * @return player's nickname
     */
    public String getPlayerNickName(){
        return playerNickName;
    }//Valutare protected

    /**
     * Handles dice placement
     * @param r row
     * @param c column
     * @param d dice
     * @throws RemoteException
     */
    private void handleDicePlacement(int r, int c, Dice d)throws RemoteException {
        notify(new DicePlacement(playerNickName,r,c,d));
    }

    /**
     * Handles card activation
     * @param n card number
     * @param dice dice
     * @throws RemoteException
     */
    private void handleCardActivation(int n,Dice dice)throws RemoteException{
        notify(new CardActivation(playerNickName,n,dice));
    }

    /**
     * Handles change dice
     * @param dice dice
     * @param action action
     * @param num tool card number
     * @throws RemoteException
     */
    private void handleChangeDice(Dice dice,String action,int num)throws RemoteException {
        notify(new ChangeDice(playerNickName,dice,action,num));
    }

    /**
     * Handles move dice
     * @param dr dice row
     * @param dc dice column
     * @param row row
     * @param column column
     * @param num tool card number
     * @throws RemoteException
     */
    private void handleMoveDice(int dr,int dc,int row,int column,int num)throws RemoteException {
        notify(new MoveDice(playerNickName,dr,dc,row,column,num));
    }

    /**
     * Handles change of dice from stock with a dice in round track
     * @param dice dice
     * @param diceR dice from round track
     * @param num tool card number
     * @throws RemoteException
     */
    private void handleChangeDiceDR(Dice dice,Dice diceR,int num)throws RemoteException {
        notify(new ChangeDiceDR(playerNickName,dice,diceR,num));
    }

    /**
     * Handles change and place
     * @param dice dice
     * @param num tool card number
     * @throws RemoteException
     */
    private void handleChangeAndPlace(Dice dice,int num)throws RemoteException {
        notify(new ChangeAndPlace(playerNickName,dice,num));
    }

    /**
     * Handles dice placement card
     * @param r row
     * @param c column
     * @param d dice
     * @param num tool card number
     * @throws RemoteException
     */
    private void handleDicePlacementCard(int r, int c, Dice d,int num)throws RemoteException {
        notify(new DicePlacementCard(playerNickName,r,c,d,num));
    }

    /**
     * Shows message
     * @param message message
     * @throws RemoteException
     */
    public void showMessage(StringMessage message)throws RemoteException{//Valutare protected
        System.out.printf(message.getMessage());
    }

    /**
     * Reports error
     * @param message message
     * @throws RemoteException
     */
    public void reportError(Message message)throws RemoteException{

        showMessage(message.getMessage()+"\n\n");
    }

    /**
     * Updates message
     * @param message message
     * @throws RemoteException
     */
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
                showMessage(message.getPlayer().getPrivateGoalCard().toString() + "\n\n");
                showMessage(message.getPlayer().getPlayerScheme().toString() +  message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
                if (gameMode == 1) {
                    showMessage(" FAVOR MARKS: " + message.getModel().findPlayerByName(playerNickName).getFavorMarkers());
                }
            } else {
                showMessage(message.getModel().findPlayerByOrder(message.getModel().getTurn()).getPrivateGoalCard().toString() + "\n\n");
                showMessage("Another player has passed the turn...\n" +message.getModel().findPlayerByOrder(message.getModel().getTurn()).getPlayerScheme().toString() +message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
                if (gameMode == 1) {
                    showMessage(" FAVOR MARKS: " + message.getModel().findPlayerByName(playerNickName).getFavorMarkers());
                }
            }
        }
        if (message instanceof DicePlacementMessage) {
            if (message.getPlayer().getNickname().equals(playerNickName)){
                showMessage(message.getPlayer().getPlayerScheme().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                if (gameMode == 1) {
                    showMessage(" FAVOR MARKS: " + message.getModel().findPlayerByName(playerNickName).getFavorMarkers());
                }
            }
            else {showMessage("Another player has placed a dice...\n"+ message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
            }
        }
        if (message instanceof CardActivationMessage) {
            if(message.getPlayer().getNickname().equals(playerNickName)) {
                showMessage(message.getPlayer().getPlayerScheme().toString() + message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
                if (gameMode == 1) {
                    showMessage(" FAVOR MARKS: " + message.getModel().findPlayerByName(playerNickName).getFavorMarkers());
                }
            }else{ showMessage("Another player has activated a card...\n"+ message.getModel().getDiceStock().toString() + message.getModel().getRoundsTrack().toString());
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
            if (message.getPlayer().getNickname().equals(playerNickName) && message.getModel().findPlayerByOrder(message.getModel().getTurn())!=message.getPlayer()) {
                showMessage("You have been suspended!\n");
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
            }
            else if(!message.getPlayer().getNickname().equals(playerNickName) && message.getModel().findPlayerByOrder(message.getModel().getTurn())!=message.getPlayer()) {
                showMessage("Player: "+message.getPlayer().getNickname()+" has been suspended!\n");
                showMessage("ROUND: " + message.getModel().getRound() + " PLAYER: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getNickname() + " TURN: " + message.getModel().findPlayerByOrder(message.getModel().getTurn()).getnTurns() + "\n");
                showMessage("CHOOSE AN OPTION: \n1)PLACE A DICE\n2)ACTIVATE A CARD\n3)PASS TURN\n0)EXIT\n");
            }
            else if(message.getPlayer().getNickname().equals(playerNickName) && message.getModel().findPlayerByOrder(message.getModel().getTurn())==message.getPlayer()){
                showMessage("You have been suspended!\n");
            }
            else if(!message.getPlayer().getNickname().equals(playerNickName) && message.getModel().findPlayerByOrder(message.getModel().getTurn())==message.getPlayer()){
                showMessage("Player: "+message.getPlayer().getNickname()+" has been suspended!\n");
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

    /**
     * Shows message
     * @param message message
     */
    private void showMessage(String message){
        System.out.println(message+"\n");
    }

    /**
     * Reports error
     * @param message message
     */
    private void reportError(String message){
        System.out.println(message+"\n");
    }

    /**
     * Runs game
     */
    @Override
    public void run() {
        int n;
        do {
        s=new Scanner(System.in);
        n=stringToInt(s.next());
            try {
                schemeSelection(n);
            } catch (RemoteException e) {
                System.out.println("You are disconnected!\n");
            }
        }while(error==1);
        //nel caso del singleplayer si chiede la difficolta con cui vuole giocare
        if(gameMode==0) {
            int difficult;
            do {
                showMessage("CHOOSE A DIFFICULT: \n1)EXTREME CHALLENGE\n5)EASY CHALLENGE");
                difficult = stringToInt(s.next());

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

    /**
     * Scheme selection
     * @param n scheme number
     * @throws RemoteException
     */
    private void schemeSelection(int n) throws RemoteException{
        notify(new SchemeSelection(playerNickName,n));
    }

    /**
     * Sets game over
     */
    public void setGameOver(){
        gameOver=true;
    }

    /**
     * Converts dice color letter to full dice color name
     * @param c color letter
     * @return full dice color name
     * @throws RemoteException
     */
    private DiceColor convert(String c){
        switch (c){
            case "B": return DiceColor.BLUE;
            case "G": return DiceColor.GREEN;
            case "R": return DiceColor.RED;
            case "V": return DiceColor.VIOLET;
            case "Y": return DiceColor.YELLOW;
            default: return null;
        }
    }

    /**
     * Converts row letter to number
     * @param rowLetter row letter
     * @return converted row number
     * @throws RemoteException
     */
    private int rowConversion(String rowLetter){
        switch (rowLetter){
            case "A": return 0;
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            default: return -1;
        }
    }

    /**
     * Converts string row number to int number
     * @param rowLetter row letter
     * @return converted int row number
     */
    private int stringToInt(String rowLetter){
        switch (rowLetter){
            case "0": return 0;
            case "1": return 1;
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10": return 10;
            case "11": return 11;
            case "12": return 12;
            default: return -1;
        }
    }

    /**
     * Ends turn
     * @throws RemoteException
     */
    private void endTurn()throws RemoteException{//FORSE VA RIVISTA LA REGISTRAZIONE DEGLI OSSERVATORI PER VIEW E REMOTE VIEW
        notify(new EndTurn(playerNickName));
    }

    /**
     * Quits game
     * @throws RemoteException
     */
    private void quitGame()throws RemoteException{
        notify(new QuitPlayerEvent(playerNickName));
    }

    /**
     * Places dice
     * @throws RemoteException
     */
    private void placeDice()throws RemoteException{
        showMessage("DICE COLOR (B/G/R/V/Y): ");
        String color = s.next().toUpperCase();
        showMessage("DICE VALUE (1/2/3/4/5/6): ");
        int value = stringToInt(s.next());
        Dice dice = new Dice(convert(color));
        dice.setValue(value);
        showMessage("ROW (A/B/C/D): ");
        String row = s.next().toUpperCase();
        showMessage("COLUMN (1/2/3/4/5): ");
        int column = stringToInt(s.next());
        handleDicePlacement(rowConversion(row), column - 1, dice);
    }

    /**
     * Activates card
     * @throws RemoteException
     */
    private void activateCard()throws RemoteException{
        int number;
        do {
            if (gameMode == 1) {
                Dice dice = new Dice(DiceColor.RED);//assegno un dado generico che non verra mai utilizzato
                showMessage("TOOL CARD NUMBER: \n");
                number = stringToInt(s.next());
                handleCardActivation(number, dice);

            } else {
                showMessage("SELECT A DICE TO USE THE TOOL CARD: \n");
                Dice dice = selectDice();

                showMessage("TOOL CARD NUMBER: \n");
                number = stringToInt(s.next());
                handleCardActivation(number, dice);
            }

            if(error==1){
                showMessage("DO YOU WANNA CHOOSE ANOTHER CARD:(YES OR NO) \n");
                String choose = s.next().toUpperCase();
                if(choose.equals("NO")){
                    error=2;
                }

            }
        }while(error==1);


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
                    }while(error==1);
                }

                break;

            case 1:
                do{
                    Dice dice;
                    if(number==1){
                        dice=selectDice();
                        handleChangeDice(dice,increment(),number);
                    }
                    else{
                        dice=selectDice();
                        handleChangeDice(dice,"N",number);
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
                }while(error==1);
                break;
            case 3:

                if(number==11){
                    int value;
                    do{
                        Dice dice = selectDice();
                        handleChangeAndPlace(dice, number);
                    }while(error==1);
                    do{
                        showMessage("CHOOSE A VALUE FOR THE DICE: \n");
                        value=stringToInt(s.next());
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
                        }while(error==1);
                        if(error!=3){
                            do{
                                place(diceC,number);
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

    /**
     * Asks where to place a dice after modifying it and sends to the controller
     * @param dice dice
     * @param num dice value
     * @throws RemoteException
     */
    private void place(Dice dice,int num)throws RemoteException{

        showMessage("WHERE DO YOU WANNA PLACE THE DICE?");
        showMessage("ROW (A/B/C/D): ");
        String row = s.next().toUpperCase();
        showMessage("COLUMN (1/2/3/4/5): ");
        int column = stringToInt(s.next());
        handleDicePlacementCard(rowConversion(row), column - 1, dice,num);

    }

    /**
     * Selects a dice
     * @return dice
     * @throws RemoteException
     */
    private Dice selectDice() throws RemoteException{

        showMessage("DICE COLOR (B/G/R/V/Y): ");
        String color = s.next().toUpperCase();
        showMessage("DICE VALUE (1/2/3/4/5/6): ");
        int value = stringToInt(s.next());
        Dice dice = new Dice(convert(color));
        dice.setValue(value);
        return dice;
    }

    /**
     * String that gives choice to increment or decrement dice value for tool card number 1
     * @return choice to increment or decrement dice value
     * @throws RemoteException
     */
    public String increment()throws RemoteException{
        showMessage("INCREMENT(I) OR DECREMENT(D): ");
        return s.next().toUpperCase();

    }

    /**
     * Selects the position of the dice that the player wants to move and the position where they want to put it
     * @param number tool card number
     * @throws RemoteException
     */
    public void selectDiceToMove(int number)throws RemoteException{
        showMessage("DICE'S ROW (A/B/C/D):");
        int dr = rowConversion(s.next().toUpperCase());
        showMessage("DICE'S COLUMN (1/2/3/4/5)): ");
        int dc = stringToInt(s.next());
        showMessage("WHERE DO YOU WANNA TO MOVE THE DICE");
        showMessage("ROW (A/B/C/D): ");
        int row = rowConversion(s.next().toUpperCase());
        showMessage("COLUMN (1/2/3/4/5): ");
        int column = stringToInt(s.next());

        handleMoveDice(dr,dc-1,row,column-1,number);

    }



}
