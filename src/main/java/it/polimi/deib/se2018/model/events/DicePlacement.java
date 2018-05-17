package it.polimi.deib.se2018.model.events;

import it.polimi.deib.se2018.model.dice.Dice;
import it.polimi.deib.se2018.model.player.Player;

/**
 * Dice placement class: defines where to place the dice
 * @author Simone Mariani
 */
public class DicePlacement extends Event {

    private final Player player;

    private final int row;

    private final int column;

    private final Dice dice;

    /**
     * Constructor, initializes dice placement class
     * @param p player
     * @param r row
     * @param c column
     * @param d dice
     */
    public DicePlacement(Player p, int r, int c, Dice d){
        player=p;
        column=c;
        row=r;
        dice=d;
    }

    /**
     * Get player
     * @return player
     */
    @Override
    public Player getPlayer() {
        return player;
    }

    /**
     * Get row
     * @return row
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Get column
     * @return column
     */
    @Override
    public int getColumn() {
        return column;
    }

    /**
     * Get dice
     * @return dice
     */
    @Override
    public Dice getDice() { return dice; }
}
