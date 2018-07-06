package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.common.view.ViewInterface;
import it.polimi.deib.se2018.server.controller.toolcard.*;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.*;
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.*;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.common.utils.Observer;


import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Controller class
 * @author Simone Marian, Sirlan Fernandes
 */
public class Controller implements Observer<Event> {

    private Model model;
    private RemoteView view;//IN REALTA' E' LA REMOTE...MA IL CONTROLLER NON LO SA
    private ArrayList <ToolCard> toolCardList;
    private ScoreController scoreController;
    private GameInitController gameInitController;
    private GameRoundController gameRoundController;
    private DicePlacementController dicePlacementController;
    private CardActivationController cardActivationController;
    private int difficult;
    private int category=-1;

    /**
     * Constructor, initializes controller class
     * @param m model
     * @param v remote view
     */
    public Controller(Model m, RemoteView v){
        view=v;
        model=m;
        toolCardList=new ArrayList<ToolCard>(3);
        scoreController=new ScoreController(m,v);
        gameInitController=new GameInitController(m);
        gameRoundController=new GameRoundController(m,v,scoreController);
        dicePlacementController=new DicePlacementController();
        cardActivationController= new CardActivationController(m,toolCardList,dicePlacementController);
    }

    /**
     * Gets game round controller
     * @return Game round controller
     */
    public GameRoundController getGameRoundController() {
        return gameRoundController;
    }

    /**
     * Gets tool card list
     * @return tool card list
     */
    public ArrayList<ToolCard> getToolCardsList() { return toolCardList; }

    /**
     * Adds tool card
     * @param c tool card
     */
    public void addToolCard(ToolCard c){ toolCardList.add(c); }

    /**
     * Gets model
     * @return model
     */
    public Model getModel() {
        return model;
    }

    /**
     * Gets view interface
     * @return view interface
     */
    public ViewInterface getView() {
        return view;
    }

    /**
     * Converts row from numbers to letters
     * @param r row
     * @return row letter
     */
    private String convertRow(int r){
        switch (r){
            case 0:return "A";
            case 1:return "B";
            case 2:return "C";
            case 3:return "D";
            default:return null;
        }

    }

    /**
     * Initiates game
     * @param suspensionTimerInterval suspension timer interval
     * @throws RemoteException
     */
    public void initGame(int suspensionTimerInterval)throws RemoteException{
        gameInitController.init();
        gameRoundController.updateDiceStock();
        gameRoundController.setSuspensionTimerInterval(suspensionTimerInterval);
        if(!gameInitController.isSinglePlayer()){
            loadToolCards();
        }
        schemeSelection();
    }

    /**
     * Scheme selection
     * @throws RemoteException
     */
    private void schemeSelection()throws RemoteException{
        for(int i=0;i<model.getPlayerList().size();i++){
            view.showMessage(new StringMessage("WELCOME TO SAGRADA!\n"+model.getPlayerList().get(i).getPrivateGoalCard().toString()+"\nPlease select your scheme card(0-FIRST FRONT/1-FIRST RETRO/2-SECOND FRONT/3-SECOND RETRO):\n",model.getPlayerList().get(i)));
            view.showMessage(new StringMessage(model.getPlayerList().get(i).getOfferedSchemeCards().get(0).basicVisualization()+"\n\n"+
                    model.getPlayerList().get(i).getOfferedSchemeCards().get(0).getRetro().basicVisualization()+"\n\n"+
                    model.getPlayerList().get(i).getOfferedSchemeCards().get(1).basicVisualization()+"\n\n"+
                    model.getPlayerList().get(i).getOfferedSchemeCards().get(1).getRetro().basicVisualization(),model.getPlayerList().get(i)));
        }
    }

