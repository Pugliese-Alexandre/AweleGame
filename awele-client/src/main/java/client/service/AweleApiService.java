package client.service;

import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.RestBoard;
import io.swagger.client.model.RestPlayer;

/**
 * Service permettant au client JavaFX de communiquer
 * avec l’API REST du jeu Awélé.
 *
 * Cette classe envoie les requêtes HTTP vers le serveur
 * afin de récupérer les informations du jeu.
 */
public class AweleApiService {

    /**
     * Objet généré par Swagger permettant
     * d’utiliser facilement les endpoints de l’API.
     */
    private final DefaultApi api;

    /**
     * Constructeur du service API.
     * Configure l’adresse du serveur backend.
     */
    public AweleApiService() {

        // Création de l’API Swagger
        api = new DefaultApi();

        // Adresse de base du serveur REST
        api.getApiClient().setBasePath(
                "http://localhost:8080/v1"
        );
    }

    /**
     * Connecte un joueur au serveur.
     *
     * @param name Nom du joueur
     * @return Identifiant du joueur connecté
     * @throws Exception En cas d’erreur serveur
     */
    public int connect(String name) throws Exception {
        return api.connect(name);
    }

    /**
     * Déconnecte un joueur du serveur.
     *
     * @param playerId Identifiant du joueur
     * @throws Exception En cas d’erreur serveur
     */
    public void disconnect(int playerId) throws Exception {
        api.disconnect(playerId);
    }

    /**
     * Retourne l’état actuel de la partie.
     *
     * @param playerId Identifiant du joueur
     * @return État de la partie
     * @throws Exception En cas d’erreur serveur
     */
    public int getState(int playerId) throws Exception {
        return api.getState(playerId);
    }

    /**
     * Récupère le plateau de jeu.
     *
     * @param playerId Identifiant du joueur
     * @return Plateau du jeu
     * @throws Exception En cas d’erreur serveur
     */
    public RestBoard getBoard(int playerId) throws Exception {
        return api.getBoard(playerId);
    }

    /**
     * Retourne le joueur dont c’est actuellement le tour.
     *
     * @param playerId Identifiant du joueur
     * @return Joueur actif
     * @throws Exception En cas d’erreur serveur
     */
    public RestPlayer getTurn(int playerId) throws Exception {
        return api.getTurn(playerId);
    }

    /**
     * Permet au joueur humain de jouer un coup.
     *
     * @param hole Numéro de la case choisie
     * @param playerId Identifiant du joueur
     * @return Résultat du coup joué
     * @throws Exception En cas d’erreur serveur
     */
    public int sowUser(int hole, int playerId)
            throws Exception {

        return api.sowUser(hole, playerId);
    }

    /**
     * Retourne les informations du joueur connecté.
     *
     * @param playerId Identifiant du joueur
     * @return Joueur connecté
     * @throws Exception En cas d'erreur serveur
     */
    public RestPlayer getPlayer(int playerId)
            throws Exception {

        return api.getPlayer(playerId);
    }

    /**
     * Demande à la machine de jouer automatiquement.
     *
     * @param playerId Identifiant du joueur
     * @return Résultat du coup de la machine
     * @throws Exception En cas d’erreur serveur
     */
    public int sowMachine(int playerId)
            throws Exception {

        return api.sowMachine(playerId);
    }
}