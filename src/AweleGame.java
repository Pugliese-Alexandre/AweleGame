import java.util.Scanner;

public class AweleGame {

    private Board board;     // plateau de jeu
    private Player user;     // joueur humain
    private Player machine;  // machine (IA)
    private Scanner scanner; // lecture clavier

    // Constructeur
    public AweleGame() {
        board = new Board();
        user = new Player("USER");
        machine = new Player("MACHINE");
        scanner = new Scanner(System.in);
    }

    // Point d'entrée du programme
    public static void main(String[] args) {
        AweleGame game = new AweleGame();
        game.awele();
    }

    // Méthode principale du jeu
    public void awele() {
        board.loadBoard(); // chargement du pit.txt

        // boucle jusqu'à la fin du jeu
        while (!isGameOver()) {

            board.printBoard(user.getScore(), machine.getScore());

            // tour du joueur
            System.out.print("USER: choisis une caisse de 1 à 6 : ");
            playUserTurn();

            if (isGameOver()) break;

            // tour machine
            board.printBoard(user.getScore(), machine.getScore());
            playMachineTurn();
        }

        // fin de partie
        finishGame();
    }

    // Tour du joueur
    private void playUserTurn() {
        int choice;
        int index;

        while (true) {
            choice = scanner.nextInt();

            if (choice < 1 || choice > 6) {
                System.out.println("Choix invalide.");
                continue;
            }

            index = choice - 1;

            if (!isLegalMove(index, true)) {
                System.out.println("Coup interdit.");
                continue;
            }

            break;
        }

        int lastPit = sow(index);
        int captured = captureSeeds(lastPit, true);
        user.addScore(captured);
    }

    // Tour de la machine (aléatoire)
    private void playMachineTurn() {
        int choice = machine.chooseMove(board);
        System.out.println("MACHINE joue : " + (choice - 5));

        int lastPit = sow(choice);
        int captured = captureSeeds(lastPit, false);
        machine.addScore(captured);
    }

    // Semailles (distribution des graines)
    private int sow(int startIndex) {
        int seeds = board.removeSeeds(startIndex);
        int index = startIndex;

        while (seeds > 0) {
            index = (index + 1) % 12;

            if (index != startIndex) {
                board.addSeed(index);
                seeds--;
            }
        }

        return index;
    }

    // Gestion des captures
    private int captureSeeds(int lastPit, boolean isUser) {
        int total = 0;
        int index = lastPit;

        // copie pour tester la famine
        Board testBoard = board.copy();

        while (isOpponentPit(index, isUser)) {
            int seeds = testBoard.getSeeds(index);

            if (seeds == 2 || seeds == 3) {
                testBoard.setSeeds(index, 0);
                index = (index - 1 + 12) % 12;
            } else break;
        }

        // règle famine : interdit de vider l’adversaire
        if (isUser && testBoard.countSeedsOnMachineSide() == 0) return 0;
        if (!isUser && testBoard.countSeedsOnUserSide() == 0) return 0;

        // capture réelle
        index = lastPit;
        while (isOpponentPit(index, isUser)) {
            int seeds = board.getSeeds(index);

            if (seeds == 2 || seeds == 3) {
                total += board.removeSeeds(index);
                index = (index - 1 + 12) % 12;
            } else break;
        }

        return total;
    }

    // Vérifie si c'est un trou adverse
    private boolean isOpponentPit(int index, boolean isUser) {
        if (isUser) return index >= 6;
        else return index < 6;
    }

    // Vérifie si le coup est autorisé
    private boolean isLegalMove(int index, boolean isUser) {

        if (board.getSeeds(index) == 0) return false;

        if (isUser && index > 5) return false;
        if (!isUser && index < 6) return false;

        boolean opponentEmpty;

        if (isUser) opponentEmpty = board.countSeedsOnMachineSide() == 0;
        else opponentEmpty = board.countSeedsOnUserSide() == 0;

        if (!opponentEmpty) return true;

        return feedsOpponent(index, isUser);
    }

    // Vérifie si le coup nourrit l'adversaire
    private boolean feedsOpponent(int index, boolean isUser) {
        int seeds = board.getSeeds(index);

        if (isUser) return index + seeds >= 6;
        else return index + seeds >= 12;
    }

    // Vérifie si la partie est terminée
    private boolean isGameOver() {
        if (user.getScore() >= 25 || machine.getScore() >= 25) return true;
        if (!canUserPlay() || !canMachinePlay()) return true;
        return false;
    }

    private boolean canUserPlay() {
        for (int i = 0; i < 6; i++) {
            if (isLegalMove(i, true)) return true;
        }
        return false;
    }

    private boolean canMachinePlay() {
        for (int i = 6; i < 12; i++) {
            if (isLegalMove(i, false)) return true;
        }
        return false;
    }

    // Fin du jeu
    private void finishGame() {

        if (user.getScore() < 25 && machine.getScore() < 25) {
            user.addScore(board.collectUserSeeds());
            machine.addScore(board.collectMachineSeeds());
        }

        board.printBoard(user.getScore(), machine.getScore());

        System.out.println("Score final USER : " + user.getScore());
        System.out.println("Score final MACHINE : " + machine.getScore());

        if (user.getScore() > machine.getScore()) System.out.println("USER gagne !");
        else if (machine.getScore() > user.getScore()) System.out.println("MACHINE gagne !");
        else System.out.println("Egalité !");
    }
}