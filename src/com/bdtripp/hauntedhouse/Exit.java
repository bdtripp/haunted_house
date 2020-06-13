package com.bdtripp.hauntedhouse;


/**
 * Write a description of class Exit here.
 *
 * @author (your name)
 * @version (a version number or a date)
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

