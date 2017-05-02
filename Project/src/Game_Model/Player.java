package Game_Model;

/**
 * Created by Apple on 29/04/2017.
 */
public class Player {

    String playerName;
    int playerNo;
    int score;

    public Player(int playerNo){
        this.playerNo = playerNo;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
