package game;

import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import org.jbox2d.common.Vec2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a list of all the objects containing the character information
 */
public class CharacterInfoUtil {
    private static final String characterInfoFile = "data/CharacterInfo.tsv";
    private final ArrayList<CharacterInfo> characterInfoList;
    private CharacterType characterType;

    /**
     * The constructer for this object
     * @param ct this variable determines whether or not the list should include characters or not
     */
    public CharacterInfoUtil(CharacterType ct) {
        this.characterType = ct;
        this.characterInfoList = getAllCharacterInfo();
    }

    /**
     * Gets a list containing information about all the characters in this level
     * @return the character info list
     */
    public ArrayList<CharacterInfo> getCharacterInfoList() {
        return characterInfoList;
    }

    /**
     * Gets all the information about all characters. Used to generate a character info list
     * @return the information of all character info objects
     */
    private ArrayList<CharacterInfo> getAllCharacterInfo() {
        ArrayList<CharacterInfo> infoList = new ArrayList<>();
        BufferedReader fileReader;
        int lineCount = 0;
        try {
            // read from the character information file
            fileReader = new BufferedReader(new FileReader(characterInfoFile));
            String row = fileReader.readLine();
            while (row != null) {
                lineCount++;
                if (lineCount == 1) {
                    row = fileReader.readLine();
                    continue;
                }
                // split each row in the tab separated file by tabs
                String[] parts = row.split("\\t");
                String name = parts[0];
                String shapeString = parts[1];
                String bodyImagePath = parts[2];
                boolean isSupporter = Boolean.parseBoolean(parts[3]);
                boolean includeThisChar = false;
                if (this.characterType == CharacterType.BOTH) {
                    includeThisChar = true;
                } else if (this.characterType == CharacterType.SUPPORTERS_ONLY) {
                    if (isSupporter) {
                        includeThisChar = true;
                    }
                } else if (this.characterType == CharacterType.HATERS_ONLY) {
                    if (!isSupporter) {
                        includeThisChar = true;
                    }
                }
                if (includeThisChar) {
                    // converting a string of coordinates, used to determine the position of the Generic Character
                    List<Vec2> coordList = convertStrToList(shapeString);
                    infoList.add(new CharacterInfo(
                            new PolygonShape(coordList),
                            new BodyImage(bodyImagePath, 4f),
                            name,
                            isSupporter));
                }
                row = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infoList;
    }

    /**
     * Coverts a string of coordinates to a list to be used to determine the Polygon shape of CharacterInfo objects, to be used to create
     * generic characters
     * @param coords the string of coordinates
     * @return a list of coordinates
     */
    private List<Vec2>convertStrToList(String coords) {
        List<Vec2> coordList = new ArrayList<>();
        String[] coordParts = coords.split(",");
        for (int i = 0; i < coordParts.length; i += 2) {
            float x = Float.parseFloat(coordParts[i]);
            float y = Float.parseFloat(coordParts[i + 1]);
            coordList.add(new Vec2(x, y));
        }
        return coordList;
    }
}
