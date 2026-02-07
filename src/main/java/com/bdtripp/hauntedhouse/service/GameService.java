package com.bdtripp.hauntedhouse.service;

import com.bdtripp.hauntedhouse.Game;
import com.bdtripp.hauntedhouse.Parser;
import org.springframework.stereotype.Service;

@Service
public class GameService
{
    private final Game game = new Game();
    private Parser parser;

    public String execute(GameRequest request)
    {
        parser = new Parser(request.getCommand());
        Command command = parser.getCommand();
        return game.processCommand(command);
    }
}