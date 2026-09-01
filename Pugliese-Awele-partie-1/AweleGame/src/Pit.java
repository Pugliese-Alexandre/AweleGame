/*
    Représente un trou du plateau.
    Un trou contient un nombre de graines.
*/
public class Pit {

    // Nombre de graines dans le trou
    private int seeds;

    // -------------------- CONSTRUCTEUR --------------------

    // Crée un trou avec X graines
    public Pit(int seeds) {
        this.seeds = seeds;
    }

    // -------------------- GETTER / SETTER --------------------

    // Retourne le nombre de graines
    public int getSeeds() {
        return seeds;
    }

    // Modifie le nombre de graines
    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    // -------------------- ACTIONS --------------------

    // Ajoute une graine (+1)
    public void addSeed() {
        seeds++;
    }

    // Vide le trou et retourne le nombre de graines
    public int removeSeeds() {
        int temp = seeds;
        seeds = 0;
        return temp;
    }
}