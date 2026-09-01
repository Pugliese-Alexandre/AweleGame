import java.io.File;
import java.util.Scanner;

/*
 Classe représentant le plateau (12 trous)
*/
public class Board {

    private Pit[] pits;

    public Board() {
        pits = new Pit[12];

        for (int i = 0; i < 12; i++) {
            pits[i] = new Pit(0);
        }
    }

    // Lecture du fichier pit.txt
    public void loadBoard() {
        try {
            Scanner scanner = new Scanner(new File("pit.txt"));

            for (int i = 0; i < 12; i++) {
                if (scanner.hasNextInt()) {
                    pits[i].setSeeds(scanner.nextInt());
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Erreur fichier pit.txt");
        }
    }

    public int getSeeds(int index) {
        return pits[index].getSeeds();
    }

    public void setSeeds(int index, int value) {
        pits[index].setSeeds(value);
    }

    public void addSeed(int index) {
        pits[index].addSeed();
    }

    public int removeSeeds(int index) {
        return pits[index].removeSeeds();
    }

    public int countSeedsOnUserSide() {
        int total = 0;
        for (int i = 0; i < 6; i++) total += pits[i].getSeeds();
        return total;
    }

    public int countSeedsOnMachineSide() {
        int total = 0;
        for (int i = 6; i < 12; i++) total += pits[i].getSeeds();
        return total;
    }

    public int collectUserSeeds() {
        int total = 0;
        for (int i = 0; i < 6; i++) total += removeSeeds(i);
        return total;
    }

    public int collectMachineSeeds() {
        int total = 0;
        for (int i = 6; i < 12; i++) total += removeSeeds(i);
        return total;
    }

    public Board copy() {
        Board b = new Board();
        for (int i = 0; i < 12; i++) {
            b.setSeeds(i, this.getSeeds(i));
        }
        return b;
    }

    // affichage du plateau sur 2 lignes
    public void printBoard(int userScore, int machineScore) {

        System.out.println("MACHINE : | " + machineScore + " |");

        System.out.print("| ");
        for (int i = 11; i >= 6; i--) {
            System.out.print(pits[i].getSeeds() + " | ");
        }

        System.out.println();
        System.out.println("-------------------------");

        System.out.print("| ");
        for (int i = 0; i < 6; i++) {
            System.out.print(pits[i].getSeeds() + " | ");
        }

        System.out.println();
        System.out.println("USER : | " + userScore + " |");
    }
}