package it.polimi.deib.se2018;

import it.polimi.deib.se2018.client.Client;
import it.polimi.deib.se2018.server.Server;

import java.util.Scanner;

public class Game {

    public static void main(String[] args){
        Scanner s=new Scanner(System.in);
        System.out.println("SERVER(0) OR CLIENT(1)?\n");
        int choice=s.nextInt();
        if(choice==0){
            Server.startServer();
        }
        else if(choice==1) {
            Client.startClient();
        }
    }

}
