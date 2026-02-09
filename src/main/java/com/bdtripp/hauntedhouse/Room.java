package com.bdtripp.hauntedhouse;

import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Class Room - a room in the haunted house.
 *
 * This class is part of the "Haunted House" application.
 * "Haunted House" is a text based adventure game.
 *
 * A "Room" represents one location in the layout of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author  Michael Kölling, David J. Barnes, and Brian Tripp
 * @version 2020.06.13
 */
public class Room
{
    private String description;
    private HashMap<String, Exit> exits;
    private ArrayList<Item> items;
    private ArrayList<Character> characters;

    /**
     * Create a room. Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        characters = new ArrayList<>();
    }

    /**
     * Create an item that exists in this room
     * @param name The name of the item
     * @param description The description of the item
     * @param weight The weight of the item
     * @param isEdible The edibility of the item
     * @param propToAffect The property the item affects
     * @param affectValue The amount the property will be changed by
     */
    public void addItem(String name, String description, int weight, boolean isEdible, String propToAffect, int affectValue)
    {
        items.add(new Item(name, description, weight, isEdible, propToAffect, affectValue));
    }

    /**
     * Create an item that exists in this room
     * @param item The item to add
     */
    public void addItem(Item item)
    {
        items.add(item);
    }

    /**
     * Remove an item from this room
     * @param name The name of the item to remove
     * @return item The item that was removed
     */
    public Item removeItemFromRoom(String name)
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
     * Create a character that will exist in this room
     * @param name The name of the character
     * @param initialDialog The initial dialog the character will speak
     * @param acceptanceDialog The dialog the character will speak upon accepting an item
     * @param itemSought The name of the item that the character is seeking
     * @param itemForReward The item the character will reward a player
     */
    public void addCharacter(String name, String initialDialog,
                             String acceptanceDialog, String itemSought, Item itemForReward)
    {
        characters.add(new Character(
                name,
                initialDialog,
                acceptanceDialog,
                itemSought,
                itemForReward
        ));
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     * @param locked True if the door is locked
     */
    public void setExit(String direction, Room neighbor, boolean locked)
    {
        Exit exit = new Exit(direction, neighbor, locked);
        exits.put(direction, exit);
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction." If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction
     */
    public Room getExitNeighbor(String direction)
    {
        if (exits.containsKey(direction)) {
            return exits.get(direction).getNeighbor();
        }
        return null;
    }

    /**
     * Get the exit in the direction provided
     * @param direction The direction of the exit
     * @return The exit in the direction proved
     */
    public Exit getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Return a description of the room's exits,
     * for example, "Exits: north west".
     * @return A description of the available exits
     */
    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Get the details about the items in this room
     * @return Details about the items in this room such as description
     * and weight
     */
    public String getItemsInRoomDetails()
    {
        String returnString = "";
        if (items.isEmpty()) {
            return "No items were found.";
        }
        returnString += "You found:\n";
        for(Item item : items) {
            returnString += item.getDescription() +
                    "(weight: " + item.getWeight() + ") To take item use the" +
                    " command: take " + item.getName();
        }
        return returnString;
    }

    /**
     * Get the details about the characters in this room
     * @return Details about the characters in this room such as name
     * and dialog
     */
    public String getCharactersInRoomDetails()
    {
        String returnString = "";
        if (characters.isEmpty()) {
            return "No one is here.";
        }
        returnString += "Characters:\n";
        for(Character character : characters) {
            returnString += character.getName();
        }
        return returnString;
    }

    /**
     * Return a long desciption of this room, of the form:
     *      You are in the kitchen.
     *      Exits: north west
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Get the character in a room
     * @return The character that is in the room
     */
    public Character getCharacter(String name)
    {
        for(Character character : characters) {
            if(character.getName().toLowerCase().equals(name.toLowerCase())) {
                return character;
            }
        }
        return null;
    }
}
