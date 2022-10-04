package game;

import city.cs.engine.*;

/**
 * The projectile that will shoot from Sunmi's cannon in Level 1 and the stationary guns in Level 3.
 */
public class CannonProjectile extends DynamicBody {
    //The shape of the projectile
    private static final Shape projectileShape = new PolygonShape(0.27f,-1.99f, -1.0f,-1.98f, -1.04f,1.95f, 1.04f,1.95f, 1.03f,-1.99f);
    //The image of the projectile
    private static final BodyImage projectileImage =
            new BodyImage("data/bojoshakespearebook.png", 2f);

    /**
     * The constructor for the projectile class
     * @param level the level passed to the class
     */
    public CannonProjectile(GameLevel level) {
        super(level,projectileShape);
        addImage(projectileImage);
        setAlwaysOutline(false);
        setName("projectile");
        setBullet(true);
    }
}
