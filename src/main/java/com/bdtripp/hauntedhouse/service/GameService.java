package com.bdtripp.hauntedhouse.service;

import com.bdtripp.hauntedhouse.Parser;
import com.bdtripp.hauntedhouse.Command;
import com.bdtripp.hauntedhouse.api.GameRequest;
import com.bdtripp.hauntedhouse.GameEngine;
import com.bdtripp.hauntedhouse.Parser;
import org.springframework.stereotype.Service;

@Service
public class GameService
{
    private final GameEngine gameEngine = new GameEngine();
    private Parser parser;

    public String startGame()
    {
        return gameEngine.getWelcomeMessage();
    }

    public String execute(GameRequest request)
    {
        String input = request.getInput();
        parser = new Parser(input);
        Command command = parser.getCommand();
        return "> " + input + "\n" + gameEngine.processCommand(command) + "\n\n";
    }
}