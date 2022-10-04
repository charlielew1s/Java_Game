package game.gui;

import city.cs.engine.DynamicBody;
import game.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the state of the game
 */
public class GameStateHandler {
    private Game game;
    private static final String DEFAULT_DIRECTORY = "data";
    private static final String NEWLINE = "\n";
    private static final String DELIMITER = "\t";
    private static final String CURRENT_LEVEL = "current level:";
    private static final String CURRENT_SCORE = "current score:";
    private static final String FALLING_CHARACTER = "current character";
    private static final String FALLING_CHARACTER_POS = "current character position";
    private static final String FALLING_CHARACTER_GRAVITY_SCALE = "current character gravity scale";
    private static final String SUPPORTER_PREFIX = "supporter_";
    private static final String SUNMI_ACTIVITY = "current Sunmi state";
    private static final String SUNMI_POS = "current Sunmi position";
    private static final String SUNMI_DIRECTION = "current Sunmi direction";



    private FileNameExtensionFilter fileNameExtensionFilter;
    private JFileChooser jFileChooser;

    /**
     * The constructor for the GameStateHandler object
     * @param game the game passed to the constructor
     */
    public GameStateHandler(Game game) {
        this.game = game;
        jFileChooser = new JFileChooser(DEFAULT_DIRECTORY);
        fileNameExtensionFilter = new FileNameExtensionFilter("Game State File (*.state)", "state");
        jFileChooser.setFileFilter(fileNameExtensionFilter);
    }

