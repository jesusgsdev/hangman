package com.jesusgsdev.entities.users;

import com.jesusgsdev.entities.game.Game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Created by jesgarsal on 19/04/17.
 */
public class Player extends User {

    private List<Game> games;
    private Game currentGame;

    public Player(String username) {
        super(username);
        games = new LinkedList<>();
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public List<Game> getAllGames() {
        List<Game> allGames = new LinkedList<>();
        allGames.addAll(getGames());
        if(nonNull(getCurrentGame())){
            allGames.add(getCurrentGame());
        }

        return allGames;
    }
}
