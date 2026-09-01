package model;

import java.util.Scanner;

// Cette classe gère : Le jeu, la boucle du jeu, tours Joueur et Machine, Règles

public class AweleGame {

    // Plateau de jeu
    private Board board;

    // Joueur humain
    private Player user;

    // Joueur machine
    private Player machine;

    // Lecture clavier
    private Scanner scanner;

    // État du jeu : -1 = prêt, 0 = en cours, 1 = fini
    private int state;

    // true = joueur, false = machine
    private boolean userTurn = true;

    public void startGame() {
        board.loadBoard();
    }

    // -------------------- CONSTRUCTEUR --------------------
    public int playMachineFromApi(int id) {

        if (user == null || user.getId() != id) {
            return -2;
        }

        // Empêche la machine de jouer si la partie est terminée
        if (getState(id) != 0) {
            return -3;
        }

        // Liste des coups possibles
        int[] possibleMoves = new int[6];
        int count = 0;

        // Recherche des cases jouables
        for (int i = 6; i < 12; i++) {

            if (isLegalMove(i, false)) {

                possibleMoves[count] = i;
                count++;
            }
        }

        // Aucun coup possible
        if (count == 0) {
            return -1;
        }

        // Choix aléatoire
        int randomIndex =
                (int) (Math.random() * count);

        int choice =
                possibleMoves[randomIndex];

        // Jeu du coup
        int lastPit = semer(choice);

        int captured =
                capturerGraines(lastPit, false);

        machine.addScore(captured);

        userTurn = true;

        return choice - 5;
    }

    public AweleGame() {
        board = new Board();
        user = null;
        machine = new Player("Ordinateur");
        scanner = new Scanner(System.in);
        state = -1;
    }

    // -------------------- MAIN --------------------

    public static void main(String[] args) {

        AweleGame game = new AweleGame();
        Scanner clavier = new Scanner(System.in);

        System.out.print("Entre ton nom : ");
        String nom = clavier.nextLine();

        int id = game.connection(nom);

        // Si un joueur est déjà connecté
        if (id == -1) {
            System.out.println("Un joueur est déjà connecté.");
            return;
        }

        System.out.println("Bienvenue " + nom + " !");
        System.out.println("Ton identifiant est : " + id);

        game.awele();
    }

    // -------------------- CONNEXION / DECONNEXION --------------------

    // Connecte le joueur
    public int connection(String name) {

        if (user != null) {
            return -1;
        }

        board.loadBoard();

        user = new Player(name);

        state = 0;

        System.out.println(name + " est connecté.");

        return user.getId();
    }

    // Pour renvoyer le score

    public Player getUser() {
        return user;
    }

    public Player getMachine() {
        return machine;
    }

    // Déconnecte le joueur
    public int deconnection(int id) {
        if (user == null || user.getId() != id) {
            return -2;
        }

        String nom = user.getName();

        user = null;
        board = new Board();
        machine = new Player("Ordinateur");
        state = -1;
        userTurn = true;

        System.out.println("Déconnexion réussie.");
        System.out.println("À bientôt " + nom + " !");

        return id;
    }

    // Retourne le joueur
    public Player getPlayer(int id) {
        if (user == null || user.getId() != id) {
            return null;
        }
        return user;
    }

    // Retourne le plateau
    public Board getBoard(int id) {
        if (user == null || user.getId() != id) {
            return null;
        }
        return board;
    }

    // Retourne le joueur dont c'est le tour
    public Player getTurn(int id) {
        if (user == null || user.getId() != id) {
            return null;
        }

        if (userTurn) {
            return user;
        } else {
            return machine;
        }
    }

    // Retourne l'état du jeu
    public int getState(int id) {
        if (user == null || user.getId() != id) {
            return -2;
        }

        if (state == -  1) {
            return -1;
        }

        if (user.getScore() >= 25) {
            return 1;
        }

        if (machine.getScore() >= 25) {
            return 2;
        }

        if (isGameOver()) {
            return 3;
        }

        return 0;
    }

