package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * A net that carried by Sunmi that the player controls is used to collect supporters to gain points in Level 2
 */
public class SunmiNet extends Walker {
    public static final PolygonShape sunmiNetShape = new PolygonShape(-0.77f, 1.79f, -0.8f, -2.55f, 1.01f, -2.58f, 1.04f, 1.78f);
    private String direction;
    private BodyImage sunmiNetRight = new BodyImage("data/sunmiAndNetRight.png",5);
    private BodyImage sunmiNetLeft = new BodyImage("data/sunmiAndNetLeft.png",5);

    /**
     * Constructor to create the sunmi net
     * @param level
     */
    public SunmiNet(GameLevel level) {
        super(level, sunmiNetShape);
        BodyImage sunmiNetImage = new BodyImage("data/sunmiAndNet.png", 5);
        addImage(sunmiNetImage);
        setName("Sunmi Net");
    }

    @Override
    /**
     * Gets Sunmi and her net moving. It also changes the position of the Sunmi and her net depending on this object's
     * direction.
     */
    public void startWalking(float speed) {
        super.startWalking(speed);
        if (speed < 0) {
            this.removeAllImages();
            this.addImage(sunmiNetLeft);
            direction = "left";
        } else {
            this.removeAllImages();
            this.addImage(sunmiNetRight);
            direction = "right";
        }
    }

    /**
     * Gets direction of net
     * @return direction
     */
    public String getDirection(){
        return direction;
    }

    /**
     * Updates the position of Sunmi's net
     * @param newPos the new position
     * @param dir the new direction
     */
    public void updatePosition (Vec2 newPos, String dir){
        setPosition(newPos);
        this.direction = dir;
        if (this.direction.equals("left")){
            this.removeAllImages();
            this.addImage(sunmiNetLeft);
        }
        else{
            this.removeAllImages();
            this.addImage(sunmiNetRight);
        }
    }
}
