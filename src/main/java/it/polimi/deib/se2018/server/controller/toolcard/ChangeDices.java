package it.polimi.deib.se2018.server.controller.toolcard;

import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.dice.DiceColor;
import it.polimi.deib.se2018.server.model.events.Event;


import java.rmi.RemoteException;

public class ChangeDices implements ToolCard  {
    private String name;
    private DiceColor solitaryColor;
    private int number;
    private boolean alreadyUsed;
    private boolean activated;

    public ChangeDices(String name, int n, DiceColor sColor){
        this.name=name;
        this.number=n;
        this.solitaryColor=sColor;

    }

    public void activateEffect(Model model,Event event) throws RemoteException {
        if(event.getAction().equals("N")){
            int value=model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).getValue();
            if(value==1)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(6);
            if(value==2)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(5);
            if(value==3)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(4);
            if(value==4)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(3);
            if(value==5)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(2);
            if(value==6)model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(1);

        }
        else if(event.getAction().equals("I")){
            int value =model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).getValue();
            model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(value+1);


        }
        else {
            int value =model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).getValue();
            model.getDiceStock().getDice(model.getDiceStock().findDice(event.getDice())).setValue(value-1);

        }



    }
    public DiceColor getColorDice(){return null;}
    public void setColorDice(DiceColor color){}
    public DiceColor getSolitaryColor() {
        return solitaryColor;
    }
    public int getNumber() {
        return number;
    }

    public int getNumberD() {
        return 0;
    }

    public Restriction getRestriction() {return null;}
    public boolean getActivated() {
        return activated;
    }
    public void activated(boolean act){activated=act;}

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }
    public String toString(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| ALREADY USED? "+alreadyUsed+"|| ACTIVATED: "+activated);
    }
    public String toStringSolitary(){
        return("CARD: "+name+"|| NUMBER: "+number+"|| COLOR: "+solitaryColor+"|| ACTIVATED: "+activated);
    }


    public void used(){
        alreadyUsed=true;
    }
}
