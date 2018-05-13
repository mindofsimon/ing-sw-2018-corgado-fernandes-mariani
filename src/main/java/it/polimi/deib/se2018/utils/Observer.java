package it.polimi.deib.se2018.utils;

public interface Observer<T> {

    void update(T message);

}
