package service;

import dto.BoardResponseDTO;
import model.AweleGame;
import model.Player;

public class AweleService {

    private AweleGame game;

    public AweleService() {
        game = new AweleGame();
    }

    /**
     * Connecte un joueur et retourne son identifiant.
     */
    public int connect(String name) {
        return game.connection(name);
    }

    /**
     * Déconnecte un joueur et retourne son identifiant.
     */
    public int disconnect(int playerId) {
        return game.deconnection(playerId);
    }

    /**
     * Retourne le joueur connecté.
     */
    public Player getPlayer(int playerId) {
        return game.getPlayer(playerId);
    }

    /**
     * Retourne l'état de la partie.
     */
    public int getState(int playerId) {
        return game.getState(playerId);
    }

    /**
     * Retourne le plateau sous forme DTO.
     */
    public BoardResponseDTO getBoard(int playerId) {

        int[] pits = game.getBoard(playerId).getPits();

        int[] player1 = new int[6];
        int[] player2 = new int[6];

        for (int i = 0; i < 6; i++) {
            player1[i] = pits[i];
        }

        for (int i = 0; i < 6; i++) {
            player2[i] = pits[i + 6];
        }

        return new BoardResponseDTO(
                player1,
                player2,
                game.getUser().getScore(),
                game.getMachine().getScore()
        );
    }

    /**
     * Retourne le joueur qui doit jouer.
     */
    public Player getTurn(int playerId) {
        return game.getTurn(playerId);
    }

    /**
     * Permet au joueur humain de jouer une case.
     */
    public int sowUser(int hole, int playerId) {
        int holeIndex = hole - 1;
        return game.sow(holeIndex, playerId);
    }

    /**
     * Permet à la machine de jouer.
     */
    public int sowMachine(int playerId) {
        return game.playMachineFromApi(playerId);
    }
}