    /**
     * Creates the 12 tool cards
     * @return tool cards
     */
    private ArrayList<ToolCard> createToolCards(){
        ArrayList<ToolCard> toolCards=new ArrayList<ToolCard>();
        toolCards.add(new ChangeDices("Pinza  Sgrossatrice",1,DiceColor.VIOLET,"Dopo  aver  scelto  un  dado, aumenta  o  dominuisci  il  valore del  dado  scelto  di  1"));
        toolCards.add(new MoveDices("Pennello  per  Eglomise",2,Restriction.COLOR,DiceColor.BLUE,1,"Muovi  un  qualsiasi  dado  nella  tua vetrata  ignorando  le  restrizioni  di  colore"));
        toolCards.add(new MoveDices("Alesatore  per  lamina  di  rame",3,Restriction.SHADE,DiceColor.RED,1,"Muovi  un  qualsiasi  dado  nella  tua  vetrata  ignorando  le  restrizioni di  valore"));
        toolCards.add(new MoveDices("Lathekin",4,Restriction.NULL,DiceColor.YELLOW,2,"Muovi  esattamente  due  dadi, rispettando  tutte  le  restrizioni  di piazzamento"));
        toolCards.add(new Taglierina("Taglierina  circolare",5,DiceColor.GREEN,"Dopo  aver  scelto  un  dado,  scambia  quel  dado  con  un  dadoì sul  Tracciato  dei  Round"));
        toolCards.add(new ChangeAndPlaceCard("Pennello  per  Pasta  Salda",6,DiceColor.VIOLET,1,"Dopo  aver  scelto  un  dado, tira  nuovamente  quel  dado Se  non  puoi  piazzarlo, riponilo  nella  Riserva"));
        toolCards.add(new Marteletto("Marteletto",7,DiceColor.BLUE,"Tira  nuovamentetutti tutti  i  dadi della  Riserva Questa  carta  può  essera  usata solo  durante  il  tuo  secondo  turno,  prima  di  scegliere  il  secondo  dado"));
        toolCards.add(new ChangeAndPlaceCard("Tenaglia  a  Rotelle",8,DiceColor.RED,1,"Dopo  il  tuo  primo  turno scegli  immediatamente  un  altro  dado Salta  il  tuo  secondo  turno  in  questo  round"));
        toolCards.add(new ChangeAndPlaceCard("Riga  in  Sughero",9,DiceColor.YELLOW,1,"Dopo  aver  scelto  un  dado,  piazzalo  in  una  casella  che  non  sia  adiacente  a  un  altro  dado"));
        toolCards.add(new ChangeDices("Tampone  Diamantato",10,DiceColor.GREEN,"Dopo  aver  scelto  un  dado, giralo  sulla  faccia  opposta"));
        toolCards.add(new ChangeAndPlaceCard("Diluente  per  Pasta  Salda",11,DiceColor.VIOLET,1,"Dopo  aver  scelto  un  dado, riponilo  nel  Sacchetto, poi  pescane  uno  dal  SacchettoScegli  il  valore  del  nuovo  dado  e  piazzalo,  rispettando  tutte  le  restrizioni  di  piazzamento"));
        toolCards.add(new MoveDices("Taglierina  Manuale",12,Restriction.NULL,DiceColor.BLUE,2,"Muovi  fino  a  due  dadi  dello  stesso  colore di  un  solo  dado  sul  Tracciato  dei  Round"));

        return toolCards;
    }


    /**
     * Loads usable tool cards
     * @throws RemoteException
     */
    private void loadToolCards()throws RemoteException{
        ArrayList<ToolCard> toolCardLists=createToolCards();
        if(gameInitController.isSinglePlayer()) {
            for (int i = 0; i < difficult; i++) {
                int index = (int) ((Math.random() * toolCardLists.size()));
                addToolCard(toolCardLists.remove(index));
            }
        }else {
            for (int i = 0; i < 3; i++) {
                int index = (int) ((Math.random() * toolCardLists.size()));
                addToolCard(toolCardLists.remove(index));
            }
        }

    }

    /**
     * Sets and shows tool cards
     * @param p player
     * @throws RemoteException
     */
    public void showToolCards(Player p) throws RemoteException {
        cardActivationController.setActivated(p,gameInitController.isSinglePlayer());
        view.showMessage(new StringMessage(toStringToolCards(), p));

    }

    /**
     * Cleans tool cards list
     */
    public void cleanToolCards(){
        toolCardList.clear();
    }

