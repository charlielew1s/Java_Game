package game;

import city.cs.engine.UserView;

import javax.swing.*;
import java.awt.*;

/**
 * Defines the viewable area of the World. Each level has its own unique World.
 */
public class GameView extends UserView {

    /* Defines the background image that the player sees whilst playing the game. This will change as the view is shifted to different
    levels */
    private Image background;
    // the font used to write the score on the top left of the page
    private static final Font myFont = new Font("TimesRoman", Font.BOLD,15);

    /**
     * The constructor for the GameView object
     * @param level the level passed to the view
     * @param width the width of the field of view
     * @param height the height of the field of view
     */
    public GameView(GameLevel level, int width, int height) {
        super(level, width, height);
        this.background = level.getBackgroundImage();
    }
    @Override
    /**
     * Create and size the background image for the specific game level
     */
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, -35, 0, this);
    }
    @Override
    /**
     * Create the writing on the game that shows the player their score
     */
    protected void paintForeground(Graphics2D g) {
        setFont(myFont);
        g.drawString("Score : " + ScoreKeeper.getScore(), 500, 17);
    }

    /**
     * Set the background image for each level
     * @param image the background image
     */
    public void setBackgroundImage(Image image){
        this.background = image;
    }
}