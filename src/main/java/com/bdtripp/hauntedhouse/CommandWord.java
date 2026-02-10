package com.bdtripp.hauntedhouse;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An enumeration of all command words known to the game.
 *
 * @author  Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2026.02.10
 */

public enum CommandWord
{
    GO, QUIT, HELP, LOOK, EAT, BACK, TAKE, DROP,
    ITEMS, STATS, CHARGE, FIRE, TALK, GIVE;

    /**
     * Checks whether a given string is a valid command word.
     * @param aString The string to check
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public static boolean isCommand(String aString)
    {
        try {
            CommandWord.valueOf(aString.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Get a list of all valid commands
     * @return A list of all valid commands separated by spaces
     */
    public static String getCommandList() 
    {
        return Arrays.stream(CommandWord.values())
                     .map(Enum::name)
                     .map(String::toLowerCase)
                     .collect(Collectors.joining(" "));
    }

    /**
     * Converts a word into a CommandWord enum if it matches the valid command words
     * @param word A string to convert to an enum
     * @return A CommandWord Enum
     */
    public static CommandWord fromString(String word)
    {
        if(word == null) {
            return null;
        }
        try {
            return CommandWord.valueOf(word.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
