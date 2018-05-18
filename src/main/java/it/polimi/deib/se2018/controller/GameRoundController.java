package it.polimi.deib.se2018.controller;

import it.polimi.deib.se2018.model.Model;
import it.polimi.deib.se2018.model.player.Player;
import it.polimi.deib.se2018.view.View;

public class GameRoundController {
    private Model model;
    private View view;
    private ScoreController scoreController;

    public GameRoundController(Model m,View v,ScoreController sc){
        model=m;
        view=v;
        scoreController=sc;
    }

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


    private void updateRound(Player p) {
        if (model.getPlayerList().size() == 1) {
            if (model.getRound() == 10) {//End of the game
                p.setVictoryPoints(scoreController.calculateVictoryPoints(p));
                view.setGameOver();
                scoreController.calculateWinner();
                model.notifyRoundUpdate(p);
            } else {
                updateRoundsTrack();
                updateDiceStock();
                model.incrRound();
                model.notifyRoundUpdate(p);
            }
        }
        if(model.getPlayerList().size()>1){
            if (model.getRound() == 10) {//End of the game
                p.setVictoryPoints(scoreController.calculateVictoryPoints(p));
                view.setGameOver();
                scoreController.calculateWinner();
                model.notifyRoundUpdate(p);

            } else{
                modifyPlayersOrder();
                updateRoundsTrack();
                updateDiceStock();
                model.incrRound();
                model.notifyRoundUpdate(p);
            }
        }
    }

    private void modifyPlayersOrder(){
        for(int i=0;i<model.getPlayerList().size();i++){
            if(model.getPlayerList().get(i).getOrder()!=model.getPlayerList().size()){
                model.getPlayerList().get(i).setOrder( model.getPlayerList().get(i).getOrder()+1);
            }
            if(model.getPlayerList().get(i).getOrder()!=model.getPlayerList().size())
                model.getPlayerList().get(i).setOrder(1);
        }
    }

    public void updateTurn(Player p){
        if(model.getPlayerList().size()==1){//Single Player
            if(p.getnMoves()==2&&p.getnTurns()==1){
                p.setnMoves(0);
                p.setnTurns(2);
            }

            if(p.getnMoves()==2&&p.getnTurns()==2){
                p.setnMoves(0);
                p.resetCardActivated();
                p.resetDicePlacement();
                p.setnTurns(1);
                updateRound(p);
            }
        }
        if(model.getPlayerList().size()>1&&p.getnMoves()==2){//Multi-Player
            p.setnMoves(0);
            p.resetCardActivated();
            p.resetDicePlacement();
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

}
