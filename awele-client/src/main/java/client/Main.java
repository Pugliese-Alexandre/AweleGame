package client;

import client.controller.AweleController;
import client.service.AweleApiService;
import client.view.AweleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Création du service REST
        AweleApiService apiService = new AweleApiService();

        // Création de la vue
        AweleView view = new AweleView();

        // Création du contrôleur
        new AweleController(view, apiService);

        // Création de la scène JavaFX
        Scene scene = new Scene(view.getRoot(), 900, 600);

        // Configuration de la fenêtre
        stage.setTitle("Awélé - Client JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//ANCIEN CODE : FIN PROJET 2 VALIDER, JE PREFERE CLEAN ET BIEN SEPARER CHAQUE PARTIE DE MON CODE
//package client;
//
//import io.swagger.client.api.DefaultApi;
//import io.swagger.client.model.RestBoard;
//import io.swagger.client.model.RestPlayer;
//
//import java.util.Scanner;
//
///**
// * Client console du jeu Awélé.
// * Cette classe communique avec le serveur REST
// * via l’API OpenAPI générée automatiquement.
// */
//public class Main {
//
//    /**
//     * Méthode principale du programme.
//     *
//     * @param args arguments du programme
//     */
//    public static void main(String[] args) {
//
//        /**
//         * Objet API permettant d’appeler
//         * les routes REST du serveur.
//         */
//        DefaultApi api = new DefaultApi();
//
//        /**
//         * Adresse du serveur Spring Boot.
//         */
//        api.getApiClient().setBasePath("http://localhost:8080/v1");
//
//        /**
//         * Scanner permettant de lire
//         * les entrées clavier du joueur.
//         */
//        Scanner scanner = new Scanner(System.in);
//
//        try {
//
//            // -------------------- CONNEXION --------------------
//
//            // Demande le nom du joueur
//            System.out.print("Nom du joueur : ");
//            String name = scanner.nextLine();
//
//            // Connexion au serveur
//            int playerId = api.connect(name);
//
//            System.out.println("Connecté avec ID : " + playerId);
//
//            // -------------------- BOUCLE PRINCIPALE --------------------
//
//            /**
//             * Boucle tant que la partie est en cours.
//             * L’état 0 signifie : partie active.
//             */
//            while (api.getState(playerId) == 0) {
//
//                // -------------------- AFFICHAGE DU PLATEAU --------------------
//
//                // Récupération du plateau
//                RestBoard board = api.getBoard(playerId);
//
//                System.out.println("\n===== PLATEAU =====");
//
//                // Affichage des cases machine
//                System.out.println("Machine : "
//                        + board.getHolesPlayer2());
//
//                // Affichage des cases joueur
//                System.out.println("Joueur : "
//                        + board.getHolesPlayer1());
//
//                // -------------------- TOUR ACTUEL --------------------
//
//                // Récupération du joueur qui doit jouer
//                RestPlayer turn = api.getTurn(playerId);
//
//                // -------------------- TOUR DU JOUEUR --------------------
//
//                if (turn.getId() == playerId) {
//
//                    // Demande une case au joueur
//                    System.out.print("Choisissez une case (1-6) : ");
//
//                    int hole = scanner.nextInt();
//
//                    // Envoi du coup au serveur
//                    int captured = api.sowUser(hole, playerId);
//
//                    // Affiche le nombre de graines capturées
//                    System.out.println(
//                            "Graines capturées : " + captured
//                    );
//
//                } else {
//
//                    // -------------------- TOUR MACHINE --------------------
//
//                    System.out.println("La machine joue...");
//
//                    // Le serveur joue automatiquement
//                    int machineChoice =
//                            api.sowMachine(playerId);
//
//                    // Affiche le coup de la machine
//                    System.out.println(
//                            "Machine joue la case : "
//                                    + machineChoice
//                    );
//                }
//            }
//
//            // -------------------- FIN DE PARTIE --------------------
//
//            // Récupère l’état final
//            int state = api.getState(playerId);
//
//            System.out.println("\n===== FIN DE PARTIE =====");
//
//            // Victoire du joueur
//            if (state == 1) {
//                System.out.println("Victoire du joueur !");
//            }
//
//            // Victoire machine
//            else if (state == 2) {
//                System.out.println("Victoire de la machine !");
//            }
//
//            // Partie terminée
//            else {
//                System.out.println("Partie terminée.");
//            }
//
//            // Déconnexion du joueur
//            api.disconnect(playerId);
//
//        }
//
//        // Gestion des erreurs
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}