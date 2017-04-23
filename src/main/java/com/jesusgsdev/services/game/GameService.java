package com.jesusgsdev.services.game;

import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.users.Player;

import java.util.List;

/**
 * Created by jesgarsal on 19/04/17.
 */

public interface GameService {

    List<Game> getAllGames();

    List<Game> getAllCurrentGames();

    Game startNewGame(Player player, String secretWord, String visibleWord);

    void updateGame(Game game, Character character);

}
