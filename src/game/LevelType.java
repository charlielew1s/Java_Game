package game;

/**
 * Used to determine what the current level is
 */
public enum LevelType {
    LEVEL_ONE(1),
    LEVEL_TWO(2),
    LEVEL_THREE(3);
    int value;

    /**
     * The constructor for the level type enum
     * @param value the value of the current level.
     */
    LevelType(int value){
        this.value = value;
    }

    /**
     * Gets the current value of the level type enum
     * @return the current level
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the current level
     * @param value returns the updated level type enum value
     */
    public void setValue(int value) {
        this.value = value;
    }
}
