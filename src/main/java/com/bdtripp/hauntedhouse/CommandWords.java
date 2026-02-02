package com.bdtripp.hauntedhouse;

/**
 * This class is part of the "Haunted House" application.
 * "Haunted House" is a text based adventure game.
 *
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2020.06.13
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
            "go", "quit", "help", "look", "eat", "back", "take", "drop", "items", "stats",
            "charge", "fire", "talk", "give"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word.
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Get a list of all valid commands
     * @return A list of all valid commands separated by spaces
     */
    public String getCommandList()
    {
        String commandString = "";
        for(String command : validCommands) {
            commandString += command + " ";
        }
        return commandString;
    }
}
