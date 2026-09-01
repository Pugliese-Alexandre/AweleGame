import java.util.ArrayList;
import java.util.Random;

/*
    Représente un joueur (humain ou machine), nom, score, choix aléatoire
*/
public class Player {

    // Nom du joueur
    private String name;

    // Score du joueur
    private int score;

    // Générateur aléatoire (utilisé par la machine)
    private Random random;

    // Identifiant du joueur
    private int id;

    // -------------------- CONSTRUCTEUR --------------------

    // Crée un joueur avec un nom
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.random = new Random();

        // Machine = id 1, sinon id aléatoire
        id = 1;
        if (!name.equals("Mr. Ordinateur")) {
            id = random.nextInt(1000) + 2;
        }
    }

    // -------------------- GETTERS --------------------

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    // -------------------- SCORE --------------------

    // Ajoute des points au score
    public void addScore(int seeds) {
        score += seeds;
    }

    // -------------------- IA --------------------

    // Choisit un coup pour la machine (case 6 à 11)
    public int chooseMove(Board board) {

        ArrayList<Integer> moves = new ArrayList<>();

        // Vérifie si le joueur n'a plus de graines
        boolean userEmpty = board.countSeedsOnUserSide() == 0;

        for (int i = 6; i < 12; i++) {

            // Ignore les cases vides
            if (board.getSeeds(i) == 0) continue;

            // Si le joueur est vide → il faut le nourrir
            if (userEmpty) {
                if (i + board.getSeeds(i) >= 12) {
                    moves.add(i);
                }
            } else {
                moves.add(i);
            }
        }

        // Aucun coup possible
        if (moves.isEmpty()) {
            return -1;
        }

        // Choix aléatoire
        return moves.get(random.nextInt(moves.size()));
    }
}