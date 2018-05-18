package it.polimi.deib.se2018.controller;

import it.polimi.deib.se2018.model.Model;
import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.dice.DiceColor;
import it.polimi.deib.se2018.model.events.CardActivation;
import it.polimi.deib.se2018.model.events.DicePlacement;
import it.polimi.deib.se2018.model.events.EndTurn;
import it.polimi.deib.se2018.model.events.Event;
import it.polimi.deib.se2018.controller.toolcard.PlusOrMinEffect;
import it.polimi.deib.se2018.controller.toolcard.ToolCard;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.utils.Observer;
import it.polimi.deib.se2018.view.View;

import java.util.ArrayList;

public class Controller implements Observer<Event> {

    private Model model;
    private View view;
    private ArrayList <ToolCard> toolCardList;
    private ScoreController scoreController;
    private GameInitController gameInitController;
    private GameRoundController gameRoundController;
    private DicePlacementController dicePlacementController;
    private CardActivationController cardActivationController;

    public Controller(Model m, View v){
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

    public View getView() {
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
    public void initGame(){
        //MANCA LA CREAZIONE SCHEMI
        gameInitController.init();
        //INIZIO A PROVARE QUALCHE TOOL CARD
        ToolCard toolCard1=new PlusOrMinEffect("Pinza Sgrossatrice",DiceColor.VIOLET,1);
        addToolCard(toolCard1);
    }


    private boolean isPlayerTurn(Player p){return model.getTurn()==p.getOrder();}

    private boolean isDicePlacementAlreadyDone(Player p){
        if(p.dicePlaced()) return true;
        else return false;
    }

    private boolean isCardActivationAlreadyDone(Player p){
        if(p.cardActivated()) return true;
        else return false;
    }

    //Perform actions and events methods
    private synchronized void performDicePlacement(Event placement){
        if(!isPlayerTurn(placement.getPlayer())){
            view.reportError("ERROR! Wait, it's not your turn.");
            return;
        }
        if(isDicePlacementAlreadyDone(placement.getPlayer())){
            view.reportError("ERROR! You've already placed a dice in this turn!");
            return;
        }
        if(model.getDiceStock().findDice(placement.getDice())==-1){
            view.reportError("ERROR! Selected dice is not into dice stock!");
            return;
        }
        if(!dicePlacementController.isRowColOk(placement.getRow(),placement.getColumn())){
            view.reportError("ERROR! Insert a correct value for row and column!");
            return;
        }
        if(dicePlacementController.firstDice(placement.getPlayer())&&(placement.getRow()!=0&&placement.getRow()!=3&&placement.getColumn()!=0&&placement.getColumn()!=4)){
            view.reportError("ERROR! First dice must be placed on borders!");
            return;
        }
        if(!dicePlacementController.isBoxOk(placement.getPlayer(),placement.getRow(),placement.getColumn(),placement.getDice())){
            view.reportError(("ERROR! Selected box is not compatible with selected dice!"));
            return;
        }
        if(!dicePlacementController.firstDice(placement.getPlayer())&&!dicePlacementController.similarDicesOk(placement.getPlayer(),placement.getRow(),placement.getColumn(),placement.getDice())){
            view.reportError("ERROR! There are some dices similar to the one you've selected near selected box");
            return;
        }
        if(!dicePlacementController.firstDice(placement.getPlayer())&&!dicePlacementController.alreadyPlacedDicesOk(placement.getPlayer(),placement.getRow(),placement.getColumn())){
            view.reportError("ERROR! A dice must be placed near already placed dices");
            return;
        }
        //Once controls are done, the dice is setted
        placeDice((DicePlacement)placement);
        view.showMessage("\nDICE color: " + placement.getDice().getColor() + " value: " + placement.getDice().getValue() + " placed in " +convertRow(placement.getRow())+(placement.getColumn()+1) +"\n\n");
        placement.getPlayer().setnMoves(placement.getPlayer().getnMoves()+1);
        placement.getPlayer().setDicePlacement();
        gameRoundController.updateTurn(placement.getPlayer());
    }

    private void placeDice(DicePlacement p){
        model.getDiceStock().extractDice(p.getDice());
        p.getPlayer().getPlayerScheme().getScheme()[p.getRow()][p.getColumn()].setDice(p.getDice());
        model.notifyPlacement(p);
    }



    private synchronized void performCardActivation(Event cardActivation) {
        if (!isPlayerTurn(cardActivation.getPlayer())) {
            view.reportError("ERROR! Wait, it's not your turn.");
            return;
        }
        if(isCardActivationAlreadyDone(cardActivation.getPlayer())){
            view.reportError("ERROR! You've already activated a card in this turn.");
            return;
        }
        if (cardActivationController.findCard(cardActivation.getCardNumber()) == null) {
            view.reportError("ERROR! Card not found.");
            return;
        }
        if (!cardActivationController.canTakeCard(cardActivation.getPlayer(), cardActivationController.findCard(cardActivation.getCardNumber()))) {
            view.reportError("ERROR! Not enough resources to take card");
            return;
        }
        //PER ORA USO SOLO UNA CARTA, POI DOVREMO LANCIARE UN METODO SPECIFICO IN BASE AL TIPO DI CARTA
        //PER ORA CERCO DI CAPIRE SE L'EFFETTO FUNZIONA, POI VALUTERO' LE DIFFERENZE TRA 1P e MP
        Dice d = view.selectDice();
        if (model.getDiceStock().findDice(d) == -1) {
            view.reportError("ERROR! Selected dice is not into dice stock!");
            return;
        }
        String increment = view.increment();
        if (!increment.equals("I") && !increment.equals("D")) {
            view.reportError("ERROR! Not a valid option.");
            return;
        }
        if (increment.equals("I"))
            cardActivationController.findCard(cardActivation.getCardNumber()).activateEffect(cardActivation.getPlayer(), model.getDiceStock().findDice(d), model.getDiceStock(), true);
        if (increment.equals("D"))
            cardActivationController.findCard(cardActivation.getCardNumber()).activateEffect(cardActivation.getPlayer(), model.getDiceStock().findDice(d), model.getDiceStock(), false);
        cardActivation.getPlayer().setnMoves(cardActivation.getPlayer().getnMoves()+1);
        cardActivation.getPlayer().setCardActivated();
        gameRoundController.updateTurn(cardActivation.getPlayer());
    }

    private synchronized void performEndTurn(Event e){
        if(!isPlayerTurn(e.getPlayer())){
            view.reportError("ERROR! Wait, it's not your turn.");
            return;
        }
        if(e.getPlayer().getnMoves()==1) e.getPlayer().setnMoves(e.getPlayer().getnMoves()+1);
        if(e.getPlayer().getnMoves()==0) e.getPlayer().setnMoves(e.getPlayer().getnMoves()+2);
        gameRoundController.updateTurn(e.getPlayer());
    }

    @Override
    public void update(Event event) {
        if(event instanceof DicePlacement) {
            performDicePlacement(event);
        }
        if(event instanceof CardActivation){
            performCardActivation(event);
        }
        if(event instanceof EndTurn){
            performEndTurn(event);
        }
    }

}


