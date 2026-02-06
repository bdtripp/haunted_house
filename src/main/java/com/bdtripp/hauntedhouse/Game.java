package com.bdtripp.hauntedhouse;

import java.util.ArrayList;
import java.util.Random;

/**
 *  This is the Game Class of the "Haunted House" application.
 *
 *  "Haunted House" is a text based adventure game in which the user can
 *  explore rooms in a haunted house. The goal of the game is to try to find
 *  your way out of the house.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2026.02.05
 */

public class Game
{
    private Parser parser;
    private Player player;
    private ArrayList<Room> rooms;
    private boolean gameOver;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        player = new Player("Brian", 75);
        parser = new Parser();
        rooms = new ArrayList<>();
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room hallway, study, indoorGarden, rootCellar, library, billiardRoom,
                den, wineCellar, bathroom, outside;

        // create the rooms
        hallway = new Room("in a dark hallway");
        study = new Room("in a study");
        indoorGarden = new Room("in a misty indoor garden");
        rootCellar = new Room("in the root cellar");
        library = new Room("in the library");
        billiardRoom = new Room("in the billiard room");
        den = new Room("in the musty den");
        wineCellar = new Room("in the wine cellar");
        bathroom = new Room("in a flooded bathroom...gross");
        outside = new Room("outside of the haunted house and the sun is so delightful");

        //create the items in each room
        hallway.addItem("elixir", "an elixir", 50, true, "health", 10);
        hallway.addItem("cookie", "a magic cookie", 5, true, "strength", 5);
        indoorGarden.addItem("spade", "an old spade", 1, false, "", 0);
        indoorGarden.addItem("plant", "fox glove", 5, false, "", 0);
        wineCellar.addItem("crate", "a big old crate", 2000, false, "", 0);
        bathroom.addItem("key", "a rusty skeleton key", 1, false, "", 0);
        bathroom.addItem("bucket", "an empty bucket", 20, false, "", 0);

        //create the characters in each room
        billiardRoom.addCharacter(
                "Beatrice",
                "How lovely to meet you. You didn't happen to see my spade...",
                "Oh so you found it! Wonderful! Here is something that might come in handy...",
                "spade",
                new Item("potion", "a powerful muscle building potion", 50, true, "maximum carry weight", 50)
        );

        // initialise room exits
        hallway.setExit("north", den, false);
        hallway.setExit("south", outside, true);

        study.setExit("east", indoorGarden, false);

        indoorGarden.setExit("east", billiardRoom, false);
        indoorGarden.setExit("south", rootCellar, false);
        indoorGarden.setExit("west", study, false);

        rootCellar.setExit("east", library, false);
        rootCellar.setExit("south", wineCellar, false);
        rootCellar.setExit("west", den, false);

        library.setExit("north", billiardRoom, false);
        library.setExit("west", rootCellar, false);

        billiardRoom.setExit("south", library, false);
        billiardRoom.setExit("west", indoorGarden, false);

        den.setExit("east", rootCellar, false);
        den.setExit("south", hallway, false);

        wineCellar.setExit("north", rootCellar, false);
        wineCellar.setExit("east", bathroom, false);

        bathroom.setExit("west", wineCellar, false);

        outside.setExit("north", hallway, false);

        player.moveToRoom(billiardRoom, false);  // start game in the billiard room

