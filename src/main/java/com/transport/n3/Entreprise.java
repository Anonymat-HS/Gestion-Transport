package com.transport.n3;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Entreprise {
    private String id;
    private String nom;
    private String email;
    private String telephone;
    private List<Voiture> listeVoiture;
    private List<Employee> listeEmployee;

    public Entreprise(String id, String nom, String email, String telephone, List<Voiture> listeVoiture, List<Employee> listeEmployee) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        // Bug corrigé : on récupère bien les listes passées en paramètre.
        // L'ancienne version ignorait listeVoiture/listeEmployee et créait des listes vides,
        // ce qui aurait rendu toute l'entreprise "vide" dès sa création.
        this.listeVoiture = (listeVoiture != null) ? new ArrayList<>(listeVoiture) : new ArrayList<>();
        this.listeEmployee = (listeEmployee != null) ? new ArrayList<>(listeEmployee) : new ArrayList<>();
    }

    /**
     * Affiche/retourne la liste des employés de l'entreprise.
     * Ne dépend d'aucune autre classe non terminée : utilise uniquement listeEmployee.
     *
     * @return une copie non modifiable de la liste des employés (pour éviter
     *         qu'un appelant externe modifie la liste interne directement)
     */
    public List<Employee> afficherListeEmployes() {
        if (listeEmployee == null || listeEmployee.isEmpty()) {
            System.out.println("Aucun employé enregistré pour le moment.");
            return Collections.emptyList();
        }
        System.out.println("=== Liste des employés (" + listeEmployee.size() + ") ===");
        for (Employee e : listeEmployee) {
            System.out.println(e);
        }
        // On retourne une copie immuable pour protéger l'encapsulation :
        // l'appelant ne doit pas pouvoir ajouter/retirer un employé via cette méthode.
        return Collections.unmodifiableList(listeEmployee);
    }

    /**
     * Affiche/retourne la liste des voitures de l'entreprise.
     * Ne dépend d'aucune autre classe non terminée : utilise uniquement listeVoiture.
     *
     * @return une copie non modifiable de la liste des voitures
     */
    public List<Voiture> afficherListeVoitures() {
        if (listeVoiture == null || listeVoiture.isEmpty()) {
            System.out.println("Aucune voiture enregistrée pour le moment.");
            return Collections.emptyList();
        }
        System.out.println("=== Liste des voitures (" + listeVoiture.size() + ") ===");
        for (Voiture v : listeVoiture) {
            System.out.println(v);
        }
        return Collections.unmodifiableList(listeVoiture);
    }

    /**
     * Paie le salaire d'un employé donné.
     * Ne dépend d'aucune méthode externe non terminée : on lit simplement
     * le salaire déjà présent sur l'objet Employee (attribut déjà en place selon l'UML).
     *
     * Hypothèse : Employee possède un attribut "salaire" (double) avec
     * getter/setter générés par Lombok (getSalaire/setSalaire), conformément
     * à l'UML corrigé. À adapter si le nom réel diffère.
     *
     * @param e l'employé à payer
     * @return true si le paiement a été effectué, false sinon (employé invalide)
     */
    public boolean payerSalaireEmploye(Employee e) {
        // Validation défensive : on ne fait jamais confiance à un paramètre externe sans le vérifier.
        if (e == null) {
            System.out.println("Erreur : employé null, paiement impossible.");
            return false;
        }
        if (listeEmployee == null || !listeEmployee.contains(e)) {
            System.out.println("Erreur : cet employé ne fait pas partie de l'entreprise.");
            return false;
        }

        double salaire = e.getSalaire();
        if (salaire <= 0) {
            // On ne bloque pas forcément le paiement, mais on signale une anomalie probable
            // (salaire à 0 ou négatif n'a normalement pas de sens métier).
            System.out.println("Attention : salaire invalide (" + salaire + ") pour l'employé " + e.getId());
            return false;
        }

        // TODO (dépendance future) : une fois la classe Log/HistoriqueAction finalisée (Partie 3),
        // tracer ce paiement via ajouterLog("Paiement salaire de " + salaire + " à l'employé " + e.getId()).
        System.out.printf("Salaire de %.2f Ar versé à l'employé %s %s.%n",
                salaire, e.getNom(), e.getPrenom());

        return true;
    }

    /**
     * Calcule le bénéfice de l'entreprise pour un mois et une année donnés.
     *
     * BLOQUANT : dépend de Reservation.getPrixTotal() / estPayee() (Partie 1)
     * pour connaître les revenus, et d'un moyen d'accéder à toutes les réservations
     * du mois concerné (à clarifier avec l'équipe : où sont stockées les réservations
     * globalement ? Receptionniste.listeReservations ?).
     * Le calcul des charges (salaires) est lui prêt, car il ne dépend que de listeEmployee.
     */
    public double calculerBeneficeParMois(int mois, int annee) {
        validerMoisAnnee(mois, annee);

        // Partie "charges" : déjà réalisable car ne dépend que de nos propres données.
        double totalSalaires = 0.0;
        if (listeEmployee != null) {
            for (Employee e : listeEmployee) {
                totalSalaires += e.getSalaire();
            }
        }

        // Partie "revenus" : non implémentable tant que Reservation.getPrixTotal()/estPayee()
        // et l'accès à la liste globale des réservations ne sont pas confirmés par la Partie 1.
        throw new UnsupportedOperationException(
                "calculerBeneficeParMois() en attente de Reservation.getPrixTotal()/estPayee() (Partie 1) "
                        + "et d'un accès aux réservations du mois " + mois + "/" + annee
        );
    }

    /**
     * Retourne les trajets les plus populaires (les plus réservés).
     *
     * BLOQUANT : dépend de Reservation/PlanningVoyage (Partie 1) pour
     * compter le nombre de réservations par trajet.
     */
    public List<Trajet> getTrajetsPopulaires() {
        throw new UnsupportedOperationException(
                "getTrajetsPopulaires() en attente de Reservation/PlanningVoyage (Partie 1)"
        );
    }

    /**
     * Ajoute une entrée dans l'historique des actions (Log).
     *
     * BLOQUANT : dépend de la classe Log (Partie 3) et de son constructeur.
     * Il faudra aussi décider si "employe" doit être passé en paramètre,
     * car Log possède un attribut employe selon l'UML.
     */
    public void ajouterLog(String action) {
        throw new UnsupportedOperationException(
                "ajouterLog() en attente de la classe Log finalisée (Partie 3)"
        );
    }

    /**
     * Petite validation utilitaire pour éviter des appels avec des dates absurdes.
     * Évite de calculer un bénéfice pour un mois 13 ou une année négative par exemple.
     */
    private void validerMoisAnnee(int mois, int annee) {
        if (mois < 1 || mois > 12) {
            throw new IllegalArgumentException("Mois invalide : " + mois + " (doit être entre 1 et 12)");
        }
        if (annee < 2000) {
            throw new IllegalArgumentException("Année invalide : " + annee);
        }
    }
}