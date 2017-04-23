package com.jesusgsdev.facades;

import com.jesusgsdev.dto.GameForPlayerDTO;
import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.game.GameStatus;
import com.jesusgsdev.entities.users.Player;
import com.jesusgsdev.services.game.GameService;
import com.jesusgsdev.services.game.WordsService;
import com.jesusgsdev.services.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * Created by jesgarsal on 19/04/17.
 */
@Service
public class PlayerFacade {

    private GameService gameService;
    private UserService userService;
    private WordsService wordsService;

    public PlayerFacade(GameService gameService, UserService userService, WordsService wordsService) {
        this.gameService = gameService;
        this.userService = userService;
        this.wordsService = wordsService;
    }

    public GameForPlayerDTO getCurrentGame(String username){
        Player player = getCurrentPlayerPlayer(username);

        Game game;
        if(nonNull(player.getCurrentGame()) && player.getCurrentGame().getStatus().equals(GameStatus.ACTIVE) ){
            game = player.getCurrentGame();
        }else{
            game = getANewGame(player);
        }

        return new GameForPlayerDTO(game);
    }

    private Player getCurrentPlayerPlayer(String username) {
        Optional<Player> optPlayer = userService.getUserByUsername(username);
        return optPlayer.isPresent() ? optPlayer.get() : userService.createNewPlayer(username);
    }

    public GameForPlayerDTO updateGame(String username, Character character){
        Player player = userService.getUserByUsername(username).get();
        gameService.updateGame(player.getCurrentGame(), character);

        return new GameForPlayerDTO(player.getCurrentGame());
    }

    private Game getANewGame(Player player) {
        String secretWord = wordsService.getSecretWord();
        String visibleWord = wordsService.getVisibleWord(secretWord);

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        player.setCurrentGame(game);

        return game;
    }

}
