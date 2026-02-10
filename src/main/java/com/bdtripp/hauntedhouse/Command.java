package com.bdtripp.hauntedhouse;

/**
 * Represents a command in the Haunted House game.
 *
 * Stores data about a command that was executed by the user.
 * A command consists of three strings: a command word, a second
 * word, and a third word (for example, if the command was "give spade Beatrice", 
 * then the three strings are "give", "spade", and "Beatrice").
 *
 * Checks the validity of each command word. If the user entered an invalid command 
 * then the command word is null.
 *
 * If the command had only one word, then the second and third words are null.
 *
 * @author  Michael Kölling and David J. Barnes, Brian Tripp
 * @version 2020.06.13
 */

public class Command
{
    private String commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * Create a command with three words. Words may be null.
     * @param firstWord The first word of the command.
     * @param secondWord The second word of the command.
     * @param thirdWord The third word of the command.
     *
     */
    public Command(String firstWord, String secondWord, String thirdWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return The third word of this command. Returns null if there was no
     * third word.
     */
    public String getThirdWord()
    {
        return thirdWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasThirdWord()
    {
        return (thirdWord != null);
    }
}



