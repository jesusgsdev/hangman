package com.jesusgsdev;

import com.jesusgsdev.dto.GameForManagerDTO;
import com.jesusgsdev.dto.GameForPlayerDTO;
import com.jesusgsdev.facades.LoginFacade;
import com.jesusgsdev.facades.ManagerFacade;
import com.jesusgsdev.facades.PlayerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jesgarsal on 19/04/17.
 */
@RestController
public class HangManController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HangManController.class);

    private PlayerFacade playerFacade;
    private ManagerFacade managerFacade;
    private LoginFacade loginFacade;

    public HangManController(PlayerFacade playerFacade, ManagerFacade managerFacade, LoginFacade loginFacade) {
        this.playerFacade = playerFacade;
        this.managerFacade = managerFacade;
        this.loginFacade = loginFacade;
    }

    @RequestMapping(value = "/login/{username}", method = RequestMethod.GET, produces = {"application/json"})
    public String login(@PathVariable("username") String username) {
        LOGGER.info("User with username " + username + " is trying to login into the system.");
        return loginFacade.login(username);
    }

    @RequestMapping(value = "/management", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<GameForManagerDTO> manage() {
        LOGGER.info("Retrieving all the games for the Manager");
        return managerFacade.getAllGames();
    }

    @RequestMapping(value = "/game/{username}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public GameForPlayerDTO getGame(@PathVariable("username") String username) {
        LOGGER.info("Retrieving the current game for " + username);
        return playerFacade.getCurrentGame(username);
    }

    @RequestMapping(value = "/game/update/{username}/{character}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public GameForPlayerDTO updateGame(@PathVariable("username") String username, @PathVariable("character") Character character) {
        LOGGER.info("Updating the current game for " + username + " with the character " + character);
        return playerFacade.updateGame(username, character);
    }

}
