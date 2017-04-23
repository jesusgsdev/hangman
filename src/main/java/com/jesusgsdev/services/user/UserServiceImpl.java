package com.jesusgsdev.services.user;

import com.jesusgsdev.entities.users.Player;
import com.jesusgsdev.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by jesgarsal on 19/04/17.
 */

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Player createNewPlayer(String username) {
        Player player = new Player(username);
        userRepository.addNewPlayer(player);
        return player;
    }

    @Override
    public List<Player> getAllPlayers() {
        return new LinkedList<>(userRepository.getAllPlayers());
    }

    @Override
    public Optional<Player> getUserByUsername(String username) {
        Optional<Player> player = userRepository.getUserByUsername(username);
        return player;
    }

}
