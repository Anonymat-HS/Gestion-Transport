package com.transport.n3;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests — Classe Entreprise")
class EntrepriseTest {

    // =========================================================
    // Objets partagés entre tous les tests
    // =========================================================

    private Entreprise entreprise;
    private Employee employe1;
    private Employee employe2;
    private Voiture voiture1;
    private Voiture voiture2;

    @BeforeEach
    void setUp() {
        // Ces objets sont recréés proprement avant chaque test
        // pour éviter qu'un test pollue les suivants
        employe1 = new Receptionniste("E001", "Rakoto", "Jean",  "034000001", Sexe.MASCULIN, 500_000.0);
        employe2 = new Receptionniste("E002", "Rasoa",  "Marie", "034000002", Sexe.FEMININ,  450_000.0);
        voiture1 = new Voiture("V001", "1234-TBA", TypeVoiture.VIP,     null);
        voiture2 = new Voiture("V002", "5678-TBA", TypeVoiture.PREMIUM, null);

        entreprise = new Entreprise(
                "ENT001", "Transport Mada", "contact@transport.mg", "032000001",
                new ArrayList<>(List.of(voiture1, voiture2)),
                new ArrayList<>(List.of(employe1, employe2))
        );
    }

    // =========================================================
    // CONSTRUCTEUR
    // =========================================================

    @Nested
    @DisplayName("Constructeur")
    class ConstructeurTests {

        @Test
        @DisplayName("Création avec des listes valides")
        void constructeur_listesValides_attributsBienInitialises() {
            // Given : des listes non vides passées au constructeur

            // When : on accède aux attributs

            // Then : tout est bien initialisé
            assertEquals("ENT001", entreprise.getId());
            assertEquals("Transport Mada", entreprise.getNom());
            assertEquals(2, entreprise.getListeEmployee().size());
            assertEquals(2, entreprise.getListeVoiture().size());
            assertNotNull(entreprise.getListeReservations());
            assertNotNull(entreprise.getListeLogs());
            assertTrue(entreprise.getListeReservations().isEmpty());
            assertTrue(entreprise.getListeLogs().isEmpty());
        }

        @Test
        @DisplayName("Création avec des listes null ne plante pas")
        void constructeur_listesNull_creeListesVidesAuLieuDePlanter() {
            // Given : on passe null pour les deux listes

            // When
            Entreprise e = new Entreprise("ENT002", "Test", "a@b.mg", "000", null, null);

            // Then : pas d'exception, listes vides créées automatiquement
            assertNotNull(e.getListeVoiture());
            assertNotNull(e.getListeEmployee());
            assertTrue(e.getListeVoiture().isEmpty());
            assertTrue(e.getListeEmployee().isEmpty());
        }

        @Test
        @DisplayName("Modification de la liste externe après création ne modifie pas l'entreprise")
        void constructeur_listesCopiees_independanceDeLaListeOriginale() {
            // Given : une liste externe passée au constructeur
            List<Employee> listeExterne = new ArrayList<>(List.of(employe1));
            Entreprise e = new Entreprise("ENT003", "Test", "a@b.mg", "000", null, listeExterne);

            // When : on modifie la liste externe après coup
            listeExterne.add(employe2);

            // Then : l'entreprise n'est pas affectée (elle a sa propre copie)
            assertEquals(1, e.getListeEmployee().size());
        }
    }

    // =========================================================
    // afficherListeEmployes()
    // =========================================================

    @Nested
    @DisplayName("afficherListeEmployes()")
    class AfficherListeEmployesTests {

        @Test
        @DisplayName("Retourne tous les employés quand la liste est remplie")
        void afficherListeEmployes_listeNonVide_retourneTousLesEmployes() {
            // Given : entreprise avec 2 employés (setUp)

            // When
            List<Employee> result = entreprise.afficherListeEmployes();

            // Then
            assertEquals(2, result.size());
            assertTrue(result.contains(employe1));
            assertTrue(result.contains(employe2));
        }