    // -------------------- JOUER UN COUP --------------------

    // Joue un coup depuis une case donnée
    public int sow(int pitIndex, int id) {
        if (user == null || user.getId() != id) {
            return -2;
        }
        // Empêche de jouer si la partie est déjà terminée
        if (getState(id) != 0) {
            return -3;
        }
        boolean isUser = (id == user.getId());

        if (!isLegalMove(pitIndex, isUser)) {
            return -1;
        }

        int lastPit = semer(pitIndex);
        int captured = capturerGraines(lastPit, isUser);

        if (isUser) {
            user.addScore(captured);
            userTurn = false;
        } else {
            machine.addScore(captured);
            userTurn = true;
        }

        return captured;
    }

    // -------------------- PARTIE --------------------

    public void awele() {

        // Sécurité : aucun joueur connecté
        if (user == null) {
            System.out.println("Aucun joueur n'est connecté.");
            return;
        }

        board.loadBoard();

        while (!isGameOver()) {
            board.printBoard(user.getScore(), machine.getScore());
            playUserTurn();

            if (isGameOver()) {
                break;
            }

            board.printBoard(user.getScore(), machine.getScore());
            playMachineTurn();
        }

        finishGame();
    }

    // -------------------- TOUR JOUEUR --------------------

    private void playUserTurn() {

        int choice;
        int index;

        while (true) {
            System.out.print(user.getName() + ", choisis une case de 1 à 6 : ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                deconnection(user.getId());
                System.exit(0);
            }

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Tape un nombre entre 1 et 6, ou q pour quitter.");
                continue;
            }

            if (choice < 1 || choice > 6) {
                System.out.println("Choix incorrect.");
                continue;
            }

            index = choice - 1;

            if (!isLegalMove(index, true)) {
                System.out.println("Coup impossible, choisis une autre case.");
                continue;
            }

            break;
        }

