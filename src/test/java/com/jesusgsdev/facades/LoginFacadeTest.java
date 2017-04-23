package com.jesusgsdev.facades;

import com.jesusgsdev.HangmanApplication;
import com.jesusgsdev.database.Database;
import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.users.Player;
import com.jesusgsdev.entities.users.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by jesgarsal on 23/04/17.
 */
@ComponentScan
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginFacadeTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private LoginFacade loginFacade;

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
    }

    @Test
    public void whenAManagerLoginThenManagerIsDetected(){
        String typeOfUser = loginFacade.login(UserType.MANAGER.toString());

        assertThat(typeOfUser, is(UserType.MANAGER.toString()));
    }

    @Test
    public void whenAPlayerLoginThenPlayerIsDetected(){
        String typeOfUser = loginFacade.login("jesus");

        assertThat(typeOfUser, is(UserType.PLAYER.toString()));
    }

    @Test
    public void whenANewPlayerLoginThenPlayerIsDetected(){
        String typeOfUser = loginFacade.login("matt");

        assertThat(typeOfUser, is(UserType.PLAYER.toString()));
    }


}
