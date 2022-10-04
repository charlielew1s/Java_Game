package game;

import city.cs.engine.DynamicBody;

/**
 * A supporter or hater object. The player can identify them as being uncontrollable and also having
 * a green outline if they are a supporter and a red outline if they are a hater
 */
public class GenericCharacter extends DynamicBody {
    private final boolean isSupporter;
    public GenericCharacter(GameLevel gameLevel,CharacterInfo characterInfo){
        super(gameLevel, characterInfo.getBodyShape());
        addImage(characterInfo.getBodyImage());
        setName(characterInfo.getName());
        this.isSupporter = characterInfo.isSupporter();
    }

    /**
     * A boolean condition check to determine if a particular generic character is a supporter or not
     * @return true if this generic character is a supporter, false if it is not.
     */
    public boolean isSupporter() {
        return isSupporter;
    }
}