        @Test
        @DisplayName("Retourne liste vide si aucun employé enregistré")
        void afficherListeEmployes_listeVide_retourneListeVide() {
            // Given : entreprise sans employés
            entreprise.setListeEmployee(new ArrayList<>());

            // When
            List<Employee> result = entreprise.afficherListeEmployes();

            // Then
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("La liste retournée ne peut pas être modifiée de l'extérieur")
        void afficherListeEmployes_listeRetournee_estNonModifiable() {
            // Given : entreprise avec employés (setUp)

            // When
            List<Employee> result = entreprise.afficherListeEmployes();

            // Then : l'encapsulation est protégée, toute tentative de modification plante
            assertThrows(UnsupportedOperationException.class, () -> result.add(employe1));
        }

        @Test
        @DisplayName("Ne plante pas si listeEmployee est null")
        void afficherListeEmployes_listeNull_retourneListeVideSansException() {
            // Given
            entreprise.setListeEmployee(null);

            // When + Then : aucune NullPointerException
            assertDoesNotThrow(() -> entreprise.afficherListeEmployes());
            assertTrue(entreprise.afficherListeEmployes().isEmpty());
        }
    }

    // =========================================================
    // afficherListeVoitures()
    // =========================================================

    @Nested
    @DisplayName("afficherListeVoitures()")
    class AfficherListeVoituresTests {

        @Test
        @DisplayName("Retourne toutes les voitures quand la liste est remplie")
        void afficherListeVoitures_listeNonVide_retourneToutesLesVoitures() {
            // Given : entreprise avec 2 voitures (setUp)

            // When
            List<Voiture> result = entreprise.afficherListeVoitures();

            // Then
            assertEquals(2, result.size());
            assertTrue(result.contains(voiture1));
            assertTrue(result.contains(voiture2));
        }

        @Test
        @DisplayName("Retourne liste vide si aucune voiture enregistrée")
        void afficherListeVoitures_listeVide_retourneListeVide() {
            // Given
            entreprise.setListeVoiture(new ArrayList<>());

            // When
            List<Voiture> result = entreprise.afficherListeVoitures();

            // Then
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("La liste retournée ne peut pas être modifiée de l'extérieur")
        void afficherListeVoitures_listeRetournee_estNonModifiable() {
            // Given : entreprise avec voitures (setUp)

            // When
            List<Voiture> result = entreprise.afficherListeVoitures();

            // Then
            assertThrows(UnsupportedOperationException.class, () -> result.add(voiture1));
        }

        @Test
        @DisplayName("Ne plante pas si listeVoiture est null")
        void afficherListeVoitures_listeNull_retourneListeVideSansException() {
            // Given
            entreprise.setListeVoiture(null);

            // When + Then
            assertDoesNotThrow(() -> entreprise.afficherListeVoitures());
        }
    }

    // =========================================================
    // payerSalaireEmploye()
    // =========================================================

    @Nested
    @DisplayName("payerSalaireEmploye()")
    class PayerSalaireEmployeTests {

