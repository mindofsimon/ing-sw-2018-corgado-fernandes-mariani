package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.common.view.ViewInterface;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.*;
import it.polimi.deib.se2018.server.controller.toolcard.PlusOrMinEffect;
import it.polimi.deib.se2018.server.controller.toolcard.ToolCard;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.common.utils.Observer;
import it.polimi.deib.se2018.server.model.player.schemecard.SchemeCard;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controller implements Observer<Event> {

    private Model model;
    private RemoteView view;//IN REALTA' E' LA REMOTE...MA IL CONTROLLER NON LO SA
    private ArrayList <ToolCard> toolCardList;
    private ScoreController scoreController;
    private GameInitController gameInitController;
    private GameRoundController gameRoundController;
    private DicePlacementController dicePlacementController;
    private CardActivationController cardActivationController;

    public Controller(Model m, RemoteView v){
        view=v;
        model=m;
        toolCardList=new ArrayList<ToolCard>(3);
        scoreController=new ScoreController(m,v);
        gameInitController=new GameInitController(m);
        gameRoundController=new GameRoundController(m,v,scoreController);
        dicePlacementController=new DicePlacementController();
        cardActivationController= new CardActivationController(m,toolCardList);
    }

    public GameRoundController getGameRoundController() {
        return gameRoundController;
    }

    public ArrayList<ToolCard> getToolCardsList() { return toolCardList; }

    public void addToolCard(ToolCard c){ toolCardList.add(c); }

    public Model getModel() {
        return model;
    }

    public ViewInterface getView() {
        return view;
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
    public void initGame()throws RemoteException{
        gameInitController.init();
        gameRoundController.updateDiceStock();
        //INIZIO A PROVARE QUALCHE TOOL CARD
        ToolCard toolCard1=new PlusOrMinEffect("Pinza Sgrossatrice",DiceColor.VIOLET,1);
        addToolCard(toolCard1);
        schemeSelection();
    }

    private void schemeSelection()throws RemoteException{
        for(int i=0;i<model.getPlayerList().size();i++){
            view.showMessage(new StringMessage("WELCOME TO SAGRADA!\n"+model.getPlayerList().get(i).getPrivateGoalCard().toString()+"\nPlease select your scheme card(0-FIRST FRONT/1-FIRST RETRO/2-SECOND FRONT/3-SECOND RETRO):\n",model.getPlayerList().get(i)));
            view.showMessage(new StringMessage(model.getPlayerList().get(i).getOfferedSchemeCards().get(0).basicVisualization()+"\n\n"+
                    model.getPlayerList().get(i).getOfferedSchemeCards().get(0).getRetro().basicVisualization()+"\n\n"+
                    model.getPlayerList().get(i).getOfferedSchemeCards().get(1).basicVisualization()+"\n\n"+
                    model.getPlayerList().get(i).getOfferedSchemeCards().get(1).getRetro().basicVisualization(),model.getPlayerList().get(i)));
        }
    }

    private boolean isPlayerTurn(Player p)throws RemoteException{return model.getTurn()==p.getOrder();}

    private boolean isDicePlacementAlreadyDone(Player p){
        if(p.dicePlaced()) return true;
        else return false;
    }

    private boolean isCardActivationAlreadyDone(Player p){
        if(p.cardActivated()) return true;
        else return false;
    }

    //Perform actions and events methods
    private synchronized void performDicePlacement(Event placement)throws RemoteException{
        Player p=findPlayerByName(placement.getPlayerNickName());
        if(!isPlayerTurn(p)){
            view.reportError(new StringMessage("ERROR! Wait, it's not your turn.",p));
            return;
        }
        if(isDicePlacementAlreadyDone(p)){
            view.reportError(new StringMessage("ERROR! You've already placed a dice in this turn!",p));
            return;
        }
        if(model.getDiceStock().findDice(placement.getDice())==-1){
            view.reportError(new StringMessage("ERROR! Selected dice is not into dice stock!",p));
            return;
        }
        if(!dicePlacementController.isRowColOk(placement.getRow(),placement.getColumn())){
            view.reportError(new StringMessage("ERROR! Insert a correct value for row and column!",p));
            return;
        }
        if(dicePlacementController.firstDice(p)&&(placement.getRow()!=0&&placement.getRow()!=3&&placement.getColumn()!=0&&placement.getColumn()!=4)){
            view.reportError(new StringMessage("ERROR! First dice must be placed on borders!",p));
            return;
        }
        if(!dicePlacementController.isBoxOk(p,placement.getRow(),placement.getColumn(),placement.getDice())){
            view.reportError((new StringMessage("ERROR! Selected box is not compatible with selected dice!",p)));
            return;
        }
        if(!dicePlacementController.firstDice(p)&&!dicePlacementController.similarDicesOk(p,placement.getRow(),placement.getColumn(),placement.getDice())){
            view.reportError(new StringMessage("ERROR! There are some dices similar to the one you've selected near selected box",p));
            return;
        }
        if(!dicePlacementController.firstDice(p)&&!dicePlacementController.alreadyPlacedDicesOk(p,placement.getRow(),placement.getColumn())){
            view.reportError(new StringMessage("ERROR! A dice must be placed near already placed dices",p));
            return;
        }
        //Once controls are done, the dice is setted
        placeDice((DicePlacement)placement);
        view.showMessage(new StringMessage("\nDICE color: " + placement.getDice().getColor() + " value: " + placement.getDice().getValue() + " placed in " +convertRow(placement.getRow())+(placement.getColumn()+1) +"\n\n",p));
        p.setnMoves(p.getnMoves()+1);
        p.setDicePlacement();
        gameRoundController.updateTurn(p);
    }

    private void placeDice(DicePlacement placement)throws RemoteException {
        Player p=findPlayerByName(placement.getPlayerNickName());
        if(p!=null){
            model.getDiceStock().extractDice(placement.getDice());
            p.getPlayerScheme().getScheme()[placement.getRow()][placement.getColumn()].setDice(placement.getDice());
            model.notifyPlacement(placement);
        }}

    private Player findPlayerByName(String name)throws RemoteException{
        for(int i=0;i<model.getPlayerList().size();i++){
            if(model.getPlayerList().get(i).getNickname().equals(name)) return model.getPlayerList().get(i);
        }
        return null;

    }


    private synchronized void performCardActivation(Event cardActivation)throws RemoteException{
        Player p=findPlayerByName(cardActivation.getPlayerNickName());
        if(p!=null) {
            if (!isPlayerTurn(p)) {
                view.reportError(new StringMessage("ERROR! Wait, it's not your turn.", p));
                return;
            }
            if (isCardActivationAlreadyDone(p)) {
                view.reportError(new StringMessage("ERROR! You've already activated a card in this turn.", p));
                return;
            }
            /*if (cardActivationController.findCard(cardActivation.getCardNumber()) == null) {
                view.reportError(new StringMessage("ERROR! Card not found.", p));
                return;
            }
            if (!cardActivationController.canTakeCard(p, cardActivationController.findCard(cardActivation.getCardNumber()))) {
                view.reportError(new StringMessage("ERROR! Not enough resources to take card", p));
                return;
            }
            //PER ORA USO SOLO UNA CARTA, POI DOVREMO LANCIARE UN METODO SPECIFICO IN BASE AL TIPO DI CARTA
            //PER ORA CERCO DI CAPIRE SE L'EFFETTO FUNZIONA, POI VALUTERO' LE DIFFERENZE TRA 1P e MP
            Dice d = view.selectDice();
            if (model.getDiceStock().findDice(d) == -1) {
                view.reportError(new StringMessage("ERROR! Selected dice is not into dice stock!", p));
                return;
            }
            String increment = view.increment();
            if (!increment.equals("I") && !increment.equals("D")) {
                view.reportError(new StringMessage("ERROR! Not a valid option.", p));
                return;
            }
            if (increment.equals("I"))
                cardActivationController.findCard(cardActivation.getCardNumber()).activateEffect(p, model.getDiceStock().findDice(d), model.getDiceStock(), true);
            if (increment.equals("D"))
                cardActivationController.findCard(cardActivation.getCardNumber()).activateEffect(p, model.getDiceStock().findDice(d), model.getDiceStock(), false);
            */
            p.setnMoves(p.getnMoves() + 1);
            p.setCardActivated();
            model.performCardActivation(p,cardActivation.getCardNumber());
            gameRoundController.updateTurn(p);
        }
    }

    private synchronized void performEndTurn(Event e) throws RemoteException {
        Player p = findPlayerByName(e.getPlayerNickName());
        if (p != null) {
            if (!isPlayerTurn(p)) {
                view.reportError(new StringMessage("ERROR! Wait, it's not your turn.", p));
                return;
            }
            if (p.getnMoves() == 1) p.setnMoves(p.getnMoves() + 1);
            if (p.getnMoves() == 0) p.setnMoves(p.getnMoves() + 2);
            gameRoundController.updateTurn(p);

        }
    }

    private synchronized void performSchemeSelection(Event e)throws RemoteException{
        /*
        CASO 0---> SCHEMA 1 FRONTE
        CASO 1---> SCHEMA 1 RETRO
        CASO 2---> SCHEMA 2 FRONTE
        CASO 3---> SCHEMA 2 RETRO

        NEI CASI 1 E 3 VADO AD INVERTIRE FRONTE E RETRO DELLE OFFERED SCHEME CARDS

         */
        Player p=findPlayerByName(e.getPlayerNickName());
        if(p!=null) {
            SchemeCard temp;
            switch (e.getSchemeNumber()) {
                case 0: {
                    p.setPlayerScheme(p.getOfferedSchemeCards().get(0));
                    break;
                }
                case 1: {
                    temp = p.getOfferedSchemeCards().get(0).getRetro();
                    p.getOfferedSchemeCards().get(0).setRetro(p.getOfferedSchemeCards().get(0));
                    p.getOfferedSchemeCards().get(0).setScheme(temp);
                    p.setPlayerScheme(p.getOfferedSchemeCards().get(0));
                    break;
                }
                case 2: {
                    p.setPlayerScheme(p.getOfferedSchemeCards().get(1));
                    break;
                }
                case 3: {
                    temp = p.getOfferedSchemeCards().get(1).getRetro();
                    p.getOfferedSchemeCards().get(1).setRetro(p.getOfferedSchemeCards().get(1));
                    p.getOfferedSchemeCards().get(1).setScheme(temp);
                    p.setPlayerScheme(p.getOfferedSchemeCards().get(1));
                    break;
                }
                default:break;
            }
            gameInitController.initFavorMarkers(p);
            view.showMessage(new StringMessage("\nYour Scheme Card\n" + p.getPlayerScheme().toString() + "\n" + p.getPrivateGoalCard().toString() + "\n\n"+model.printPublicGoalCards()+"\n"+ model.getDiceStock().toString(), p));
            view.showMessage(new StringMessage("ROUND: " + model.getRound() + " PLAYER: " + model.findPlayerByOrder(model.getTurn()).getNickname() +" TURN: "+model.findPlayerByOrder(model.getTurn()).getnTurns()+"\n", p));
        }
    }

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
    }

}