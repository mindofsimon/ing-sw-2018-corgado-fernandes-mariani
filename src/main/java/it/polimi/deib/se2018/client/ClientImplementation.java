package it.polimi.deib.se2018.client;

import it.polimi.deib.se2018.server.ServerInterface;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Client implementation class
 * @author Simone Mariani
 */
public class ClientImplementation implements ClientInterface {

    private String playerNickName;
    private View view;
    private boolean disconnected=false;
    private Timer connectionCheckerTimer;
    private TimerTask connectionChecker;

    /**
     * Notifies client
     * @param message message
     * @throws RemoteException
     */
    public void notify(ClientStringMessage message) throws RemoteException {
        System.out.println(message.getMessage());
    }

    /**
     * Gets player's nickname
     * @return player's nickname
     * @throws RemoteException
     */
    public String getPlayerNickName()throws RemoteException{
        return playerNickName;
    }

    /**
     * Gets view
     * @return view
     * @throws RemoteException
     */
    public View getView()throws RemoteException{
        return view;
    }

    /**
     * Sets view
     * @param v view
     * @throws RemoteException
     */
    public void setView(View v)throws RemoteException{
        view=v;
    }

    /**
     * Sets player's nickname
     * @param n player's nickname
     * @throws RemoteException
     */
    public void setPlayerNickName(String n) throws RemoteException{
        playerNickName=n;
    }

    /**
     * Pings server
     * @return string message "I'm alive"
     * @throws RemoteException
     */
    public ClientStringMessage pingServer() throws RemoteException{
        return new ClientStringMessage("I'm alive");
    }

    /**
     * Sets timer that evaluates client's disconnection
     * @param server server
     * @param client client
     */
    public void setConnectionCheckerTimer(final ServerInterface server,final ClientInterface client) {
        connectionChecker=new TimerTask() {
            @Override
            public void run() {
                try{
                    server.getNPlayers();//La uso solo per vedere se ci sarà una Remote Exception...potevo usare qualsiasi altra cosa
                    if(disconnected){
                        server.reconnectClient(client);
                        System.out.println("Now you are connected again!");
                        disconnected=false;
                    }
                }catch (RemoteException e){
                    if(!disconnected){
                        System.out.println("You are disconnected!");
                    }
                    disconnected=true;}
            }
        };
        connectionCheckerTimer=new Timer();
        connectionCheckerTimer.schedule(connectionChecker,0,1000);//La eseguo ogni secondo
    }
}