package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.controller.toolcard.ChangeAndPlaceCard;
import it.polimi.deib.se2018.server.controller.toolcard.MoveDices;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.controller.toolcard.ToolCard;
import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.toolCardsEvents.ChangeAndPlace;
import it.polimi.deib.se2018.server.model.player.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class CardActivationController {

    private Model model;
    private ArrayList<ToolCard> toolCardList;
    private DicePlacementController dicePlacementController;

    public CardActivationController(Model m,ArrayList<ToolCard>list,DicePlacementController dicePlacementController){
        model=m;
        toolCardList=list;
        this.dicePlacementController=dicePlacementController;
    }

    //passando il giocatore,controllo se quel giocatore ha gia attivato una carta in questo turno,ritorna vero o falso
    public boolean isCardActivationAlreadyDone(Player p){
        if(p.cardActivated()) return true;
        else return false;
    }

    //passando il giocatore,controllo se quel giocatore ha piazzato un dado in questo turno,ritorna vero o falso
    public boolean isDicePlacementAlreadyDone(Player p){
        if(p.dicePlaced()) return true;
        else return false;
    }

    //controllo se almeno 1 carta è attivabile,torno vero o falso
    public boolean noOneCardsActivated(){
        boolean control=true;
        for(int i=0;i<toolCardList.size();i++) {
            if (toolCardList.get(i).getActivated() == true){
                control=false;
            }
        }
        return control;

    }

    //metodo che setta le toolcards come attivabili o meno tramitte controlli specifici
    public void setActivated(Player p,boolean singleP) throws RemoteException {

        for(int i=0;i<toolCardList.size();i++){
            boolean control=true;

            if (isCardActivationAlreadyDone(p)) {
                control=false;

            }

            if((toolCardList.get(i) instanceof ChangeAndPlaceCard)&& isDicePlacementAlreadyDone(p)){
                control=false;
            }

            if(p.getnTurns()!=2&&p.getnMoves()!=1&&toolCardList.get(i).getNumber()==7){
                control=false;


            }
            if(p.getnTurns()!=2&&toolCardList.get(i).getNumber()==8&&model.getDiceStock().size()<3){
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

            if((toolCardList.get(i) instanceof ChangeAndPlace) && canPlaceAdice(p,toolCardList.get(i).getNumber())<=0){
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

    public boolean canPlaceDice(Player p, Dice dice, int cardNumb){
        boolean control=false;
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++) {
                switch(cardNumb){
                    case 2:
                        if (!dicePlacementController.firstDice(p)) {
                            if (dicePlacementController.isBoxOkShade(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && dicePlacementController.alreadyPlacedDicesOk(p, i, j)) {
                                control = true;
                            }


                        }else{
                            if (dicePlacementController.isBoxOkShade(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && (i==0||i==3||j==0||j==4)) {
                                control = true;
                            }

                        }
                        break;
                    case 3:
                        if (!dicePlacementController.firstDice(p)) {
                            if (dicePlacementController.isBoxOkShade(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && dicePlacementController.alreadyPlacedDicesOk(p, i, j)) {
                                control = true;
                            }


                        }else{
                            if (dicePlacementController.isBoxOkColor(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && (i==0||i==3||j==0||j==4)) {
                                control = true;
                            }

                        }
                        break;

                    case 9:
                        if (!dicePlacementController.firstDice(p)) {
                            if (dicePlacementController.isBoxOkShade(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice)) {
                                control = true;
                            }


                        }else{
                            if (dicePlacementController.isBoxOkColor(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && (i==0||i==3||j==0||j==4)) {
                                control = true;
                            }

                        }
                        break;

                    default:
                        if (!dicePlacementController.firstDice(p)) {
                            if (dicePlacementController.isBoxOk(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && dicePlacementController.alreadyPlacedDicesOk(p, i, j)) {
                                control = true;
                            }


                        }else{
                            if (dicePlacementController.isBoxOk(p, i, j, dice) && dicePlacementController.similarDicesOk(p, i, j, dice) && (i==0||i==3||j==0||j==4)) {
                                control = true;
                            }

                        }
                        break;


                }

            }
        }
        return control;
    }

    public int canPlaceAdice(Player p,int cardNumb){
        int cont=0;
        for(int i=0;i<model.getDiceStock().size();i++){
            if(canPlaceDice(p,model.getDiceStock().getDice(i),cardNumb))cont++;
        }
        return cont;
    }

    public int canMoveAdice(Player p,int cardNumb){
        int cont=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++) {
                if(p.getPlayerScheme().getScheme()[i][j].getDice()!=null){
                    if (canPlaceDice(p, p.getPlayerScheme().getScheme()[i][j].getDice(),cardNumb)) cont++;
                }

            }
        }
        return cont;
    }

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

    private boolean cardColorOk(DiceColor color){
        return model.getDiceStock().findDice(color);
    }

    public ToolCard findCard(int n){
        for(int i=0;i<toolCardList.size();i++){
            if(toolCardList.get(i).getNumber()==n) return toolCardList.get(i);
        }
        return null;
    }

    public boolean canTakeCard(Player p, ToolCard c)throws RemoteException {
        if(model.getPlayerList().size()==1){
            if(model.getDiceStock().findDice(c.getSolitaryColor())){
                return true;
            }
            else return false;
        }
        if(model.getPlayerList().size()>1){
            if(c.isAlreadyUsed()&&p.getFavorMarkers()<2) return false;
            if(!c.isAlreadyUsed()&&p.getFavorMarkers()<1) return false;
            return true;
        }
        return false;
    }
}