    /**
     * Saves the state of the game
     */
    public void saveState() {
        int choice = jFileChooser.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            String absolutePath = jFileChooser.getSelectedFile().getAbsolutePath();
            writeDataToStateFile(absolutePath);
        }

    }
    //writes data from the game state to the state file
    private void writeDataToStateFile(String absoluteFilePath) {
        if (!absoluteFilePath.endsWith(".state")) {
            absoluteFilePath += ".state";
        }
        GameStateData gameStateData = determineGameStateData();

        StringBuffer sb = new StringBuffer();
        int currentLevel = gameStateData.getLevel();
        int currentScore = gameStateData.getScore();
        sb.append(CURRENT_LEVEL).append(DELIMITER).append(currentLevel).append(NEWLINE);
        sb.append(CURRENT_SCORE).append(DELIMITER).append(currentScore).append(NEWLINE);

        if (gameStateData.hasFallingChar()){
            String nameOfFallingChar = gameStateData.getFallingCharName();
            sb.append(FALLING_CHARACTER).append(DELIMITER).append(nameOfFallingChar).append(NEWLINE);

            Vec2 fallingPosition = gameStateData.getFallingCharPos();
            sb.append(FALLING_CHARACTER_POS).append(DELIMITER).append(fallingPosition).append(NEWLINE);

            float fallingGravityScale = gameStateData.getFallingCharGravityScale();
            sb.append(FALLING_CHARACTER_GRAVITY_SCALE).append(DELIMITER).append(fallingGravityScale).append(NEWLINE);
        }
        // if a playable Sunmi object is contained in the game state data object
        if (gameStateData.hasSunmi()){
            String activityName = gameStateData.getActivitySunmi();
            sb.append(SUNMI_ACTIVITY).append(DELIMITER).append(activityName).append(NEWLINE);
            String direction = gameStateData.getSunmiDirection();
            sb.append(SUNMI_DIRECTION).append(DELIMITER).append(direction).append(NEWLINE);
            Vec2 positionSunmi = gameStateData.getPosSunmi();
            sb.append(SUNMI_POS).append(DELIMITER).append(positionSunmi).append(NEWLINE);

        }
        if (currentLevel == 3) {
            List<SupporterState> level3SupporterState = gameStateData.getLevel3SupporterList();
            for (int i = 0; i < level3SupporterState.size(); i++){
                SupporterState supporterState = level3SupporterState.get(i);
                String name = supporterState.getName();
                int position = supporterState.getPosition();
                sb.append(SUPPORTER_PREFIX).append(i).
                        append(DELIMITER).append(name).
                        append(DELIMITER).append(position).
                        append(NEWLINE);
            }
        }


        FileWriter fOut;
        try {
            fOut = new FileWriter(absoluteFilePath);
            fOut.write(sb.toString());
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //creates a game state data object from the values in the current game
    private GameStateData determineGameStateData() {
        GameStateData gameStateData = new GameStateData();
        int level = game.getCurrentLevel().getLevelType().getValue();
        gameStateData.setLevel(level);
        int score = ScoreKeeper.getScore();
        gameStateData.setScore(score);
        //Get falling body if one exists
        DynamicBody fallingCharacter = findFallingCharacter();
        DynamicBody Sunmi = findSunmi();
        String nameOfFallingCharacter = null;
        Vec2 positionOfFallingCharacter = null;
        float gravityScaleOfFallingCharacter = -1;
        String activitySunmi = null;
        Vec2 posSunmi = null;
        if (fallingCharacter != null) {
            nameOfFallingCharacter = fallingCharacter.getName();
            positionOfFallingCharacter = fallingCharacter.getPosition();
            gravityScaleOfFallingCharacter = fallingCharacter.getGravityScale();
            gameStateData.setHasFallingChar(true);

        }
        // if there is a Sunmi object located in the game state data object
        if (Sunmi != null) {
            activitySunmi = Sunmi.getName();
            gameStateData.setActivitySunmi(activitySunmi);
            posSunmi = Sunmi.getPosition();
            gameStateData.setPosSunmi(posSunmi);
            gameStateData.setHasSunmi(true);
            String direction = null;
            // if the Sunmi object (activity of Sunmi) is a cannon
            if (activitySunmi.equals("Sunmi Cannon")){
                direction = ((SunmiCannon) Sunmi).getDirection();
            }
            // if the Sunmi object (activity of Sunmi) is a net
            else if (activitySunmi.equals("Sunmi Net")){
                direction = ((SunmiNet) Sunmi).getDirection();
            }
            gameStateData.setSunmiDirection(direction);
        }
        gameStateData.setFallingCharName(nameOfFallingCharacter);
        gameStateData.setFallingCharPos(positionOfFallingCharacter);
        gameStateData.setFallingCharGravityScale(gravityScaleOfFallingCharacter);

        gameStateData.setActivitySunmi(activitySunmi);
        gameStateData.setPosSunmi(posSunmi);

        if (level == 3) {
            List<SupporterState> supporterStateList = new ArrayList<>(3);
            List <GenericCharacter> leftSideSupporters = getLevel3Supporters();
            for (GenericCharacter gc : leftSideSupporters){
                String name = gc.getName();
                Vec2 sPos = gc.getPosition();
                int supporterPos = findSupporterPos(sPos);
                SupporterState aState = new SupporterState(name, supporterPos);
                supporterStateList.add(aState);
            }
            gameStateData.setLevel3SupporterList(supporterStateList);

        }
        return gameStateData;
    }
    //find the position of a suppporter
    private int findSupporterPos(Vec2 sVec){
        int sPos = -1;
        float y = sVec.y;
        if (y < 0.0){
            sPos = 3;
        }
        else if ((y > 0) && (y < 10)){
            sPos = 2;
        }
        else if (y > 10){
            sPos = 1;
        }
        return sPos;
    }
    // find a falling generic character
    private DynamicBody findFallingCharacter() {
        DynamicBody fallingCharacter = null;
        List<DynamicBody> dynamicBodyList = game.getCurrentLevel().getDynamicBodies();
        for (DynamicBody dynamicBody : dynamicBodyList){
            if (dynamicBody instanceof GenericCharacter && dynamicBody.getGravityScale() > 0){
                fallingCharacter = dynamicBody;
                break;
            }
        }
        return fallingCharacter;
    }
    //find the player controlled Sunmi object in levels 1 and 2
    private DynamicBody findSunmi() {
        DynamicBody Sunmi = null;
        List<DynamicBody> dynamicBodyList = game.getCurrentLevel().getDynamicBodies();
        for (DynamicBody dynamicBody : dynamicBodyList){
            if (dynamicBody.getName().startsWith("Sunmi ")){
                Sunmi = dynamicBody;
                break;
            }
        }
        return Sunmi;
    }
    // get the supporters in level 3
    private List <GenericCharacter> getLevel3Supporters() {
        List<GenericCharacter> supporterList = new ArrayList<>();
        for (DynamicBody db : game.getCurrentLevel().getDynamicBodies()) {
            if (db instanceof GenericCharacter) {
                GenericCharacter gc = (GenericCharacter) db;
                if (gc.isSupporter()) {
                    supporterList.add(gc);
                }
            }
        }
        return supporterList;
    }
    //load a state file into the game
    public void loadState() {
        int fileChoice = jFileChooser.showOpenDialog(null);
        if (fileChoice == JFileChooser.APPROVE_OPTION){
            String absolutePath = jFileChooser.getSelectedFile().getAbsolutePath();
            readDataFromStateFile(absolutePath);
        }
    }

    //read data from the state file in order to load the information from the state file into the game
    private void readDataFromStateFile(String absolutePath){
        GameStateData gameStateData = new GameStateData();
        List<SupporterState> supporterStateList = null;
        BufferedReader fileIn;
        try {
            fileIn = new BufferedReader(new FileReader(absolutePath));
            String line = fileIn.readLine();
            while (line != null){
                String[] parts = line.split(DELIMITER);
                String key = parts[0];
                if (CURRENT_LEVEL.equals(key)){
                    int currentLevel = Integer.parseInt(parts [1]);
                    gameStateData.setLevel(currentLevel);
                }
                else if (CURRENT_SCORE.equals(key)){
                    int currentScore = Integer.parseInt(parts [1]);
                    gameStateData.setScore(currentScore);
                }
                else if (FALLING_CHARACTER.equals(key)){
                    String fallingName = parts[1];
                    gameStateData.setFallingCharName(fallingName);
                    gameStateData.setHasFallingChar(true);
                }
                else if (FALLING_CHARACTER_POS.equals(key)){
                    String rawStr = parts[1];
                    rawStr = rawStr.replace('(',' ');
                    rawStr = rawStr.replace(')',' ');
                    String rawParts [] = rawStr.split(",");
                    float xVal = Float.parseFloat(rawParts[0]);
                    float yVal = Float.parseFloat(rawParts[1]);
                    Vec2 posVec = new Vec2(xVal,yVal);
                    gameStateData.setFallingCharPos(posVec);
                }
                else if (FALLING_CHARACTER_GRAVITY_SCALE.equals(key)){
                    gameStateData.setFallingCharGravityScale(Float.parseFloat(parts[1]));
                }

                else if (key != null && key.startsWith(SUPPORTER_PREFIX)){
                    if (supporterStateList == null){
                        supporterStateList = new ArrayList<>();
                    }
                    String name = parts [1];
                    int pos = Integer.parseInt(parts[2]);
                    SupporterState ss = new SupporterState(name,pos);
                    supporterStateList.add(ss);
                    gameStateData.setLevel3SupporterList(supporterStateList);
                }
                else if (SUNMI_ACTIVITY.equals(key)){
                    String activity = parts[1];
                    gameStateData.setActivitySunmi(activity);
                    gameStateData.setHasSunmi(true);
                }
                else if (SUNMI_DIRECTION.equals(key)){
                    String direction = parts[1];
                    gameStateData.setSunmiDirection(direction);
                }
                else if (SUNMI_POS.equals(key)){
                    String rawStr = parts[1];
                    rawStr = rawStr.replace('(',' ');
                    rawStr = rawStr.replace(')',' ');
                    String rawParts [] = rawStr.split(",");
                    float xVal = Float.parseFloat(rawParts[0]);
                    float yVal = Float.parseFloat(rawParts[1]);
                    Vec2 posVec = new Vec2(xVal,yVal);
                    gameStateData.setPosSunmi(posVec);
                }
                line = fileIn.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        applyNewState(gameStateData);
    }
    // makes final preparations to run the game after loading
    private void applyNewState (GameStateData gameStateData){
        int levelToSwitchTo = gameStateData.getLevel();
        game.goToSpecificLevel(levelToSwitchTo);
        game.getCurrentLevel().stop();
        ScoreKeeper.setScore(gameStateData.getScore());
        if (gameStateData.hasFallingChar()){
            String fallingName = gameStateData.getFallingCharName();
            Vec2 fallingPos = gameStateData.getFallingCharPos();
            float fallingGravScale = gameStateData.getFallingCharGravityScale();
            game.getCurrentLevel().dropSpecificCharacter(fallingName,fallingPos,fallingGravScale);
        }
        if (levelToSwitchTo == 3){
            LevelThree theNewLevel = (LevelThree) game.getCurrentLevel();
            SupporterHandler supporterHandler = theNewLevel.getSupporterHandler();
            List <SupporterState> level3SupporterList = gameStateData.getLevel3SupporterList();
            supporterHandler.positionSpecificSupporters(level3SupporterList);
        }
        if (gameStateData.hasSunmi()){
            Vec2 newPos = gameStateData.getPosSunmi();
            String direction = gameStateData.getSunmiDirection();
            game.getCurrentLevel().setSunmiPosition(newPos,direction);
        }
        game.getCurrentLevel().start();
    }
}

