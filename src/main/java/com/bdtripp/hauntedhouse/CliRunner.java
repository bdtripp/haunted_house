package com.bdtripp.hauntedhouse;

public class CliRunner
{
    private Parser parser;
    private Game game = new Game();

    /**
     * The main class of the CLI version of Haunted House. 
     * This class starts the loop that continues to process commands until end of play.
     * 
     * @author Brian Tripp
     * @version 2026.02.05
     */

    public static void main(String[] args)
    {
        System.out.println(game.getWelcomeMessage());

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        while(!game.isGameOver()) {
            Command command = parser.getCommand();
            String output = game.processCommand(command);
            System.out.println(output);
        }

        System.out.println("Thank you for playing.  Good bye.");
    }
}