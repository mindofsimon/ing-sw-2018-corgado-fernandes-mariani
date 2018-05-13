package it.polimi.deib.se2018.model.events;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.player.Player;

public class DicePlacement extends Event {

    private final Player player;

    private final int row;

    private final int column;

    private final Dice dice;

    public DicePlacement(Player p, int r, int c, Dice d){
        player=p;
        column=c;
        row=r;
        dice=d;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public Dice getDice() { return dice; }
}
