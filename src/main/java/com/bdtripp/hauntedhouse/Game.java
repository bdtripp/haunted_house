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
    private String getWelcomeMessage()
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
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean quitGame = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if(commandWord.equals("help")) {
            printHelp();
        }
        else if(commandWord.equals("go")) {
            quitGame = goRoom(command);
        }
        else if(commandWord.equals("look")) {
            look();
        }
        else if(commandWord.equals("eat")) {
            eat(command);
        }
        else if(commandWord.equals("take")) {
            take(command);
        }
        else if(commandWord.equals("drop")) {
            drop(command);
        }
        else if(commandWord.equals("items")) {
            showItems();
        }
        else if(commandWord.equals("stats")) {
            showStats();
        }
        else if(commandWord.equals("talk")) {
            talk(command);
        }
        else if(commandWord.equals("give")) {
            giveItem(command);
        }
        else if(commandWord.equals("charge")) {
            chargeBeamer(command);
        }
        else if(commandWord.equals("fire")) {
            fireBeamer(command);
        }
        else if(commandWord.equals("back")) {
            goBack(command);
        }
        else if(commandWord.equals("quit")) {
            quitGame = quit(command);
        }

        return quitGame;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a list of the command words and directions on how to
     * play the game.
     */
    private void printHelp()
    {
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands() + "\n");

        System.out.println("How to use the commands: \n");

        System.out.println("go: Use to move from room to room");
        System.out.println("Usage: type \"go\" + \"space\" + \"a direction\"");
        System.out.println("Hint(s): Directions are north, south, east, or west\n");

        System.out.println("quit: Use to quit the program");
        System.out.println("Usage: type \"quit\"");
        System.out.println("Hint(s): N/A\n");

        System.out.println("help: Use to get information on how to play the game ");
        System.out.println("Usage: type \"help\"");
        System.out.println("Hint(s): N/A\n");

        System.out.println("look: Use to get a description of your location and directions that you are able to travel in");
        System.out.println("Usage: type \"look\"");
        System.out.println("Hint(s): N/A\n");

        System.out.println("eat: Use to eat an item. Eating an item can boost your stats. Not all items are edible.");
        System.out.println("Usage: type \"eat\" + \"space\" + \"the name of the item you want to eat\"");
        System.out.println("Hint(s): example command - \"eat potion\"\n");

        System.out.println("back: Use to backtrack consecutively through the rooms that you were just in");
        System.out.println("Usage: type \"back\"");
        System.out.println("Hint(s): N/A\n");

        System.out.println("take: If you find an item in a room, you can use the \"take\" command to pick up the item");
        System.out.println("Usage: type \"take\" + \"space\" + \"the name of the item you want to pick up\"");
        System.out.println("Hint(s): N/A\n");

        System.out.println("drop: Use to drop an item that you are carrying.");
        System.out.println("Usage: type \"drop\" + \"space\" + \"the name of the item you want to drop\"");
        System.out.println("Hint(s): You may want to drop an item since you can only carry so much weight\n");

        System.out.println("items: Use to print a list of items that you are carrying and descriptions of each item");
        System.out.println("Usage: type \"items\"");
        System.out.println("Hint(s): N/A\n");

        System.out.println("stats: Use to print a list of the players current stats");
        System.out.println("Usage: type \"stats\"");
        System.out.println("Hint(s): This command will display information such as health, strength, and maximum carry weight.\n");

        System.out.println("charge: Use to charge an item");
        System.out.println("Usage: type \"charge\" + \"space\" + \"the name of the item you want to charge\"");
        System.out.println("Hint(s): You will need to charge your beamer before firing it. " +
                "Charge the beamer in a room that you want to use as a return point. Later when you fire the beamer, " +
                "it will send you back to the room that you charged it in originally.\n"
        );

        System.out.println("fire: ");
        System.out.println("Usage: type \"fire\" + \"space\" + \"the name of the item you want to fire\"");
        System.out.println("Hint(s): You will need to charge your beamer before firing it. " +
                "Charge the beamer in a room that you want to use as a return point. Later when you fire the beamer, " +
                "it will send you back to the room that you charged it in originally.\n"
        );

        System.out.println("talk: If there is a character in a room, you can use this command to talk to them.");
        System.out.println("Usage: type \"talk\" + \"space\" + \"the name of the person you want to talk to\"");
        System.out.println("Hint(s): N/A\n");

        System.out.println("give: Use to give an item to a Character");
        System.out.println("Hint(s): Certain characters will give you a reward in exchange for giving them an item that you found. " +
                "You must be in the same room as the Character that you want to give an item to.\n"
        );
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     * @param command The command that was entered
     */
    private boolean goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }

        String direction = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getExitNeighbor(direction);

        if(nextRoom == null) {
            System.out.println("There is no door!");
        }
        else if(currentRoom.getExit(direction).isLocked()) {
            if(player.hasKey()) {
                System.out.println("The door is locked...but you have the key!");
                enterRoom(nextRoom, true);
            }
            else {
                System.out.println("The door is locked...you need to find the key!");
            }
        }
        else {
            enterRoom(nextRoom, true);
            if(player.getMovesLeft() == 0) {
                System.out.println("You ran out of moves! Game Over...");
                return true; // signals to quit the game
            }
        }
        return false;
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
     * Print out information about the current location
     */
    private void look()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Eat some food to reduce hunger
     * @param command The command that was entered
     */
    private void eat(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Eat what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item itemToEat = player.dropItem(itemName);

        if(itemToEat == null) {
            System.out.println("That item doesn't exist.");
        }
        else if(!itemToEat.isEdible()) {
            System.out.println("You can't eat that...");
        }
        else {
            player.ingest(itemToEat);
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * "Back" was entered. Takes the player back to the previous room they
     * were in.
     * @param command The command that was entered
     */
    private void goBack(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("\"back\" command does not allow a second word.");
            return;
        }
        if(player.getRoomHistory().empty()) {
            System.out.println("There is nowhere to go back to.");
        }
        else {
            enterRoom(player.getPreviousRoom(), false);
        }
    }

    /**
     * Player picks up and item to carry with them
     * @param command The command that was entered
     */
    private void take(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item itemToTake = player.getCurrentRoom().removeItemFromRoom(itemName);

        if(itemToTake == null) {
            System.out.println("That item doesn't exist in this room");
        }
        else if((itemToTake.getWeight() + player.getCurrentCarryWeight()) >
                player.getMaxCarryWeight()) {
            System.out.println("It's too heavy! You can carry up to " +
                    player.getMaxCarryWeight() + " units. Maybe if you dropped \n" +
                    "some items you could manage it. Or perhaps it's simply too heavy.");
        }
        else {
            player.takeItem(itemToTake);
            System.out.println("You picked up " + itemToTake.getDescription() +
                    "!");
        }
    }

    /**
     * Player drops an item so they no longer have to carry it
     * @param command The command that was entered
     */
    private void drop(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item droppedItem = player.dropItem(itemName);

        if(droppedItem == null) {
            System.out.println("You don't have one of those.");
        }
        else {
            System.out.println("You dropped " + droppedItem.getDescription());
            player.getCurrentRoom().addItem(droppedItem);
        }
    }

    /**
     * Prints out the details of all of the items that the player is
     * currently carrying
     */
    private void showItems()
    {
        System.out.println(player.getCurrentItemDetails());
    }

    /**
     * Prints out the players current stats
     */
    private void showStats()
    {
        System.out.println(player.getStats());
    }

    /**
     * Charges the beamer. The beamer memorizes the location of the players
     * current room.
     * @param command The command that was entered
     */
    private void chargeBeamer(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Charge what?");
            return;
        }

        String itemToCharge = command.getSecondWord();

        if(!itemToCharge.equals("beamer")) {
            System.out.println("That item can't be charged.");
        }
        else {
            System.out.println("Beamer charged!");
            player.setBeamerLocation();
            player.setBeamerCharge(true);
        }
    }

    /**
     * Fires the beamer. Returns the player to the location at which the
     * beamer was last charged.
     * @param command The command that was entered
     */
    private void fireBeamer(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Fire what?");
            return;
        }

        String itemToFire = command.getSecondWord();

        if(!itemToFire.equals("beamer")) {
            System.out.println("That item can't be fired.");
        }
        else if(player.getBeamerCharge() == false){
            System.out.println("Your beamer isn't charged!");
        }
        else {
            System.out.println("Beamer fired!");
            enterRoom(player.getBeamerLocation(), true);
        }
    }

    /**
     * Talk a character
     * @param command The command that was entered
     */
    public void talk(Command command)
    {
        if(!command.hasSecondWord()){
            System.out.println("Talk to who?");
        }

        String characterName = command.getSecondWord();
        Character character = player.getCurrentRoom().getCharacter(characterName);

        if(character == null) {
            System.out.println("That is not someone who can be spoken to.");
        }
        else {
            System.out.println(character.getInitialDialog());
        }
    }

    /**
     * Gives the item to the specified character
     * @param command The command that was entered
     *
     */
    public void giveItem(Command command)
    {
        if(!command.hasSecondWord()){
            System.out.println("Give what... to who...?");
            return;
        }

        if(!command.hasThirdWord()){
            System.out.println("Give it to who?");
            return;
        }

        String characterName = command.getThirdWord();
        Character character = player.getCurrentRoom().getCharacter(characterName);

        if(character == null) {
            System.out.println("Who is \"" + characterName + "\" ?");
            return;
        }

        String itemSoughtName = character.getItemSought();
        String itemToGiveName = command.getSecondWord();
        Item itemToGive = player.getItem(itemToGiveName);
        Item itemForReward = character.getItemForReward();

        if(itemToGive == null) {
            System.out.println("You don't have a(n) \"" + itemToGiveName + "\"");
        }
        else if(itemToGive.getName().equals(itemSoughtName)) {
            player.dropItem(itemToGiveName);
            System.out.println(character.getAcceptanceDialog());
            player.takeItem(itemForReward);
            System.out.println("Received " + itemForReward.getDescription() + "!");
        }
        else {
            System.out.println(characterName + " doesn't want your " + itemToGiveName);
        }
    }
}
