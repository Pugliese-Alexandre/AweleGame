package api.controller;

import dto.BoardResponseDTO;
import model.Player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import service.AweleService;

/**
 * Contrôleur REST du jeu Awélé.
 * Cette classe expose les routes demandées dans le fichier OpenAPI/YAML.
 */
@RestController
@RequestMapping("/v1/awele")
@Tag(
        name = "Awélé API",
        description = "API REST pour gérer une partie d’Awélé"
)
public class AweleController {

    /**
     * Service contenant la logique entre le contrôleur REST et le modèle.
     */
    private final AweleService service;

    /**
     * Constructeur du contrôleur.
     */
    public AweleController() {
        service = new AweleService();
    }

    /**
     * Connecte un joueur humain et retourne son identifiant.
     */
    @Operation(summary = "Connecter un joueur")
    @PostMapping("/connect")
    public int connect(@RequestParam("name") String name) {
        return service.connect(name);
    }

    /**
     * Déconnecte le joueur et retourne son identifiant.
     */
    @Operation(summary = "Déconnecter un joueur")
    @PostMapping("/deconnect")
    public int disconnect(@RequestParam("playerId") int playerId) {
        return service.disconnect(playerId);
    }

    /**
     * Retourne les informations du joueur connecté.
     */
    @Operation(summary = "Afficher le joueur")
    @GetMapping("/player")
    public Player getPlayer(@RequestParam("playerId") int playerId) {
        return service.getPlayer(playerId);
    }

    /**
     * Retourne l’état actuel du plateau.
     */
    @Operation(summary = "Afficher le plateau")
    @GetMapping("/board")
    public BoardResponseDTO getBoard(@RequestParam("playerId") int playerId) {
        return service.getBoard(playerId);
    }

    /**
     * Permet au joueur humain de jouer un coup.
     */
    @Operation(summary = "Jouer un coup utilisateur")
    @PostMapping("/sow_user")
    public int sowUser(@RequestParam("hole") int hole,
                       @RequestParam("playerId") int playerId) {
        return service.sowUser(hole, playerId);
    }

    /**
     * Permet à la machine de jouer un coup.
     */
    @Operation(summary = "Faire jouer la machine")
    @PostMapping("/sow_machine")
    public int sowMachine(@RequestParam("playerId") int playerId) {
        return service.sowMachine(playerId);
    }

    /**
     * Retourne le joueur qui doit jouer.
     */
    @Operation(summary = "Afficher le tour actuel")
    @GetMapping("/turn")
    public Player getTurn(@RequestParam("playerId") int playerId) {
        return service.getTurn(playerId);
    }

    /**
     * Retourne l’état de la partie.
     */
    @Operation(summary = "Afficher l’état de la partie")
    @GetMapping("/state")
    public int getState(@RequestParam("playerId") int playerId) {
        return service.getState(playerId);
    }
}