        // add the rooms to the game
        rooms.add(hallway);
        rooms.add(study);
        rooms.add(indoorGarden);
        rooms.add(rootCellar);
        rooms.add(library);
        rooms.add(billiardRoom);
        rooms.add(den);
        rooms.add(wineCellar);
        rooms.add(bathroom);
        rooms.add(outside);
    }

    /**
     * Get all of the rooms
     * @return A list of the rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Get a random room in the haunted house
     * @return A random room
     */
    public Room getRandomRoom() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(rooms.size());

        return rooms.get(index);
    }

    /**
     * Get the welcome message that displays when a new game is started. Includes 
     * information about the room a player is in. 
     * @return The welcome message 
     */
    public String getWelcomeMessage()
    {
        StringBuilder buffer = new StringBuilder();

        buffer.append("\n");
        buffer.append("Welcome to the Haunted House!").append("\n");
        buffer.append("Haunted House is a spooky adventure game.").append("\n");
        buffer.append("Those who enter may never escape.").append("\n");
        buffer.append("Find the exit and you just might survive.").append("\n");
        buffer.append("Use the \"help\" command if you need help.").append("\n");
        buffer.append("\n");
        buffer.append(getRoomDetails());

        return buffer.toString();
    }

    /**
     * Get details about the room including the items and characters that it contains
     * @return The details about the room
     */
    public String getRoomDetails()
    {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append(player.getCurrentRoom().getLongDescription()).append("\n");
        buffer.append(player.getCurrentRoom().getItemsInRoomDetails()).append("\n");
        buffer.append(player.getCurrentRoom().getCharactersInRoomDetails()).append("\n");

        return buffer.toString();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return A message to display
     */
    public String processCommand(Command command)
    {
        if(command.isUnknown()) {
            return "I don't know what you mean...";
        }

        String commandWord = command.getCommandWord();
        if(commandWord.equals("help")) {
            return getHelpMessage();
        }
        else if(commandWord.equals("go")) {
            return goRoom(command);
        }
        else if(commandWord.equals("look")) {
            return look();
        }
        else if(commandWord.equals("eat")) {
            return eat(command);
        }
        else if(commandWord.equals("take")) {
            return take(command);
        }
        else if(commandWord.equals("drop")) {
            return drop(command);
        }
        else if(commandWord.equals("items")) {
            return showItems();
        }
        else if(commandWord.equals("stats")) {
            return showStats();
        }
        else if(commandWord.equals("talk")) {
            return talk(command);
        }
        else if(commandWord.equals("give")) {
            return giveItem(command);
        }
        else if(commandWord.equals("charge")) {
            return chargeBeamer(command);
        }
        else if(commandWord.equals("fire")) {
            return fireBeamer(command);
        }
        else if(commandWord.equals("back")) {
            return goBack(command);
        }
        else if(commandWord.equals("quit")) {
            return quit(command);
        } else {
            return "";
        }
    }

    // implementations of user commands:

    /**
     * Get the help message.
     * Here we get a list of the command words and directions on how to
     * play the game.
     * @return A help message
     */
    private String getHelpMessage()
    {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Your command words are: \n");
        buffer.append(parser.getCommands() + "\n");

        buffer.append("How to use the commands: \n");

        buffer.append("go: Use to move from room to room");
        buffer.append("Usage: type \"go\" + \"space\" + \"a direction\"");
        buffer.append("Hint(s): Directions are north, south, east, or west\n");

        buffer.append("quit: Use to quit the program");
        buffer.append("Usage: type \"quit\"");
        buffer.append("Hint(s): N/A\n");

        buffer.append("help: Use to get information on how to play the game ");
        buffer.append("Usage: type \"help\"");
        buffer.append("Hint(s): N/A\n");

        buffer.append("look: Use to get a description of your location and directions that you are able to travel in");
        buffer.append("Usage: type \"look\"");
        buffer.append("Hint(s): N/A\n");

        buffer.append("eat: Use to eat an item. Eating an item can boost your stats. Not all items are edible.");
        buffer.append("Usage: type \"eat\" + \"space\" + \"the name of the item you want to eat\"");
        buffer.append("Hint(s): example command - \"eat potion\"\n");

        buffer.append("back: Use to backtrack consecutively through the rooms that you were just in");
        buffer.append("Usage: type \"back\"");
        buffer.append("Hint(s): N/A\n");

        buffer.append("take: If you find an item in a room, you can use the \"take\" command to pick up the item");
        buffer.append("Usage: type \"take\" + \"space\" + \"the name of the item you want to pick up\"");
        buffer.append("Hint(s): N/A\n");

        buffer.append("drop: Use to drop an item that you are carrying.");
        buffer.append("Usage: type \"drop\" + \"space\" + \"the name of the item you want to drop\"");
        buffer.append("Hint(s): You may want to drop an item since you can only carry so much weight\n");

        buffer.append("items: Use to print a list of items that you are carrying and descriptions of each item");
        buffer.append("Usage: type \"items\"");
        buffer.append("Hint(s): N/A\n");

        buffer.append("stats: Use to print a list of the players current stats");
        buffer.append("Usage: type \"stats\"");
        buffer.append("Hint(s): This command will display information such as health, strength, and maximum carry weight.\n");

        buffer.append("charge: Use to charge an item");
        buffer.append("Usage: type \"charge\" + \"space\" + \"the name of the item you want to charge\"");
        buffer.append("Hint(s): You will need to charge your beamer before firing it. " +
                "Charge the beamer in a room that you want to use as a return point. Later when you fire the beamer, " +
                "it will send you back to the room that you charged it in originally.\n"
        );

        buffer.append("fire: ");
        buffer.append("Usage: type \"fire\" + \"space\" + \"the name of the item you want to fire\"");
        buffer.append("Hint(s): You will need to charge your beamer before firing it. " +
                "Charge the beamer in a room that you want to use as a return point. Later when you fire the beamer, " +
                "it will send you back to the room that you charged it in originally.\n"
        );

        buffer.append("talk: If there is a character in a room, you can use this command to talk to them.");
        buffer.append("Usage: type \"talk\" + \"space\" + \"the name of the person you want to talk to\"");
        buffer.append("Hint(s): N/A\n");

        buffer.append("give: Use to give an item to a Character");
        buffer.append("Hint(s): Certain characters will give you a reward in exchange for giving them an item that you found. " +
                "You must be in the same room as the Character that you want to give an item to.\n"
        );

        return buffer.toString();
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise display an error message.
     * @param command The command that was entered
     * @return A message to display in the console
     */
    private String goRoom(Command command)
    {
        StringBuilder buffer = new StringBuilder();

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            return "Go where?";
        }

        String direction = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getExitNeighbor(direction);

        if(nextRoom == null) {
            return "There is no door!";
        }
        else if(currentRoom.getExit(direction).isLocked()) {
            if(player.hasKey()) {
                buffer.append("The door is locked...but you have the key!").append("\n");
                buffer.append(enterRoom(nextRoom, true));
                return buffer.toString();
            }
            else {
                return "The door is locked...you need to find the key!";
            }
        }
        else {
            if(player.getMovesLeft() == 0) {
                endGame();
                return "You ran out of moves! Game Over...";
            }
            return enterRoom(nextRoom, true);
        }
    }

    /**
     * Enters the specified room and prints the description
     * @param nextRoom The room to move to
     * @param addToHistory True if the current room should be added to history
     * @return The details about the room
     */
    private String enterRoom(Room nextRoom, boolean addToHistory)
    {
        player.moveToRoom(nextRoom, addToHistory);
        return getRoomDetails();
    }

    /**
     * Get information about the current location
     * @return A message about the current location
     */
    private String look()
    {
        return player.getCurrentRoom().getLongDescription();
    }

    /**
     * Eat some food to reduce hunger
     * @param command The command that was entered
     * @return A message to display in the console
     */
    private String eat(Command command)
    {
        if(!command.hasSecondWord()) {
            return "Eat what?";
        }

        String itemName = command.getSecondWord();
        Item itemToEat = player.dropItem(itemName);

        if(itemToEat == null) {
            return "That item doesn't exist.";
        }
        else if(!itemToEat.isEdible()) {
            return "You can't eat that...";
        }
        else {
            return player.ingest(itemToEat);
        }
    }

    /**
     * End the game.
     */
    private void endGame() {
        gameOver = true;
    }

    /**
     * Check if the game is over
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Player picks up an item to carry with them
     * @param command The command that was entered
     * @return A message to display in the console
     */
    private String take(Command command)
    {
        if(!command.hasSecondWord()) {
            return "Take what?";
        }

        String itemName = command.getSecondWord();
        Item itemToTake = player.getCurrentRoom().removeItemFromRoom(itemName);

        if(itemToTake == null) {
            return "That item doesn't exist in this room";
        }
        else if((itemToTake.getWeight() + player.getCurrentCarryWeight()) >
                player.getMaxCarryWeight()) {
            return "It's too heavy! You can carry up to " +
                    player.getMaxCarryWeight() + " units. Maybe if you dropped \n" +
                    "some items you could manage it. Or perhaps it's simply too heavy.";
        }
        else {
            player.takeItem(itemToTake);
            return "You picked up " + itemToTake.getDescription() + "!";
        }
    }

    /**
     * Player drops an item so they no longer have to carry it
     * @param command The command that was entered
     * @return A message to display in the console
     */
    private String drop(Command command)
    {
        if(!command.hasSecondWord()) {
            return "Drop what?";
        }

        String itemName = command.getSecondWord();
        Item droppedItem = player.dropItem(itemName);

        if(droppedItem == null) {
            return "You don't have one of those.";
        }
        else {
            player.getCurrentRoom().addItem(droppedItem);
            return "You dropped " + droppedItem.getDescription();
        }
    }

    /**
     * Get details about all of the items that the player is
     * currently carrying
     * @return A message about the items the player is carrying
     */
    private String showItems()
    {
        return player.getCurrentItemDetails();
    }

    /**
     * Get the players current stats
     * @return A message about the players current stats
     */
    private String showStats()
    {
        return player.getStats();
    }

    /**
     * Talk a character
     * @param command The command that was entered
     * @return A message to display in the console
     */
    public String talk(Command command)
    {
        if(!command.hasSecondWord()){
            return "Talk to who?";
        }

        String characterName = command.getSecondWord();
        Character character = player.getCurrentRoom().getCharacter(characterName);

        if(character == null) {
            return "That is not someone who can be spoken to.";
        }
        else {
            return character.getInitialDialog();
        }
    }

    /**
     * Gives the item to the specified character
     * @param command The command that was entered
     * @return A message to display in the console
     */
    public String giveItem(Command command)
    {
        StringBuilder buffer = new StringBuilder();

        if(!command.hasSecondWord()){
            return "Give what... to who...?";
        }

        if(!command.hasThirdWord()){
            return "Give it to who?";
        }

        String characterName = command.getThirdWord();
        Character character = player.getCurrentRoom().getCharacter(characterName);

        if(character == null) {
            return "Who is \"" + characterName + "\" ?";
        }

        String itemSoughtName = character.getItemSought();
        String itemToGiveName = command.getSecondWord();
        Item itemToGive = player.getItem(itemToGiveName);
        Item itemForReward = character.getItemForReward();

        if(itemToGive == null) {
            return "You don't have a(n) \"" + itemToGiveName + "\"";
        }
        else if(itemToGive.getName().equals(itemSoughtName)) {
            player.dropItem(itemToGiveName);
            buffer.append(character.getAcceptanceDialog()).append("\n");
            player.takeItem(itemForReward);
            buffer.append("Received " + itemForReward.getDescription() + "!").append("\n");
            return buffer.toString();
        }
        else {
            return characterName + " doesn't want your " + itemToGiveName;
        }
    }

    /**
     * Charges the beamer. The beamer memorizes the location of the players
     * current room.
     * @param command The command that was entered
     * @return A message to display in the console
     */
    private String chargeBeamer(Command command)
    {
        if(!command.hasSecondWord()) {
            return "Charge what?";
        }

        String itemToCharge = command.getSecondWord();

        if(!itemToCharge.equals("beamer")) {
            return "That item can't be charged.";
        }
        else {
            player.setBeamerLocation();
            player.setBeamerCharge(true);
            return "Beamer charged!";
        }
    }

    /**
     * Fires the beamer. Returns the player to the location at which the
     * beamer was last charged.
     * @param command The command that was entered
     * @return A message to display in the console
     */
    private String fireBeamer(Command command)
    {
        StringBuilder buffer = new StringBuilder();
        if(!command.hasSecondWord()) {
            return "Fire what?";
        }

        String itemToFire = command.getSecondWord();

        if(!itemToFire.equals("beamer")) {
            return "That item can't be fired.";
        }
        else if(player.getBeamerCharge() == false){
            return "Your beamer isn't charged!";
        }
        else {
            buffer.append("Beamer fired!").append("\n");
            buffer.append(enterRoom(player.getBeamerLocation(), true)).append("\n");
            return buffer.toString();
        }
    }

    /**
     * "Back" was entered. Takes the player back to the previous room they
     * were in.
     * @param command The command that was entered
     * @return A message to display in the console
     */
    private String goBack(Command command)
    {
        if(command.hasSecondWord()) {
            return ("\"back\" command does not allow a second word.");
        }
        if(player.getRoomHistory().empty()) {
            return "There is nowhere to go back to.";
        }
        else {
            return enterRoom(player.getPreviousRoom(), false);
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return A message to display in the console
     */
    private String quit(Command command)
    {
        if(command.hasSecondWord()) {
            return "Quit what?";
        }
        else {
            endGame();
            return "Thank you for playing.  Good bye.";
        }
    }
}
