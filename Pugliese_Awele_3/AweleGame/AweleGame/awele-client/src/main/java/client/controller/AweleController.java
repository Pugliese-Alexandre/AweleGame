package client.controller;

import client.service.AweleApiService;
import client.view.AweleView;
import io.swagger.client.model.RestBoard;
import io.swagger.client.model.RestPlayer;
import javafx.scene.control.Button;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.List;

/**
 * Contrôleur principal du jeu Awélé.
 *
 * Cette classe fait le lien entre :
 * - la vue JavaFX (interface graphique)
 * - le service API REST
 *
 * Le contrôleur gère les actions de l’utilisateur
 * et met à jour l’interface en fonction des données reçues.
 */
public class AweleController {

    /**
     * Vue contenant les composants graphiques.
     */
    private final AweleView view;

    /**
     * Service permettant de communiquer avec l’API REST.
     */
    private final AweleApiService apiService;

    /**
     * Identifiant du joueur connecté.
     * -1 signifie qu’aucun joueur n’est connecté.
     */
    private int playerId = -1;

    /**
     * Constructeur du contrôleur.
     *
     * Initialise les événements des boutons.
     */
    public AweleController(
            AweleView view,
            AweleApiService apiService
    ) {

        this.view = view;
        this.apiService = apiService;

        // Action du bouton connexion
        view.getConnectButton()
                .setOnAction(e -> connectPlayer());

        // Action du bouton nouvelle partie
        view.getNewGameButton()
                .setOnAction(e -> newGame());

        // Action du bouton deconnection

        view.getBtnDeconnexion().setOnAction(e -> {

            try {

                apiService.disconnect(playerId);

                playerId = -1;

                view.getPlayerIdLabel().setText("Non connecté");
                view.getStatusLabel().setText("Déconnexion réussie.");
                view.getLogArea().appendText("Déconnexion réussie.\n");
                view.getConnectButton().setDisable(false);
                view.getBtnDeconnexion().setDisable(true);

            } catch (Exception ex) {

                view.getStatusLabel().setText("Erreur déconnexion");
                ex.printStackTrace();
            }
        });

        // Action du bouton actualiser
        view.getRefreshButton()
                .setOnAction(e -> refreshBoard());

        // Action du bouton faire jouer la machine
        view.getMachineButton()
                .setOnAction(e -> playMachine());
    }

    /**
     * Réinitialise l'interface pour commencer
     * une nouvelle partie.
     */
    private void newGame() {

        try {

            if (playerId != -1) {
                apiService.disconnect(playerId);
            }

            playerId = -1;

            view.getPlayerIdLabel().setText("Non connecté");
            view.getScoreLabel().setText("Score joueur : 0 | Score machine : 0");
            view.getTurnLabel().setText("Tour : -");
            view.getStatusLabel().setText("Nouvelle partie prête.");

            view.getMachineRow().getChildren().clear();
            view.getPlayerRow().getChildren().clear();

            view.getLogArea().clear();

            view.getConnectButton().setDisable(false);
            view.getBtnDeconnexion().setDisable(true);

        } catch (Exception e) {

            view.getStatusLabel().setText("Erreur nouvelle partie.");

            view.getLogArea().appendText(
                    "Erreur : impossible de créer une nouvelle partie.\n"
            );

            e.printStackTrace();
        }
    }

