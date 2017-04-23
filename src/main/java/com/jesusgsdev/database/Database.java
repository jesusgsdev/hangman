package com.jesusgsdev.database;

import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.users.Player;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by jesgarsal on 19/04/17.
 */
@Component
public class Database {

    private Map<String, Player> data = populateDataBase();

    public Map<String, Player> getData() {
        return data;
    }

    //TODO small hack to make tests sense
    public void resetDataForTests(){
        data = new HashMap<>();
    }

    //TODO Hack for demo purposes
    private Map<String, Player> populateDataBase(){
        final String username = "Player";

        Map<String, Player> dataToUpdate = new HashMap<>();
        IntStream.rangeClosed(1,20).forEach(i -> {
            String usernameFinal = username + i;
            Player player = new Player(usernameFinal);
            String secretWord = usernameFinal + " SW";
            String visibleWord = String.join("", Collections.nCopies(secretWord.length(), "?"));
            Game game = new Game(player, secretWord, visibleWord);
            player.setCurrentGame(game);
            dataToUpdate.put(player.getUsername(), player);
        });

        return dataToUpdate;
    }

}
