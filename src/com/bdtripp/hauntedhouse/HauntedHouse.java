package com.bdtripp.hauntedhouse;

import static spark.Spark.*;
/**
 * The main class of the Haunted House game. This class contains the first method that is
 * executed when the game is run.
 *
 * @author Brian Tripp
 * @version 2020.06.13
 */
public class HauntedHouse
{
    public static void main(String[] args)
    {
        // This satisfies Replit's Health Check
        port(8080);
        get("/", (req, res) -> "Haunted House Engine Online");

        Game game = new Game();
        game.play();
    }
}
