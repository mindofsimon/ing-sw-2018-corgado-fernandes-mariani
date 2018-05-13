package it.polimi.deib.se2018.controller.toolcard;

public class RestrictionMove implements ToolCard {

    private final RestrictionType restrictionType;
    private final int number;
    private final String name;

    public RestrictionMove(RestrictionType rt, int number, String name){
        restrictionType=rt;
        this.name=name;
        this.number=number;
    }

    @Override
    public void activateEffect() {

    }
}
