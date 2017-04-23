package com.jesusgsdev.services.user;

import com.jesusgsdev.HangmanApplication;
import com.jesusgsdev.database.Database;
import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.users.Player;
import com.jesusgsdev.services.game.WordsService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.util.Assert.notNull;

/**
 * Created by jesgarsal on 22/04/17.
 */
@ComponentScan
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private WordsService wordsService;

    @Autowired
    private UserService userService;

    @Before
    public void Setup(){
        addOnePlayerTwoGames("jesus");
        addOnePlayerTwoGames("javier");
        addOnePlayerTwoGames("juan");
    }

    @Test
    public void createNewPlayer(){
        String username = "newPlayerUsername";
        Player player = userService.createNewPlayer(username);

        notNull(player);
        assertThat(player.getUsername(), is(username));
    }

    @Test
    public void retrieveAllUsers(){
        List<Player> allPlayers = userService.getAllPlayers();

        notNull(allPlayers);
        assertThat(allPlayers, not(IsEmptyCollection.empty()));
        assertThat(allPlayers, hasSize(3));
    }

    @Test
    public void getUserByUsername(){
        Optional<Player> player = userService.getUserByUsername("jesus");

        assertTrue(player.isPresent());
    }

    private void addOnePlayerTwoGames(String username) {
        Map<String, Player> dataToUpdate = datatabase.getData();

        Player player1 = new Player(username);

        String randomWord = wordsService.getSecretWord();
        String secretWord = wordsService.getVisibleWord(randomWord);
        Game game1 = new Game(player1, randomWord, secretWord);
        Game game2 = new Game(player1, randomWord, secretWord);
        player1.setCurrentGame(game1);
        player1.getGames().add(game2);
        dataToUpdate.put(player1.getUsername(), player1);
    }

}