    /**
     * Connecte un joueur au serveur.
     */
    private void connectPlayer() {

        try {

            String name =
                    view.getNameField().getText();

            if (name == null || name.isBlank()) {

                view.getStatusLabel().setText(
                        "Entre un nom."
                );

                return;
            }

            playerId = apiService.connect(name);

            if (playerId == -1) {
                view.getPlayerIdLabel().setText("Non connecté");
                view.getStatusLabel().setText("Connexion refusée ou erreur serveur.");
                return;
            }

            view.getConnectButton().setDisable(true);
            view.getBtnDeconnexion().setDisable(false);

            view.getMachineButton().setVisible(false);
            view.getMachineButton().setManaged(false);

            view.getPlayerIdLabel().setText(
                    "Connecté : " + playerId
            );

            refreshBoard();

            view.getStatusLabel().setText(
                    "Connexion réussie pour " + name + " avec l'id " + playerId + ".\n"
            );

            view.getLogArea().appendText(
                    "Connexion réussie pour " + name + " avec l'id " + playerId + ".\n"
            );

        } catch (Exception e) {

            view.getStatusLabel().setText(
                    "Erreur connexion."
            );

            e.printStackTrace();
        }
    }
    /**
     * Actualise l’affichage du plateau.
     */
    private void refreshBoard() {

        try {

            // Vérifie si un joueur est connecté
            if (playerId == -1) {

                view.getStatusLabel().setText(
                        "Connecte-toi."
                );

                return;
            }

            // Récupération du plateau depuis l’API
            RestBoard board =
                    apiService.getBoard(playerId);

            /**
             * Récupère les informations
             * du joueur connecté.
             */
            RestPlayer player =
                    apiService.getPlayer(playerId);

/**
 * Met à jour l'affichage
 * du score du joueur.
 */
            view.getScoreLabel().setText(
                    "Score joueur : "
                            + board.getPlayerScore()
                            + " | Score machine : "
                            + board.getMachineScore()
            );
            // Affichage des cases de la machine
            displayMachineHoles(board.getHolesPlayer2());

            // Affichage des cases du joueur
            displayPlayerHoles(board.getHolesPlayer1());

            // Récupération du joueur dont c’est le tour
            RestPlayer turn =
                    apiService.getTurn(playerId);

            /**
             * Vérifie l'état actuel de la partie.
             */
            int state =
                    apiService.getState(playerId);

            if (turn.getId() == playerId) {
                view.getTurnLabel().setText("Tour : Joueur");
            } else {
                view.getTurnLabel().setText("Tour : Machine");
            }

            // Vérifie si c’est le tour du joueur
            if (turn.getId() == playerId) {

                view.getStatusLabel().setText(
                        "Ton tour."
                );

            } else {

                view.getStatusLabel().setText(
                        "Tour machine."
                );
            }
/**
 * Victoire du joueur.
 */
            if (state == 1) {

                view.getStatusLabel().setText(
                        "Victoire du joueur."
                );

                view.getLogArea().appendText(
                        "\n=== VICTOIRE DU JOUEUR ===\n"
                );

                setPlayerButtonsDisabled(true);
                return;
            }

/**
 * Victoire de la machine.
 */
            if (state == 2) {

                view.getStatusLabel().setText(
                        "Victoire de la machine."
                );

                view.getLogArea().appendText(
                        "\n=== VICTOIRE DE LA MACHINE ===\n"
                );

                setPlayerButtonsDisabled(true);
                return;
            }
/**
 * Fin de partie.
 */
            else if (state == 3) {

                view.getStatusLabel().setText(
                        "Fin de la partie."
                );

                view.getTurnLabel().setText(
                        "Partie terminée"
                );

                view.getLogArea().appendText(
                        "\n=== Fin de la partie ===\n"
                );

                setPlayerButtonsDisabled(true);
            }
        } catch (Exception e) {

            // Message affiché si erreur lors du chargement
            view.getStatusLabel().setText(
                    "Erreur plateau."
            );

            e.printStackTrace();
        }
    }

    /**
     * Active ou désactive les cases du joueur.
     *
     * @param disabled true = désactive les cases, false = active les cases
     */
    private void setPlayerButtonsDisabled(boolean disabled) {

        view.getPlayerRow()
                .getChildren()
                .forEach(node -> {

                    Button button = (Button) node;

                    int seeds =
                            Integer.parseInt(button.getText());

                    if (seeds == 0) {
                        button.setDisable(true);
                    } else {
                        button.setDisable(disabled);
                    }
                });
    }

