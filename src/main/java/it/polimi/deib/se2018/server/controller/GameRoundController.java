package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.rmi.RemoteException;

public class GameRoundController {
    private Model model;
    private RemoteView view;
    private ScoreController scoreController;

    public GameRoundController(Model m, RemoteView v, ScoreController sc){
        model=m;
        view=v;
        scoreController=sc;
    }

    private void updateRoundsTrack()throws RemoteException{
        if(model.getPlayerList().size()==1) {
            model.getRoundsTrack().getDiceList().addAll(model.getDiceStock().getDiceList());
        }
        if(model.getPlayerList().size()>1){
            model.getRoundsTrack().insertDice(model.getDiceStock().extractRandomDice());
        }
    }

    public void updateDiceStock()throws RemoteException{//DEVO ELIMINARE TUTTI I DADI RIMASTI
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


    private void updateRound(Player p) throws RemoteException {
        if (model.getPlayerList().size() == 1) {
            if (model.getRound() == 10) {//End of the game
                p.setVictoryPoints(scoreController.calculateVictoryPoints(p));
                scoreController.calculateWinner();
                model.setGameOver1P(scoreController.solitaryObjectivePoint());
            } else {
                updateRoundsTrack();
                updateDiceStock();
                model.incrRound();
                model.notifyTurnAndRoundUpdate(p);
            }
        }
        if(model.getPlayerList().size()>1){
            if (model.getRound() == 10) {//End of the game
                p.setVictoryPoints(scoreController.calculateVictoryPoints(p));
                model.setGameOverMP(scoreController.calculateWinner());
            } else{
                modifyPlayersOrder();
                updateRoundsTrack();
                updateDiceStock();
                model.incrRound();
                model.notifyTurnAndRoundUpdate(p);
            }
        }
    }

    private void modifyPlayersOrder()throws RemoteException{
        for(int i=0;i<model.getPlayerList().size();i++){
            if(model.getPlayerList().get(i).getOrder()!=model.getPlayerList().size()){
                model.getPlayerList().get(i).setOrder( model.getPlayerList().get(i).getOrder()+1);
            }
            else if(model.getPlayerList().get(i).getOrder()==model.getPlayerList().size())
                model.getPlayerList().get(i).setOrder(1);
        }
    }

    public void updateTurn(Player p)throws RemoteException{
        if(model.getPlayerList().size()==1){//Single Player
            if(p.getnMoves()==2&&p.getnTurns()==1){
                p.setnMoves(0);
                p.resetCardActivated();
                p.resetDicePlacement();
                p.setnTurns(2);
                model.notifyTurnAndRoundUpdate(p);
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
                model.incrTurn();
                p.setnTurns(p.getnTurns()+1);
                model.notifyTurnAndRoundUpdate(p);
            }
            else if(p.getnTurns()==2&&model.getTurn()!=1){
                model.decrTurn();
                p.setnTurns(p.getnTurns()-1);
                model.notifyTurnAndRoundUpdate(p);
            }
            else if(p.getnTurns()==2&&model.getTurn()==1){
                p.setnTurns(p.getnTurns()-1);
                updateRound(p);
            }
            else if(p.getnTurns()==1&&model.getTurn()==model.getPlayerList().size()){
                p.setnTurns(p.getnTurns()+1);
                model.notifyTurnAndRoundUpdate(p);
            }


        }
    }

}
