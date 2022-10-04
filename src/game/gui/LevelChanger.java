package game.gui;

import javax.swing.*;

/**
 * Used to change the levels in the game
 */
public class LevelChanger {
    private String[] levelsToChoose = {"Level One", "Level Two", "Level Three"};
    private String newLevel;

    /**
     * Level changer constructor
     */
    public LevelChanger() {
        this.newLevel = (String) JOptionPane.showInputDialog(
                null,
                "What level do you want to switch to?",
                "Choose Level",
                JOptionPane.QUESTION_MESSAGE,
                null,
                levelsToChoose,
                levelsToChoose[0]
        );
    }

    /**
     * Gets a different level
     * @return a different level
     */
    public int getNewLevel() {
        int returnValue = -1;
        if (levelsToChoose[0].equals(newLevel)) {
            returnValue = 1;
        } else if (levelsToChoose[1].equals(newLevel)) {
            returnValue = 2;
        } else if (levelsToChoose[2].equals(newLevel)) {
            returnValue = 3;
        }
        return returnValue;
    }
}