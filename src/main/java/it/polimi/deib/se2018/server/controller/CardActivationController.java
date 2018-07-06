package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.controller.toolcard.ChangeAndPlaceCard;
import it.polimi.deib.se2018.server.controller.toolcard.MoveDices;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.controller.toolcard.ToolCard;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.player.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Card activation controller class
 * @author Simone Marian, Sirlan Fernandes
 */
public class CardActivationController {

    private Model model;
    private ArrayList<ToolCard> toolCardList;
    private DicePlacementController dicePlacementController;

    /**
     * Constructor, initializes card activation controller class
     * @param m model
     * @param list tool card list
     * @param dicePlacementController dice placement controller
     */
    public CardActivationController(Model m,ArrayList<ToolCard>list,DicePlacementController dicePlacementController){
        model=m;
        toolCardList=list;
        this.dicePlacementController=dicePlacementController;
    }

    /**
     * Checks if a player has already activated a card in current turn
     * @param p player
     * @return true if card has been already activated, else false
     */
    public boolean isCardActivationAlreadyDone(Player p){
        return p.cardActivated();

    }

    /**
     * Checks if a player has already placed a dice in current turn
     * @param p player
     * @return true if dice has been already placed, else false
     */
    public boolean isDicePlacementAlreadyDone(Player p){
        return p.dicePlaced();
    }


    /**
     * Checks if at least one card can be activated
     * @return true if at least one card can be activated, else false
     */    public boolean noOneCardsActivated(){
        boolean control=true;
        for(int i=0;i<toolCardList.size();i++) {
            if (toolCardList.get(i).getActivated()){
                control=false;
            }
        }
        return control;

    }

    /**
     * Sets if cards can be activated or not through specific controls
     * @param p player
     * @param singleP single player
     * @throws RemoteException
     */    public void setActivated(Player p,boolean singleP) throws RemoteException {

        for(int i=0;i<toolCardList.size();i++){
            boolean control=true;

            if (isCardActivationAlreadyDone(p)) {
                control=false;

            }

            if((toolCardList.get(i) instanceof ChangeAndPlaceCard && toolCardList.get(i).getNumber()!=8)&& isDicePlacementAlreadyDone(p)){
                control=false;
            }

            if(p.getnTurns()!=2&&p.getnMoves()!=1&&toolCardList.get(i).getNumber()==7){
                control=false;


            }
            if(toolCardList.get(i).getNumber()==8&&(p.getnTurns()==2||canPlaceAdice(p,toolCardList.get(i).getNumber())<=0||!isDicePlacementAlreadyDone(p))){
                control=false;


            }
            if(singleP){
                if(model.getDiceStock().size()<2){
                    control=false;
                }
                if(toolCardList.get(i).getNumber()==8&&model.getDiceStock().size()<3){
                    control=false;
                }
                if(!cardColorOk(toolCardList.get(i).getSolitaryColor())){
                    control=false;

                }
            }else{
                if(!canTakeCard(p,toolCardList.get(i))){
                    control=false;

                }
            }

            if((toolCardList.get(i) instanceof ChangeAndPlaceCard) && canPlaceAdice(p,toolCardList.get(i).getNumber())==0){
                control=false;
            }

            if((toolCardList.get(i) instanceof MoveDices) && canMoveAdice(p,toolCardList.get(i).getNumber())<=0){
                control=false;
            }

            if(toolCardList.get(i).getNumber()==4 && canMoveAdice(p,toolCardList.get(i).getNumber())<=1){
                control=false;
            }
            if(toolCardList.get(i).getNumber()==5&&(model.getRoundsTrack().size()<1||model.getDiceStock().size()<1)){
                control=false;
            }
            if(toolCardList.get(i).getNumber()==12 && !canMoveAdiceColor(p,toolCardList.get(i).getNumber())){
                control=false;
            }
            toolCardList.get(i).activated(control);
        }

    }

