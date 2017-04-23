package com.jesusgsdev.services.game;

import com.jesusgsdev.HangmanApplication;
import com.jesusgsdev.database.Database;
import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.game.GameStatus;
import com.jesusgsdev.entities.users.Player;
import com.jesusgsdev.services.user.UserService;
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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by jesgarsal on 22/04/17.
 */
@ComponentScan
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameServiceTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Before
    public void Setup(){
        datatabase.resetDataForTests();
        addOnePlayerTwoGames("jesus");
        addOnePlayerTwoGames("javier");
        addOnePlayerTwoGames("juan");
    }

    @Test
    public void retrieveAllGames(){
        List<Game> allGames = gameService.getAllGames();

        Assert.notNull(allGames);
        assertThat(allGames, not(IsEmptyCollection.empty()));
        assertThat(allGames, hasSize(6));
    }

    @Test
    public void retrieveAllCurrentGames(){
        List<Game> allGames = gameService.getAllCurrentGames();

        Assert.notNull(allGames);
        assertThat(allGames, not(IsEmptyCollection.empty()));
        assertThat(allGames, hasSize(3));
    }

    @Test
    public void givenAPlayerThenStartANewGame(){
        Player player = new Player("matt");

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);

        Assert.notNull(game);
        assertThat(game.getSecretWord(), is(secretWord));
        assertThat(game.getVisibleWord(), is(visibleWord));
    }

    @Test
    public void givenAPlayerThenStartANewGameWhileCurrentStillActive(){
        Player player = new Player("matt");

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        game = gameService.startNewGame(game.getPlayer(), secretWord, visibleWord);

        Assert.notNull(game);
        assertThat(game.getSecretWord(), is(secretWord));
        assertThat(game.getVisibleWord(), is(visibleWord));
    }

    @Test
    public void givenAPlayerThenStartANewGameWhenCurrentIsFinished(){
        Player player = new Player("matt");

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        game.setStatus(GameStatus.WON);
        game.getPlayer().setCurrentGame(game);
        game = gameService.startNewGame(game.getPlayer(), secretWord, visibleWord);

        Assert.notNull(game);
        assertThat(game.getSecretWord(), is(secretWord));
        assertThat(game.getVisibleWord(), is(visibleWord));
    }

    @Test
    public void givenANewGameCheckIfCorrectlyStored(){
        Player player = userService.getUserByUsername("jesus").get();

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);

        List<Game> games = gameService.getAllCurrentGames();

        assertThat(games, hasItem(game));
    }

    @Test
    public void updateGameWhenIsNewGameAndTheLetterIsInTheWord(){
        Player player = new Player("matt");

        String secretWord = "HELLO";
        String visibleWord = "?????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer attemptsLeftBefore = game.getAttemptsLeft();
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'H');

        Assert.notNull(game);
        assertThat(game.getSecretWord(), is(secretWord));
        assertThat(game.getVisibleWord(), is("H????"));
        assertThat(game.getAttemptsLeft(), is(attemptsLeftBefore));
        assertThat(game.getAvailableCharacters().size(), is(numOfAvailableLettersBefore - 1));
        assertThat(game.getStatus(), is(GameStatus.ACTIVE));
    }


    @Test
    public void updateGameWhenIsNewGameAndTheLetterIsNotInTheWord(){
        Player player = new Player("matt");

        String secretWord = "HELLO";
        String visibleWord = "?????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer attemptsLeftBefore = game.getAttemptsLeft();
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'X');

        Assert.notNull(game);
        assertThat(game.getSecretWord(), is(secretWord));
        assertThat(game.getVisibleWord(), is("?????"));
        assertThat(game.getAttemptsLeft(), is(attemptsLeftBefore - 1));
        assertThat(game.getAvailableCharacters().size(), is(numOfAvailableLettersBefore - 1));
        assertThat(game.getStatus(), is(GameStatus.ACTIVE));
    }

    @Test
    public void updateGameWhenIsNewGameAndTheLetterIsMoreThanOneTimeInTheWord(){
        Player player = new Player("matt");

        String secretWord = "GOOD";
        String visibleWord = "????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer attemptsLeftBefore = game.getAttemptsLeft();
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'O');

        Assert.notNull(game);
        assertThat(game.getSecretWord(), is(secretWord));
        assertThat(game.getVisibleWord(), is("?OO?"));
        assertThat(game.getAttemptsLeft(), is(attemptsLeftBefore));
        assertThat(game.getAvailableCharacters().size(), is(numOfAvailableLettersBefore - 1));
        assertThat(game.getStatus(), is(GameStatus.ACTIVE));
    }


    @Test
    public void updateGameWhenIHaveGuessedTheWord(){
        Player player = new Player("matt");

        String secretWord = "H";
        String visibleWord = "?";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer attemptsLeftBefore = game.getAttemptsLeft();
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'H');

        Assert.notNull(game);
        assertThat(game.getSecretWord(), is(secretWord));
        assertThat(game.getVisibleWord(), is("H"));
        assertThat(game.getAttemptsLeft(), is(attemptsLeftBefore));
        assertThat(game.getAvailableCharacters().size(), is(numOfAvailableLettersBefore - 1));
        assertThat(game.getStatus(), is(GameStatus.WON));
    }

    @Test
    public void updateGameWhenIDoNotHaveMoreAttemps(){
        Player player = new Player("matt");

        String secretWord = "H";
        String visibleWord = "?";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'A');
        gameService.updateGame(game, 'B');
        gameService.updateGame(game, 'C');
        gameService.updateGame(game, 'D');
        gameService.updateGame(game, 'E');

        Assert.notNull(game);
        assertThat(game.getSecretWord(), is(secretWord));
        assertThat(game.getVisibleWord(), is("?"));
        assertThat(game.getAttemptsLeft(), is(0));
        assertThat(game.getAvailableCharacters().size(), is(numOfAvailableLettersBefore - 5));
        assertThat(game.getStatus(), is(GameStatus.LOST));
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
