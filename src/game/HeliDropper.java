package game;

import city.cs.engine.DynamicBody;
import org.jbox2d.common.Vec2;

import java.util.*;

/**
 * Drops the falling characters from a helicopter outside of the field of view, hence the name HeliDropper.
 * It drops either haters and supporters or only haters.
 */
public class HeliDropper {
    private static final int INIT_Y = 15;
    private final ArrayList<CharacterInfo> characterInfoList;
    private int LOWEST_X;
    private int HIGHEST_X;

    private static final Random randGenerator = new Random();
    private Stack<CharacterInfo> randomStack;
    private HashMap<String, CharacterInfo> nameToInfoMap;

    private GameLevel baseLevel;
    private boolean includeSupporters;

    /**
     * The constructor for the HeliDropper class.
     * @param gameLevel the level HeliDropper is being used in
     * @param includeSupporters a boolean condition check which determines whether or not this specific HeliDropper will drop supporters
     *                          as well as haters.
     */
    public HeliDropper(GameLevel gameLevel, boolean includeSupporters) {
        this.baseLevel = gameLevel;
        this.includeSupporters = includeSupporters;
        if (this.includeSupporters) {
            LOWEST_X = -11;
            HIGHEST_X = 11;
        } else {
            LOWEST_X = 0;
            HIGHEST_X = 0;
        }
        if (includeSupporters) {
            //create a character info list containing both haters and supporters
            this.characterInfoList = (new CharacterInfoUtil(CharacterType.BOTH)).getCharacterInfoList();
        } else {
            //create a character info list containing only haters
            this.characterInfoList = (new CharacterInfoUtil(CharacterType.HATERS_ONLY)).getCharacterInfoList();
        }
        this.randomStack = new Stack<>();

        initializeStack();
        nameToInfoMap = buildNameToInfoMap();
    }

    /**
     * Maps the name of each character will all the other information about each character
     * @return the nameToInfoMap
     */
    private HashMap<String, CharacterInfo> buildNameToInfoMap() {
        HashMap<String,CharacterInfo> tempMap = new HashMap<>();
        for (CharacterInfo characterInfo : this.characterInfoList) {
            tempMap.put(characterInfo.getName(), characterInfo);
        }
        return tempMap;
    }
    // get range of indexes
    private int getRandomSpan(int low, int high) {
        return randGenerator.nextInt((high + 1) - low) + low;
    }

    /**
     * A stack of objects containing information about characters will be populated
     */
    private void initializeStack() {
        System.out.println("Initializing Stack");
        Set<Integer> usedIndicies = new HashSet<>(this.characterInfoList.size());
        while (usedIndicies.size() < this.characterInfoList.size()) {
            int index = getRandomSpan(0, this.characterInfoList.size() - 1);
            if (!usedIndicies.contains(index)) {
                usedIndicies.add(index);
                this.randomStack.push(this.characterInfoList.get(index));
            }
        }
    }

    /**
     * Drops each character one at a time, removing each character information object from the stack and creating Generic Characters
     * from those information objects.
     * If the stack is empty, it will be reinitialized before
     * further dropping.
     */
    public void dropCharacter() {
        if (this.randomStack.empty()) {
            initializeStack();
        }
        GenericCharacter genericCharacter = new GenericCharacter(this.baseLevel, this.randomStack.pop());
        int initX = getRandomSpan(LOWEST_X, HIGHEST_X);
        genericCharacter.setPosition(new Vec2(initX, INIT_Y));
        int gravityScale = getRandomSpan(1, 2);
        genericCharacter.setGravityScale((float) gravityScale);
        genericCharacter.addCollisionListener(this.baseLevel);
    }
    /**
     * Drops a specific character into the level. Note that the character must be a falling character generated by the HeliDropper class.
     * @param name the name of the character
     * @param position the position of the character
     * @param gravityScale the force of gravity that is applied to the character
     */
    public void dropSpecificCharacter(String name, Vec2 position, float gravityScale) {
        //remove any other falling characters that are in the world right now
        for (DynamicBody db : baseLevel.getDynamicBodies()){
            if (db instanceof GenericCharacter && db.getGravityScale() > 0){
                db.destroy();
                break;
            }
        }
        CharacterInfo characterInfo = nameToInfoMap.get(name);
        GenericCharacter genericCharacter = new GenericCharacter(this.baseLevel,characterInfo);
        genericCharacter.setGravityScale(gravityScale);
        genericCharacter.setPosition(position);
        genericCharacter.addCollisionListener(this.baseLevel);
    }
}