package com.jesusgsdev.facades;

import com.jesusgsdev.HangmanApplication;
import com.jesusgsdev.database.Database;
import com.jesusgsdev.dto.GameForManagerDTO;
import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.users.Player;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created by jesgarsal on 22/04/17.
 */
@ComponentScan
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManagerFacadeTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private ManagerFacade managerFacade;

    @Before
    public void Setup(){
        datatabase.resetDataForTests();
        addOnePlayerTwoGames("jesus");
        addOnePlayerTwoGames("javier");
        addOnePlayerTwoGames("juan");
    }

    @Test
    public void retrieveAllGames(){
        List<GameForManagerDTO> allGames = managerFacade.getAllGames();

        Assert.notNull(allGames);
        assertThat(allGames, not(IsEmptyCollection.empty()));
        assertThat(allGames, hasSize(6));
    }

    private void addOnePlayerTwoGames(String username) {
        Map<String, Player> dataToUpdate = datatabase.getData();

        Player player1 = new Player(username);

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game1 = new Game(player1, secretWord, visibleWord);
        Game game2 = new Game(player1, secretWord, visibleWord);
        player1.setCurrentGame(game1);
        player1.getGames().add(game2);
        dataToUpdate.put(player1.getUsername(), player1);
    }

}
