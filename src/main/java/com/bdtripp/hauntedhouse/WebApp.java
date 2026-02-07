package com.bdtripp.hauntedhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the web version of Haunted House. 
 * This class starts a Spring Boot server and starts the game.
 *
 * @author Brian Tripp
 * @version 2026.02.05
 */

@SpringBootApplication
public class WebApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(WebApp.class, args);

    }
}
