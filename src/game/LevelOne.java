package game;

import city.cs.engine.*;
import game.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * The first level of the game
 */
public class LevelOne extends GameLevel {
    private final Game game;
    private final SunmiCannon sunmiCannon;
    private final AntarcticRock antarcticRock;
    private SoundClip pporappippam;
    private static final float JUMPING_SPEED = 9;
    private final Hilux hilux;

    /**
     * Constructor for level one
     * @param game the game itself, passed to the level one constructor
     */
    public LevelOne(Game game){
        super(game);
        this.game = game;
        this.levelType = LevelType.LEVEL_ONE;
        try {
            pporappippam = new SoundClip("data/pporappippam.mp3");
            pporappippam.setVolume(0.08f);
            pporappippam.loop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
        this.background = new ImageIcon("data/mariebyrdlandlevelone.png").getImage();
        antarcticRock = new AntarcticRock(this);
        antarcticRock.setPosition(new Vec2(-15f, -8.5f));
        sunmiCannon = new SunmiCannon(this);
        sunmiCannon.setPosition(new Vec2(-6.5f,-8.4f));
        hilux = new Hilux(this);
        hilux.setPosition(new Vec2(13, -8.5f));
        heliDropper = new HeliDropper(this,true);
        heliDropper.dropCharacter();
    }

    /** Determines whether or not the current level is complete
     * @return true if level is complete
     */
    @Override
    public boolean isComplete() {
        return ScoreKeeper.getScore() == 500;
    }

    /** Gets the background image for this level
     * @return the background image for the level
     */
    @Override
    public Image getBackgroundImage() {
        return this.background;
    }

    /**
     * Stops the background music for this level
     */
    @Override
    public void stopBackgroundMusic() {
        pporappippam.stop();
    }

    @Override
    public void setSunmiPosition(Vec2 position, String direction) {
        sunmiCannon.updatePosition(position,direction);
    }

    // what happens when there is a collision between the reporters in this level and the other objects
    @Override
    public void collide(CollisionEvent collisionEvent) {
        // play the splat sound
        splat.play();
        GenericCharacter reporter = (GenericCharacter) collisionEvent.getReportingBody();
        Body other = collisionEvent.getOtherBody();
        if (other instanceof DynamicBody){
            DynamicBody db = (DynamicBody) other;
            if (db.isBullet()){
                db.destroy();
                if (reporter.isSupporter()){
                    ScoreKeeper.decrementScore(100);
                }
                else {
                    ScoreKeeper.incrementScore(100);
                }
                if (isComplete()){
                    pporappippam.stop();
                    this.game.goToNextLevel();
                }
                System.out.println("Current Score : " + ScoreKeeper.getScore());
            }
            //hello there
            else if (db instanceof SunmiCannon){
                game.goToSpecificLevel(1);
            }
        }
        else{
            if (!reporter.isSupporter()){
                ScoreKeeper.decrementScore(100);
            }
        }
        if (ScoreKeeper.getScore() < 0) {
            game.goToSpecificLevel(1);
        }
        reporter.destroy();
        heliDropper.dropCharacter();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    // what happens when the player presses a key
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // if the s key is pressed
        if (code == KeyEvent.VK_S) {
            sunmiCannon.shootCannon();
        }
        // if the up arrow key is pressed
        else if (code == KeyEvent.VK_UP){
            sunmiCannon.jump(JUMPING_SPEED);
        }
        // if the down arrow key is pressed
        else if (code == KeyEvent.VK_DOWN){
            sunmiCannon.stopWalking();
        }
        // if cannon is stationary, the cannon must first begin walking
        else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT){
            float walkingSpeed = 9.0f;
            float currentXVelocity = sunmiCannon.getLinearVelocity().x;
            if (currentXVelocity == 0){
            /* the cannon will initially face right and pressing VK_RIGHT will move the cannon
            right. Therefore, when the player adds speed the velocity will be positive.  */
                if (code == KeyEvent.VK_RIGHT){
                    sunmiCannon.startWalking(walkingSpeed);
                }
            /* the cannon will initially face right and pressing VK_LEFT will move the cannon
            left. Therefore, when the player adds speed the velocity must be negated.  */
                else{
                    sunmiCannon.startWalking(walkingSpeed * -1);
                }
            }
        /* If the cannon is already moving, then the following conditions apply. If a key is pressed to move the cannon in the
        same direction as it is currently moving, the speed will increase by a value of 1.2f. If a key is pressed to move the cannon
        in the opposite direction, the velocity will be negated although there will be no change in speed.
         */
            else {
                if (sunmiCannon.getDirection() == "right"){
                    if (code == KeyEvent.VK_RIGHT){
                        sunmiCannon.startWalking(currentXVelocity * 1.1f);
                    }
                    else {
                        sunmiCannon.startWalking(currentXVelocity * -1);
                    }
                }
                else{
                    if (code == KeyEvent.VK_LEFT){
                        sunmiCannon.startWalking(currentXVelocity * 1.1f);
                    }
                    else{
                        sunmiCannon.startWalking(currentXVelocity * -1);
                    }
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

