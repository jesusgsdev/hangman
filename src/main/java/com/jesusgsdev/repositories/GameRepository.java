package com.jesusgsdev.repositories;

import com.jesusgsdev.database.Database;
import com.jesusgsdev.entities.game.Game;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jesgarsal on 19/04/17.
 */

@Repository
public class GameRepository {

    private Database database;

    public GameRepository(Database database) {
        this.database = database;
    }

    public List<Game> getAllGames(){
        return database.getData().values().parallelStream().flatMap(games -> games.getAllGames().stream()).collect(Collectors.toList());
    }

    public List<Game> getAllCurrentGames(){
        return database.getData().values().parallelStream().map(games -> games.getCurrentGame()).collect(Collectors.toList());
    }

}
