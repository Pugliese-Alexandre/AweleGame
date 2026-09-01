package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO représentant le plateau du jeu Awélé.
 * Cette classe est utilisée pour envoyer
 * les données du plateau via l’API REST.
 */
@Schema(description = "Plateau du jeu Awélé")
public class BoardResponseDTO {

    /**
     * Cases du joueur humain.
     */
    @JsonProperty("holes-player1")
    private int[] holesPlayer1;

    /**
     * Cases de la machine.
     */
    @JsonProperty("holes-player2")
    private int[] holesPlayer2;


    /**
     * Score du joueur humain.
     */
    @JsonProperty("player-score")
    private int playerScore;

    /**
     * Score de la machine.
     */
    @JsonProperty("machine-score")
    private int machineScore;

    /**
     * Constructeur du plateau.
     *
     * @param holesPlayer1 cases du joueur
     * @param holesPlayer2 cases de la machine
     */
    public BoardResponseDTO(
            int[] holesPlayer1,
            int[] holesPlayer2,
            int playerScore,
            int machineScore
    ) {
        this.holesPlayer1 = holesPlayer1;
        this.holesPlayer2 = holesPlayer2;
        this.playerScore = playerScore;
        this.machineScore = machineScore;
    }

    /**
     * Retourne les cases du joueur humain.
     *
     * @return tableau des cases joueur
     */
    public int[] getHolesPlayer1() {
        return holesPlayer1;
    }

    /**
     * Modifie les cases du joueur humain.
     *
     * @param holesPlayer1 nouveau tableau des cases
     */
    public void setHolesPlayer1(int[] holesPlayer1) {
        this.holesPlayer1 = holesPlayer1;
    }

    /**
     * Retourne les cases de la machine.
     *
     * @return tableau des cases machine
     */
    public int[] getHolesPlayer2() {
        return holesPlayer2;
    }

    /**
     * Modifie les cases de la machine.
     *
     * @param holesPlayer2 nouveau tableau des cases
     */
    public void setHolesPlayer2(int[] holesPlayer2) {
        this.holesPlayer2 = holesPlayer2;
    }

    /**
     * Retourne le score du joueur.
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * Retourne le score de la machine.
     */
    public int getMachineScore() {
        return machineScore;
    }
}