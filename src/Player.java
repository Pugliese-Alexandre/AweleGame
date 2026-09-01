import java.util.ArrayList;
import java.util.Random;

/*
 Classe représentant un joueur (humain ou machine)
*/
public class Player {

    private String name;
    private int score;
    private Random random;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.random = new Random();
    }

    public int getScore() {
        return score;
    }

    public void addScore(int seeds) {
        score += seeds;
    }

    // Choix aléatoire pour la machine
    public int chooseMove(Board board) {

        ArrayList<Integer> moves = new ArrayList<>();
        boolean userEmpty = board.countSeedsOnUserSide() == 0;

        for (int i = 6; i < 12; i++) {

            if (board.getSeeds(i) == 0) continue;

            if (userEmpty) {
                if (i + board.getSeeds(i) >= 12) {
                    moves.add(i);
                }
            } else {
                moves.add(i);
            }
        }

        return moves.get(random.nextInt(moves.size()));
    }
}