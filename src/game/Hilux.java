package game;

import city.cs.engine.*;

/**
 * The toyota Hilux that is seen in Levels 1 and 2
 */
public class Hilux extends StaticBody {
    // the coordinate generated polygon shape of the Hilux
    private static final Shape hiluxShape = new PolygonShape(-2.78f,1.65f, -3.1f,-1.73f, 3.16f,-1.49f, 1.8f,1.83f, -2.74f,1.87f
    );
    // the image of the Hilux
    private static final BodyImage Hilux =
            new BodyImage("data/formerlyclarksonshilux.png", 4f);

    /**
     * The constructor of a Hilux object
     * @param level the level the Hilux will be passed to
     */
    public Hilux(GameLevel level) {
        super(level,hiluxShape);
        addImage(Hilux);
        setAlwaysOutline(false);
    }

}