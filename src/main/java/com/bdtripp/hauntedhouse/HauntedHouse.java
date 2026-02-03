package com.bdtripp.hauntedhouse;

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
        Game game = new Game();
        game.play();
    }
}
