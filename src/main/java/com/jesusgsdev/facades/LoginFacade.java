package com.jesusgsdev.facades;

import com.jesusgsdev.entities.users.Player;
import com.jesusgsdev.entities.users.UserType;
import com.jesusgsdev.services.user.UserService;
import org.springframework.stereotype.Service;


import java.util.Optional;

/**
 * Created by jesgarsal on 19/04/17.
 */
@Service
public class LoginFacade {

    private UserService userService;

    public LoginFacade(UserService userService) {
        this.userService = userService;
    }

    public String login(String username){
        if(username.equals(UserType.MANAGER.toString())){
            return UserType.MANAGER.toString();
        }

        Optional<Player> optionalPlayer = userService.getUserByUsername(username);
        if(!optionalPlayer.isPresent()){
            userService.createNewPlayer(username);
        }

        return UserType.PLAYER.toString();
    }

}
