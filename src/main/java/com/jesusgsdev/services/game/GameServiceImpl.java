package com.jesusgsdev.services.game;

import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.game.GameStatus;
import com.jesusgsdev.entities.users.Player;
import com.jesusgsdev.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Created by jesgarsal on 19/04/17.
 */

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    @Override
    public List<Game> getAllCurrentGames() {
        return gameRepository.getAllCurrentGames();
    }

    @Override
    public Game startNewGame(Player player, String secretWord, String visibleWord) {
        archiveCurrentGameIfItIsFinished(player);

        Game currentGame;
        if(isTheCurrentGameStillActive(player)){
            currentGame = player.getCurrentGame();
        }else{
            currentGame = new Game(player, secretWord, visibleWord);
            player.setCurrentGame(currentGame);
            currentGame.setPlayer(player);
        }

        return currentGame;
    }

    @Override
    public void updateGame(Game game, Character character) {
        updateVisibleWordGivenACharacter(game, character);
        game.getAvailableCharacters().remove(character);
        updateGameStatus(game);
    }

    private void archiveCurrentGameIfItIsFinished(Player player) {
        if(isThisGameFinished(player)){
            player.getGames().add(player.getCurrentGame());
        }
    }

    private Boolean isTheCurrentGameStillActive(Player player){
        return nonNull(player.getCurrentGame()) && player.getCurrentGame().getStatus().equals(GameStatus.ACTIVE);
    }

    private Boolean isThisGameFinished(Player player){
        return nonNull(player.getCurrentGame()) && !player.getCurrentGame().getStatus().equals(GameStatus.ACTIVE);
    }

    private void updateVisibleWordGivenACharacter(Game game, Character character) {
        String secretWord = game.getSecretWord();
        StringBuilder visibleWord = new StringBuilder(game.getVisibleWord());

        Boolean nonMatched = Boolean.TRUE;
        int index = secretWord.indexOf(character);
        while (index >= 0) {
            visibleWord.setCharAt(index, character);
            index = secretWord.indexOf(character, index + 1);
            nonMatched = Boolean.FALSE;
        }

        if(nonMatched){
            game.setAttemptsLeft(game.getAttemptsLeft() - 1);
        }
        game.setVisibleWord(visibleWord.toString());
    }

    private void updateGameStatus(Game game) {
        if(game.getVisibleWord().equals(game.getSecretWord())){
            game.setStatus(GameStatus.WON);
            game.setEndDate(LocalDateTime.now());
        }

        if(game.getAttemptsLeft() == 0 && game.getStatus().equals(GameStatus.ACTIVE)){
            game.setStatus(GameStatus.LOST);
            game.setEndDate(LocalDateTime.now());
        }
    }
}