        int lastPit = semer(index);
        int captured = capturerGraines(lastPit, true);
        user.addScore(captured);
        userTurn = false;
    }

    // -------------------- TOUR MACHINE --------------------

    private void playMachineTurn() {

        int choice = -1;

        for (int i = 6; i < 12; i++) {
            if (isLegalMove(i, false)) {
                choice = i;
                break;
            }
        }

        if (choice == -1) {
            return;
        }

        System.out.println("L'ordinateur joue la case : " + (choice - 5));

        int lastPit = semer(choice);
        int captured = capturerGraines(lastPit, false);

        machine.addScore(captured);
        userTurn = true;
    }

    // -------------------- SEMAILLES --------------------

    // Distribue les graines d'une case
    private int semer(int startIndex) {
        int seeds = board.removeSeeds(startIndex);
        int index = startIndex;

        while (seeds > 0) {
            index = (index + 1) % 12;

            // On ne remet pas dans la case de départ
            if (index != startIndex) {
                board.addSeed(index);
                seeds--;
            }
        }

        return index;
    }

    // -------------------- CAPTURE --------------------

    // -------------------- CAPTURE --------------------

    // Capture les graines si possible
    private int capturerGraines(int lastPit, boolean isUser) {

        int total = 0;
        int index = lastPit;

        // ----------- SIMULATION ----------- //
        Board testBoard = board.copy();
        int tempIndex = index;

        while (isOpponentPit(tempIndex, isUser)) {
            int seeds = testBoard.getSeeds(tempIndex);

            if (seeds == 2 || seeds == 3) {
                testBoard.setSeeds(tempIndex, 0);
                tempIndex = (tempIndex - 1 + 12) % 12;
            } else {
                break;
            }
        }

        // ----------- VERIF FAMINE ----------- //
        if (isUser && board.countSeedsOnMachineSide() > 0
                && testBoard.countSeedsOnMachineSide() == 0) {
            return 0;
        }

        if (!isUser && board.countSeedsOnUserSide() > 0
                && testBoard.countSeedsOnUserSide() == 0) {
            return 0;
        }

        // ----------- CAPTURE REELLE ----------- //
        index = lastPit;

        while (isOpponentPit(index, isUser)) {
            int seeds = board.getSeeds(index);

            if (seeds == 2 || seeds == 3) {
                total += board.removeSeeds(index);
                index = (index - 1 + 12) % 12;
            } else {
                break;
            }
        }

        return total;
    }
    // -------------------- VERIFICATIONS --------------------

    // Vérifie si la case est chez l'adversaire
    private boolean isOpponentPit(int index, boolean isUser) {
        if (isUser) {
            return index >= 6;
        } else {
            return index < 6;
        }
    }

    // Vérifie si le coup est autorisé
    private boolean isLegalMove(int index, boolean isUser) {

        System.out.println("TEST COUP -> index = " + index + " | isUser = " + isUser);

        // Case vide
        if (board.getSeeds(index) == 0) {
            System.out.println("REFUS : case vide");
            return false;
        }

        // Côté joueur
        if (isUser && index > 5) {
            System.out.println("REFUS : le joueur essaie de jouer côté machine");
            return false;
        }

        // Côté machine
        if (!isUser && index < 6) {
            System.out.println("REFUS : la machine essaie de jouer côté joueur");
            return false;
        }

        boolean opponentEmpty;

        if (isUser) {
            opponentEmpty = board.countSeedsOnMachineSide() == 0;
        } else {
            opponentEmpty = board.countSeedsOnUserSide() == 0;
        }

        // Si le camp adverse n'est pas vide
        if (!opponentEmpty) {
            System.out.println("ACCEPTÉ : le camp adverse contient encore des graines");
            return true;
        }

        // Sinon il faut nourrir l'adversaire
        boolean feeds =
                feedsOpponent(index, isUser);

        if (feeds) {
            System.out.println("ACCEPTÉ : le coup nourrit l'adversaire");
        } else {
            System.out.println("REFUS : famine, le coup ne nourrit pas l'adversaire");
        }

        return feeds;
    }

    // Vérifie si le coup nourrit l'adversaire
    private boolean feedsOpponent(int index, boolean isUser) {
        int seeds = board.getSeeds(index);

        if (isUser) {
            return index + seeds >= 6;
        } else {
            return index + seeds >= 12;
        }
    }

    // Vérifie si la partie est finie
    private boolean isGameOver() {
        if (user.getScore() >= 25 || machine.getScore() >= 25) {
            return true;
        }

        if (userTurn) {
            return !canUserPlay();
        } else {
            return !canMachinePlay();
        }
    }

    // Vérifie si le joueur peut encore jouer
    private boolean canUserPlay() {
        for (int i = 0; i < 6; i++) {
            if (isLegalMove(i, true)) {
                return true;
            }
        }
        return false;
    }

    // Vérifie si la machine peut encore jouer
    private boolean canMachinePlay() {
        for (int i = 6; i < 12; i++) {
            if (isLegalMove(i, false)) {
                return true;
            }
        }
        return false;
    }

    // -------------------- FIN --------------------

    private void finishGame() {

        // Ramasse les graines restantes
        if (user.getScore() < 25 && machine.getScore() < 25) {
            user.addScore(board.collectUserSeeds());
            machine.addScore(board.collectMachineSeeds());
        }

        state = 1;

        board.printBoard(user.getScore(), machine.getScore());

        System.out.println("----- FIN DE LA PARTIE -----");
        System.out.println(user.getName() + " : " + user.getScore() + " points");
        System.out.println(machine.getName() + " : " + machine.getScore() + " points");

        if (user.getScore() > machine.getScore()) {
            System.out.println("Victoire, tu as gagné !");
        } else if (machine.getScore() > user.getScore()) {
            System.out.println("L'ordinateur gagne la partie !");
        } else {
            System.out.println("C'est une égalité !");
        }
    }
}
