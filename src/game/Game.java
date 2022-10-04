package game;

import city.cs.engine.DynamicBody;
import game.gui.ButtonController;
import org.jbox2d.common.Vec2;

import javax.swing.JFrame;
import java.awt.*;
import java.io.ObjectOutputStream;

/**
 * Your main game entry point
 */
public class Game {
    private GameLevel currentLevel;
    private final GameView view;
    /** Initialise a new Game. */
    public Game() {

        //1. make your game world
        currentLevel = new LevelOne(this);
        view = new GameView (currentLevel, 600, 500);
        view.setZoom(20);

        view.addMouseListener(new GiveFocus(view));

        view.addKeyListener(currentLevel);

        //3. create a Java window (frame) and add the game
        //   view to it
        final JFrame frame = new JFrame("City Game");
        frame.add(view);
        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        // finally, make the frame visible
        frame.setVisible(true);
        ButtonController buttonController = new ButtonController(this);
        frame.add(buttonController, BorderLayout.SOUTH);
        frame.pack();
        //optional: uncomment this to make a debugging view
        //  JFrame debugView = new DebugViewer(world, 500, 500);
        // start our game world simulation!
        this.currentLevel.start();
    }

    /**
     * Player automatically switches to the next level in the game
     */
    public void goToNextLevel (){
        if (currentLevel.getLevelType() == LevelType.LEVEL_ONE) {
            goToSpecificLevel(2);
        }
        else if (currentLevel.getLevelType() == LevelType.LEVEL_TWO){
            goToSpecificLevel(3);
        }
        else if (currentLevel.getLevelType() == LevelType.LEVEL_THREE){
            System.out.println("Goodbye!!");
            System.exit(0);
        }
    }

    /**
     * Player moves to a specific level not in any particular order
     * @param whichLevel the level the player will move to
     */
    public void goToSpecificLevel(int whichLevel){
        if (whichLevel == -1){
            return;
        }
        view.removeKeyListener(currentLevel);
        currentLevel.stopBackgroundMusic();
        currentLevel.stop();
        switch (whichLevel){
            case 1 :
                currentLevel = new LevelOne(this);
                break;
            case 2 :
                currentLevel = new LevelTwo(this);
                break;
            case 3 :
                currentLevel = new LevelThree(this);
                break;
        }
        view.setWorld(currentLevel);
        view.addKeyListener(currentLevel);
        view.setBackgroundImage(currentLevel.getBackgroundImage());
        currentLevel.start();
    }

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }


    /** Run the game. */
    public static void main(String[] args) {
        new Game();
    }
}
