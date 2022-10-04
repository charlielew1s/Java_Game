package game;

/**
 * Generates the current score of the level.
 */
public class ScoreKeeper {
    private static int score = 0;

    /**
     * increases the player's score
     * @param amount the amount the player's score will be increased by
     * @return the updated, increased score
     */
    public static int incrementScore(int amount) {
        ScoreKeeper.score += amount;
        return score;
    }

    /**
     * decreases the player's score
     * @param amount the amount the player's score will be decreased by
     * @return the updated, decreased score
     */
    public static int decrementScore(int amount) {
        ScoreKeeper.score -= amount;
        return score;
    }

    /**
     * Set the score to a different value
     * @param score the updated score
     */
    public static void setScore(int score) {
        ScoreKeeper.score = score;
    }

    /**
     * Get the player's current score
     * @return the current score
     */
    public static int getScore(){
        return score;
    }

    /**
     * reset the score to 0
     */
    public static void resetScore(){
        score = 0;
    }
}