/*
 Classe représentant un trou du plateau
*/
public class Pit {

    private int seeds;

    public Pit(int seeds) {
        this.seeds = seeds;
    }

    public int getSeeds() {
        return seeds;
    }

    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    public void addSeed() {
        seeds++;
    }

    public int removeSeeds() {
        int temp = seeds;
        seeds = 0;
        return temp;
    }
}