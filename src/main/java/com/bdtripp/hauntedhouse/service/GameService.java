package com.bdtripp.hauntedhouse.service;

import com.bdtripp.hauntedhouse.Parser;
import com.bdtripp.hauntedhouse.Command;
import com.bdtripp.hauntedhouse.api.GameRequest;
import com.bdtripp.hauntedhouse.api.GameResponse;
import com.bdtripp.hauntedhouse.GameEngine;
import com.bdtripp.hauntedhouse.GameStatus;
import com.bdtripp.hauntedhouse.Parser;
import org.springframework.stereotype.Service;

@Service
public class GameService
{
    private GameEngine gameEngine;
    private Parser parser;

    public GameResponse startGame()
    {
        gameEngine = new GameEngine();
        return new GameResponse(gameEngine.getWelcomeMessage() + "\n", GameStatus.RUNNING);
    }

    public GameResponse execute(GameRequest request)
    {
        String input = request.getInput();
        parser = new Parser(input);
        Command command = parser.getCommand();
        String cmdResult = gameEngine.processCommand(command);
        String output = "> " + input + "\n" + cmdResult + "\n\n";
        if(gameEngine.isGameOver())
        {
            return new GameResponse(output, GameStatus.STOPPED);
        }
        return new GameResponse(output, GameStatus.RUNNING);
    }
}