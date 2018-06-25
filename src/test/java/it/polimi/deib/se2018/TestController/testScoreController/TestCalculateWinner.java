package it.polimi.deib.se2018.TestController.testScoreController;

import it.polimi.deib.se2018.server.RemoteView;
import it.polimi.deib.se2018.server.controller.ScoreController;
import it.polimi.deib.se2018.server.model.Model;
import it.polimi.deib.se2018.server.model.gametable.DiceBag;
import it.polimi.deib.se2018.server.model.gametable.DiceStock;
import it.polimi.deib.se2018.server.model.gametable.RoundsTrack;
import it.polimi.deib.se2018.server.model.player.Player;
import it.polimi.deib.se2018.server.model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class TestCalculateWinner {

    private ScoreController scoreController;
    private Model model;
    private RemoteView view;

    @Before
    public void setUp(){
        ArrayList<Player> list=new ArrayList<Player>();
        for(int i=0;i<4;i++){
            list.add(new Player("abc",i+1,PlayerColor.RED));//Possono essere anche tutti uguali...qui contano solo i punti
            list.get(i).setVictoryPoints(i*10);
        }
        model=new Model(DiceBag.getSingletonDiceBag(),DiceStock.getSingletonDiceStock(),RoundsTrack.getSingletonRoundsTrack());
        model.setPlayerList(list);
        scoreController=new ScoreController(model,view);
    }

    @Test
    public void testCalculateWinner(){
        assertEquals(model.getPlayerList().get(3),scoreController.calculateWinner());
    }
}
