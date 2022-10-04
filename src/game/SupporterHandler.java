package game;

import city.cs.engine.Body;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DynamicBody;
import game.gui.SupporterState;
import org.jbox2d.common.Vec2;

import java.util.*;

/**
 * Used to generate the supporters in Level 3
 */
public class SupporterHandler implements CollisionListener {
    GameLevel levelWorld;
    private static final Random randGenerator = new Random();

    private static final int STAGE_START_X = -20;
    private static final int STAGE_X_INC = -20;
    private static final int STAGE_START_Y = 0;
    private int CurrentX;

    private static final Vec2 TOP_SUPPORTER;
    private static final Vec2 MIDDLE_SUPPORTER;
    private static final Vec2 BOTTOM_SUPPORTER;
    private static Map<Integer,Vec2> positionMapping;

    private Stack <CharacterInfo> supporterStack;
    private final ArrayList<CharacterInfo> characterInfoList;
    private Stack<CharacterInfo> randomStack;
    private final Map<String,CharacterInfo> nameToInfoMap;

    static{
        TOP_SUPPORTER = new Vec2(-13f,11f);
        MIDDLE_SUPPORTER = new Vec2(-13f,2f);
        BOTTOM_SUPPORTER = new Vec2(-13f,-6f);
        positionMapping = new HashMap<>(3);
        positionMapping.put(1,TOP_SUPPORTER);
        positionMapping.put(2,MIDDLE_SUPPORTER);
        positionMapping.put(3,BOTTOM_SUPPORTER);
    }

    /**
     * The constructor for the SupporterHandler class
     * @param gameLevel the level passed to this class
     */
    public SupporterHandler(GameLevel gameLevel){
        this.levelWorld = gameLevel;
        this.supporterStack = new Stack<>();
        this.characterInfoList = (new CharacterInfoUtil(CharacterType.SUPPORTERS_ONLY)).getCharacterInfoList();
        this.randomStack = new Stack<>();
        this.nameToInfoMap = buildNameToInfoMap();
        initializeSupporterStack();
        positionSupporter(1);
        positionSupporter(2);
        positionSupporter(3);
    }
    // get the range of indexes
    private int getRandomSpan (int low, int high){
        return randGenerator.nextInt((high + 1)-low)+low;
    }
    // map the name of character info objects to the rest of the information of those objects
    private Map<String,CharacterInfo> buildNameToInfoMap(){
        Map<String,CharacterInfo> tmpMap = new HashMap<>(this.characterInfoList.size());
        for (CharacterInfo ci: this.characterInfoList){
            String name = ci.getName();
            tmpMap.put(name,ci);
        }
        return tmpMap;
    }


    //Populates the stack of supporters
    private void initializeSupporterStack(){
        System.out.println("Initializing Supporter Stack");
        Set<Integer> usedIndicies = new HashSet<>(this.characterInfoList.size());
        while (usedIndicies.size() < this.characterInfoList.size()){
            int index = getRandomSpan(0,this.characterInfoList.size()-1);
            if (!usedIndicies.contains(index)){
                usedIndicies.add(index);
                this.randomStack.push(this.characterInfoList.get(index));

            }

        }
    }

    //Set the position of the supporters

    private void positionSupporter(int supporterPosition){
        if (this.supporterStack.empty()){
            initializeSupporterStack();
        }
        GenericCharacter genericCharacter = new GenericCharacter(this.levelWorld,this.randomStack.pop());
        genericCharacter.setPosition(positionMapping.get(supporterPosition));
        genericCharacter.setGravityScale(0f);
        genericCharacter.addCollisionListener(this);
    }

    /**
     * Position the supporters on the left of the sccreen in level 3
     * @param supporterStateList the list of supporter states
     */
    public void positionSpecificSupporters(List<SupporterState> supporterStateList){
        //first the existing supporters are removed
        List <DynamicBody> dynamicBodies = this.levelWorld.getDynamicBodies();
        for (DynamicBody db : dynamicBodies){
            if (db instanceof GenericCharacter){
                GenericCharacter gc = (GenericCharacter) db;
                if (gc.isSupporter()){
                    db.destroy();
                }
            }
        }
        // now all the supporters in the supporterStateList are positioned
        for (SupporterState ss : supporterStateList){
            CharacterInfo characterInfo = this.nameToInfoMap.get(ss.getName());
            GenericCharacter genericCharacter = new GenericCharacter(this.levelWorld,characterInfo);
            genericCharacter.setPosition(positionMapping.get(ss.getPosition()));
            genericCharacter.setGravityScale(0f);
            genericCharacter.addCollisionListener(this);

        }
    }
    //remove a supporter when one is hit
    private void removeSpecificSupporter (int supporterPosition){
        float vecY = -100f;
        switch (supporterPosition){
            case 1 -> {vecY = TOP_SUPPORTER.y;}
            case 2 -> {vecY = MIDDLE_SUPPORTER.y;}
            case 3 -> {vecY = BOTTOM_SUPPORTER.y;}
        }
        List <DynamicBody> dynamicBodies = levelWorld.getDynamicBodies();
        for (DynamicBody db : dynamicBodies){
            if (vecY == db.getPosition().y){
                //supporter found
                db.destroy();
                return;
            }
        }
    }
    // determine the position of supporters
    private int determineSupporterPosition(Vec2 vector){
        int vecY = Math.round(vector.y);
        int pos = 0;
        if (vecY == TOP_SUPPORTER.y){
            pos = 1;
        }
        if (vecY == MIDDLE_SUPPORTER.y){
            pos = 2;
        }
        if (vecY == BOTTOM_SUPPORTER.y){
            pos = 3;
        }
        return pos;
    }
    // what happens when a bullet collides with a supporter
    @Override
    public void collide(CollisionEvent collisionEvent) {
        GenericCharacter reporter = (GenericCharacter) collisionEvent.getReportingBody();
        Body other = collisionEvent.getOtherBody();
        if (other instanceof DynamicBody) {
            DynamicBody db = (DynamicBody) other;
            if (db.isBullet()) {
                db.destroy();
                ScoreKeeper.decrementScore(100);
                int pos = determineSupporterPosition(reporter.getPosition());
                reporter.destroy();
                positionSupporter(pos);
            }
        }
    }
}