package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.security.Key;

/**
 * A gun that is mounted to a wall and shoots horizontally. There are three of these in Level 3
 */
public class StationaryGun extends StaticBody {
    /**
     * the shape of the gun
     */
    private static final Shape gunShape = new PolygonShape(-2.91f, 1.93f, -3.07f, -2.21f, 3.05f, -2.19f, 3.05f, 1.99f);
    /**
     * the sound the gun makes when it shoots
     */
    private static SoundClip shoot;
    /**
     * the level passed to the gun
     */
    private GameLevel level;

    static {
        try {
            shoot = new SoundClip("data/lasercannon.mp3");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /**
     * The image of the gun
     */
    private static final BodyImage Gun =
            new BodyImage("data/gun.png", 4f);

    /**
     * The constructor of the gun
     * @param level the level each gun is passed to
     */
    public StationaryGun(GameLevel level) {
        super(level, gunShape);
        addImage(Gun);
        setAlwaysOutline(false);
        this.level = level;
    }

    /**
     * A public void method used to shoot the gun
     */
    public void shootGun() {
        CannonProjectile projectile = new CannonProjectile(level);
        shoot.play();
        projectile.setPosition(new Vec2(this.getPosition().x-1, this.getPosition().y-1));
        projectile.setLinearVelocity(new Vec2(-100, 0));
    }
}
