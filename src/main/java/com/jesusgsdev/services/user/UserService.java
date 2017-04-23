package com.jesusgsdev.services.user;


import com.jesusgsdev.entities.users.Player;

import java.util.List;
import java.util.Optional;

/**
 * Created by jesgarsal on 19/04/17.
 */

public interface UserService {

    Player createNewPlayer(String username);

    List<Player> getAllPlayers();

    Optional<Player> getUserByUsername(String username);

}