    /**
     * Affiche les cases de la machine.
     *
     * @param holes Liste des graines de chaque case
     */
    private void displayMachineHoles(
            List<Integer> holes
    ) {

        // Supprime les anciens boutons
        view.getMachineRow()
                .getChildren()
                .clear();

        // Création des nouvelles cases
// Création des nouvelles cases
        for (int i = holes.size() - 1; i >= 0; i--) {

            int seeds = holes.get(i);

            Button button =
                    new Button(
                            String.valueOf(seeds)
                    );

            button.setPrefSize(80, 80);

            button.setDisable(true);

            view.getMachineRow()
                    .getChildren()
                    .add(button);
        }
    }

    /**
     * Affiche les cases du joueur.
     *
     * @param holes Liste des graines de chaque case
     */
    private void displayPlayerHoles(
            List<Integer> holes
    ) {

        // Supprime les anciens boutons
        view.getPlayerRow()
                .getChildren()
                .clear();

        // Parcours des cases du joueur
        for (int i = 0; i < holes.size(); i++) {

            // Numéro de la case
            int holeNumber = i + 1;

            // Nombre de graines dans la case
            int seeds = holes.get(i);

            // Création du bouton
            Button button =
                    new Button(
                            String.valueOf(seeds)
                    );

            // Taille du bouton
            button.setPrefSize(80, 80);

            if (seeds == 0) {
                button.setDisable(true);
            }

            // Action lorsqu’on clique sur une case
            button.setOnAction(
                    e -> playUser(holeNumber)
            );

            // Ajout du bouton dans la ligne
            view.getPlayerRow()
                    .getChildren()
                    .add(button);
        }
    }

    /**
     * Permet au joueur humain de jouer un coup.
     *
     * @param hole Numéro de la case choisie
     */
    private void playUser(int hole) {

        try {

            // Vérifie si un joueur est connecté
            if (playerId == -1) {
                view.getStatusLabel().setText("Connecte-toi avant de jouer.");
                view.getLogArea().appendText(
                        "Action refusée : aucun joueur connecté.\n"
                );
                return;
            }

            // Vérifie si c'est bien le tour du joueur
            RestPlayer turn =
                    apiService.getTurn(playerId);

            if (turn.getId() != playerId) {
                view.getStatusLabel().setText("Ce n'est pas ton tour.");
                view.getLogArea().appendText(
                        "Action refusée : ce n'est pas au joueur de jouer.\n"
                );
                return;
            }

            // Envoie du coup au serveur
            int captured =
                    apiService.sowUser(hole, playerId);

            if (captured == -3) {
                view.getStatusLabel().setText("La partie est terminée.");
                view.getLogArea().appendText("Action refusée : la partie est terminée.\n");
                setPlayerButtonsDisabled(true);
                return;
            }

            // Vérifie si le coup est interdit
            if (captured == -1) {
                view.getStatusLabel().setText("Coup interdit.");
                view.getLogArea().appendText(
                        "Coup refusé : la case "
                                + hole
                                + " ne peut pas être jouée.\n"
                );
                refreshBoard();
                return;
            }

            // Actualise le plateau après le coup du joueur
            refreshBoard();

            // Vérifie si la partie est terminée après le coup du joueur
            int stateAfterMove =
                    apiService.getState(playerId);

            if (stateAfterMove != 0) {
                setPlayerButtonsDisabled(true);
                return;
            }

            // Affiche le coup joué et le nombre de graines capturées
            view.getStatusLabel().setText(
                    "Tu as joué la case "
                            + hole
                            + ". Capture : "
                            + captured
            );

            view.getLogArea().appendText(
                    "\nJoueur joue case "
                            + hole
                            + "\n"
            );

            view.getLogArea().appendText(
                    "Capture : "
                            + captured
                            + " graine(s)\n"
            );

            // Après le coup du joueur, on vérifie si c'est à la machine
            RestPlayer turnAfterMove =
                    apiService.getTurn(playerId);

            if (turnAfterMove.getId() != playerId) {
                playMachine();
            }

        } catch (Exception e) {

            view.getStatusLabel().setText("Coup impossible.");

            view.getLogArea().appendText(
                    "Erreur : le coup joueur n'a pas pu être effectué.\n"
            );

            e.printStackTrace();
        }
    }

