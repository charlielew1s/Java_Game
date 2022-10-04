package game;

import city.cs.engine.*;

/**
 * The rock that is seen in the game
 */
public class AntarcticRock extends StaticBody {
    // the coordinate generated polygon shape of the rock
    private static final Shape antarcticRockShape = new PolygonShape(1.1f,0.44f, -1.56f,-0.46f, -0.9f,-1.72f, 2.46f,-1.73f, 1.65f,0.56f);
    // rock image
    private static final BodyImage antarcticRock = new BodyImage("data/antarcticrock.png",5f);

    /**
     * The constructor for the antarctic rock
     * @param level the level that the rock will be placed in
     */
    public AntarcticRock(GameLevel level) {
        super(level,antarcticRockShape);
        addImage(antarcticRock);
        setAlwaysOutline(false);
    }
}
