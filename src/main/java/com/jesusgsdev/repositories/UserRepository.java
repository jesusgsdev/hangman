package com.jesusgsdev.repositories;

import com.jesusgsdev.database.Database;
import com.jesusgsdev.entities.users.Player;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by jesgarsal on 19/04/17.
 */
@Repository
public class UserRepository {

    private Database database;

    public UserRepository(Database database) {
        this.database = database;
    }

    public Collection<Player> getAllPlayers(){
        return database.getData().values();
    }

    public synchronized void addNewPlayer(Player player){
        database.getData().put(player.getUsername(), player);
    }

    public Optional<Player> getUserByUsername(String username){
        Optional<Player> player = Optional.empty();
        if(database.getData().containsKey(username)){
            player = Optional.of(database.getData().get(username));
        }
        return player;
    }

}
