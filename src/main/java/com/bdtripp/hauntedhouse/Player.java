package com.bdtripp.hauntedhouse;

import java.util.ArrayList;
import java.util.Stack;
/**
 * Class Player - a player in the game.
 *
 * This class is part of the Haunted House application.
 * Haunted House is a text based adventure game.
 *
 * A "Player" represents a player in the game and keeps track of where the player is, what items they are carrying,
 * and their current stats.
 *
 * @author  Brian Tripp
 * @version 2026.02.05
 */
public class Player
{
    private String name;
    private int maxCarryWeight;
    private int health = 100;
    private int strength = 10;
    private ArrayList<Item> items;
    private Room currentRoom;
    private Stack<Room> roomHistory;
    private static final int MAX_MOVES_ALLOWED = 30;
    private Room beamerLocation;
    private boolean beamerCharged;

    /**
     * Constructor for objects of class Player
     * @param name The name of the player
     * @param maxCarryWeight The maximum weight the player can carry
     */
    public Player(String name, int maxCarryWeight)
    {
        items = new ArrayList<>();
        this.maxCarryWeight = maxCarryWeight;
        this.name = name;
        roomHistory = new Stack<>();
    }

    /**
     * Set whether or not the beamer is charged
     * @param value True if the beamer is charged, otherwise false
     */
    public void setBeamerCharge(boolean value) {
        beamerCharged = value;
    }

    /**
     * Get the beamer charge
     * @return Whether or not the beamer is charged
     */
    public boolean getBeamerCharge() {
        return beamerCharged;
    }
    
    /**
     * Set the maximum weight that the player can carry at a time
     */
    public void setMaxCarryWeight(int max)
    {
        maxCarryWeight = max;
    }

    /**
     * Get the maximum weight that the player can carry at a time
     * @return The maximum weight the player can carry
     */
    public int getMaxCarryWeight()
    {
        return maxCarryWeight;
    }

    /**
     * Moves the player to the specified room
     * @param room The room to move the player to
     * @param addToHistory True if current room should be added to history
     */
    public void moveToRoom(Room room, boolean addToHistory)
    {
        if(addToHistory) {
            updateRoomHistory();
        }
        currentRoom = room;
    }

    /**
     * Adds the current room to the room history
     */
    public void updateRoomHistory()
    {
        roomHistory.push(currentRoom);
    }

    /**
     * Get the history of the rooms the player has been in
     * @return The history of the rooms the player has been in
     */
    public Stack<Room> getRoomHistory()
    {
        return roomHistory;
    }

    /**
     * Get the current room
     * @return The room the player is currently in
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Gets the previous room and removes it from the room history
     * @return The previous room
     */
    public Room getPreviousRoom()
    {
        return roomHistory.pop();
    }

    /**
     * Adds an item to the list of what the player is carrying
     * @param item The item to take
     */
    public void takeItem(Item item)
    {
        items.add(item);
    }

    /**
     * Removes an item from the list of what the player is currently carrying
     * @param name The name of the item to drop
     * @return The item that was dropped
     */
    public Item dropItem(String name)
    {
        for(Item item : items) {
            if(item.getName().equals(name)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    /**
     * Get the weight of the items that the player is currently carrying
     * @return The total weight of the items
     */
    public int getCurrentCarryWeight()
    {
        int totalWeight = 0;
        for (Item item : items) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    /**
     * Get the items the player is currently carrying
     * @return The items the player is carrying
     */
    public ArrayList<Item> getItems()
    {
        return items;
    }

    /**
     * Get an item the player is currently carrying
     * @param name The name of the item
     * @return The item with the specified name, or null if the player is not
     * carrying an item with that name
     */
    public Item getItem(String name)
    {
        for(Item item : items) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * See if the player has the key
     * @return True if the player has the key
     */
    public boolean hasKey()
    {
        for(Item item : items) {
            if(item.getName().equals("key")) {
                return true;
            }
        }
        return false;
    }
    /**
     * Get the details of the items that the player is carrying
     * @return The details of each item
     */
    public String getCurrentItemDetails()
    {
        if(items.isEmpty()) {
            return "There are no items in your posession.";
        }
        String returnString = "You are carrying the following:\n\n";
        for(Item item : items) {
            returnString += "Name: " + item.getName() + "\n";
            returnString += "Description: " + item.getDescription() + "\n";
            returnString += "Weight: " + item.getWeight() + "\n\n";
        }
        returnString += "Total Weight: " + getCurrentCarryWeight() + "\n\n";
        return returnString;
    }

    /**
     * Get the players current stats
     * @return The players stats
     */
    public String getStats() {
        String returnString = "Health: " + health + "\n";
        returnString += "Strength: " + strength + "\n";
        returnString += "Maximum Carry Weight: " + maxCarryWeight + "\n";

        return returnString;
    }

    /**
     * Get the number of moves that a player has left
     * @return The number of moves left
     */
    public int getMovesLeft()
    {
        return MAX_MOVES_ALLOWED - roomHistory.size();
    }

    /**
     * Sets the location of a beamer charge
     */
    public void setBeamerLocation()
    {
        beamerLocation = currentRoom;
    }

    /**
     * Gets the location of the last beamer charge
     * @return The beamer location
     */
    public Room getBeamerLocation()
    {
        return beamerLocation;
    }

    /**
     * Ingest an item
     * @param item The item to ingest
     * @return A message to display when the item is ingested
     */
    public String ingest(Item item) {
        String propToAffect = item.getPropToAffect();
        int affectValue = item.getAffectValue();

        if(propToAffect.equals("health")) {
            health += affectValue;
        }
        else if(propToAffect.equals("strength")) {
            strength += affectValue;
        }
        else if(propToAffect.equals("maximum carry weight")) {
            maxCarryWeight += affectValue;
        }

        return "That was delicious. Not only that but your " + propToAffect + " increased by " + affectValue + "!";
    }

    /**
     * Set the health value of the player
     * @param value The value to set health to
     */
    public void setHealth(int value) {
        health = value;
    }

    /**
     * Set the strength value of the player
     * @param value The value to set strength to
     */
    public void setStrength(int value) {
        strength = value;
    }
}

