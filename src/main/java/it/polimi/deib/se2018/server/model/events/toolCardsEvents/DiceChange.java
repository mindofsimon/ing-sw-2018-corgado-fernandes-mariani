package it.polimi.deib.se2018.server.model.events.toolCardsEvents;

import it.polimi.deib.se2018.server.model.dice.Dice;
import it.polimi.deib.se2018.server.model.events.Message;
import it.polimi.deib.se2018.server.model.player.Player;

import java.io.Serializable;

public class DiceChange extends Message implements Serializable {


    private String message;
    private Player player;
    private Dice dice;

    public DiceChange(String message, Player player,Dice dice) {
        this.message = message;
        this.player=player;
        this.dice=dice;
    }

    public String getMessage() {
        return message;
    }

    public Dice getDice() {
        return dice;
    }

    public Player getPlayer(){return player;}
}
