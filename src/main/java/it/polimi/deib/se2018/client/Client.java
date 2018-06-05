package it.polimi.deib.se2018.client;

import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.ServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) {
        ServerInterface server;
        try {

            server = (ServerInterface)Naming.lookup("//localhost/MyServer");

            ClientImplementation client = new ClientImplementation();

            ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(client, 0);


            Scanner scanner = new Scanner(System.in);
            boolean active = true;
            boolean viewStarted=false;
            View view=null;
            String name;
            int gameMod;
            Player p;
            if(server.getClientsNumber()==0) {
                do{
                    System.out.println("Game mode (0=Single Player, 1=Multi Player): ");
                    gameMod=scanner.nextInt();}
                while(!server.isGameModeAccepted(gameMod,remoteRef));
                do {
                    System.out.println("Insert your nickname: ");
                    name=scanner.next();}
                while(!server.isNameAccepted(name,remoteRef));
                server.createGame();

                remoteRef.setPlayerNickName(name);
                server.setNPlayers(gameMod);
                view=new View(name);
                NetworkHandler networkHandler=new NetworkHandler(server);
                view.register(networkHandler);
                networkHandler.register(view);

                NetworkHandlerInterface networkHandlerRemoteRef=(NetworkHandlerInterface) UnicastRemoteObject.exportObject(networkHandler,0);
                server.addNetworkHandler(networkHandlerRemoteRef);

                server.addClient(remoteRef);



            }

            else if(server.getClientsNumber()<server.getNPlayers()&&server.getClientsNumber()>0){
                do {
                    System.out.println("Insert your nickname: ");
                    name=scanner.next();}
                while(!server.isNameAccepted(name,remoteRef));
                remoteRef.setPlayerNickName(name);
                view=new View(name);
                NetworkHandler networkHandler=new NetworkHandler(server);
                view.register(networkHandler);
                networkHandler.register(view);

                NetworkHandlerInterface networkHandlerRemoteRef=(NetworkHandlerInterface) UnicastRemoteObject.exportObject(networkHandler,0);
                server.addNetworkHandler(networkHandlerRemoteRef);

                server.addClient(remoteRef);

                if(server.getClientsNumber()==2){server.setTimer();}
                if(server.getClientsNumber()==4){server.stopTimer();}
            }

            else if(server.isGameFull()){
                System.out.println("The current game is full");
                return;
            }

            while(active&&!viewStarted){
                if(server.getClientsNumber()==server.getNPlayers()) {
                    viewStarted=true;
                    view.run();
                }
            }
            scanner.close();
        }
        catch (MalformedURLException e) {
            System.err.println("URL not found!");
        } catch (RemoteException e) {
            System.err.println("Connection error: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.err.println("Reference associated to nothing!");
        }

    }

}
