package com.bdtripp.hauntedhouse;

/**
 * Class Item - an item in the haunted house.
 *
 * This class is part of the "Haunted House" application.
 * "Haunted House" is a text based adventure game.
 *
 * A "Item" represents an object that can be found. Items will help the player
 * in various ways. Some items may be too heavy to pick up.
 *
 * @author Brian Tripp
 * @version 2020.06.13
 */
public class Item
{
    private String name;
    private String description;
    private int weight;
    private boolean isEdible;
    private String propToAffect;
    private int affectValue;

    /**
     * Constructor for objects of class Item
     * @param name The name of the item
     * @param description A description of the item
     * @param weight The items weight
     * @param isEdible Whether or not the item is edible
     * @param propToAffect The property of a player that the item has an affect on
     * @param affectValue The amount that the property will be affected by
     */
    public Item(String name, String description, int weight, boolean isEdible, String propToAffect, int affectValue)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.isEdible = isEdible;
        this.propToAffect = propToAffect;
        this.affectValue = affectValue;
    }

    /**
     * Get the name of the item
     * @return The name of the item
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the description of the item
     * @return The description of the item
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Get the weight of the item
     * @return The weight of the item
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Figure out if this item is edible or not
     * @return True if this item is edible
     */
    public boolean isEdible()
    {
        return isEdible;
    }

    /**
     * @return The property the item has an affect on
     */
    public String getPropToAffect() {
        return propToAffect;
    }

    /**
     * @return The amount that the item will have an affect on a property
     */
    public int getAffectValue() {
        return affectValue;
    }
}