    /**
     * Demande à la machine de jouer automatiquement.
     */
    /**
     * Demande à la machine de jouer automatiquement.
     */
    private void playMachine() {

        try {

            // Vérifie si un joueur est connecté
            if (playerId == -1) {

                view.getStatusLabel().setText(
                        "Connecte-toi avant de faire jouer la machine."
                );

                view.getLogArea().appendText(
                        "Action refusée : aucun joueur connecté.\n"
                );

                return;
            }

            // Vérifie si c'est bien le tour de la machine
            RestPlayer turn =
                    apiService.getTurn(playerId);

            if (turn.getId() == playerId) {

                view.getStatusLabel().setText(
                        "Ce n'est pas à la machine de jouer."
                );

                view.getLogArea().appendText(
                        "Action refusée : ce n'est pas à la machine de jouer.\n"
                );

                return;
            }

            // Bloque les cases du joueur pendant le tour machine
            setPlayerButtonsDisabled(true);

            // Message affiché pendant la réflexion
            view.getStatusLabel().setText(
                    "La machine réfléchit..."
            );

            view.getLogArea().appendText(
                    "\nLa machine réfléchit...\n"
            );

            PauseTransition pause =
                    new PauseTransition(Duration.seconds(2));

            pause.setOnFinished(event -> {

                try {

                    // Récupère le score machine avant le coup
                    RestBoard boardBefore =
                            apiService.getBoard(playerId);

                    int machineScoreBefore =
                            boardBefore.getMachineScore();

                    // Appel du coup machine
                    int choice =
                            apiService.sowMachine(playerId);

                    // Vérifie si la partie est déjà terminée
                    if (choice == -3) {

                        view.getStatusLabel().setText(
                                "La partie est terminée."
                        );

                        view.getLogArea().appendText(
                                "Action refusée : la partie est terminée.\n"
                        );

                        setPlayerButtonsDisabled(true);
                        return;
                    }

                    // Vérifie si le coup est impossible
                    if (choice == -1) {

                        view.getStatusLabel().setText(
                                "La machine ne peut pas jouer."
                        );

                        view.getLogArea().appendText(
                                "La machine n'a pas pu jouer.\n"
                        );

                        refreshBoard();
                        return;
                    }

                    // Récupère le score machine après le coup
                    RestBoard boardAfter =
                            apiService.getBoard(playerId);

                    int machineScoreAfter =
                            boardAfter.getMachineScore();

                    int capturedByMachine =
                            machineScoreAfter - machineScoreBefore;

                    // Historique machine
                    view.getLogArea().appendText(
                            "Machine joue case "
                                    + choice
                                    + "\n"

                    );

                    view.getLogArea().appendText(
                            "Capture : "
                                    + capturedByMachine
                                    + " graine(s)\n"
                    );

                    // Actualise le plateau après avoir affiché le coup machine
                    refreshBoard();

                    // Message utilisateur
                    view.getStatusLabel().setText(
                            "La machine a joué la case "
                                    + choice
                                    + ". Capture : "
                                    + capturedByMachine
                                    + "."
                    );

                } catch (Exception e) {

                    view.getStatusLabel().setText(
                            "Erreur machine."
                    );

                    view.getLogArea().appendText(
                            "Erreur pendant le tour machine.\n"
                    );

                    e.printStackTrace();

                } finally {

                    try {
                        int state =
                                apiService.getState(playerId);

                        if (state == 0) {
                            setPlayerButtonsDisabled(false);
                        } else {
                            setPlayerButtonsDisabled(true);
                        }

                    } catch (Exception ex) {
                        setPlayerButtonsDisabled(true);
                        ex.printStackTrace();
                    }
                }
            });

            pause.play();

        } catch (Exception e) {

            view.getStatusLabel().setText(
                    "Erreur machine."
            );

            view.getLogArea().appendText(
                    "Erreur générale machine.\n"
            );

            e.printStackTrace();
        }
    }}