        @Test
        @DisplayName("Paiement réussi pour un employé valide appartenant à l'entreprise")
        void payerSalaire_employeValideEtAppartientEntreprise_retourneTrue() {
            // Given : employe1 est dans l'entreprise avec un salaire de 500 000 Ar

            // When
            boolean result = entreprise.payerSalaireEmploye(employe1);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Echec si l'employé passé est null")
        void payerSalaire_employeNull_retourneFalse() {
            // Given : aucun employé

            // When
            boolean result = entreprise.payerSalaireEmploye(null);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Echec si l'employé n'appartient pas à l'entreprise")
        void payerSalaire_employeExterne_retourneFalse() {
            // Given : un employé qui n'est pas dans la liste de l'entreprise
            Employee inconnu = new Receptionniste("E999", "Inconnu", "X", "000", Sexe.MASCULIN, 300_000.0);

            // When
            boolean result = entreprise.payerSalaireEmploye(inconnu);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Echec si le salaire de l'employé est à 0")
        void payerSalaire_salaireZero_retourneFalse() {
            // Given : employé avec salaire = 0 (données corrompues ou erreur de saisie)
            Employee sansSalaire = new Receptionniste("E003", "Vide", "Sans", "000", Sexe.MASCULIN, 0.0);
            entreprise.getListeEmployee().add(sansSalaire);

            // When
            boolean result = entreprise.payerSalaireEmploye(sansSalaire);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Echec si le salaire est négatif")
        void payerSalaire_salaireNegatif_retourneFalse() {
            // Given : employé avec salaire négatif (erreur de données)
            Employee salaireNegatif = new Receptionniste("E004", "Neg", "Test", "000", Sexe.FEMININ, -1000.0);
            entreprise.getListeEmployee().add(salaireNegatif);

            // When
            boolean result = entreprise.payerSalaireEmploye(salaireNegatif);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Un log est automatiquement créé après un paiement réussi")
        void payerSalaire_paiementReussi_logAjouteAutomatiquement() {
            // Given : liste de logs initialement vide
            int nbLogsAvant = entreprise.getListeLogs().size();

            // When
            entreprise.payerSalaireEmploye(employe1);

            // Then : un log de traçabilité a été ajouté
            assertEquals(nbLogsAvant + 1, entreprise.getListeLogs().size());
        }

        @Test
        @DisplayName("Payer tous les employés un par un fonctionne correctement")
        void payerSalaire_tousLesEmployes_tousPayesAvecSucces() {
            // Given : 2 employés valides dans l'entreprise

            // When + Then : les deux paiements réussissent
            assertTrue(entreprise.payerSalaireEmploye(employe1));
            assertTrue(entreprise.payerSalaireEmploye(employe2));
        }
    }

    // =========================================================
    // calculerBeneficeParMois()
    // HYPOTHÈSES (Partie 1) :
    //  - Reservation.getPrixTotal() : double
    //  - Reservation.estPayee()     : boolean
    //  - Reservation.getDateVoyage(): LocalDate
    // =========================================================

    @Nested
    @DisplayName("calculerBeneficeParMois()")
    class CalculerBeneficeParMoisTests {

        // Méthode utilitaire : construit une réservation simplifiée
        // À REMPLACER par de vrais objets Reservation une fois la Partie 1 finalisée
        private Reservation creerReservation(double prix, boolean payee, LocalDate date) {
            Reservation r = new Reservation();
            r.setPrixTotal(prix);
            r.setDateVoyage(date);
            if (payee) {
                r.ajouterPaiement(new PaiementEspece("P", LocalDateTime.now(), prix, "Client", prix, 0));
            }
            return r;
        }

        @Test
        @DisplayName("Bénéfice correct = revenus payés - total salaires du mois")
        void calculerBenefice_reservationsPayees_beneficeCorrect() {
            // Given : 2 réservations payées en juin 2025
            // Revenus = 300 000 + 500 000 = 800 000 Ar
            // Charges = 500 000 + 450 000 = 950 000 Ar
            entreprise.ajouterReservation(creerReservation(300_000.0, true, LocalDate.of(2025, 6, 10)));
            entreprise.ajouterReservation(creerReservation(500_000.0, true, LocalDate.of(2025, 6, 20)));

            // When
            double benefice = entreprise.calculerBeneficeParMois(6, 2025);

            // Then : 800 000 - 950 000 = -150 000
            assertEquals(-150_000.0, benefice, 0.01);
        }

        @Test
        @DisplayName("Les réservations non payées ne comptent pas dans les revenus")
        void calculerBenefice_reservationNonPayee_nonCompteeDansRevenus() {
            // Given : une réservation faite mais pas encore réglée
            entreprise.ajouterReservation(creerReservation(300_000.0, false, LocalDate.of(2025, 6, 15)));

            // When
            double benefice = entreprise.calculerBeneficeParMois(6, 2025);

            // Then : revenus = 0, charges = 950 000 → bénéfice = -950 000
            assertEquals(-950_000.0, benefice, 0.01);
        }

        @Test
        @DisplayName("Les réservations d'autres mois ne sont pas comptées")
        void calculerBenefice_reservationsAutresMois_ignorees() {
            // Given : réservations en mai et juillet, on demande juin
            entreprise.ajouterReservation(creerReservation(300_000.0, true, LocalDate.of(2025, 5, 10)));
            entreprise.ajouterReservation(creerReservation(200_000.0, true, LocalDate.of(2025, 7, 10)));

            // When
            double benefice = entreprise.calculerBeneficeParMois(6, 2025);

            // Then : revenus juin = 0 → bénéfice = -950 000
            assertEquals(-950_000.0, benefice, 0.01);
        }

        @Test
        @DisplayName("Bénéfice = charges négatives si aucune réservation ce mois")
        void calculerBenefice_aucuneReservation_retourneChargeSalarialeNegative() {
            // Given : aucune réservation ajoutée

            // When
            double benefice = entreprise.calculerBeneficeParMois(6, 2025);

            // Then
            assertEquals(-950_000.0, benefice, 0.01);
        }

        @Test
        @DisplayName("Mois 0 lève une IllegalArgumentException")
        void calculerBenefice_moisZero_leveIllegalArgumentException() {
            // Given : mois invalide (0)

            // When + Then
            assertThrows(IllegalArgumentException.class,
                    () -> entreprise.calculerBeneficeParMois(0, 2025));
        }

        @Test
        @DisplayName("Mois 13 lève une IllegalArgumentException")
        void calculerBenefice_mois13_leveIllegalArgumentException() {
            // Given : mois invalide (13)

            // When + Then
            assertThrows(IllegalArgumentException.class,
                    () -> entreprise.calculerBeneficeParMois(13, 2025));
        }

        @Test
        @DisplayName("Année avant 2000 lève une IllegalArgumentException")
        void calculerBenefice_anneeInvalide_leveIllegalArgumentException() {
            // Given : année invalide (1999)

            // When + Then
            assertThrows(IllegalArgumentException.class,
                    () -> entreprise.calculerBeneficeParMois(6, 1999));
        }

        @Test
        @DisplayName("Mix payées et non payées : seules les payées comptent")
        void calculerBenefice_mixPayeesEtNonPayees_seulementPayeesComptees() {
            // Given : 1 payée à 600 000 et 1 non payée à 1 000 000 en juin
            entreprise.ajouterReservation(creerReservation(600_000.0, true,  LocalDate.of(2025, 6, 5)));
            entreprise.ajouterReservation(creerReservation(1_000_000.0, false, LocalDate.of(2025, 6, 15)));

            // When
            double benefice = entreprise.calculerBeneficeParMois(6, 2025);

            // Then : revenus = 600 000, charges = 950 000 → bénéfice = -350 000
            assertEquals(-350_000.0, benefice, 0.01);
        }
    }

    // =========================================================
    // getTrajetsPopulaires()
    // HYPOTHÈSES :
    //  - Reservation.getVoyage() retourne un Voyage
    //  - Voyage.getTrajet()      retourne un Trajet
    //  - Trajet a equals()/hashCode() basé sur son id
    // =========================================================

    @Nested
    @DisplayName("getTrajetsPopulaires()")
    class GetTrajetsPopulairesTests {

        private Trajet trajetA;
        private Trajet trajetB;
        private Trajet trajetC;

        @BeforeEach
        void setUpTrajets() {
            trajetA = new Trajet("T001", Ville.ANTANANARIVO, Ville.TOAMASINA,  240, 25_000.0);
            trajetB = new Trajet("T002", Ville.ANTANANARIVO, Ville.MAHAJANGA,  480, 30_000.0);
            trajetC = new Trajet("T003", Ville.ANTANANARIVO, Ville.FIANARANTSOA, 300, 20_000.0);
        }

        // Utilitaire : crée une réservation pointant vers un trajet donné
        private Reservation creerReservationPourTrajet(Trajet trajet) {
            Voyage voyage = new Voyage();
            voyage.setTrajet(trajet);
            Reservation r = new Reservation();
            r.setVoyage(voyage);
            return r;
        }

        @Test
        @DisplayName("Le trajet le plus réservé apparaît en premier")
        void getTrajetsPopulaires_plusieursResas_triDecroissant() {
            // Given : trajetA réservé 3 fois, trajetB 2 fois, trajetC 1 fois
            entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));
            entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));
            entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));
            entreprise.ajouterReservation(creerReservationPourTrajet(trajetB));
            entreprise.ajouterReservation(creerReservationPourTrajet(trajetB));
            entreprise.ajouterReservation(creerReservationPourTrajet(trajetC));

