package com.jesusgsdev.facades;

import com.jesusgsdev.HangmanApplication;
import com.jesusgsdev.database.Database;
import com.jesusgsdev.dto.GameForPlayerDTO;
import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.game.GameStatus;
import com.jesusgsdev.entities.users.Player;
import com.jesusgsdev.services.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by jesgarsal on 23/04/17.
 */
@ComponentScan
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerFacadeTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private UserService userService;

    @Before
    public void Setup(){
        datatabase.resetDataForTests();
        Map<String, Player> dataToUpdate = datatabase.getData();

        Player player1 = new Player("jesus");

        String randomWord = "ANY";
        String secretWord = "???";
        Game game1 = new Game(player1, randomWord, secretWord);
        player1.setCurrentGame(game1);

        dataToUpdate.put(player1.getUsername(), player1);

        Player player2 = new Player("javier");
        dataToUpdate.put(player2.getUsername(), player2);
    }

    @Test
    public void whenGetCurrentGameButThePlayerIsNew(){
        String username = "javier";

        Player player = userService.getUserByUsername(username).get();
        Game currentGameBefore = player.getCurrentGame();

        GameForPlayerDTO game = playerFacade.getCurrentGame(username);

        player = userService.getUserByUsername(username).get();
        Game currentGameAfter = player.getCurrentGame();

        assertThat(currentGameBefore, is(nullValue()));
        assertThat(currentGameAfter, is(notNullValue()));
        assertThat(currentGameAfter.getVisibleWord(), is(game.getVisibleWord()));
    }


    @Test
    public void whenGetCurrentGameButThePlayerHadAnActiveGame(){
        String username = "jesus";

        Player player = userService.getUserByUsername(username).get();
        Game currentGameBefore = player.getCurrentGame();

        GameForPlayerDTO game = playerFacade.getCurrentGame(username);

        player = userService.getUserByUsername(username).get();
        Game currentGameAfter = player.getCurrentGame();

        assertThat(currentGameBefore, is(notNullValue()));
        assertThat(currentGameAfter, is(notNullValue()));
        assertThat(currentGameAfter, is(currentGameAfter));
    }

    @Test
    public void updateGameWhenIsNewGameAndTheLetterIsInTheWord(){
        String username = "jesus";
        GameForPlayerDTO gameBefore = playerFacade.getCurrentGame(username);
        Integer availableCharactersBefore = gameBefore.getAvailableCharacters().size();
        GameForPlayerDTO gameAfterUpdate = playerFacade.updateGame(username, 'A');

        Assert.notNull(gameAfterUpdate);
        assertThat(gameAfterUpdate.getVisibleWord(), is("A??"));
        assertThat(gameAfterUpdate.getAttemptsLeft(), is(gameBefore.getAttemptsLeft()));
        assertThat(gameAfterUpdate.getAvailableCharacters().size(), is(availableCharactersBefore - 1));
        assertThat(gameAfterUpdate.getStatus(), is(GameStatus.ACTIVE.toString()));
    }

}
