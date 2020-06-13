package com.bdtripp.hauntedhouse;
//TODO add descriptions of how to use each command in the help section

import java.util.ArrayList;
import java.util.Random;

/**
 *  This class is the main class of the "Haunted House" application.
 *  "Haunted House" is a text based adventure game in which the user can
 *  explore rooms in a haunted house. The goal of the game is to try to find
 *  your way out of the house.
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2019.01.13
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
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while(! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Haunted House!");
        System.out.println("Haunted House is a spooky adventure game.");
        System.out.println("Those who enter may never escape.");
        System.out.println("Find the exit and you just might survive.");
        System.out.println("Type \"help\" if you need help.");
        System.out.println();
        printRoomDetails();
    }

    /**
     * Print details about the room including the items and characters that it contains
     */
    public void printRoomDetails()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println(player.getCurrentRoom().getItemsInRoomDetails());
        System.out.println(player.getCurrentRoom().getCharactersInRoomDetails());
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
        System.out.println(parser.getCommands());
        System.out.println("To move, type \"go\" + \"space\" + \"a direction\"");
        System.out.println("Directions are north, south, east, or west");
        System.out.println("To give an item to a character, type \"give\" + \"space\" + \"the name of the item you want" +
                " to give\" + \"space\" + \"the name of the character you want to give it to\"");
        System.out.println("Your command words are:");
        System.out.println("To exit the game, type \"quit\"");
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
     */
    private void enterRoom(Room nextRoom, boolean addToHistory)
    {
        player.moveToRoom(nextRoom, addToHistory);
        printRoomDetails();
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

    //TODO make sure to upload map to github
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
