package com.bdtripp.hauntedhouse.controller;

import com.bdtripp.hauntedhouse.service.GameService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class GameController
{
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // @PostMapping("/command")
    //     public CommandResult executeCommand(@RequestBody CommandRequest request) {
    //     return commandService.execute(request); // logic lives in the service
    // }
}