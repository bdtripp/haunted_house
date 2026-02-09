package com.bdtripp.hauntedhouse;

/**
 * Class Character - a character in the haunted house.
 *
 * This class is part of the Haunted House application.
 * Haunted House is a text based adventure game.
 *
 * A "Character" represents a character that the player can interact with.
 * A character may offer to help the player in exchange for certain items.
 *
 * @author Brian Tripp
 * @version 2020.06.13
 */
public class Character
{
    private String name;
    private String initialDialog;
    private String acceptanceDialog;
    private String itemSought;
    private Item itemForReward;

    /**
     * Constructor for objects of class Character
     * @param name The name of the character
     * @param initialDialog The initial dialog that the character speaks
     * @param acceptanceDialog The dialog that the character speaks upon accepting an item
     * @param itemSought The name of the item that the character is seeking
     * @param itemForReward The item that the character rewards the player with
     */
    public Character(
            String name,
            String initialDialog,
            String acceptanceDialog,
            String itemSought,
            Item itemForReward
    )
    {
        this.name = name;
        this.initialDialog = initialDialog;
        this.acceptanceDialog = acceptanceDialog;
        this.itemSought = itemSought;
        this.itemForReward = itemForReward;
    }

    /**
     * Get the name of the character
     * @return The name of the character
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the initial dialog that the character speaks
     * @return The initial dialog that the character speaks
     */
    public String getInitialDialog()
    {
        return initialDialog;
    }

    /**
     * Get the dialog that the character speaks upon accepting an item
     * @return The dialog that the character speaks upon accepting an item
     */
    public String getAcceptanceDialog()
    {
        return acceptanceDialog;
    }

    /**
     * Get the name of the item that the character is seeking
     * @return The name of the item at the character is seeking
     */
    public String getItemSought()
    {
        return itemSought;
    }

    /**
     * Get the item that the character has as a reward
     * @return The item that the character has as a reward
     */
    public Item getItemForReward()
    {
        return itemForReward;
    }
}