            // When
            List<Trajet> result = entreprise.getTrajetsPopulaires();

            // Then : ordre attendu A → B → C
            assertEquals(trajetA, result.get(0));
            assertEquals(trajetB, result.get(1));
            assertEquals(trajetC, result.get(2));
        }

        @Test
        @DisplayName("Retourne liste vide si aucune réservation")
        void getTrajetsPopulaires_aucuneReservation_retourneListeVide() {
            // Given : aucune réservation

            // When
            List<Trajet> result = entreprise.getTrajetsPopulaires();

            // Then
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Réservations avec voyage null sont ignorées sans plantage")
        void getTrajetsPopulaires_voyageNull_ignoreSansException() {
            // Given : une réservation mal formée (voyage non défini)
            Reservation rSansVoyage = new Reservation();

            // When
            entreprise.ajouterReservation(rSansVoyage);

            // Then : aucune NullPointerException
            assertDoesNotThrow(() -> entreprise.getTrajetsPopulaires());
        }

        @Test
        @DisplayName("Un seul trajet réservé : retourné seul dans la liste")
        void getTrajetsPopulaires_unSeulTrajet_retourneListeAvecUnSeulElement() {
            // Given : toutes les réservations sur le même trajet
            entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));
            entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));

            // When
            List<Trajet> result = entreprise.getTrajetsPopulaires();

