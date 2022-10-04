package game.gui;

import game.Game;
import game.LevelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.ObjectOutputStream;
import java.util.concurrent.Flow;
/**
 * The class that implements the functionality for the gui buttons
 */
public class ButtonController extends JPanel implements ActionListener {
    private Game game;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton quitButton;
    private JButton levelSelectButton;
    private JButton loadStateButton;
    private JButton saveStateButton;
    private FlowLayout flowLayout;

    /**
     * The constructor used for ButtonController
     * @param game the game passed to the constructor
     */
    public ButtonController(Game game) {
        this.game = game;
        this.flowLayout = new FlowLayout(FlowLayout.LEFT);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.setLayout(flowLayout);
        // create pause button
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);
        add(pauseButton);
        // create resume button
        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(this);
        add(resumeButton);
        // create quit button
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        add(quitButton);
        //create button that selects levels
        levelSelectButton = new JButton("Select Level");
        levelSelectButton.addActionListener(this);
        add(levelSelectButton);
        //creates button that a game state from a game state file
        loadStateButton = new JButton("Load Game");
        loadStateButton.addActionListener(this);
        add(loadStateButton);
        //creates button that saves game state to a file
        saveStateButton = new JButton("Save Game");
        saveStateButton.addActionListener(this);
        add(saveStateButton);

        setBackground(Color.GRAY);
    }

    /**
     * Implements the gui actions performed when the button is pressed
     * @param e the variable used to call the action events
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //if the player presses the pause button
        if (e.getSource() == this.pauseButton) {
            if (game.getCurrentLevel().isRunning()) {
                game.getCurrentLevel().stop();
            }
            //if the player presses the resume button
        } else if (e.getSource() == this.resumeButton) {
            if (!game.getCurrentLevel().isRunning()) {
                game.getCurrentLevel().start();
            }
            //if the player presses the quit button
        } else if (e.getSource() == this.quitButton) {
            System.exit(0);
        }
        //if the player presses the select level button
        else if (e.getSource() == this.levelSelectButton){
            game.getCurrentLevel().stop();
            int newLevelToGoTo = (new LevelChanger()).getNewLevel();
            // only change level if new level is different than existing level
            if (game.getCurrentLevel().getLevelType().getValue() != newLevelToGoTo){
                game.goToSpecificLevel(newLevelToGoTo);
            }
            game.getCurrentLevel().start();
        }
        //if the player presses the button to save a game
        else if (e.getSource() == this.saveStateButton){
            game.getCurrentLevel().stop();
            GameStateHandler gameStateHandler = new GameStateHandler(game);
            gameStateHandler.saveState();
            game.getCurrentLevel().start();
        }
        //if the player presses a button to load a saved game
        else if (e.getSource() == this.loadStateButton){
            game.getCurrentLevel().stop();
            GameStateHandler gameStateHandler = new GameStateHandler(game);
            gameStateHandler.loadState();
            game.getCurrentLevel().start();
        }
    }
}
