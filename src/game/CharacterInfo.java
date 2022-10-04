package game;

import city.cs.engine.BodyImage;
import city.cs.engine.Shape;

/**
 * An object that stores information about each character. It is used to construct generic characters
 */
public class CharacterInfo {
    private final Shape bodyShape;
    private final BodyImage bodyImage;
    private final String name;
    private final boolean isSupporter;


    /**
     * The CharacterInfo object constructor
     * @param shape the shape data
     * @param image the image data
     * @param name the name of the Generic character this object will be used to create
     * @param isSupporter whether the Generic character this object will be used to create is a supporter (true) or not (false)
     */
    public CharacterInfo(Shape shape, BodyImage image, String name, boolean isSupporter){
        this.bodyShape = shape;
        this.bodyImage = image;
        this.name = name;
        this.isSupporter = isSupporter;
    }

    /**
     * Gets the body shape data
     * @return the body shape data
     */
    public Shape getBodyShape() {
        return bodyShape;
    }

    /**
     * Gets the body image data
     * @return the body image data
     */
    public BodyImage getBodyImage() {
        return bodyImage;
    }

    /**
     * Gets the name of the generic character this object will be used to create
     * @return the name of the generic character this object will be used to create
     */
    public String getName() {
        return name;
    }

    /**
     * Determine whether the generic character this object will be used to create will be a supporter (true) or hater (false)
     * @return whether the generic character this object will be used to create will be a supporter (true) or hater (false)
     */
    public boolean isSupporter() {
        return isSupporter;
    }
}
