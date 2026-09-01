package model;

import java.io.File;
import java.util.Scanner;

// Cette classe gère : le plateau, les 12 cases, les graines, affichage

public class Board {

    // Les 12 trous du plateau
    private Pit[] pits;

    public Board() {
        pits = new Pit[12];

        for (int i = 0; i < 12; i++) {
            pits[i] = new Pit(0);
        }
    }

    // -------------------- CHARGEMENT --------------------

    // Charge le plateau depuis pit.txt
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

    // -------------------- ACCES AUX CASES --------------------

    // Retourne le nombre de graines d'une case
    public int getSeeds(int index) {
        return pits[index].getSeeds();
    }

    // Modifie le nombre de graines d'une case
    public void setSeeds(int index, int value) {
        pits[index].setSeeds(value);
    }

    // Ajoute une graine dans une case
    public void addSeed(int index) {
        pits[index].addSeed();
    }

    // Retire toutes les graines d'une case
    public int removeSeeds(int index) {
        return pits[index].removeSeeds();
    }

    // -------------------- COMPTER LES GRAINES --------------------

    // Compte les graines du côté joueur
    public int countSeedsOnUserSide() {
        int total = 0;

        for (int i = 0; i < 6; i++) {
            total += pits[i].getSeeds();
        }

        return total;
    }

    // Compte les graines du côté machine
    public int countSeedsOnMachineSide() {
        int total = 0;

        for (int i = 6; i < 12; i++) {
            total += pits[i].getSeeds();
        }

        return total;
    }

    // -------------------- RAMASSER LES GRAINES --------------------

    // Ramasse les graines restantes du joueur
    public int collectUserSeeds() {
        int total = 0;

        for (int i = 0; i < 6; i++) {
            total += removeSeeds(i);
        }

        return total;
    }

    // Ramasse les graines restantes de la machine
    public int collectMachineSeeds() {
        int total = 0;

        for (int i = 6; i < 12; i++) {
            total += removeSeeds(i);
        }

        return total;
    }

    // -------------------- COPIE --------------------

    // Crée une copie du plateau
    public Board copy() {
        Board b = new Board();

        for (int i = 0; i < 12; i++) {
            b.setSeeds(i, this.getSeeds(i));
        }

        return b;
    }
    /**
     * Retourne le contenu des 12 cases du plateau.
     */
    public int[] getPits() {

        int[] values = new int[12];

        for (int i = 0; i < 12; i++) {
            values[i] = pits[i].getSeeds();
        }

        return values;
    }
    // -------------------- AFFICHAGE --------------------

    // Affiche le plateau et les scores
    public void printBoard(int userScore, int machineScore) {

        System.out.println("Ordinateur : " + machineScore);

        // Ligne machine
        for (int i = 11; i >= 6; i--) {
            System.out.print(pits[i].getSeeds() + " ");
        }

        System.out.println();

        // Ligne joueur
        for (int i = 0; i < 6; i++) {
            System.out.print(pits[i].getSeeds() + " ");
        }

        System.out.println();

        System.out.println("Joueur : " + userScore + "\n");
    }
}