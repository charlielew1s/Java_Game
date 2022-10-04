package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * The controllable Sunmi Cannon
 */
public class SunmiCannon extends Walker {
    private static final Shape sunmiCannonShape = new PolygonShape(
            -1.68f,1.41f, -1.68f,-1.56f, 1.51f,-1.58f, 1.48f,1.46f);

    private static final BodyImage SunmiCannonLeftImage =
            new BodyImage("data/sunmicannonleftimage.png", 5f);

    private static final BodyImage SunmiCannonRightImage =
            new BodyImage("data/sunmicannonrightimage.png", 5f);

    // direction of the cannon
    private String direction;
    // sound of the cannon shooting
    private static SoundClip shoot;

    private static GameLevel level;

    static {
        try{
            shoot = new SoundClip("data/lasercannon.mp3");
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
    }

    /**
     * The constructor for a Sunmi cannon
     * @param level the level the cannon will appear in (always level one)
     */
    public SunmiCannon(GameLevel level) {
        super(level, sunmiCannonShape);
        addImage(SunmiCannonRightImage);
        direction = "right";
        this.level = level;
        setName("Sunmi Cannon");
    }

    /**
     * Gets Sunmi and her cannon moving. It also changes the position of the Sunmi and her net depending on this object's
     *    direction. It can also speed up Sunmi.
     * @param speed the speed that Sunmi's cannon will travel at
     */
    @Override
    public void startWalking(float speed) {
        super.startWalking(speed);
        if (speed < 0){
            this.removeAllImages();
            this.addImage(SunmiCannonLeftImage);
            direction = "left";
        }
        else{
            this.removeAllImages();
            this.addImage(SunmiCannonRightImage);
            direction = "right";
        }
    }

    /**
     * Shoots the cannon
     */
    public void shootCannon(){
        CannonProjectile projectile = new CannonProjectile(level);
        shoot.play();
        if (direction.equals("right")) {
            projectile.setPosition(new Vec2(this.getPosition().x+1,this.getPosition().y+1));
            projectile.setLinearVelocity(new Vec2(25,25));
        }
        else{
            projectile.setPosition(new Vec2(this.getPosition().x-1,this.getPosition().y-1));
            projectile.setLinearVelocity(new Vec2(-25,25));
        }
    }
    /**
     * Gets direction of cannon
     * @return direction
     */
    public String getDirection(){
        return direction;
    }

    public void updatePosition(Vec2 newPos, String dir){
        setPosition(newPos);
        this.direction = dir;
        if (this.direction.equals("left")){
            this.removeAllImages();
            this.addImage(SunmiCannonLeftImage);
        }
        else{
            this.removeAllImages();
            this.addImage(SunmiCannonRightImage);
        }

    }
}