    /**
     * Checks if a dice can be placed or not
     * @param p player
     * @param dice dice
     * @param cardNumb card number
     * @return true if a dice can be placed, else false
     */
    public boolean canPlaceDice(Player p, Dice dice, int cardNumb){
        boolean control=false;
        if(dicePlacementController.firstDice(p)){
            control=true;
        }else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    switch (cardNumb) {
                        case 2:
                            if (dicePlacementController.isBoxOkShade(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && dicePlacementController.alreadyPlacedDicesOk(p, i, j)) {
                                control = true;
                            }

                            break;
                        case 3:
                            if (dicePlacementController.isBoxOkShade(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && dicePlacementController.alreadyPlacedDicesOk(p, i, j)) {
                                control = true;
                            }

                            break;

                        case 9:
                            if (dicePlacementController.isBoxOk(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice)) {
                                control = true;
                            }

                            break;

                        default:
                            if (dicePlacementController.isBoxOk(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && dicePlacementController.alreadyPlacedDicesOk(p, i, j)) {
                                control = true;
                            }

                            break;


                    }

                }
            }
        }
        return control;
    }

    /**
     * Counts how many dices are placeable
     * @param p player
     * @param cardNumb card number
     * @return number of placeable dices
     */
    public int canPlaceAdice(Player p,int cardNumb){
        int cont=0;
        if(model.getDiceStock().size()==0){return 0;}
        else{
            for(int i=0;i<model.getDiceStock().size();i++){
                if(canPlaceDice(p,model.getDiceStock().getDice(i),cardNumb))cont++;
            }
            return cont;}
    }

    /**
     * Counts how many dices are moveable
     * @param p player
     * @param cardNumb card number
     * @return number of moveable dices
     */
    public int canMoveAdice(Player p,int cardNumb){
        int cont=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++) {
                if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null){
                    Dice d=p.getPlayerScheme().getScheme()[i][j].getDice();
                    p.getPlayerScheme().getScheme()[i][j].setDice(null);
                    if (canPlaceDice(p, d,cardNumb)) cont++;
                    p.getPlayerScheme().getScheme()[i][j].setDice(d);
                }

            }
        }
        return cont;
    }

    /**
     * Checks if there are at least two moveable placed dices with the same color as a dice in the round track
     * @param p player
     * @param cardNumb card number
     * @return true if at least two moveable placed dices with the same color as a dice in the round track
     */
    public boolean canMoveAdiceColor (Player p,int cardNumb){
        int cont=0;
        boolean control=false;

        for(DiceColor c: DiceColor.values()){
            if(model.getRoundsTrack().findDice(c)){
                for(int i=0;i<4;i++){
                    for(int j=0;j<5;j++) {
                        if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null){
                            if(p.getPlayerScheme().getScheme()[i][j].getDice().getColor().equals(c)){
                                if (canPlaceDice(p, p.getPlayerScheme().getScheme()[i][j].getDice(),cardNumb)) cont++;
                            }
                        }

                    }
                }
                if(cont>1){control=true;}
            }
        }

        return control;
    }

    /**
     * Checks if there is a dice in the dice stock with the given color (if a card can be activated in single player mode)
     * @param color dice color
     * @return true if there is a dice in the dice stock with the given color
     */
    private boolean cardColorOk(DiceColor color){
        return model.getDiceStock().findDice(color);
    }

    /**
     * Finds tool card from tool card list
     * @param n card number
     * @return tool card or else null
     */
    public ToolCard findCard(int n){
        for(int i=0;i<toolCardList.size();i++){
            if(toolCardList.get(i).getNumber()==n) return toolCardList.get(i);
        }
        return null;
    }

    /**
     * Checks if a tool card can be taken
     * @param p player
     * @param c tool card
     * @return true if a tool card can be taken, else false
     * @throws RemoteException
     */
    public boolean canTakeCard(Player p, ToolCard c)throws RemoteException {

        if(c.isAlreadyUsed()&&p.getFavorMarkers()<2) return false;
        if(!c.isAlreadyUsed()&&p.getFavorMarkers()<1) return false;
        return true;


    }

}