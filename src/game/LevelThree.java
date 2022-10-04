package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The third level of the game
 */
public class LevelThree extends GameLevel implements CollisionListener{
    private final Game game;
    private final StationaryGun gunOne;
    private final StationaryGun gunTwo;
    private final StationaryGun gunThree;
    private final Image background;
    private final SupporterHandler supporterHandler;
    private SoundClip youCantSitWithUs;
    /**
     * Constructor for level three
     * @param game the game itself, passed to the level three constructor
     */
    public LevelThree(Game game) {
        super(game);
        try{
            youCantSitWithUs = new SoundClip("data/youCantSitWithUs.mp3");
            youCantSitWithUs.setVolume(0.08f);
            youCantSitWithUs.loop();
        } catch (UnsupportedAudioFileException|IOException|LineUnavailableException e){
            e.printStackTrace();
        }

        this.game = game;
        this.levelType = LevelType.LEVEL_THREE;
        gunOne = new StationaryGun(this);
        gunTwo = new StationaryGun(this);
        gunThree = new StationaryGun(this);
        gunOne.setPosition(new Vec2(12.5f,9));
        gunTwo.setPosition(new Vec2(12.5f,1));
        gunThree.setPosition(new Vec2(12.5f,-7));
        heliDropper = new HeliDropper(this,false);
        heliDropper.dropCharacter();
        background = new ImageIcon("data/mariebyrdlandlevel3.png").getImage();
        Shape platformShape = new BoxShape(0.5f, 30);
        StaticBody platform = new StaticBody(this, platformShape);
        platform.setPosition(new Vec2(14.5f,20));
        platform.setFillColor(Color.MAGENTA);
        supporterHandler = new SupporterHandler(this);
    }

    /**
     * Determines whether or not the current level is complete
     * @return true if level is complete
     */
    @Override
    public boolean isComplete() {
        return ScoreKeeper.getScore() >= 1100;
    }

    /**
     * Gets the background image for this level
     * @return the background image for this level and the scaled instance of this image
     */
    @Override
    public Image getBackgroundImage() {
        return background.getScaledInstance(800,500,0);
    }

    /**
     * Stops the currently playing background music
     */
    @Override
    public void stopBackgroundMusic() {
        youCantSitWithUs.stop();
    }

    @Override
    public void setSunmiPosition(Vec2 position, String direction) {
        // There is no Sunmi in this level, therefore this method in this level is empty
    }

    // what happens during collisions between reporters and other dynamic bodies
    @Override
    public void collide(CollisionEvent collisionEvent) {
        splat.play();
        GenericCharacter reporter = (GenericCharacter) collisionEvent.getReportingBody();
        Body other = collisionEvent.getOtherBody();
        if (other instanceof DynamicBody) {
            DynamicBody db = (DynamicBody) other;
            if (db.isBullet()) {
                db.destroy();
                if (!reporter.isSupporter()) {
                    ScoreKeeper.incrementScore(100);
                }
            }
        }
        else{
            if (!reporter.isSupporter()){
                ScoreKeeper.decrementScore(100);
            }
            splat.play();
        }
        reporter.destroy();
        if (this.isComplete()){
            game.goToNextLevel();
        }
        else if (ScoreKeeper.getScore() < 0){
            game.goToSpecificLevel(3);
        }
        heliDropper.dropCharacter();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    /// what happens when player presses a key
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_1){
            gunOne.shootGun();
        }
        if (code == KeyEvent.VK_2){
            gunTwo.shootGun();
        }
        if (code == KeyEvent.VK_3){
            gunThree.shootGun();
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

    /**
     * Gets the supporter handler used in this level
     * @return the supporter handler used in this level
     */
    public SupporterHandler getSupporterHandler() {
        return supporterHandler;
    }
}