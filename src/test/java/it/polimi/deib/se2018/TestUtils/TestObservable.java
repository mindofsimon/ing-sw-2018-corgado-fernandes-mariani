package it.polimi.deib.se2018.TestUtils;

import it.polimi.deib.se2018.common.utils.Observable;
import it.polimi.deib.se2018.common.utils.Observer;
import org.junit.Test;

import java.rmi.RemoteException;

/**
 * Class used to test Observable/Observer
 * @author Simone Mariani
 */
public class TestObservable {

    private Observable observable=new Observable();
    private Observer observer=new Observer() {
        @Override
        public void update(Object message) throws RemoteException {

        }
    };

    /**
     * Testing registration and deregistration of an observer by an observable
     */
    @Test
    public void testObservable(){
        observable.register(observer);
        observable.deregister(observer);

    }
}