            // Then
            assertEquals(1, result.size());
            assertEquals(trajetA, result.get(0));
        }
    }

    // =========================================================
    // ajouterLog()
    // HYPOTHÈSE : HistoriqueAction(id, action, dateHeure, employe)
    // =========================================================

    @Nested
    @DisplayName("ajouterLog()")
    class AjouterLogTests {

        @Test
        @DisplayName("Log correctement ajouté dans listeLogs")
        void ajouterLog_actionValide_logAjoute() {
            // Given : liste de logs vide

            // When
            entreprise.ajouterLog("Connexion admin");

            // Then
            assertEquals(1, entreprise.getListeLogs().size());
            assertEquals("Connexion admin", entreprise.getListeLogs().get(0).getAction());
        }

        @Test
        @DisplayName("Plusieurs logs s'accumulent dans l'ordre d'ajout")
        void ajouterLog_plusieursActions_accumulationDansOrdre() {
            // Given : aucun log

            // When
            entreprise.ajouterLog("Action 1");
            entreprise.ajouterLog("Action 2");
            entreprise.ajouterLog("Action 3");

            // Then
            assertEquals(3, entreprise.getListeLogs().size());
            assertEquals("Action 1", entreprise.getListeLogs().get(0).getAction());
            assertEquals("Action 3", entreprise.getListeLogs().get(2).getAction());
        }

        @Test
        @DisplayName("Action null n'est pas loggée et ne plante pas")
        void ajouterLog_actionNull_aucunLogAjoute() {
            // Given : action null

            // When + Then
            assertDoesNotThrow(() -> entreprise.ajouterLog(null));
            assertTrue(entreprise.getListeLogs().isEmpty());
        }

        @Test
        @DisplayName("Action vide ou espaces n'est pas loggée et ne plante pas")
        void ajouterLog_actionVide_aucunLogAjoute() {
            // Given : action composée uniquement d'espaces

            // When + Then
            assertDoesNotThrow(() -> entreprise.ajouterLog("   "));
            assertTrue(entreprise.getListeLogs().isEmpty());
        }

        @Test
        @DisplayName("Log avec employé : l'employé est bien référencé dans le log")
        void ajouterLog_avecEmploye_employe_bienAssocie() {
            // Given : une action associée à employe1

            // When
            entreprise.ajouterLog("Modification planning", employe1);

            // Then
            HistoriqueAction log = entreprise.getListeLogs().get(0);
            assertEquals(employe1, log.getEmployee());
            assertEquals("Modification planning", log.getAction());
        }

        @Test
        @DisplayName("Log sans employé : le champ employe est null")
        void ajouterLog_sansEmploye_employeEstNull() {
            // Given : surcharge simple sans employé

            // When
            entreprise.ajouterLog("Ouverture système");

            // Then
            assertNull(entreprise.getListeLogs().get(0).getEmployee());
        }
    }

    // =========================================================
    // ajouterReservation()
    // =========================================================

    @Nested
    @DisplayName("ajouterReservation()")
    class AjouterReservationTests {

        @Test
        @DisplayName("Réservation valide correctement ajoutée")
        void ajouterReservation_reservationValide_ajouteeDansListe() {
            // Given
            Reservation r = new Reservation();

            // When
            entreprise.ajouterReservation(r);

            // Then
            assertEquals(1, entreprise.getListeReservations().size());
            assertTrue(entreprise.getListeReservations().contains(r));
        }

        @Test
        @DisplayName("Réservation null ignorée sans plantage")
        void ajouterReservation_null_ignoreeSansException() {
            // Given : on passe null volontairement

            // When + Then
            assertDoesNotThrow(() -> entreprise.ajouterReservation(null));
            assertTrue(entreprise.getListeReservations().isEmpty());
        }

        @Test
        @DisplayName("Plusieurs réservations s'accumulent correctement")
        void ajouterReservation_plusieursResas_toutesPresentes() {
            // Given
            Reservation r1 = new Reservation();
            Reservation r2 = new Reservation();
            Reservation r3 = new Reservation();

            // When
            entreprise.ajouterReservation(r1);
            entreprise.ajouterReservation(r2);
            entreprise.ajouterReservation(r3);

            // Then
            assertEquals(3, entreprise.getListeReservations().size());
        }
    }
}
