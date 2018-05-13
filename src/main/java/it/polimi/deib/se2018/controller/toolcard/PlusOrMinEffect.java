package it.polimi.deib.se2018.controller.toolcard;

public class PlusOrMinEffect implements ToolCard {

    private final int number;
    private final String name;

    public PlusOrMinEffect(int number, String name){
        this.name=name;
        this.number=number;
    }

    @Override
    public void activateEffect() {

    }
}
