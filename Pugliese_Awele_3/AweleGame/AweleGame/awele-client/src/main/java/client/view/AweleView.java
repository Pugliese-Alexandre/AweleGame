package client.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Classe représentant l’interface graphique du jeu Awélé.
 * Cette vue contient tous les éléments affichés à l’écran :
 * boutons, champs de texte, labels et plateau du jeu.
 */
public class AweleView {

    /**
     * Conteneur principal vertical de l’application.
     */
    private final VBox root;

    /**
     * Champ permettant au joueur d’entrer son nom.
     */
    private final TextField nameField;

    /**
     * Bouton de connexion du joueur.
     */
    private final Button connectButton;

    /**
     * Bouton permettant d’actualiser le plateau.
     */
    private final Button refreshButton;

    /**
     * Bouton permettant à la machine de jouer automatiquement.
     */
    private final Button machineButton;

    /**
     * Label affichant l’identifiant du joueur connecté.
     */
    private final Label playerIdLabel;

    /**
     * Label affichant les messages de statut du jeu.
     */
    private final Label statusLabel;


    /**
     * Label affichant le score du joueur
     * et de la machine.
     */
    private final Label scoreLabel;


    /**
     * Label affichant le joueur dont c'est le tour.
     */
    private final Label turnLabel;

    /**
     * Ligne contenant les cases de la machine.
     */
    private final HBox machineRow;

    /**
     * Ligne contenant les cases du joueur.
     */
    private final HBox playerRow;

    /**
     * Ligne contenant le bouton deconnection
     */

    private Button btnDeconnexion;

    /**
     * Bouton permettant de recommencer une nouvelle partie.
     */
    private final Button newGameButton;

    /**
     * Affiche un texte et garde l'historique des retours machines
     */
    private final TextArea logArea;

    /**
     * Constructeur de la vue.
     * Initialise et organise tous les composants graphiques.
     */
    public AweleView() {

        // Création du conteneur principal avec un espacement de 15 px
        root = new VBox(15);

        // Ajout d’une marge intérieure autour de la fenêtre
        root.setPadding(new Insets(20));

        // Création du titre principal
        Label title = new Label("Jeu Awélé");

        // Style CSS appliqué au titre
        title.setStyle(
                "-fx-font-size: 28px;"
                        + "-fx-font-weight: bold;"
        );

        // Champ texte pour saisir le nom du joueur
        nameField = new TextField();

        // Texte affiché lorsque le champ est vide
        nameField.setPromptText("Nom du joueur");

        // Bouton de connexion
        connectButton = new Button("Connexion");

        // Bouton nouvelle partie
        newGameButton = new Button("Nouvelle partie");

        // Bouton d’actualisation du plateau
        refreshButton =
                new Button("Actualiser");

        refreshButton.setVisible(false);
        refreshButton.setManaged(false);

        // Bouton pour faire jouer la machine
        machineButton =
                new Button("Faire jouer machine");

        machineButton.setVisible(false);
        machineButton.setManaged(false);

        // Label affichant l’état de connexion
        playerIdLabel =
                new Label("Non connecté");

        /**
         * Label affichant les scores
         * des deux joueurs.
         */
        scoreLabel =
                new Label(
                        "Score joueur : 0 | Score machine : 0"
                );

        // Label affichant les informations du jeu
        statusLabel =
                new Label("Bienvenue");

        btnDeconnexion = new Button("Déconnexion");
        btnDeconnexion.setDisable(true);

/**
 * Label affichant le joueur dont c'est le tour.
 */
        turnLabel =
                new Label("Tour : -");

        // Ligne des cases de la machine
        machineRow = new HBox(10);

        // Ligne des cases du joueur
        playerRow = new HBox(10);

        // Conteneur horizontal pour le champ nom + bouton connexion
        HBox connectionBox =
                new HBox(10);

        // Ajout des composants dans la zone de connexion
        connectionBox.getChildren().addAll(
                nameField,
                connectButton,
                btnDeconnexion
        );

        // Message pour les retours machines text area

        logArea = new TextArea();

        logArea.setEditable(false);

        logArea.setPrefHeight(150);

        logArea.setPromptText("Historique du jeu...");

        // Ajout de tous les éléments dans le conteneur principal
        root.getChildren().addAll(
                title,
                connectionBox,
                newGameButton,
                playerIdLabel,
                scoreLabel,
                turnLabel,
                new Label("Machine"),
                machineRow,
                new Label("Joueur"),
                playerRow,
                refreshButton,
                machineButton,
                statusLabel,
                new Label("Historique"),
                logArea
        );
    }

    /**
     * Retourne le conteneur principal de la vue.
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * Retourne le champ du nom du joueur.
     */
    public TextField getNameField() {
        return nameField;
    }

    /**
     * Retourne le bouton de connexion.
     */
    public Button getConnectButton() {
        return connectButton;
    }

    /**
     * Retourne le bouton de deconnection.
     */

    public Button getBtnDeconnexion() {
        return btnDeconnexion;
    }

    /**
     * Retourne le bouton d’actualisation.
     */
    public Button getRefreshButton() {
        return refreshButton;
    }

    /**
     * Retourne le bouton permettant à la machine de jouer.
     */
    public Button getMachineButton() {
        return machineButton;
    }

    /**
     * Retourne le label contenant l’identifiant du joueur.
     */
    public Label getPlayerIdLabel() {
        return playerIdLabel;
    }

    /**
     * Retourne le label de statut du jeu.
     */
    public Label getStatusLabel() {
        return statusLabel;
    }

    /**
     * Retourne le label affichant
     * les scores de la partie.
     *
     * @return Label des scores
     */
    public Label getScoreLabel() {
        return scoreLabel;
    }

    /**
     * Retourne la ligne des cases de la machine.
     */
    public HBox getMachineRow() {
        return machineRow;
    }

    /**
     * Retourne la ligne des cases du joueur.
     */
    public HBox getPlayerRow() {
        return playerRow;
    }

    /**
     * Retourne la zone de texte contenant
     * l’historique des actions du jeu.
     *
     * Cette zone permet d’afficher :
     * - les connexions
     * - les coups joués
     * - les messages de la machine
     * - les erreurs éventuelles
     */

    public TextArea getLogArea() {
        return logArea;
    }

    /**
     * Retourne le label affichant le tour actuel.
     */
    public Label getTurnLabel() {
        return turnLabel;
    }

    /**
     * Retourne le bouton nouvelle partie.
     */
    public Button getNewGameButton() {
        return newGameButton;
    }
}