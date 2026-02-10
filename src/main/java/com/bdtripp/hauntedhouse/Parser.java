package com.bdtripp.hauntedhouse;

import java.util.Scanner;
import java.io.InputStream;

/**
 * The Parser class of the Haunted House application.
 *
 * This parser reads user input and tries to interpret it as a
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a three word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 *
 * @author  Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2026.02.06
 */
public class Parser
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    private Parser(Scanner reader) // shared constructor for both CLI and web
    {
        commands = new CommandWords();
        this.reader = reader;
    }

    public Parser(String string) {  // for the Web application
        this(new Scanner(string));
    }

    public Parser(InputStream inputStream) // for the CLI application
    {
        this(new Scanner(inputStream));
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand()
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;

        inputLine = reader.nextLine();

        // Find up to three words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                if(tokenizer.hasNext()) {
                    word3 = tokenizer.next();
                }
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(commands.isCommand(word1)) {
            return new Command(word1, word2, word3);
        }
        else {
            return new Command(null, word2, word3);
        }
    }
}
