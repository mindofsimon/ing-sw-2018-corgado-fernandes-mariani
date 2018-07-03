package it.polimi.deib.se2018.client;

import java.io.Serializable;

/**
 * Client string message class
 * @author Simone Mariani
 */
public class ClientStringMessage implements Serializable {

    private String message;

    /**
     * Client string message class
     * @param message message
     */
    public ClientStringMessage(String message){
        this.message=message;
    }

    /**
     * Gets message
     * @return message
     */
    public String getMessage(){
        return message;
    }
}
