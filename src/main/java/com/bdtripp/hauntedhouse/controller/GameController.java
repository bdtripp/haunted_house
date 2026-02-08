package com.bdtripp.hauntedhouse.controller;

import com.bdtripp.hauntedhouse.service.GameService;
import com.bdtripp.hauntedhouse.api.GameRequest;
import com.bdtripp.hauntedhouse.api.GameResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/game")
public class GameController
{
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/command")
    public GameResponse executeCommand(@RequestBody GameRequest request) 
    {
        return GameResponse(gameService.execute(request));
    }
}