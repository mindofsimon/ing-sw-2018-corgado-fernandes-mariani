package it.polimi.deib.se2018.server.controller;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.player.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameRoundController {
    private Model model;
    private RemoteView view;
    private ScoreController scoreController;
    private Timer suspensionTimer;
    private TimerTask checkAction;
    private int suspensionTimerInterval;

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
                setTimer(p.getnMoves(),p.getOrder());//Viene resettato il suspensionTimer del giocatore
                model.notifyTurnAndRoundUpdate(p);
            }
        }
        if(model.getPlayerList().size()>1){
            if (p.isSuspended()||p.isOut()) {
                modifyPlayersOrderAfterSuspensionOrEscape(p);
                setTimer(model.findPlayerByOrder(model.getTurn()).getnMoves(),model.getTurn());//Setto il suspensionTimer sospensione per il prossimo giocatore
                if(p.isOut()){
                    model.notifyPlayerQuit(p);
                    return;
                }
                else{
                    model.notifyPlayerSuspension(p);
                }
            }
            if (model.getRound() == 10) {//End of the game
                for(int i=0;i<model.getPlayerList().size();i++){
                    model.getPlayerList().get(i).setVictoryPoints(scoreController.calculateVictoryPoints(model.getPlayerList().get(i)));
                }
                model.setGameOverMP(scoreController.calculateWinner());
            } else{
                modifyPlayersOrder();
                updateRoundsTrack();
                updateDiceStock();
                model.incrRound();
                setTimer(model.findPlayerByOrder(model.getTurn()).getnMoves(),model.getTurn());//Setto il suspensionTimer sospensione per il prossimo giocatore
                model.notifyTurnAndRoundUpdate(p);
            }
        }
    }

    private void modifyPlayersOrderAfterSuspensionOrEscape(Player p){
        ArrayList<Player> newPlayerList=new ArrayList<Player>();
        for(int i=0;i<model.getPlayerList().size();i++){//Aggiungo prima i non sospesi
            if(!model.getPlayerList().get(i).isSuspended()) newPlayerList.add(model.getPlayerList().get(i));
        }
        for(int i=0;i<model.getPlayerList().size();i++){//poi aggiungo i sospesi
            if(model.getPlayerList().get(i).isSuspended()) newPlayerList.add(model.getPlayerList().get(i));
        }
        for(int i=0;i<model.getPlayerList().size();i++){//poi aggiungo anche gli "usciti" dal gioco
            if(model.getPlayerList().get(i).isOut()) newPlayerList.add(model.getPlayerList().get(i));
        }
        if(model.getTurn()>p.getOrder()) model.decrTurn();
        for(int i=0;i<newPlayerList.size();i++){
            if(newPlayerList.get(i).getOrder()>p.getOrder()) newPlayerList.get(i).setOrder(newPlayerList.get(i).getOrder()-1);
            if(newPlayerList.get(i).isSuspended()) newPlayerList.get(i).setOrder(i+1);
        }
        model.setPlayerList(newPlayerList);
    }

    private void modifyPlayersOrder()throws RemoteException{
        for(int i=0;i<model.getPlayerList().size()-countNotActivePlayers();i++){
            if(model.getPlayerList().get(i).getOrder()!=model.getPlayerList().size()-countNotActivePlayers()){
                model.getPlayerList().get(i).setOrder( model.getPlayerList().get(i).getOrder()+1);
            }
            else if(model.getPlayerList().get(i).getOrder()==model.getPlayerList().size()-countNotActivePlayers())
                model.getPlayerList().get(i).setOrder(1);
        }
    }

    public void updateTurn(Player p)throws RemoteException {
        if (model.getPlayerList().size() == 1) {//Single Player
            if (p.getnMoves() == 2 && p.getnTurns() == 1) {
                p.setnMoves(0);
                p.resetCardActivated();
                p.resetDicePlacement();
                p.setnTurns(2);
                setTimer(p.getnMoves(), p.getOrder());//Viene resettato il suspensionTimer del giocatore
                model.notifyTurnAndRoundUpdate(p);
            }

            if (p.getnMoves() == 2 && p.getnTurns() == 2) {
                p.setnMoves(0);
                p.resetCardActivated();
                p.resetDicePlacement();
                p.setnTurns(1);
                updateRound(p);
            }
        }
        if (model.getPlayerList().size() > 1 && p.getnMoves() == 2) {//Multi-Player
            if (p.isSuspended()||p.isOut()) {//Se è appena stato sospeso
                if((p.getOrder()==1&&p.getnTurns()==1)||(p.getOrder()!=1&&p.getOrder()!=model.getPlayerList().size()-countNotActivePlayers()+1&&p.getnTurns()==1)){
                    model.incrTurn();
                    modifyPlayersOrderAfterSuspensionOrEscape(p);
                    setTimer(model.findPlayerByOrder(model.getTurn()).getnMoves(), model.getTurn());//Setto il suspensionTimer sospensione per il prossimo giocatore
                    if(p.isOut()){
                        model.notifyPlayerQuit(p);
                        return;
                    }
                    else{
                        model.notifyPlayerSuspension(p);
                    }
                } else if ((p.getOrder()==model.getPlayerList().size()-countNotActivePlayers()+1&&(p.getnTurns()==1||p.getnTurns()==2))||(p.getOrder()!=1&&p.getOrder()!=model.getPlayerList().size()-countNotActivePlayers()+1&&p.getnTurns()==2)) {
                    model.decrTurn();
                    modifyPlayersOrderAfterSuspensionOrEscape(p);
                    setTimer(model.findPlayerByOrder(model.getTurn()).getnMoves(), model.getTurn());//Setto il suspensionTimer sospensione per il prossimo giocatore
                    if(p.isOut()){
                        model.notifyPlayerQuit(p);
                        return;
                    }
                    else{
                        model.notifyPlayerSuspension(p);
                    }
                } else if (p.getnTurns() == 2 && p.getOrder() == 1) {//Qui bisognerà cambiare round
                    updateRound(p);
                }
            } else {
                p.setnMoves(0);
                p.resetCardActivated();
                p.resetDicePlacement();
                if (p.getnTurns() == 1 && model.getTurn() != model.getPlayerList().size() - countSuspended()) {
                    model.incrTurn();
                    p.setnTurns(p.getnTurns() + 1);
                    setTimer(model.findPlayerByOrder(model.getTurn()).getnMoves(), model.getTurn());//Setto il suspensionTimer sospensione per il prossimo giocatore
                    model.notifyTurnAndRoundUpdate(p);
                } else if (p.getnTurns() == 2 && model.getTurn() != 1) {
                    model.decrTurn();
                    p.setnTurns(p.getnTurns() - 1);
                    setTimer(model.findPlayerByOrder(model.getTurn()).getnMoves(), model.getTurn());//Setto il suspensionTimer sospensione per il prossimo giocatore
                    model.notifyTurnAndRoundUpdate(p);
                } else if (p.getnTurns() == 2 && model.getTurn() == 1) {
                    p.setnTurns(p.getnTurns() - 1);
                    updateRound(p);
                } else if (p.getnTurns() == 1 && model.getTurn() == model.getPlayerList().size() - countSuspended()) {
                    p.setnTurns(p.getnTurns() + 1);
                    setTimer(model.findPlayerByOrder(model.getTurn()).getnMoves(), model.getTurn());//Setto il suspensionTimer sospensione per il prossimo giocatore
                    model.notifyTurnAndRoundUpdate(p);
                }
            }
        }
        if (p.getnMoves() == 1){
            setTimer(p.getnMoves(), p.getOrder());//Viene resettato il suspensionTimer del giocatore corrente (in quanto non cambia il turno)
        }
    }

    public void setTimer(final int nActions, final int order){//Timer per sospensione
        checkAction=new TimerTask() {
            @Override
            public void run() {
                try{
                    if(nActions==model.findPlayerByOrder(order).getnMoves()) {
                        model.suspendPlayer(model.findPlayerByOrder(order));
                        if(countSuspended()==model.getPlayerList().size()) {//Se sono tutti sospesi, termino qui il gioco
                            for (int i = 0; i < model.getPlayerList().size(); i++) {
                                model.getPlayerList().get(i).setVictoryPoints(scoreController.calculateVictoryPoints(model.getPlayerList().get(i)));
                            }
                            model.notifyPlayerSuspension(model.findPlayerByOrder(order));
                            if (model.getPlayerList().size() > 1)
                                model.setGameOverMP(scoreController.calculateWinner());
                            else
                                model.setGameOver1P(scoreController.solitaryObjectivePoint());

                        }
                        else {
                            //Qui non tutti i giocatori sono stati sospesi, quindi il gioco prosegue
                            model.findPlayerByOrder(order).setnMoves(2);
                            updateTurn(model.findPlayerByOrder(order));
                        }
                    }
                } catch (RemoteException e){System.out.println("Remote Exception!");}
            }
        };
        suspensionTimer =new Timer();
        suspensionTimer.schedule(checkAction,(long)suspensionTimerInterval*1000);
    }

    public void stopTimer(){
        checkAction.cancel();
        suspensionTimer.cancel();
        suspensionTimer.purge();
    }

    private int countSuspended(){
        int cont=0;
        for(int i=0;i<model.getPlayerList().size();i++){
            if(model.getPlayerList().get(i).isSuspended()) cont++;
        }
        return cont;
    }

    public int countEscaped(){
        int cont=0;
        for(int i=0;i<model.getPlayerList().size();i++){
            if(model.getPlayerList().get(i).isOut()) cont++;
        }
        return cont;
    }

    private int countNotActivePlayers(){
        return countSuspended()+countEscaped();
    }

    public void setSuspensionTimerInterval(int interval){
        suspensionTimerInterval=interval;
    }

}