    /**
     * Builds tool cards array to show in the view
     * @return tool cards
     */
    public String toStringToolCards() {
        StringBuilder builder = new StringBuilder();
        builder.append("TOOL CARDS: \n\n");
        if(gameInitController.isSinglePlayer()) {
            for (int i = 0; i < toolCardList.size(); i++) {
                builder.append(toolCardList.get(i).toStringSolitary());
                builder.append("\n");
                builder.append("----------------------------------------------------------------------------------------------------------");
                builder.append("\n");
            }
        }else{
            for (int i = 0; i < toolCardList.size(); i++) {
                builder.append(toolCardList.get(i).toString());
                builder.append("\n");
                builder.append("----------------------------------------------------------------------------------------------------------");
                builder.append("\n");
            }
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Checks if a player is suspended
     * @param p player
     * @return true if a player is suspended, else false
     */
    private boolean isPlayerSuspended(Player p){
        return p.isSuspended();
    }

    /**
     * Checks if it is the turn of a given player
     * @param p player
     * @return true if it is the turn of a given player
     * @throws RemoteException
     */
    private boolean isPlayerTurn(Player p)throws RemoteException{return model.getTurn()==p.getOrder();}

    /**
     * Checks if the player has already placed a dice
     * @param p player
     * @return true if the player has already placed a dice
     */
    private boolean isDicePlacementAlreadyDone(Player p){
        if(p.dicePlaced()) return true;
        else return false;
    }


    /**
     * Performs dice placement actions and events
     * @param placement dice placement
     * @throws RemoteException
     */
    private synchronized void performDicePlacement(Event placement)throws RemoteException{
        Player p=model.findPlayerByName(placement.getPlayerNickName());
        if(p!=null) {
            if(isPlayerSuspended(p)){
                view.reportError(new StringMessage("ERROR! You have been suspended.",p));
            }
            if (!isPlayerTurn(p)) {
                view.reportError(new StringMessage("ERROR! Wait, it's not your turn.", p));
                return;
            }
            if (isDicePlacementAlreadyDone(p)) {
                view.reportError(new StringMessage("ERROR! You've already placed a dice in this turn!", p));
                return;
            }
            if(p.isDicePlacedByCard()){
                view.reportError(new StringMessage("ERROR! You've already placed a dice using a card in this turn!",p));
                return;
            }
            if (model.getDiceStock().findDice(placement.getDice()) == -1) {
                view.reportError(new StringMessage("ERROR! Selected dice is not into dice stock!", p));
                return;
            }
            if (!dicePlacementController.isRowColOk(placement.getRow(), placement.getColumn())) {
                view.reportError(new StringMessage("ERROR! Insert a correct value for row and column!", p));
                return;
            }
            if (dicePlacementController.firstDice(p) && (placement.getRow() != 0 && placement.getRow() != 3 && placement.getColumn() != 0 && placement.getColumn() != 4)) {
                view.reportError(new StringMessage("ERROR! First dice must be placed on borders!", p));
                return;
            }
            if (!dicePlacementController.isBoxOk(p, placement.getRow(), placement.getColumn(), placement.getDice())) {
                view.reportError((new StringMessage("ERROR! Selected box is not compatible with selected dice!", p)));
                return;
            }
            if (!dicePlacementController.firstDice(p) && !dicePlacementController.similarDicesOk(p, placement.getRow(), placement.getColumn(), placement.getDice())) {
                view.reportError(new StringMessage("ERROR! There are some dices similar to the one you've selected near selected box", p));
                return;
            }
            if (!dicePlacementController.firstDice(p) && !dicePlacementController.alreadyPlacedDicesOk(p, placement.getRow(), placement.getColumn())) {
                view.reportError(new StringMessage("ERROR! A dice must be placed near already placed dices", p));
                return;
            }
            //Once controls are done, the dice is setted
            view.showMessage(new StringMessage("\nDICE color: " + placement.getDice().getColor() + " value: " + placement.getDice().getValue() + " placed in " + convertRow(placement.getRow()) + (placement.getColumn() + 1) + "\n\n", p));
            placeDice((DicePlacement)placement);
            p.setnMoves(p.getnMoves() + 1);
            gameRoundController.stopTimer();
            p.setDicePlacement();
            gameRoundController.updateTurn(p);
            showToolCards(p);
            if(!p.getNickname().equals(model.findPlayerByOrder(model.getTurn()).getNickname())) {
                showToolCards(model.findPlayerByName(model.findPlayerByOrder(model.getTurn()).getNickname()));
                view.showMessage(new StringMessage("CHOOSE AN OPTION: \n1)PLACE A DICE\n2)ACTIVATE A CARD\n3)PASS TURN\n0)EXIT\n",model.findPlayerByOrder(model.getTurn())));
            }
        }
    }

    /**
     * Places dice
     * @param placement dice placement
     * @throws RemoteException
     */
    private void placeDice(DicePlacement placement)throws RemoteException {
        Player p=model.findPlayerByName(placement.getPlayerNickName());
        if(p!=null){
            model.getDiceStock().extractDice(placement.getDice());
            p.getPlayerScheme().getScheme()[placement.getRow()][placement.getColumn()].setDice(placement.getDice());
            model.notifyPlacement(p);
        }}


    /**
     * Performs card activation
     * @param cardActivation card activation
     * @throws RemoteException
     */
    private synchronized void performCardActivation(Event cardActivation)throws RemoteException{
        Player p=model.findPlayerByName(cardActivation.getPlayerNickName());


        if(isPlayerSuspended(p)){
            view.reportError(new StringMessageError("ERROR! You have been suspended.",p,2));
            category=-2;
            return;
        }
        if (!isPlayerTurn(p)) {
            view.reportError(new StringMessageError("ERROR! Wait, it's not your turn.", p,2));
            category=-3;
            return;
        }
        if (cardActivationController.noOneCardsActivated()) {
            view.reportError(new StringMessageError("ERROR! In this moment you can't activated a card", p,2));
            category=-4;
            return;
        }

        if (cardActivationController.findCard(cardActivation.getCardNumber()) == null) {
            view.reportError(new StringMessageError("ERROR! Card not found.", p,1));
            category=-5;
            return;
        }

        if(!cardActivationController.findCard(cardActivation.getCardNumber()).getActivated()){
            view.reportError(new StringMessageError("ERROR! This card is not activaded", p,1));
            category=-6;
            return;
        }

        if(gameInitController.isSinglePlayer()){
            if(model.getDiceStock().findDice(cardActivation.getDice())==-1){
                view.reportError(new StringMessageError("ERROR! The dice is not in Dice stock",p,1));
                category=-8;
                return;
            }
            if(!cardActivation.getDice().getColor().equals(cardActivationController.findCard(cardActivation.getCardNumber()).getSolitaryColor())){
                view.reportError(new StringMessageError("ERROR! The color of the dice is not the same of the card",p,1));
                category=-9;
                return;
            }
            int position=model.getDiceStock().findDice(cardActivation.getDice());
            model.getDiceStock().extractDice(cardActivation.getDice());

            if(cardActivation.getCardNumber()!=6&&cardActivationController.findCard(cardActivation.getCardNumber()) instanceof ChangeAndPlaceCard && cardActivationController.canPlaceAdice(p,cardActivation.getCardNumber())<=0){
                view.reportError(new StringMessageError("ERROR! You don't have enough dices that you can place in dice stock",p,2));
                model.getDiceStock().insertDiceInPosition(cardActivation.getDice(),position);
                category=-10;
                return;
            }

        }



        switch (cardActivation.getCardNumber()) {
            case 2:
            case 3:
            case 4:
            case 12:{

                view.notifyView(new SelectDiceToMoveView(p,cardActivation.getCardNumber(),cardActivationController.findCard(cardActivation.getCardNumber()).getNumberD()));
                category=0;
                break;
            }
            case 1:
            case 10: {
                category=1;
                view.notifyView(new SelectDiceView(p,cardActivation.getCardNumber()));
                break;
            }
            case 5: {

                view.notifyView(new SelectDicesView(p,cardActivation.getCardNumber()));
                category=2;
                break;
            }

            case 7: {


                cardActivationController.findCard(cardActivation.getCardNumber()).activateEffect(model,cardActivation);
                view.showMessage(new StringMessage("All dices in Dice Stock was updates",p));
                category=4;
                break;
            }

            case 6:
            case 8:
            case 9:
            case 11:{

                view.notifyView(new ChooseAndPlaceView(p,cardActivation.getCardNumber(),cardActivationController.findCard(cardActivation.getCardNumber()).getNumberD()));
                category=3;
                break;
            }

            default:break;
        }

    }

    /**
     * Gets category
     * @return category
     */
    public int getCategory(){return category;}


    /**
     * Performs end turn
     * @param e event
     * @throws RemoteException
     */
    private synchronized void performEndTurn(Event e) throws RemoteException {
        Player p = model.findPlayerByName(e.getPlayerNickName());
        if (p != null) {
            if(isPlayerSuspended(p)){
                view.reportError(new StringMessage("ERROR! You have been suspended.",p));
                return;
            }
            if (!isPlayerTurn(p)) {
                view.reportError(new StringMessage("ERROR! Wait, it's not your turn.", p));
                return;
            }
            if (p.getnMoves() == 1) p.setnMoves(p.getnMoves() + 1);
            if (p.getnMoves() == 0) p.setnMoves(p.getnMoves() + 2);
            gameRoundController.stopTimer();
            gameRoundController.updateTurn(p);
            showToolCards(p);
            if(!p.getNickname().equals(model.findPlayerByOrder(model.getTurn()).getNickname())) {
                showToolCards(model.findPlayerByName(model.findPlayerByOrder(model.getTurn()).getNickname()));
                view.showMessage(new StringMessage("CHOOSE AN OPTION: \n1)PLACE A DICE\n2)ACTIVATE A CARD\n3)PASS TURN\n0)EXIT\n",model.findPlayerByOrder(model.getTurn())));
            }
            else{
                for(int i=0;i<model.getPlayerList().size();i++) {
                    if(!model.getPlayerList().get(i).getNickname().equals(model.findPlayerByOrder(model.getTurn()).getNickname())) {
                        view.showMessage(new StringMessage("CHOOSE AN OPTION: \n1)PLACE A DICE\n2)ACTIVATE A CARD\n3)PASS TURN\n0)EXIT\n", model.getPlayerList().get(i)));
                    }
                }
            }
        }
    }

    /**
     * Performs scheme selection
     * @param e event
     * @throws RemoteException
     */
    private synchronized void performSchemeSelection(Event e)throws RemoteException{
        /*
        CASO 0---> SCHEMA 1 FRONTE
        CASO 1---> SCHEMA 1 RETRO
        CASO 2---> SCHEMA 2 FRONTE
        CASO 3---> SCHEMA 2 RETRO

        NEI CASI 1 E 3 VADO AD INVERTIRE FRONTE E RETRO DELLE OFFERED SCHEME CARDS

         */
        Player p=model.findPlayerByName(e.getPlayerNickName());
        if(p!=null) {
            switch (e.getSchemeNumber()) {
                case 0: {
                    p.setPlayerScheme(p.getOfferedSchemeCards().get(0));
                    break;
                }
                case 1: {

                    p.setPlayerScheme(p.getOfferedSchemeCards().get(0).getRetro());
                    break;
                }
                case 2: {
                    p.setPlayerScheme(p.getOfferedSchemeCards().get(1));
                    break;
                }
                case 3: {
                    p.setPlayerScheme(p.getOfferedSchemeCards().get(1).getRetro());
                    break;
                }
                default:
                    view.reportError(new StringMessageError("ERROR! Insert a correct value for the scheme",p,1));
                    return;
            }
            gameInitController.initFavorMarkers(p);

            view.showMessage(new StringMessage("\nYour Scheme Card\n" + p.getPlayerScheme().toString() + "\n" + p.getPrivateGoalCard().toString() + "\n\n"+model.printPublicGoalCards()+"\n"+ model.getDiceStock().toString()+"Favor Markers: "+p.getFavorMarkers(), p));

            showToolCards(p);
            view.showMessage(new StringMessage("ROUND: " + model.getRound() + " PLAYER: " + model.findPlayerByOrder(model.getTurn()).getNickname() +" TURN: "+model.findPlayerByOrder(model.getTurn()).getnTurns()+"\n", p));
            if(p.getOrder()==1){
                gameRoundController.setTimer(model.findPlayerByOrder(1).getnMoves(),1);//Inizializzo il timer sospensioni...per il giocatore con ordine 1
            }
        }
    }

    /**
     * Performs events and actions if a player has been suspended or if it is not their turn
     * @param e event
     * @throws RemoteException
     */
    private synchronized void playerEscape(Event e)throws RemoteException{
        Player p=model.findPlayerByName(e.getPlayerNickName());
        if(isPlayerSuspended(p)){
            view.reportError(new StringMessage("ERROR! You have been suspended.",p));
            return;
        }
        if (!isPlayerTurn(p)) {
            view.reportError(new StringMessage("ERROR! Wait, it's not your turn.", p));
            return;
        }
        if(model.getPlayerList().size()>1){
            if(model.getPlayerList().size()-gameRoundController.countEscaped()>2){
                if(isPlayerTurn(p)){//Se è il turno di un giocatore (attivo chiaramente)
                    p.escape();
                    p.setnMoves(2);
                    gameRoundController.updateTurn(p);
                }
            }
            else{
                p.escape();
                p.setnMoves(2);
                model.notifyPlayerQuit(p);
                model.getFirstActive().setVictoryPoints(scoreController.calculateVictoryPoints(model.getFirstActive()));//Il get First Active qui rappresenta chiaramente l'ultimo rimasto
                model.setGameOverMP(model.getFirstActive());
            }
        }else{
            view.showMessage(new StringMessage("You are out of the game", p));
        }
    }

    /**
     * Performs controls and activates card category that changes dice value from dice stock
     * @param event event
     * @throws RemoteException
     */
    private synchronized void performChangeDice(Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());
        if(model.getDiceStock().findDice(event.getDice())==-1){
            view.reportError(new StringMessageError("ERROR! The dice is not in Dice stock",p,1));
            category=-2;
            return;
        }
        if(model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).getValue()==1&&event.getAction().equals("D")){
            view.reportError(new StringMessageError("ERROR! Can't decrement a dice with value 1",p,1));
            category=-3;
            return;
        }
        if(model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).getValue()==6&&event.getAction().equals("I")){
            view.reportError(new StringMessageError("ERROR! Can't increment a dice with value 6",p,1));
            category=-4;
            return;
        }
        cardActivationController.findCard(event.getCardNumber()).activateEffect(model,event);
        if(event.getAction().contains("I"))
        {view.showMessage(new StringMessage(event.getDice().getColor()+""+event.getDice().getValue()+" was incremented",p));}
        else if(event.getAction().contains("D")){view.showMessage(new StringMessage(event.getDice().getColor()+""+event.getDice().getValue()+" was decremented",p));
        }
        else{view.showMessage(new StringMessage("The dice was turn in the oposite side",p));}

    }

    /**
     * Performs end card activation if successful
     * @param event event
     * @throws RemoteException
     */
    private synchronized void performEndCardActivation(Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());
        if(p!=null) {

            if(!gameInitController.isSinglePlayer()){
                if(cardActivationController.findCard(event.getCardNumber()).isAlreadyUsed()){
                    p.setFavorMarkers(p.getFavorMarkers()-2);
                }else{p.setFavorMarkers(p.getFavorMarkers()-1);}
            }
            p.setnMoves(p.getnMoves() + 1);
            p.setCardActivated();
            gameRoundController.stopTimer();
            if(cardActivationController.findCard(event.getCardNumber()) instanceof ChangeAndPlaceCard){
                p.setDicePlacedByCard();
            }
            if(event.getCardNumber()==8){
                p.setAvoidNextTurn(true);
            }
            cardActivationController.findCard(event.getCardNumber()).used();
            if(gameInitController.isSinglePlayer())toolCardList.remove(cardActivationController.findCard(event.getCardNumber()));
            model.notifyCardActivation(p);
            gameRoundController.updateTurn(p);
            showToolCards(p);
            if(!p.getNickname().equals(model.findPlayerByOrder(model.getTurn()).getNickname())) {
                showToolCards(model.findPlayerByName(model.findPlayerByOrder(model.getTurn()).getNickname()));
                view.showMessage(new StringMessage("CHOOSE AN OPTION: \n1)PLACE A DICE\n2)ACTIVATE A CARD\n3)PASS TURN\n0)EXIT\n",model.findPlayerByOrder(model.getTurn())));
            }


        }
    }

    /**
     * Performs controls and dice move
     * @param event event
     * @throws RemoteException
     */
    private synchronized void performMoveDice(Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());

        if(!dicePlacementController.isRowColOk(event.getDiceRow(),event.getDiceColum())||!dicePlacementController.isRowColOk(event.getRow(),event.getColumn())){
            view.reportError(new StringMessageError("ERROR! Insert a correct value for row and column of the dice!",p,1));
            category=-2;
            return;
        }
        if(p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].getDice()==null){
            view.reportError(new StringMessageError("ERROR! there isn't a dice in this position!",p,1));
            category=-3;
            return;
        }


        Dice dice=p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].getDice();

        if(event.getCardNumber()==12&&!dice.getColor().equals(cardActivationController.findCard(event.getCardNumber()).getColorDice())){
            view.reportError(new StringMessageError("ERROR! The dice color is not the same that the color you have select in rounds track!",p,1));
            category=-10;
            return;
        }


        p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(null);

        if(dicePlacementController.firstDice(p)&&(event.getRow()!=0&&event.getRow()!=3&&event.getColumn()!=0&&event.getColumn()!=4)){
            p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(dice);
            view.reportError(new StringMessageError("ERROR! First dice must be placed on borders!",p,1));
            category=-4;
            return;
        }

        if(cardActivationController.findCard(event.getCardNumber()).getRestriction()==Restriction.SHADE){
            if(!dicePlacementController.isBoxOkColor(p,event.getRow(),event.getColumn(),dice)) {
                p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(dice);
                view.reportError(new StringMessageError("ERROR! Selected box is not compatible with selected dice!", p,1));
                category=-5;
                return;
            }
        }
        if(cardActivationController.findCard(event.getCardNumber()).getRestriction()==Restriction.COLOR){

            if(!dicePlacementController.isBoxOkShade(p,event.getRow(),event.getColumn(),dice)){
                p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(dice);
                view.reportError((new StringMessageError("ERROR! Selected box is not compatible with selected dice!",p,1)));
                category=-6;
                return;
            }
        }

        if(cardActivationController.findCard(event.getCardNumber()).getRestriction()==Restriction.NULL){
            if(!dicePlacementController.isBoxOk(p,event.getRow(),event.getColumn(),dice)){
                p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(dice);
                view.reportError((new StringMessageError("ERROR! Selected box is not compatible with selected dice!",p,1)));
                category=-7;
                return;
            }
        }

        if(!dicePlacementController.firstDice(p)&&!dicePlacementController.similarDicesOk(p,event.getRow(),event.getColumn(),dice)){
            p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(dice);
            view.reportError(new StringMessageError("ERROR! There are some dices similar to the one you've selected near selected box",p,1));
            category=-8;
            return;
        }
        if(!dicePlacementController.firstDice(p)&&!dicePlacementController.alreadyPlacedDicesOk(p,event.getRow(),event.getColumn())){
            p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(dice);
            view.reportError(new StringMessageError("ERROR! A dice must be placed near already placed dices",p,1));
            category=-9;
            return;
        }
        //once the controls are done the card effect are activeded
        p.getPlayerScheme().getScheme()[event.getDiceRow()][event.getDiceColum()].setDice(dice);
        cardActivationController.findCard(event.getCardNumber()).activateEffect(model,event);
        view.showMessage(new StringMessage("\nDICE color: " + dice.getColor() + " value: " + dice.getValue()+ " placed in " +convertRow(event.getRow())+(event.getColumn()+1) +"\n\n",p));
    }

    /**
     * Performs dice change from round track to dice stock (tool card number 5)
     * @param event
     * @throws RemoteException
     */
    private synchronized void performChangeDices(Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());
        if(model.getDiceStock().findDice(event.getDice())==-1){
            view.reportError(new StringMessageError("ERROR! The dice is not in Dice stock",p,1));
            category=-2;
            return;
        }
        if(model.getRoundsTrack().findDice(event.getDiceRound())==-1){
            view.reportError(new StringMessageError("ERROR! The dice is not in Rounds track",p,1));
            category=-3;
            return;
        }

        cardActivationController.findCard(event.getCardNumber()).activateEffect(model,event);
        view.showMessage(new StringMessage(event.getDice().getColor()+""+event.getDice().getValue()+" is now in Rounds track and "+event.getDiceRound().getColor()+""+event.getDiceRound().getValue()+" is now in Dice stock", p));
    }

    /**
     * Performs dice changes before placing them with change and place cards effect
     * @param event event
     * @throws RemoteException
     */
    private synchronized void performChanges(Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());
        if(model.getDiceStock().findDice(event.getDice())==-1){
            category=-2;
            view.reportError(new StringMessageError("ERROR! The dice is not in Dice stock",p,1));
            return;
        }else {
            switch (event.getCardNumber()) {
                case 6:
                    changeValue(p, event.getDice());
                    break;
                case 8:
                    category=2;
                    view.notifyView(new DiceChange("Puoi piazzare il dado.", p, event.getDice()));
                    break;
                case 9:
                    category=3;
                    view.notifyView(new DiceChange("Puoi piazzare il dado.", p, event.getDice()));
                    break;
                case 11:
                    takeAnotherDice(p, event.getDice());
                    break;
            }
        }

    }


    /**
     * Puts dice back in dice bag and picks up a new one for tool card number 11
     * @param p
     * @param dice
     * @throws RemoteException
     */
    private void takeAnotherDice(Player p,Dice dice) throws RemoteException {
        int position=model.getDiceStock().findDice(dice);
        Dice diceT= model.getDiceBag().extractRandomDice();
        model.getDiceStock().extractDice(dice);
        model.getDiceStock().insertDiceInPosition(diceT,position);
        view.notifyView(new DiceChange("You extract on the bag the dice with color: "+diceT.getColor(),p,diceT));
    }

    /**
     * Rethrow a dice in dice stock
     * @param p player
     * @param dice dice
     * @throws RemoteException
     */
    private synchronized void changeValue(Player p,Dice dice) throws RemoteException {
        int position=model.getDiceStock().findDice(dice);
        model.getDiceStock().setDiceValue(position);
        Dice diceC= model.getDiceStock().getDice(position);
        view.notifyView(new DiceChange(""+dice.getColor()+""+dice.getValue()+" è ora "+diceC.getColor()+""+diceC.getValue(),p,diceC));
        if(!cardActivationController.canPlaceDice(p,diceC,6)){
            view.reportError((new StringMessageError("The dice can't be pleaced and will be back in dice stock",p,3)));
            category=-3;
        }else{
            category=1;
        }
    }

    /**
     * Places dice activating change and place card effect
     * @param event event
     * @throws RemoteException
     */
    private synchronized void performCardPlacement(Event event) throws RemoteException {
        Player p=model.findPlayerByName(event.getPlayerNickName());



        if(!dicePlacementController.isRowColOk(event.getRow(),event.getColumn())){
            view.reportError(new StringMessageError("ERROR! Insert a correct value for row and column!",p,1));
            category=-2;
            return;
        }
        if(dicePlacementController.firstDice(p)&&(event.getRow()!=0&&event.getRow()!=3&&event.getColumn()!=0&&event.getColumn()!=4)){
            view.reportError(new StringMessageError("ERROR! First dice must be placed on borders!",p,1));
            category=-3;
            return;
        }
        if(!dicePlacementController.isBoxOk(p,event.getRow(),event.getColumn(),event.getDice())){
            view.reportError((new StringMessageError("ERROR! Selected box is not compatible with selected dice!",p,1)));
            category=-4;
            return;
        }
        if(!dicePlacementController.firstDice(p)&&!dicePlacementController.similarDicesOk(p,event.getRow(),event.getColumn(),event.getDice())){
            view.reportError(new StringMessageError("ERROR! There are some dices similar to the one you've selected near selected box",p,1));
            category=-5;
            return;
        }
        if(event.getCardNumber()!=9){
            if(!dicePlacementController.firstDice(p)&&!dicePlacementController.alreadyPlacedDicesOk(p,event.getRow(),event.getColumn())) {
                view.reportError(new StringMessageError("ERROR! A dice must be placed near already placed dices", p,1));
                category=-6;
                return;
            }
        }

        model.getDiceStock().extractDice(event.getDice());
        cardActivationController.findCard(event.getCardNumber()).activateEffect(model,event);
        view.showMessage(new StringMessage("\nDICE color: " + event.getDice().getColor() + " value: " + event.getDice().getValue()+ " placed in " +convertRow(event.getRow())+(event.getColumn()+1) +"\n\n",p));
    }

    /**
     * Updates event
     * @param event event
     * @throws RemoteException
     */
    @Override
    public void update(Event event) throws RemoteException{
        if (event instanceof DicePlacement) {
            performDicePlacement(event);
        }
        if (event instanceof CardActivation) {
            performCardActivation(event);
        }
        if (event instanceof EndTurn) {
            performEndTurn(event);
        }
        if(event instanceof SchemeSelection){
            performSchemeSelection(event);
        }
        if(event instanceof QuitPlayerEvent){
            playerEscape(event);
        }
        if(event instanceof EndCardActivation){
            performEndCardActivation(event);
        }
        if(event instanceof ChangeDice){
            performChangeDice(event);

        }
        if(event instanceof MoveDice){
            performMoveDice(event);

        }
        if(event instanceof ChangeDiceDR){
            performChangeDices(event);

        }
        if(event instanceof ChangeAndPlace){
            performChanges(event);

        }
        if(event instanceof DicePlacementCard){
            performCardPlacement(event);

        }
        if(event instanceof SetDice){

            model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(event.getValue());


        }
        if(event instanceof ColorDice){
            if(!model.getRoundsTrack().findDice(event.getColor())){
                category=-2;
                view.reportError(new StringMessageError("ERROR! The color is not in Round Track", model.findPlayerByName(event.getPlayerNickName()),1));
            }else{
                cardActivationController.findCard(event.getCardNumber()).setColorDice(event.getColor());
            }
        }
        if(event instanceof SetDiff){
            Player p=model.findPlayerByName(event.getPlayerNickName());
            difficult=event.getDifficult();
            loadToolCards();
            view.showMessage(new StringMessage("\nYour Scheme Card\n" + p.getPlayerScheme().toString() + "\n" + p.getPrivateGoalCard().toString() + "\n\n"+model.printPublicGoalCards()+"\n"+ model.getDiceStock().toString(), p));
            view.showMessage(new StringMessage("ROUND: " + model.getRound() + " PLAYER: " + model.findPlayerByOrder(model.getTurn()).getNickname() +" TURN: "+model.findPlayerByOrder(model.getTurn()).getnTurns()+"\n", p));
            showToolCards(model.findPlayerByName(event.getPlayerNickName()));

        }
    }

}