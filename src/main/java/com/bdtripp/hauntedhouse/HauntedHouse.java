package com.bdtripp.hauntedhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the Haunted House game. This class contains the first method that is
 * executed when the game is run.
 *
 * @author Brian Tripp
 * @version 2020.06.13
 */

@SpringBootApplication
public class HauntedHouse
{
    public static void main(String[] args)
    {
        SpringApplication.run(HauntedHouse.class, args);

        Game game = new Game();
        game.play();
    }
}
