package com.bdtripp.hauntedhouse;

//TODO add description of class below
/**
 * Class Exit - an exit of a room
 *
 * This class is part of the "Haunted House" application.
 * "Haunted House" is a text based adventure game.
 *
 * Exits are associated with a particular room and allow the character to move from one room to the next.
 * An exit can be located at one of four directions - north, east, south, or west. Exits can be either locked or unlocked.
 *
 * @Brian Tripp
 * @version 2020.06.13
 */
public class Exit
{
    private String direction;
    private Room neighbor;
    private boolean locked;
    /**
     * Constructor for objects of class Exit
     */
    public Exit(String direction, Room neighbor, boolean locked)
    {
        this.direction = direction;
        this.neighbor = neighbor;
        this.locked = locked;
    }

    /**
     * Get the room that the exit leads to
     * @return The room that the exit leads to
     */
    public Room getNeighbor()
    {
        return neighbor;
    }

    /**
     * See if the exit is locked
     * @return True if the exit is locked
     */
    public boolean isLocked()
    {
        return locked;
    }
}

