package it.polimi.deib.se2018.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    private static int PORT = 1099;

    public static void main(String[] args) {

        try {
            LocateRegistry.createRegistry(PORT);

        } catch (RemoteException e) {
            System.out.println("Registry already created!");
        }

        try {

            ServerImplementation serverImplementation = new ServerImplementation();


            Naming.rebind("//localhost/MyServer", serverImplementation);


        } catch (MalformedURLException e) {
            System.err.println("Impossible to register object!");
        } catch (RemoteException e) {
            System.err.println("Connection error: " + e.getMessage() + "!");
        }

    }
}

