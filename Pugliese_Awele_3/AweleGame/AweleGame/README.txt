Projet Awélé Partie 2 – REST/OpenAPI

Auteur : Alexandre Pugliese

Description :
Ce projet représente une version client/serveur du jeu Awélé.
Le serveur utilise Spring Boot et expose une API REST documentée avec OpenAPI/Swagger.
Le client utilise les classes générées automatiquement via OpenAPI Generator.

Technologies utilisées :
- Java 21
- Spring Boot
- Maven
- OpenAPI / Swagger
- REST API

Lancement du projet :

1. Lancer le serveur :
   - Ouvrir le projet awele-server
   - Exécuter AweleServerApplication

2. Lancer le client :
   - Ouvrir le projet awele-client
   - Exécuter client.Main

Adresse du serveur :
http://localhost:8080/v1

Fonctionnalités :
- connexion joueur
- affichage du plateau
- jeu utilisateur
- jeu automatique de la machine
- gestion de l’état de la partie
- déconnexion

Fichier YAML :
Le fichier OpenAPI se trouve dans :
awele-client/src/main/resources/awele-api-3.yaml

# Configuration JavaFX

Projet réalisé avec :

- Java 21 (Corretto)
- JavaFX 21.0.11

## VM Options IntelliJ

Ajouter dans :

Run → Edit Configurations → VM options

```txt
--module-path "C:\Users\pugli\Downloads\openjfx-21.0.11_windows-x64_bin-sdk\javafx-sdk-21.0.11\lib" --add-modules javafx.controls,javafx.fxml