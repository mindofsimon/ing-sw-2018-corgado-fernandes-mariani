package it.polimi.deib.se2018.controller.toolcard;

public class DiceRoll implements ToolCard {

    private final int number;
    private final String name;

    public DiceRoll(int number, String name){
        this.name=name;
        this.number=number;
    }

    @Override
    public void activateEffect() {

    }
}
