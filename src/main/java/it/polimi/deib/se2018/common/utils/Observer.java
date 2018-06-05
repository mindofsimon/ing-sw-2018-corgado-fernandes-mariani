package it.polimi.deib.se2018.common.utils;

import java.rmi.RemoteException;

public interface Observer<T> {

    void update(T message)throws RemoteException;

}

