package it.polimi.deib.se2018.client;

import java.rmi.RemoteException;

public class ClientImplementation implements ClientInterface {

    private String playerNickName;
    private View view;

    public void notify(ClientStringMessage message) throws RemoteException {
        System.out.println(message.getMessage());
    }

    public String getPlayerNickName()throws RemoteException{
        return playerNickName;
    }

    public View getView()throws RemoteException{
        return view;
    }

    public void setView(View v)throws RemoteException{
        view=v;
    }

    public void setPlayerNickName(String n) throws RemoteException{
        playerNickName=n;
    }
}
