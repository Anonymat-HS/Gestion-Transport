package com.transport.n3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class MenuJDBC {

    // =========================================================
    // Informations de connexion à la base PostgreSQL
    // À adapter selon votre configuration locale
    // =========================================================

    // Format : jdbc:postgresql://hôte:port/nom_de_la_base
    private static final String URL      = "jdbc:postgresql://localhost:5432/gestion_transport";
    private static final String USER     = "postgres";   // utilisateur PostgreSQL
    private static final String PASSWORD = "ieh20251";   // mot de passe PostgreSQL (change le mot de passe ici si nécessaire)

    public static void main(String[] args) {

        // Connection  → le pont entre Java et PostgreSQL
        // Statement   → envoie une requête SQL à la base
        // ResultSet   → reçoit le résultat (tableau de lignes)
        // On les déclare ici pour pouvoir les fermer dans le bloc finally
        Connection connexion = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // -------------------------------------------------
            // 1. Ouvrir la connexion à la base de données
            //    DriverManager cherche automatiquement le pilote
            //    PostgreSQL qu'on a ajouté dans le pom.xml
            // -------------------------------------------------
            connexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie !");

            // -------------------------------------------------
            // 2. Boucle du menu console
            // -------------------------------------------------
            int choix = 0;
            while (choix != 3) {
                System.out.println("\n=== Menu Transport ===");
                System.out.println("1 - Voir les voitures");
                System.out.println("2 - Voir les voyages");
                System.out.println("3 - Quitter");
                System.out.print("Votre choix : ");

                choix = scanner.nextInt();

                switch (choix) {
                    case 1 -> afficherVoitures(connexion);
                    case 2 -> afficherVoyages(connexion);
                    case 3 -> System.out.println("Au revoir !");
                    default -> System.out.println("Choix invalide. Tapez 1, 2 ou 3.");
                }
            }

        } catch (Exception e) {
            // Deux causes possibles :
            //  - Mauvaise URL / mot de passe / base inexistante
            //  - PostgreSQL n'est pas démarré sur la machine
            System.err.println("Erreur de connexion : " + e.getMessage());

        } finally {
            // -------------------------------------------------
            // 3. Toujours fermer la connexion à la fin,
            //    même si une erreur s'est produite,
            //    pour libérer les ressources côté PostgreSQL
            // -------------------------------------------------
            try {
                if (connexion != null && !connexion.isClosed()) {
                    connexion.close();
                    System.out.println("Connexion fermée proprement.");
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture : " + e.getMessage());
            }
            scanner.close();
        }
    }

    // =========================================================
    // Méthode : afficher toutes les voitures depuis la base
    // =========================================================
    private static void afficherVoitures(Connection connexion) {
        // La requête SQL à exécuter (exactement comme le prof l'a montré)
        String sql = "SELECT * FROM voiture";

        // try-with-resources : Statement et ResultSet sont fermés automatiquement
        // à la fin du bloc, même en cas d'erreur
        try (Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\n--- Liste des voitures ---");
            System.out.printf("%-10s %-15s %-15s %-12s%n",
                    "ID", "Type", "Matricule", "Nb Places");
            System.out.println("-".repeat(55));

            // ResultSet fonctionne comme un curseur :
            // next() avance d'une ligne et retourne false quand il n'y en a plus
            boolean aucuneVoiture = true;
            while (resultSet.next()) {
                aucuneVoiture = false;
                // getString/getInt récupèrent la valeur de la colonne
                // par son nom exact dans la table PostgreSQL
                String id          = resultSet.getString("id");
                String typeVoiture = resultSet.getString("type_voiture");
                String matricule   = resultSet.getString("numero_matricule");
                int    nbPlaces    = resultSet.getInt("nbre_places");

                System.out.printf("%-10s %-15s %-15s %-12d%n",
                        id, typeVoiture, matricule, nbPlaces);
            }

            if (aucuneVoiture) {
                System.out.println("Aucune voiture trouvée dans la base.");
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des voitures : " + e.getMessage());
        }
    }

    // =========================================================
    // Méthode : afficher tous les voyages depuis la base
    // =========================================================
    private static void afficherVoyages(Connection connexion) {
        String sql = "SELECT * FROM voyage";

        try (Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\n--- Liste des voyages ---");
            System.out.printf("%-10s %-20s %-20s %-20s %-20s%n",
                    "ID", "Ville Départ", "Ville Arrivée", "Date Départ", "Date Arrivée");
            System.out.println("-".repeat(90));

            boolean aucunVoyage = true;
            while (resultSet.next()) {
                aucunVoyage = false;
                String id           = resultSet.getString("id");
                String villeDepart  = resultSet.getString("ville_depart");
                String villeArrivee = resultSet.getString("ville_arrivee");
                String dateDepart   = resultSet.getString("date_depart");
                String dateArrivee  = resultSet.getString("date_arrivee");

                System.out.printf("%-10s %-20s %-20s %-20s %-20s%n",
                        id, villeDepart, villeArrivee, dateDepart, dateArrivee);
            }

            if (aucunVoyage) {
                System.out.println("Aucun voyage trouvé dans la base.");
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des voyages : " + e.getMessage());
        }
    }
}
