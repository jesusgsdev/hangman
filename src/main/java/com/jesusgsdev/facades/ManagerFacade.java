package com.jesusgsdev.facades;

import com.jesusgsdev.dto.GameForManagerDTO;
import com.jesusgsdev.services.game.GameService;
import com.jesusgsdev.services.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jesgarsal on 19/04/17.
 */
@Service
public class ManagerFacade {

    private GameService gameService;
    private UserService userService;

    public ManagerFacade(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    public List<GameForManagerDTO> getAllGames(){
        return gameService.getAllGames().stream().map(game -> new GameForManagerDTO(game)).collect(Collectors.toList());
    }

}
