package game.gui;

import game.SupporterHandler;

/**
 * Determines the name and position of a supporter in level 3
 */

public class SupporterState {
    private String name;
    private int position;

    /**
     * Constructor of the SupporterState object
     * @param name the name of the supporter
     * @param pos the position of the supporter
     */
    public SupporterState(String name, int pos){
        this.name = name;
        this.position = pos;
    }

    /**
     * Gets the name of the supporter
     * @return the name of the supporter
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the position of the supporter
     * @return the position of the supporter
     */
    public int getPosition() {
        return position;
    }

}
