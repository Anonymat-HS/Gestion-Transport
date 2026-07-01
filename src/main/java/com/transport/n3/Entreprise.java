package com.transport.n3;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Getter
@Setter
public class Entreprise {
    private String id;
    private String nom;
    private String email;
    private String telephone;
    private List<Voiture> listeVoiture;
    private List<Employee> listeEmployee;

    // AJOUT (hypothèse à valider en équipe) : l'UML ne donne à Entreprise aucun moyen
    // d'accéder aux réservations existantes, alors que calculerBeneficeParMois() et
    // getTrajetsPopulaires() en ont absolument besoin. Deux solutions possibles :
    //   1) Entreprise garde sa propre liste (ce qu'on fait ici), alimentée par la Réceptionniste
    //      à chaque nouvelle réservation (ex: entreprise.ajouterReservation(r) à appeler
    //      depuis Receptionniste.enregistrerReservation()).
    //   2) Ou bien on passe la liste en paramètre à chaque appel de méthode.
    // On part sur l'option 1 ici, à confirmer avec l'équipe.
    private List<Reservation> listeReservations;

    // AJOUT : nécessaire pour ajouterLog(), puisque Entreprise *-- Log (composition) dans l'UML.
    private List<HistoriqueAction> listeLogs;

    public Entreprise(String id, String nom, String email, String telephone,
                      List<Voiture> listeVoiture, List<Employee> listeEmployee) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.listeVoiture = (listeVoiture != null) ? new ArrayList<>(listeVoiture) : new ArrayList<>();
        this.listeEmployee = (listeEmployee != null) ? new ArrayList<>(listeEmployee) : new ArrayList<>();
        this.listeReservations = new ArrayList<>();
        this.listeLogs = new ArrayList<>();
    }

    public List<Employee> afficherListeEmployes() {
        if (listeEmployee == null || listeEmployee.isEmpty()) {
            System.out.println("Aucun employé enregistré pour le moment.");
            return Collections.emptyList();
        }
        System.out.println("=== Liste des employés (" + listeEmployee.size() + ") ===");
        listeEmployee.forEach(System.out::println);
        return Collections.unmodifiableList(listeEmployee);
    }

    public List<Voiture> afficherListeVoitures() {
        if (listeVoiture == null || listeVoiture.isEmpty()) {
            System.out.println("Aucune voiture enregistrée pour le moment.");
            return Collections.emptyList();
        }
        System.out.println("=== Liste des voitures (" + listeVoiture.size() + ") ===");
        listeVoiture.forEach(System.out::println);
        return Collections.unmodifiableList(listeVoiture);
    }

    public boolean payerSalaireEmploye(Employee e) {
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
            System.out.println("Attention : salaire invalide (" + salaire + ") pour l'employé " + e.getId());
            return false;
        }
        System.out.printf("Salaire de %.2f Ar versé à l'employé %s %s.%n",
                salaire, e.getNom(), e.getPrenom());

        ajouterLog("Paiement salaire de " + salaire + " Ar à l'employé " + e.getId(), e);
        return true;
    }

    /**
     * À appeler par la Réceptionniste (Partie 3) à chaque nouvelle réservation,
     * pour que l'Entreprise puisse ensuite calculer bénéfices et trajets populaires.
     */
    public void ajouterReservation(Reservation r) {
        if (r == null) {
            return;
        }
        this.listeReservations.add(r);
    }

    /**
     * Calcule le bénéfice (revenus - charges salariales) pour un mois/année donnés.
     *
     * Hypothèses (à confirmer avec la Partie 1) :
     *  - Reservation.getPrixTotal() existe et retourne le prix figé de la réservation.
     *  - Reservation.estPayee() existe et indique si la réservation est réglée.
     *  - Reservation.getDateVoyage() retourne une LocalDate (ou équivalent),
     *    utilisée comme référence pour filtrer par mois/année.
     */
    public double calculerBeneficeParMois(int mois, int annee) {
        validerMoisAnnee(mois, annee);

        double totalSalaires = 0.0;
        if (listeEmployee != null) {
            for (Employee e : listeEmployee) {
                totalSalaires += e.getSalaire();
            }
        }

        double totalRevenus = 0.0;
        if (listeReservations != null) {
            for (Reservation r : listeReservations) {
                if (r == null || r.getDateVoyage() == null) {
                    continue;
                }
                boolean memeMois = r.getDateVoyage().getMonthValue() == mois
                        && r.getDateVoyage().getYear() == annee;
                if (memeMois && r.estPayee()) {
                    totalRevenus += r.getPrixTotal();
                }
            }
        }

        double benefice = totalRevenus - totalSalaires;

        System.out.printf("Bénéfice %02d/%d : revenus=%.2f, charges=%.2f, bénéfice=%.2f%n",
                mois, annee, totalRevenus, totalSalaires, benefice);

        return benefice;
    }

    /**
     * Retourne les trajets les plus réservés, du plus populaire au moins populaire.
     *
     * Hypothèses (à confirmer avec la Partie 1) :
     *  - Reservation.getPlanning() retourne un PlanningVoyage.
     *  - PlanningVoyage.getTrajet() retourne le Trajet associé.
     */
    public List<Trajet> getTrajetsPopulaires() {
        if (listeReservations == null || listeReservations.isEmpty()) {
            System.out.println("Aucune réservation disponible pour établir un classement.");
            return Collections.emptyList();
        }

        Map<Trajet, Long> compteurParTrajet = listeReservations.stream()
                .filter(r -> r != null && r.getPlanning() != null && r.getPlanning().getTrajet() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getPlanning().getTrajet(),
                        Collectors.counting()
                ));

        return compteurParTrajet.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .map(Entry::getKey)
                .collect(Collectors.toList());
    }

    public void ajouterLog(String action) {
        ajouterLog(action, null);
    }

    /**
     * Surcharge permettant d'associer un employé précis à l'action loggée
     * (utile par exemple pour payerSalaireEmploye()).
     *
     * Hypothèse (à confirmer avec la Partie 3) : Log possède un constructeur
     * (id, action, dateHeure, employe) — typiquement via @AllArgsConstructor.
     */
    public void ajouterLog(String action, Employee employe) {
        if (action == null || action.isBlank()) {
            System.out.println("Erreur : impossible de logger une action vide.");
            return;
        }
        // id à remplacer par un vrai générateur (UUID, séquence DB...) si l'équipe en a un
        String idLog = "LOG-" + (listeLogs.size() + 1);
        HistoriqueAction log = new HistoriqueAction(idLog, action, LocalDateTime.now(), employe);
        listeLogs.add(log);
        System.out.println("Log ajouté : " + action);
    }

    private void validerMoisAnnee(int mois, int annee) {
        if (mois < 1 || mois > 12) {
            throw new IllegalArgumentException("Mois invalide : " + mois + " (doit être entre 1 et 12)");
        }
        if (annee < 2000) {
            throw new IllegalArgumentException("Année invalide : " + annee);
        }
    }
}