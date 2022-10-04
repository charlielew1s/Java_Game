package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.Key;

/**
 * The second level of the game
 */
public class LevelTwo extends GameLevel {
    private static final float WALKING_SPEED = 9;
    private final Game game;
    private final SunmiNet sunmiNet;
    private final Image background;
    private final Hilux hilux;
    private SoundClip ohSorryYa;
    /**
     * Constructor for level two
     * @param game the game itself, passed to the level two constructor
     */
    public LevelTwo(Game game){
        super(game);
        this.game = game;
        this.levelType = LevelType.LEVEL_TWO;
        try {
            ohSorryYa = new SoundClip("data/ohSorryYa.mp3");
            ohSorryYa.setVolume(0.08f);
            ohSorryYa.loop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        sunmiNet = new SunmiNet(this);
        sunmiNet.setPosition(new Vec2(-6.5f, -7.5f));
        heliDropper = new HeliDropper(this, true);
        heliDropper.dropCharacter();
        background = new ImageIcon("data/mariebyrdlandleveltwo.png").getImage();
        hilux = new Hilux(this);
        hilux.setPosition(new Vec2(12.5f, -8.5f));
    }

    /**
     * Determines whether or not the Level Two is complete
     * @return true if level is complete
     */
    @Override
    public boolean isComplete() {
        return ScoreKeeper.getScore() >= 800;
    }

    /**
     * Gets the background image for this level
     * @return the background image for this level
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
        ohSorryYa.stop();
    }

    @Override
    public void setSunmiPosition(Vec2 position, String direction) {
        sunmiNet.updatePosition(position,direction);
    }

    // what happens when there is a collision between the reporters in this level and the other objects
    @Override
    public void collide(CollisionEvent collisionEvent) {
        GenericCharacter reporter = (GenericCharacter) collisionEvent.getReportingBody();
        Body other = collisionEvent.getOtherBody();
        if (other instanceof SunmiNet){
            if (reporter.isSupporter()){
                ScoreKeeper.incrementScore(100);
            }
            else {
                game.goToSpecificLevel(2);
            }
            if (isComplete()){
                ohSorryYa.stop();
                this.game.goToNextLevel();
            }
            System.out.println("Current Score : " + ScoreKeeper.getScore());
        }
        else if (reporter.isSupporter()) {
            ScoreKeeper.decrementScore(100);
            splat.play();
        }
        else{
            splat.play();
        }
        if (ScoreKeeper.getScore() < 0){
            game.goToSpecificLevel(2);
        }
        reporter.destroy();
        heliDropper.dropCharacter();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP){
            sunmiNet.jump(WALKING_SPEED);
        }
        else if (code == KeyEvent.VK_DOWN){
            sunmiNet.stopWalking();
        }
        else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT){
            float currentXVelocity = sunmiNet.getLinearVelocity().x;
            if (currentXVelocity == 0){
            /* the net will initially face right and pressing VK_RIGHT will move the net
            right. Therefore, when the player adds speed the velocity will be positive.  */
                if (code == KeyEvent.VK_RIGHT){
                    sunmiNet.startWalking(WALKING_SPEED);
                }
            /* the net will initially face right and pressing VK_LEFT will move the net
            left. Therefore, when the player adds speed the velocity must be negated.  */
                else{
                    sunmiNet.startWalking(WALKING_SPEED * -1);
                }
            }
        /* If the net is already moving, then the following conditions apply. If a key is pressed to move the net in the
        same direction as it is currently moving, the speed will increase by a value of 1.2f. If a key is pressed to move the net
        in the opposite direction, the velocity will be negated although there will be no change in speed.
         */
            else {
                if (sunmiNet.getDirection() == "right"){
                    if (code == KeyEvent.VK_RIGHT){
                        sunmiNet.startWalking(currentXVelocity * 1.1f);
                    }
                    else {
                        sunmiNet.startWalking(currentXVelocity * -1);
                    }
                }
                else{
                    if (code == KeyEvent.VK_LEFT){
                        sunmiNet.startWalking(currentXVelocity * 1.1f);
                    }
                    else{
                        sunmiNet.startWalking(currentXVelocity * -1);
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
