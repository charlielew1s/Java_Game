package game.gui;

import org.jbox2d.common.Vec2;

import java.util.List;

/**
 * Used to hold a dataset of all the attributes of a Game at a particular moment
 */

public class GameStateData {
    private int score;
    private int level;
    private String fallingCharName;
    private Vec2 fallingCharPos;
    private float fallingCharGravityScale;
    private List<SupporterState> level3SupporterList;
    private boolean hasFallingChar;
    private String activitySunmi;
    private Vec2 posSunmi;
    private boolean hasSunmi;
    private String sunmiDirection;

    /**
     * Constructor for the Game State Data object. The boolean hasFallingChar, which flags true
     * if the state has a falling character and false if it does not, is initialised to false
     */
    GameStateData(){
        hasFallingChar = false;
    }
    /**
     * Returns the current score of the level
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Changes the score of the level
     * @param score the new score value of the level
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the current level
     * @return the current level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Changes the level the player will play
     * @param level the level the game is switched to
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Get the falling character
     * @return the name of the currently falling generic character
     */
    public String getFallingCharName() {
        return fallingCharName;
    }

    /**
     * Change the name of the currently falling generic character
     * @param fallingCharName the new name of the generic character
     */
    public void setFallingCharName(String fallingCharName) {
        this.fallingCharName = fallingCharName;
    }

    /**
     * Get the position of the falling character
     * @return the current position of the falling character
     */

    public Vec2 getFallingCharPos() {
        return fallingCharPos;
    }

    /**
     * Change the position of the currently falling generic character
     * @param fallingCharPos the new position of this generic character
     */

    public void setFallingCharPos(Vec2 fallingCharPos) {
        this.fallingCharPos = fallingCharPos;
    }

    /**
     * Change the gravity scale of the currently falling character
     * @return the gravity scale of the current falling generic character
     */
    public float getFallingCharGravityScale() {
        return fallingCharGravityScale;
    }

    /**
     * Change the gravity scale of the currently falling generic character
     * @param fallingCharGravityScale the new gravity sale value of the falling generic character
     */
    public void setFallingCharGravityScale(float fallingCharGravityScale) {
        this.fallingCharGravityScale = fallingCharGravityScale;
    }

    /**
     * Get the position of the controllable Sunmi object
     * @return the position of the controllable Sunmi object
     */
    public Vec2 getPosSunmi() {
        return posSunmi;
    }

    /**
     * Change the position of the controllable Sunmi object
     * @param posSunmi the new position of the Sunmi object
     */
    public void setPosSunmi(Vec2 posSunmi) {
        this.posSunmi = posSunmi;
    }

    /**
     * A boolean method to determine whether or not the game state contains a falling Generic Character object
     * @return true if the game state contains a falling generic character
     */
    public boolean hasFallingChar() {
        return hasFallingChar;
    }

    /**
     * A boolean methof to determine whether or not the game state contains a controllable Sunmi object
     * @return true if the game state contains a controllable Sunmi object
     */
    public boolean hasSunmi() {
        return hasSunmi;
    }

    /**
     * Change the boolean value of whether or not there is a controllable Sunmi object in the level
     * @param hasSunmi true if there is a controllable Sunmi object in the level.
     */
    public void setHasSunmi(boolean hasSunmi) {
        this.hasSunmi = hasSunmi;
    }

    /**
     * Gets Sunmi's current activity - either a net or a cannon
     * @return Sunmi's current activity - either a net or a cannon
     */
    public String getActivitySunmi() {
        return activitySunmi;
    }

    /**
     * Change Sunmi's current activity - either a net or a cannon
     * @param activitySunmi Sunmi's new activity
     */
    public void setActivitySunmi(String activitySunmi) {
        this.activitySunmi = activitySunmi;
    }

    /**
     * Change whether or not there is a falling character present in the game state
     * @param hasFallingChar the new boolean value
     */
    public void setHasFallingChar(boolean hasFallingChar) {
        this.hasFallingChar = hasFallingChar;
    }

    /**
     * Get a list of all supporters in level three
     * @return the list of all supporters in level three
     */
    public List<SupporterState> getLevel3SupporterList(){
        return level3SupporterList;
    }

    /**
     * Edit the list of supporters in level three
     * @param level3SupporterList the new supporter list in level three
     */
    public void setLevel3SupporterList(List<SupporterState> level3SupporterList) {
        this.level3SupporterList = level3SupporterList;
    }

    /**
     * Get Sunmi direction
     * @return direction
     */
    public String getSunmiDirection() {
        return sunmiDirection;
    }

    /**
     * Change the direction of the controllable Sunmi object
     * @param direction the new direction
     */
    public void setSunmiDirection(String direction) {
        this.sunmiDirection = direction;
    }
}
