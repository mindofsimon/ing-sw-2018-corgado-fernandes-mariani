package it.polimi.deib.se2018.client;

import java.io.Serializable;

public class ClientStringMessage implements Serializable {

    private String message;

    public ClientStringMessage(String message){
        this.message=message;
    }

    public String getMessage(){
        return message;
    }
}
