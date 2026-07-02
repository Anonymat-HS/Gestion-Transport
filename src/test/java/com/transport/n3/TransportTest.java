package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class TransportTest {


    // ============================================================================
// AdminTest
// NOTE : le setUp() de cette classe a été reconstruit (le fragment fourni
// n'incluait pas la déclaration de classe complète) — à vérifier.
// ============================================================================
    @Nested
    class AdminTest {

        private Admin admin;
        private Entreprise entreprise;
        private Employee employe1;
        private Employee employe2;

        @BeforeEach
        void setUp() {
            admin = new Admin("A001", "Rakoto", "Admin", "034000001", Sexe.MASCULIN, 800_000.0);

            employe1 = new Receptionniste("E001", "Rasoa", "Marie", "034000002", Sexe.FEMININ, 500_000.0);
            employe2 = new Receptionniste("E002", "Rabe", "Paul", "034000003", Sexe.MASCULIN, 450_000.0);

            List<Employee> listeEmployees = new ArrayList<>(List.of(employe1, employe2));
            entreprise = new Entreprise("ENT001", "Transport Mada", "contact@transport.mg", "032000001", null, listeEmployees);
        }

        @Test
        @DisplayName("Constructeur initialise correctement les attributs")
        void constructeur_attributsValides_initialisationCorrecte() {
            // Given + When : création dans setUp()

            // Then
            assertEquals("A001", admin.getId());
            assertEquals("Rakoto", admin.getNom());
            assertEquals("Admin", admin.getPrenom());
            assertEquals("034000001", admin.getTelephone());
            assertEquals(Sexe.MASCULIN, admin.getSexe());
            assertEquals(800_000.0, admin.getSalaire(), 0.01);
            assertNotNull(admin.getListeEmployee());
            assertTrue(admin.getListeEmployee().isEmpty());
        }

        @Test
        @DisplayName("getListeEmployee retourne la liste interne de l'admin")
        void getListeEmployee_listeVide_retourneListeVide() {
            // Given : admin créé sans employés dans sa liste

            // When
            List<Employee> result = admin.getListeEmployee();

            // Then
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("setListeEmployee modifie la liste interne")
        void setListeEmployee_listeNonVide_listeMiseAJour() {
            // Given
            List<Employee> nouvelleListe = new ArrayList<>(List.of(employe1));

            // When
            admin.setListeEmployee(nouvelleListe);

            // Then
            assertEquals(1, admin.getListeEmployee().size());
            assertTrue(admin.getListeEmployee().contains(employe1));
        }

        @Test
        @DisplayName("consulterBenefice délègue à entreprise.calculerBeneficeParMois")
        void consulterBenefice_moisEtAnneeValides_retourneBenefice() {
            // Given : entreprise avec 2 employés (salaires 500k + 450k = 950k), pas de réservation

            // When
            double benefice = admin.consulterBenefice(entreprise, 6, 2025);

            // Then : pas de revenus → bénéfice = -950 000
            assertEquals(-950_000.0, benefice, 0.01);
        }

        @Test
        @DisplayName("afficherListeEmployee retourne la liste des employés de l'entreprise")
        void afficherListeEmployee_entrepriseAvecEmployes_retourneListeEntreprise() {
            // Given : entreprise avec 2 employés

            // When
            List<Employee> result = admin.afficherListeEmployee(entreprise);

            // Then
            assertEquals(2, result.size());
            assertTrue(result.contains(employe1));
            assertTrue(result.contains(employe2));
        }

        @Test
        @DisplayName("afficherListeEmployee retourne liste vide si entreprise sans employés")
        void afficherListeEmployee_entrepriseSansEmployes_retourneListeVide() {
            // Given
            entreprise.setListeEmployee(new ArrayList<>());

            // When
            List<Employee> result = admin.afficherListeEmployee(entreprise);

            // Then
            assertTrue(result.isEmpty());
        }
    }


    @Nested
    @DisplayName("Bagage")
    class BagageTest {

        private Bagage bagage;

        @BeforeEach
        void setUp() {
            bagage = new Bagage("B1", "Valise cabine", 15.0, 10.0);
        }

        @Test
        @DisplayName("Constructor initializes all fields correctly")
        void testConstructor() {
            // GIVEN
            String id = "B2";
            String description = "Sac voyage";
            double poids = 25.0;
            double prixSupp = 5000.0;

            // WHEN
            Bagage b = new Bagage(id, description, poids, prixSupp);

            // THEN
            assertEquals(id, b.getId());
            assertEquals(description, b.getDescription());
            assertEquals(poids, b.getPoids());
            double expectedSurcharge = (poids - 20.0) * 500.0;
            assertEquals(expectedSurcharge, b.getPrixSupplementaire(), 0.001);
        }

        @Test
        @DisplayName("Constructor recalculates prixSupplementaire via calculSurcharge()")
        void testConstructorRecalculatesPrixSupplementaire() {
            // GIVEN
            double poids = 30.0;
            double prixSuppProvided = 9999.0;

            // WHEN
            Bagage b = new Bagage("B3", "Valise", poids, prixSuppProvided);

            // THEN
            double expected = (poids - 20.0) * 500.0;
            assertEquals(expected, b.getPrixSupplementaire(), 0.001);
        }

        @Test
        @DisplayName("Getters return correct values")
        void testGetters() {
            // GIVEN
            Bagage b = new Bagage("B10", "Sac à dos", 22.0, 1000.0);

            // WHEN / THEN
            assertEquals("B10", b.getId());
            assertEquals("Sac à dos", b.getDescription());
            assertEquals(22.0, b.getPoids());
        }

        @Test
        @DisplayName("Setters update fields correctly")
        void testSetters() {
            // GIVEN
            bagage.setId("B-EDIT");
            bagage.setDescription("Nouvelle description");
            bagage.setPoids(18.0);
            bagage.setPrixSupplementaire(0.0);

            // THEN
            assertEquals("B-EDIT", bagage.getId());
            assertEquals("Nouvelle description", bagage.getDescription());
            assertEquals(18.0, bagage.getPoids());
            assertEquals(0.0, bagage.getPrixSupplementaire());
        }

        @Test
        @DisplayName("calculSurcharge returns 0 when poids <= 20.0")
        void testCalculSurchargeUnderLimit() {
            // GIVEN
            Bagage b = new Bagage("B4", "Petit sac", 15.0, 0.0);

            // WHEN
            double surcharge = b.calculSurcharge();

            // THEN
            assertEquals(0.0, surcharge, 0.001);
        }

        @Test
        @DisplayName("calculSurcharge returns (poids - 20) * 500 when poids > 20.0")
        void testCalculSurchargeOverLimit() {
            // GIVEN
            double poids = 25.0;
            Bagage b = new Bagage("B5", "Grosse valise", poids, 0.0);

            // WHEN
            double surcharge = b.calculSurcharge();

            // THEN
            double expected = (poids - 20.0) * 500.0;
            assertEquals(expected, surcharge, 0.001);
        }

        @Test
        @DisplayName("calculSurcharge handles boundary at exactly 20.0")
        void testCalculSurchargeAtBoundary() {
            // GIVEN
            Bagage b = new Bagage("B6", "Valise limite", 20.0, 0.0);

            // WHEN
            double surcharge = b.calculSurcharge();

            // THEN
            assertEquals(0.0, surcharge, 0.001);
        }

        @Test
        @DisplayName("calculSurcharge handles zero poids")
        void testCalculSurchargeZeroPoids() {
            // GIVEN
            Bagage b = new Bagage("B7", "Vide", 0.0, 0.0);

            // WHEN
            double surcharge = b.calculSurcharge();

            // THEN
            assertEquals(0.0, surcharge, 0.001);
        }

        @Test
        @DisplayName("Constructor accepts null id")
        void testConstructorNullId() {
            // GIVEN
            Bagage b = new Bagage(null, "Test", 10.0, 0.0);

            // THEN
            assertNull(b.getId());
        }

        @Test
        @DisplayName("Constructor accepts null description")
        void testConstructorNullDescription() {
            // GIVEN
            Bagage b = new Bagage("B8", null, 10.0, 0.0);

            // THEN
            assertNull(b.getDescription());
        }
    }


    @Nested
    @DisplayName("Tests - Banque enum")
    class BanqueTest {

        @Test
        @DisplayName("Banque contient 2 valeurs")
        void banque_contient2valeurs() {
            // GIVEN
            Banque[] values = Banque.values();
            // WHEN & THEN
            assertEquals(2, values.length);
        }

        @Test
        @DisplayName("Banque contient BRED et BOA")
        void banque_contientValeursAttendues() {
            // GIVEN / WHEN / THEN
            assertTrue(contains(Banque.BRED));
            assertTrue(contains(Banque.BOA));
        }

        @Test
        @DisplayName("BRED name() retourne BRED")
        void bred_name() {
            // GIVEN
            Banque banque = Banque.BRED;
            // WHEN
            String name = banque.name();
            // THEN
            assertEquals("BRED", name);
        }

        @Test
        @DisplayName("BOA name() retourne BOA")
        void boa_name() {
            // GIVEN
            Banque banque = Banque.BOA;
            // WHEN
            String name = banque.name();
            // THEN
            assertEquals("BOA", name);
        }

        private boolean contains(Banque target) {
            for (Banque b : Banque.values()) {
                if (b == target) return true;
            }
            return false;
        }
    }


    // ============================================================================
// ChauffeurTest
// NOTE : le setUp() de cette classe a été reconstruit (le fragment fourni
// référençait "chauffeur", "voyage1", "voyage2" sans les déclarer) — à vérifier,
// notamment les dates utilisées pour les tests de pasAuRepos().
// ============================================================================
    @Nested
    class ChauffeurTest {

        private Chauffeur chauffeur;
        private Voyage voyage1;
        private Voyage voyage2;

        @BeforeEach
        void setUp() {
            chauffeur = new Chauffeur("C001", "Rakoto", "Jean", "0341111111", Sexe.MASCULIN, 600_000.0, "B");

            Trajet trajet = new Trajet("T1", Ville.ANTANANARIVO, Ville.TOAMASINA, 8, 50_000.0);
            Voiture voiture = new Voiture("V001", "1234-TBA", TypeVoiture.LITE, chauffeur);

            voyage1 = new Voyage(
                    "VY1",
                    LocalDateTime.of(2025, 6, 1, 8, 0),
                    LocalDateTime.of(2025, 6, 1, 16, 0),
                    trajet,
                    voiture,
                    chauffeur
            );

            voyage2 = new Voyage(
                    "VY2",
                    LocalDateTime.of(2025, 6, 5, 8, 0),
                    LocalDateTime.of(2025, 6, 5, 16, 0),
                    trajet,
                    voiture,
                    chauffeur
            );
        }

        @Test
        @DisplayName("Constructeur initialise correctement les attributs")
        void constructeur_attributsValides_initialisationCorrecte() {
            // Given + When : création dans setUp()

            // Then
            assertEquals("C001", chauffeur.getId());
            assertEquals("Rakoto", chauffeur.getNom());
            assertEquals("Jean", chauffeur.getPrenom());
            assertEquals("0341111111", chauffeur.getTelephone());
            assertEquals(Sexe.MASCULIN, chauffeur.getSexe());
            assertEquals(600_000.0, chauffeur.getSalaire(), 0.01);
            assertEquals("B", chauffeur.getPermis());
            assertNotNull(chauffeur.getCalendrierDeVoyage());
            assertTrue(chauffeur.getCalendrierDeVoyage().isEmpty());
        }

        @Test
        @DisplayName("getNbreTotalVoyage retourne 0 quand aucun voyage")
        void getNbreTotalVoyage_aucunVoyage_retourneZero() {
            // Given : calendrier vide

            // When
            int result = chauffeur.getNbreTotalVoyage();

            // Then
            assertEquals(0, result);
        }

        @Test
        @DisplayName("getNbreTotalVoyage retourne le bon nombre après ajout de voyages")
        void getNbreTotalVoyage_apresAjoutVoyages_retourneNombreCorrect() {
            // Given
            chauffeur.ajouterVoyage(voyage1);
            chauffeur.ajouterVoyage(voyage2);

            // When
            int result = chauffeur.getNbreTotalVoyage();

            // Then
            assertEquals(2, result);
        }

        @Test
        @DisplayName("ajouterVoyage ajoute bien un voyage au calendrier")
        void ajouterVoyage_voyageValide_ajouteAuCalendrier() {
            // Given
            assertTrue(chauffeur.getCalendrierDeVoyage().isEmpty());

            // When
            chauffeur.ajouterVoyage(voyage1);

            // Then
            assertEquals(1, chauffeur.getCalendrierDeVoyage().size());
            assertEquals(voyage1, chauffeur.getCalendrierDeVoyage().get(0));
        }

        @Test
        @DisplayName("pasAuRepos retourne true si le chauffeur voyage ce jour-là")
        void pasAuRepos_datePendantVoyage_retourneTrue() {
            // Given
            chauffeur.ajouterVoyage(voyage1);

            // When
            boolean result = chauffeur.pasAuRepos(LocalDate.of(2025, 6, 1));

            // Then : 1er juin 2025 est dans l'intervalle du voyage1
            assertTrue(result);
        }

        @Test
        @DisplayName("pasAuRepos retourne false si le chauffeur est libre ce jour-là")
        void pasAuRepos_dateHorsVoyage_retourneFalse() {
            // Given
            chauffeur.ajouterVoyage(voyage1);

            // When
            boolean result = chauffeur.pasAuRepos(LocalDate.of(2025, 6, 3));

            // Then : 3 juin n'est pas dans l'intervalle
            assertFalse(result);
        }

        @Test
        @DisplayName("pasAuRepos retourne false si aucun voyage planifié")
        void pasAuRepos_calendrierVide_retourneFalse() {
            // Given : aucun voyage

            // When
            boolean result = chauffeur.pasAuRepos(LocalDate.of(2025, 6, 1));

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("pasAuRepos retourne true pour la date de départ exacte")
        void pasAuRepos_dateDepartExacte_retourneTrue() {
            // Given
            chauffeur.ajouterVoyage(voyage1);

            // When
            boolean result = chauffeur.pasAuRepos(LocalDate.of(2025, 6, 1));

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("setPermis met à jour le permis")
        void setPermis_nouveauPermis_permisModifie() {
            // Given
            assertEquals("B", chauffeur.getPermis());

            // When
            chauffeur.setPermis("C");

            // Then
            assertEquals("C", chauffeur.getPermis());
        }
    }


    @DisplayName("Tests — Classe Entreprise")
    class EntrepriseTest {

        private Entreprise entreprise;
        private Employee employe1;
        private Employee employe2;
        private Voiture voiture1;
        private Voiture voiture2;

        @BeforeEach
        void setUp() {
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

        @Nested
        @DisplayName("Constructeur")
        class ConstructeurTests {

            @Test
            @DisplayName("Création avec des listes valides")
            void constructeur_listesValides_attributsBienInitialises() {
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
                Entreprise e = new Entreprise("ENT002", "Test", "a@b.mg", "000", null, null);

                assertNotNull(e.getListeVoiture());
                assertNotNull(e.getListeEmployee());
                assertTrue(e.getListeVoiture().isEmpty());
                assertTrue(e.getListeEmployee().isEmpty());
            }

            @Test
            @DisplayName("Modification de la liste externe après création ne modifie pas l'entreprise")
            void constructeur_listesCopiees_independanceDeLaListeOriginale() {
                List<Employee> listeExterne = new ArrayList<>(List.of(employe1));
                Entreprise e = new Entreprise("ENT003", "Test", "a@b.mg", "000", null, listeExterne);

                listeExterne.add(employe2);

                assertEquals(1, e.getListeEmployee().size());
            }
        }

        @Nested
        @DisplayName("afficherListeEmployes()")
        class AfficherListeEmployesTests {

            @Test
            @DisplayName("Retourne tous les employés quand la liste est remplie")
            void afficherListeEmployes_listeNonVide_retourneTousLesEmployes() {
                List<Employee> result = entreprise.afficherListeEmployes();

                assertEquals(2, result.size());
                assertTrue(result.contains(employe1));
                assertTrue(result.contains(employe2));
            }

            @Test
            @DisplayName("Retourne liste vide si aucun employé enregistré")
            void afficherListeEmployes_listeVide_retourneListeVide() {
                entreprise.setListeEmployee(new ArrayList<>());

                List<Employee> result = entreprise.afficherListeEmployes();

                assertTrue(result.isEmpty());
            }

            @Test
            @DisplayName("La liste retournée ne peut pas être modifiée de l'extérieur")
            void afficherListeEmployes_listeRetournee_estNonModifiable() {
                List<Employee> result = entreprise.afficherListeEmployes();

                assertThrows(UnsupportedOperationException.class, () -> result.add(employe1));
            }

            @Test
            @DisplayName("Ne plante pas si listeEmployee est null")
            void afficherListeEmployes_listeNull_retourneListeVideSansException() {
                entreprise.setListeEmployee(null);

                assertDoesNotThrow(() -> entreprise.afficherListeEmployes());
                assertTrue(entreprise.afficherListeEmployes().isEmpty());
            }
        }

        @Nested
        @DisplayName("afficherListeVoitures()")
        class AfficherListeVoituresTests {

            @Test
            @DisplayName("Retourne toutes les voitures quand la liste est remplie")
            void afficherListeVoitures_listeNonVide_retourneToutesLesVoitures() {
                List<Voiture> result = entreprise.afficherListeVoitures();

                assertEquals(2, result.size());
                assertTrue(result.contains(voiture1));
                assertTrue(result.contains(voiture2));
            }

            @Test
            @DisplayName("Retourne liste vide si aucune voiture enregistrée")
            void afficherListeVoitures_listeVide_retourneListeVide() {
                entreprise.setListeVoiture(new ArrayList<>());

                List<Voiture> result = entreprise.afficherListeVoitures();

                assertTrue(result.isEmpty());
            }

            @Test
            @DisplayName("La liste retournée ne peut pas être modifiée de l'extérieur")
            void afficherListeVoitures_listeRetournee_estNonModifiable() {
                List<Voiture> result = entreprise.afficherListeVoitures();

                assertThrows(UnsupportedOperationException.class, () -> result.add(voiture1));
            }

            @Test
            @DisplayName("Ne plante pas si listeVoiture est null")
            void afficherListeVoitures_listeNull_retourneListeVideSansException() {
                entreprise.setListeVoiture(null);

                assertDoesNotThrow(() -> entreprise.afficherListeVoitures());
            }
        }

        @Nested
        @DisplayName("payerSalaireEmploye()")
        class PayerSalaireEmployeTests {

            @Test
            @DisplayName("Paiement réussi pour un employé valide appartenant à l'entreprise")
            void payerSalaire_employeValideEtAppartientEntreprise_retourneTrue() {
                boolean result = entreprise.payerSalaireEmploye(employe1);

                assertTrue(result);
            }

            @Test
            @DisplayName("Echec si l'employé passé est null")
            void payerSalaire_employeNull_retourneFalse() {
                boolean result = entreprise.payerSalaireEmploye(null);

                assertFalse(result);
            }

            @Test
            @DisplayName("Echec si l'employé n'appartient pas à l'entreprise")
            void payerSalaire_employeExterne_retourneFalse() {
                Employee inconnu = new Receptionniste("E999", "Inconnu", "X", "000", Sexe.MASCULIN, 300_000.0);

                boolean result = entreprise.payerSalaireEmploye(inconnu);

                assertFalse(result);
            }

            @Test
            @DisplayName("Echec si le salaire de l'employé est à 0")
            void payerSalaire_salaireZero_retourneFalse() {
                Employee sansSalaire = new Receptionniste("E003", "Vide", "Sans", "000", Sexe.MASCULIN, 0.0);
                entreprise.getListeEmployee().add(sansSalaire);

                boolean result = entreprise.payerSalaireEmploye(sansSalaire);

                assertFalse(result);
            }

            @Test
            @DisplayName("Echec si le salaire est négatif")
            void payerSalaire_salaireNegatif_retourneFalse() {
                Employee salaireNegatif = new Receptionniste("E004", "Neg", "Test", "000", Sexe.FEMININ, -1000.0);
                entreprise.getListeEmployee().add(salaireNegatif);

                boolean result = entreprise.payerSalaireEmploye(salaireNegatif);

                assertFalse(result);
            }

            @Test
            @DisplayName("Un log est automatiquement créé après un paiement réussi")
            void payerSalaire_paiementReussi_logAjouteAutomatiquement() {
                int nbLogsAvant = entreprise.getListeLogs().size();

                entreprise.payerSalaireEmploye(employe1);

                assertEquals(nbLogsAvant + 1, entreprise.getListeLogs().size());
            }

            @Test
            @DisplayName("Payer tous les employés un par un fonctionne correctement")
            void payerSalaire_tousLesEmployes_tousPayesAvecSucces() {
                assertTrue(entreprise.payerSalaireEmploye(employe1));
                assertTrue(entreprise.payerSalaireEmploye(employe2));
            }
        }

        @Nested
        @DisplayName("calculerBeneficeParMois()")
        class CalculerBeneficeParMoisTests {

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
                entreprise.ajouterReservation(creerReservation(300_000.0, true, LocalDate.of(2025, 6, 10)));
                entreprise.ajouterReservation(creerReservation(500_000.0, true, LocalDate.of(2025, 6, 20)));

                double benefice = entreprise.calculerBeneficeParMois(6, 2025);

                assertEquals(-150_000.0, benefice, 0.01);
            }

            @Test
            @DisplayName("Les réservations non payées ne comptent pas dans les revenus")
            void calculerBenefice_reservationNonPayee_nonCompteeDansRevenus() {
                entreprise.ajouterReservation(creerReservation(300_000.0, false, LocalDate.of(2025, 6, 15)));

                double benefice = entreprise.calculerBeneficeParMois(6, 2025);

                assertEquals(-950_000.0, benefice, 0.01);
            }

            @Test
            @DisplayName("Les réservations d'autres mois ne sont pas comptées")
            void calculerBenefice_reservationsAutresMois_ignorees() {
                entreprise.ajouterReservation(creerReservation(300_000.0, true, LocalDate.of(2025, 5, 10)));
                entreprise.ajouterReservation(creerReservation(200_000.0, true, LocalDate.of(2025, 7, 10)));

                double benefice = entreprise.calculerBeneficeParMois(6, 2025);

                assertEquals(-950_000.0, benefice, 0.01);
            }

            @Test
            @DisplayName("Bénéfice = charges négatives si aucune réservation ce mois")
            void calculerBenefice_aucuneReservation_retourneChargeSalarialeNegative() {
                double benefice = entreprise.calculerBeneficeParMois(6, 2025);

                assertEquals(-950_000.0, benefice, 0.01);
            }

            @Test
            @DisplayName("Mois 0 lève une IllegalArgumentException")
            void calculerBenefice_moisZero_leveIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class,
                        () -> entreprise.calculerBeneficeParMois(0, 2025));
            }

            @Test
            @DisplayName("Mois 13 lève une IllegalArgumentException")
            void calculerBenefice_mois13_leveIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class,
                        () -> entreprise.calculerBeneficeParMois(13, 2025));
            }

            @Test
            @DisplayName("Année avant 2000 lève une IllegalArgumentException")
            void calculerBenefice_anneeInvalide_leveIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class,
                        () -> entreprise.calculerBeneficeParMois(6, 1999));
            }

            @Test
            @DisplayName("Mix payées et non payées : seules les payées comptent")
            void calculerBenefice_mixPayeesEtNonPayees_seulementPayeesComptees() {
                entreprise.ajouterReservation(creerReservation(600_000.0, true,  LocalDate.of(2025, 6, 5)));
                entreprise.ajouterReservation(creerReservation(1_000_000.0, false, LocalDate.of(2025, 6, 15)));

                double benefice = entreprise.calculerBeneficeParMois(6, 2025);

                assertEquals(-350_000.0, benefice, 0.01);
            }
        }

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
                entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));
                entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));
                entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));
                entreprise.ajouterReservation(creerReservationPourTrajet(trajetB));
                entreprise.ajouterReservation(creerReservationPourTrajet(trajetB));
                entreprise.ajouterReservation(creerReservationPourTrajet(trajetC));

                List<Trajet> result = entreprise.getTrajetsPopulaires();

                assertEquals(trajetA, result.get(0));
                assertEquals(trajetB, result.get(1));
                assertEquals(trajetC, result.get(2));
            }

            @Test
            @DisplayName("Retourne liste vide si aucune réservation")
            void getTrajetsPopulaires_aucuneReservation_retourneListeVide() {
                List<Trajet> result = entreprise.getTrajetsPopulaires();

                assertTrue(result.isEmpty());
            }

            @Test
            @DisplayName("Réservations avec voyage null sont ignorées sans plantage")
            void getTrajetsPopulaires_voyageNull_ignoreSansException() {
                Reservation rSansVoyage = new Reservation();

                entreprise.ajouterReservation(rSansVoyage);

                assertDoesNotThrow(() -> entreprise.getTrajetsPopulaires());
            }

            @Test
            @DisplayName("Un seul trajet réservé : retourné seul dans la liste")
            void getTrajetsPopulaires_unSeulTrajet_retourneListeAvecUnSeulElement() {
                entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));
                entreprise.ajouterReservation(creerReservationPourTrajet(trajetA));

                List<Trajet> result = entreprise.getTrajetsPopulaires();

                assertEquals(1, result.size());
                assertEquals(trajetA, result.get(0));
            }
        }

        @Nested
        @DisplayName("ajouterLog()")
        class AjouterLogTests {

            @Test
            @DisplayName("Log correctement ajouté dans listeLogs")
            void ajouterLog_actionValide_logAjoute() {
                entreprise.ajouterLog("Connexion admin");

                assertEquals(1, entreprise.getListeLogs().size());
                assertEquals("Connexion admin", entreprise.getListeLogs().get(0).getAction());
            }

            @Test
            @DisplayName("Plusieurs logs s'accumulent dans l'ordre d'ajout")
            void ajouterLog_plusieursActions_accumulationDansOrdre() {
                entreprise.ajouterLog("Action 1");
                entreprise.ajouterLog("Action 2");
                entreprise.ajouterLog("Action 3");

                assertEquals(3, entreprise.getListeLogs().size());
                assertEquals("Action 1", entreprise.getListeLogs().get(0).getAction());
                assertEquals("Action 3", entreprise.getListeLogs().get(2).getAction());
            }

            @Test
            @DisplayName("Action null n'est pas loggée et ne plante pas")
            void ajouterLog_actionNull_aucunLogAjoute() {
                assertDoesNotThrow(() -> entreprise.ajouterLog(null));
                assertTrue(entreprise.getListeLogs().isEmpty());
            }

            @Test
            @DisplayName("Action vide ou espaces n'est pas loggée et ne plante pas")
            void ajouterLog_actionVide_aucunLogAjoute() {
                assertDoesNotThrow(() -> entreprise.ajouterLog("   "));
                assertTrue(entreprise.getListeLogs().isEmpty());
            }

            @Test
            @DisplayName("Log avec employé : l'employé est bien référencé dans le log")
            void ajouterLog_avecEmploye_employe_bienAssocie() {
                entreprise.ajouterLog("Modification planning", employe1);

                HistoriqueAction log = entreprise.getListeLogs().get(0);
                assertEquals(employe1, log.getEmployee());
                assertEquals("Modification planning", log.getAction());
            }

            @Test
            @DisplayName("Log sans employé : le champ employe est null")
            void ajouterLog_sansEmploye_employeEstNull() {
                entreprise.ajouterLog("Ouverture système");

                assertNull(entreprise.getListeLogs().get(0).getEmployee());
            }
        }

        @Nested
        @DisplayName("ajouterReservation()")
        class AjouterReservationTests {

            @Test
            @DisplayName("Réservation valide correctement ajoutée")
            void ajouterReservation_reservationValide_ajouteeDansListe() {
                Reservation r = new Reservation();

                entreprise.ajouterReservation(r);

                assertEquals(1, entreprise.getListeReservations().size());
                assertTrue(entreprise.getListeReservations().contains(r));
            }

            @Test
            @DisplayName("Réservation null ignorée sans plantage")
            void ajouterReservation_null_ignoreeSansException() {
                assertDoesNotThrow(() -> entreprise.ajouterReservation(null));
                assertTrue(entreprise.getListeReservations().isEmpty());
            }

            @Test
            @DisplayName("Plusieurs réservations s'accumulent correctement")
            void ajouterReservation_plusieursResas_toutesPresentes() {
                Reservation r1 = new Reservation();
                Reservation r2 = new Reservation();
                Reservation r3 = new Reservation();

                entreprise.ajouterReservation(r1);
                entreprise.ajouterReservation(r2);
                entreprise.ajouterReservation(r3);

                assertEquals(3, entreprise.getListeReservations().size());
            }
        }
    }


    @Nested
    @DisplayName("Facture")
    class FactureTest {

        private Facture facture;
        private Reservation reservation;

        @BeforeEach
        void setUp() {
            reservation = new Reservation();
            facture = new Facture("F1", "Caisse-01", 15000.0, reservation);
        }

        @Test
        @DisplayName("Constructor initializes all fields correctly")
        void testConstructor() {
            String id = "F2";
            String caisse = "Caisse-02";
            double montant = 25000.0;
            Reservation resa = new Reservation();

            Facture f = new Facture(id, caisse, montant, resa);

            assertEquals(id, f.getId());
            assertEquals(caisse, f.getNumeroCaisse());
            assertEquals(montant, f.getMontantTotalPaye());
            assertEquals(resa, f.getReservation());
        }

        @Test
        @DisplayName("Constructor allows null reservation")
        void testConstructorNullReservation() {
            Facture f = new Facture("F3", "Caisse-03", 0.0, null);

            assertNull(f.getReservation());
        }

        @Test
        @DisplayName("Constructor allows zero montant")
        void testConstructorZeroMontant() {
            Facture f = new Facture("F4", "Caisse-04", 0.0, null);

            assertEquals(0.0, f.getMontantTotalPaye());
        }

        @Test
        @DisplayName("Getters return correct values")
        void testGetters() {
            assertEquals("F1", facture.getId());
            assertEquals("Caisse-01", facture.getNumeroCaisse());
            assertEquals(15000.0, facture.getMontantTotalPaye());
            assertEquals(reservation, facture.getReservation());
        }

        @Test
        @DisplayName("Setters update fields correctly")
        void testSetters() {
            Reservation newResa = new Reservation();
            facture.setId("F-EDIT");
            facture.setNumeroCaisse("Caisse-99");
            facture.setMontantTotalPaye(99999.0);
            facture.setReservation(newResa);

            assertEquals("F-EDIT", facture.getId());
            assertEquals("Caisse-99", facture.getNumeroCaisse());
            assertEquals(99999.0, facture.getMontantTotalPaye());
            assertEquals(newResa, facture.getReservation());
        }

        @Test
        @DisplayName("Setter allows null reservation")
        void testSetNullReservation() {
            facture.setReservation(null);

            assertNull(facture.getReservation());
        }

        @Test
        @DisplayName("Setter allows negative montant")
        void testSetNegativeMontant() {
            facture.setMontantTotalPaye(-100.0);

            assertEquals(-100.0, facture.getMontantTotalPaye());
        }
    }


    @Nested
    @DisplayName("Tests - Operateur enum")
    class OperateurTest {

        @Test
        @DisplayName("Operateur contient 2 valeurs")
        void operateur_contient2valeurs() {
            Operateur[] values = Operateur.values();
            assertEquals(2, values.length);
        }

        @Test
        @DisplayName("Operateur contient ORANGE et YAS")
        void operateur_contientValeursAttendues() {
            assertTrue(contains(Operateur.ORANGE));
            assertTrue(contains(Operateur.YAS));
        }

        @Test
        @DisplayName("ORANGE name() retourne ORANGE")
        void orange_name() {
            Operateur op = Operateur.ORANGE;
            String name = op.name();
            assertEquals("ORANGE", name);
        }

        @Test
        @DisplayName("YAS name() retourne YAS")
        void yas_name() {
            Operateur op = Operateur.YAS;
            String name = op.name();
            assertEquals("YAS", name);
        }

        private boolean contains(Operateur target) {
            for (Operateur o : Operateur.values()) {
                if (o == target) return true;
            }
            return false;
        }
    }


    @Nested
    @DisplayName("Tests — Classe PaiementEspece")
    class PaiementEspeceTest {

        private PaiementEspece paiementEspece;

        @BeforeEach
        void setUp() {
            paiementEspece = new PaiementEspece(
                    "P001",
                    LocalDateTime.of(2025, 6, 1, 10, 30),
                    50_000.0,
                    "Rabe",
                    60_000.0,
                    10_000.0
            );
        }

        @Test
        @DisplayName("Constructeur initialise correctement tous les attributs")
        void constructeur_attributsValides_initialisationCorrecte() {
            assertEquals("P001", paiementEspece.getId());
            assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), paiementEspece.getDateTransaction());
            assertEquals(50_000.0, paiementEspece.getMontant(), 0.01);
            assertEquals("Rabe", paiementEspece.getNomDuPayeur());

            assertEquals(60_000.0, paiementEspece.getMontantRemis(), 0.01);
            assertEquals(10_000.0, paiementEspece.getMonnaieRendue(), 0.01);
        }

        @Test
        @DisplayName("getMontantRemis retourne le montant remis")
        void getMontantRemis_montantRemisValide_retourneValeur() {
            double result = paiementEspece.getMontantRemis();

            assertEquals(60_000.0, result, 0.01);
        }

        @Test
        @DisplayName("getMonnaieRendue retourne la monnaie rendue")
        void getMonnaieRendue_monnaieRendueValide_retourneValeur() {
            double result = paiementEspece.getMonnaieRendue();

            assertEquals(10_000.0, result, 0.01);
        }

        @Test
        @DisplayName("setMontantRemis modifie le montant remis")
        void setMontantRemis_nouvelleValeur_montantRemisModifie() {
            assertEquals(60_000.0, paiementEspece.getMontantRemis(), 0.01);

            paiementEspece.setMontantRemis(70_000.0);

            assertEquals(70_000.0, paiementEspece.getMontantRemis(), 0.01);
        }

        @Test
        @DisplayName("setMonnaieRendue modifie la monnaie rendue")
        void setMonnaieRendue_nouvelleValeur_monnaieRendueModifiee() {
            assertEquals(10_000.0, paiementEspece.getMonnaieRendue(), 0.01);

            paiementEspece.setMonnaieRendue(5_000.0);

            assertEquals(5_000.0, paiementEspece.getMonnaieRendue(), 0.01);
        }

        @Test
        @DisplayName("PaiementEspece avec monnaie rendue à 0 fonctionne correctement")
        void constructeur_monnaieRendueZero_initialisationCorrecte() {
            PaiementEspece paiementExact = new PaiementEspece(
                    "P002", LocalDateTime.now(), 30_000.0, "Client", 30_000.0, 0.0
            );

            double monnaie = paiementExact.getMonnaieRendue();

            assertEquals(0.0, monnaie, 0.01);
        }
    }


    @Nested
    @DisplayName("Tests — Classe PaiementParCarte")
    class PaiementParCarteTest {

        private PaiementParCarte paiementParCarte;

        @BeforeEach
        void setUp() {
            paiementParCarte = new PaiementParCarte(
                    "P001",
                    LocalDateTime.of(2025, 6, 1, 10, 30),
                    75_000.0,
                    "Rabe",
                    Banque.BOA,
                    "MG1234567890"
            );
        }

        @Test
        @DisplayName("Constructeur initialise correctement tous les attributs")
        void constructeur_attributsValides_initialisationCorrecte() {
            assertEquals("P001", paiementParCarte.getId());
            assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), paiementParCarte.getDateTransaction());
            assertEquals(75_000.0, paiementParCarte.getMontant(), 0.01);
            assertEquals("Rabe", paiementParCarte.getNomDuPayeur());

            assertEquals(Banque.BOA, paiementParCarte.getBanque());
            assertEquals("MG1234567890", paiementParCarte.getNumeroCompte());
        }

        @Test
        @DisplayName("getBanque retourne la banque")
        void getBanque_banqueValide_retourneBanque() {
            Banque result = paiementParCarte.getBanque();

            assertEquals(Banque.BOA, result);
        }

        @Test
        @DisplayName("getNumeroCompte retourne le numéro de compte")
        void getNumeroCompte_numeroValide_retourneNumero() {
            String result = paiementParCarte.getNumeroCompte();

            assertEquals("MG1234567890", result);
        }

        @Test
        @DisplayName("setBanque modifie la banque")
        void setBanque_nouvelleBanque_banqueModifiee() {
            assertEquals(Banque.BOA, paiementParCarte.getBanque());

            paiementParCarte.setBanque(Banque.BRED);

            assertEquals(Banque.BRED, paiementParCarte.getBanque());
        }

        @Test
        @DisplayName("setNumeroCompte modifie le numéro de compte")
        void setNumeroCompte_nouveauNumero_numeroModifie() {
            assertEquals("MG1234567890", paiementParCarte.getNumeroCompte());

            paiementParCarte.setNumeroCompte("MG0987654321");

            assertEquals("MG0987654321", paiementParCarte.getNumeroCompte());
        }

        @Test
        @DisplayName("PaiementParCarte avec Banque.BRED fonctionne correctement")
        void constructeur_banqueBRED_initialisationCorrecte() {
            PaiementParCarte paiementBRED = new PaiementParCarte(
                    "P002", LocalDateTime.now(), 100_000.0, "Client", Banque.BRED, "MG1111111111"
            );

            Banque banque = paiementBRED.getBanque();

            assertEquals(Banque.BRED, banque);
        }
    }


    @Nested
    @DisplayName("Tests — Classe PaimentMobile")
    class PaimentMobileTest {

        private PaimentMobile paimentMobile;

        @BeforeEach
        void setUp() {
            paimentMobile = new PaimentMobile(
                    "P001",
                    LocalDateTime.of(2025, 6, 1, 10, 30),
                    25_000.0,
                    "Rabe",
                    "0341234567",
                    "REF-2025-001",
                    Operateur.ORANGE
            );
        }

        @Test
        @DisplayName("Constructeur initialise correctement tous les attributs")
        void constructeur_attributsValides_initialisationCorrecte() {
            assertEquals("P001", paimentMobile.getId());
            assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), paimentMobile.getDateTransaction());
            assertEquals(25_000.0, paimentMobile.getMontant(), 0.01);
            assertEquals("Rabe", paimentMobile.getNomDuPayeur());

            assertEquals("0341234567", paimentMobile.getNumero());
            assertEquals("REF-2025-001", paimentMobile.getReferenceDePaiement());
            assertEquals(Operateur.ORANGE, paimentMobile.getOperateur());
        }

        @Test
        @DisplayName("getNumero retourne le numéro de téléphone")
        void getNumero_numeroValide_retourneNumero() {
            String result = paimentMobile.getNumero();

            assertEquals("0341234567", result);
        }

        @Test
        @DisplayName("getReferenceDePaiement retourne la référence")
        void getReferenceDePaiement_referenceValide_retourneReference() {
            String result = paimentMobile.getReferenceDePaiement();

            assertEquals("REF-2025-001", result);
        }

        @Test
        @DisplayName("getOperateur retourne l'opérateur")
        void getOperateur_operateurValide_retourneOperateur() {
            Operateur result = paimentMobile.getOperateur();

            assertEquals(Operateur.ORANGE, result);
        }

        @Test
        @DisplayName("setNumero modifie le numéro")
        void setNumero_nouveauNumero_numeroModifie() {
            assertEquals("0341234567", paimentMobile.getNumero());

            paimentMobile.setNumero("0347654321");

            assertEquals("0347654321", paimentMobile.getNumero());
        }

        @Test
        @DisplayName("setReferenceDePaiement modifie la référence")
        void setReferenceDePaiement_nouvelleReference_referenceModifiee() {
            assertEquals("REF-2025-001", paimentMobile.getReferenceDePaiement());

            paimentMobile.setReferenceDePaiement("REF-2025-002");

            assertEquals("REF-2025-002", paimentMobile.getReferenceDePaiement());
        }

        @Test
        @DisplayName("setOperateur modifie l'opérateur")
        void setOperateur_nouvelOperateur_operateurModifie() {
            assertEquals(Operateur.ORANGE, paimentMobile.getOperateur());

            paimentMobile.setOperateur(Operateur.YAS);

            assertEquals(Operateur.YAS, paimentMobile.getOperateur());
        }

        @Test
        @DisplayName("PaimentMobile avec operateur YAS fonctionne correctement")
        void constructeur_operateurYAS_initialisationCorrecte() {
            PaimentMobile paiementYAS = new PaimentMobile(
                    "P002", LocalDateTime.now(), 30_000.0, "Client",
                    "0321111111", "REF-2025-002", Operateur.YAS
            );

            Operateur operateur = paiementYAS.getOperateur();

            assertEquals(Operateur.YAS, operateur);
        }
    }



    @Nested
    @DisplayName("Place")
    class PlaceTest {

        private Place place;

        @BeforeEach
        void setUp() {
            place = new Place(1, TypeVoiture.LITE);
        }

        @Test
        @DisplayName("Two-parameter constructor initializes fields correctly")
        void testTwoParamConstructor() {
            int numero = 5;
            TypeVoiture type = TypeVoiture.VIP;

            Place p = new Place(numero, type);

            assertEquals(numero, p.getNumeroPlace());
            assertEquals(type, p.getTypeVoiture());
            assertTrue(p.isPlaceDispo());
            assertTrue(p.isDisponible());
            assertTrue(p.estDispo());
        }

        @Test
        @DisplayName("Four-parameter constructor sets disponible to true regardless of parameter")
        void testFourParamConstructorIgnoresDisponibleParam() {
            int numero = 10;
            TypeVoiture type = TypeVoiture.PREMIUM;

            Place p = new Place(numero, type, false, false);

            assertEquals(numero, p.getNumeroPlace());
            assertEquals(type, p.getTypeVoiture());
            assertFalse(p.isPlaceDispo());
            assertTrue(p.isDisponible());
            assertTrue(p.estDispo());
        }

        @Test
        @DisplayName("Getters return correct values")
        void testGetters() {
            assertEquals(1, place.getNumeroPlace());
            assertEquals(TypeVoiture.LITE, place.getTypeVoiture());
            assertTrue(place.isPlaceDispo());
            assertTrue(place.isDisponible());
        }

        @Test
        @DisplayName("Setters update fields correctly")
        void testSetters() {
            place.setNumeroPlace(99);
            place.setTypeVoiture(TypeVoiture.VVIP);
            place.setPlaceDispo(false);
            place.setDisponible(false);

            assertEquals(99, place.getNumeroPlace());
            assertEquals(TypeVoiture.VVIP, place.getTypeVoiture());
            assertFalse(place.isPlaceDispo());
            assertFalse(place.isDisponible());
        }

        @Test
        @DisplayName("estDispo returns true when disponible is true")
        void testEstDispoTrue() {
            place.setDisponible(true);

            assertTrue(place.estDispo());
        }

        @Test
        @DisplayName("estDispo returns false when disponible is false")
        void testEstDispoFalse() {
            place.setDisponible(false);

            assertFalse(place.estDispo());
        }

        @Test
        @DisplayName("estDispo returns true by default after construction")
        void testEstDispoDefaultTrue() {
            Place p = new Place(1, TypeVoiture.LITE);

            assertTrue(p.estDispo());
        }

        @Test
        @DisplayName("Constructor accepts zero for numeroPlace")
        void testConstructorZeroNumero() {
            Place p = new Place(0, TypeVoiture.LITE);

            assertEquals(0, p.getNumeroPlace());
        }

        @Test
        @DisplayName("Constructor accepts negative numeroPlace")
        void testConstructorNegativeNumero() {
            Place p = new Place(-1, TypeVoiture.LITE);

            assertEquals(-1, p.getNumeroPlace());
        }

        @Test
        @DisplayName("Constructor accepts null TypeVoiture")
        void testConstructorNullType() {
            Place p = new Place(1, null);

            assertNull(p.getTypeVoiture());
        }
    }



    @Nested
    @DisplayName("Tests — Classe Receptionniste")
    class ReceptionnisteTest {
        private Receptionniste receptionniste;
        private Voiture voiture;
        private Chauffeur chauffeur;
        private Place place;
        private Voyage planDeVoyage;
        private Voyageur voyageur;
        private Trajet trajet;
        private Reservation reservationPayee;
        private Reservation reservationNonPayee;
        private TypeDePaiments paiementEspece;
        private Bagage bagage;

        @BeforeEach
        void setUp (){
            receptionniste = new Receptionniste("R1", "Rasoa", "Volana", "0341234567", Sexe.FEMININ, 500000);

            chauffeur = new Chauffeur("C1", "Rakoto", "Jean", "0341111111", Sexe.MASCULIN, 600000, "B");

            trajet = new Trajet("T1", Ville.ANTANANARIVO, Ville.TOAMASINA, 8, 50000);

            voiture = new Voiture("V1", "MAD-1234", TypeVoiture.LITE,chauffeur);

            voyageur = new Voyageur("VG1", "Rabe", "Paul", "0349999999", "rabe@gmail.com", null, null, null);

            paiementEspece  = new PaiementEspece(
                    "P1",
                    LocalDateTime.now(),
                    50000,
                    "Rabe",
                    50000,
                    0.0
            );

            planDeVoyage = new Voyage(
                    "PL1",
                    LocalDateTime.of(2025, 6, 1, 8, 0),
                    LocalDateTime.of(2025, 6, 1, 16, 0),
                    trajet,
                    voiture,
                    chauffeur
            );

            place = voiture.getPlaces().get(0);

            bagage = new Bagage("r21", "plein d'habille et valise de couleur bleu", 15.0,0.0);

            reservationPayee = new Reservation(
                    "RES1",
                    LocalDateTime.now(),
                    LocalDate.of(2025, 6, 1),
                    voyageur,
                    planDeVoyage,
                    place,
                    StatutDeReservation.PAYEE,
                    50000.0,
                    bagage
            );

            reservationNonPayee = new Reservation(
                    "RES2",
                    LocalDateTime.now(),
                    LocalDate.of(2025, 6, 1),
                    voyageur,
                    planDeVoyage,
                    place,
                    StatutDeReservation.ATTENTE,
                    0.0,
                    bagage
            );
            reservationNonPayee.setPrixTotal(0.0);
        }

        @Test
        @DisplayName("enregistrerReservation ajoute la réservation dans la liste")
        void erengistrerReservation_ajoute_bien_dans_la_liste(){
            receptionniste.enregistrerReservation(reservationNonPayee);
            receptionniste.enregistrerReservation(reservationNonPayee);

            assertEquals(2, receptionniste.getListeReservation().size() );
        }

        @Test
        @DisplayName("afficherPlaceDispo retourne toutes les places quand tout est disponible")
        void afficher_place_Dispo_ou_tout_dispo(){
            List<Place> placesDispo = receptionniste.afficherPlaceDispo(voiture, LocalDate.of(2025,6,1));

            assertEquals(16, placesDispo.size());
        }

        @Test
        @DisplayName("afficherPlaceDispo exclut les places marquées indisponibles")
        void afficher_place_dispo_ou_certains_sont_pas_dispo(){
            voiture.getPlaces().getFirst().setPlaceDispo(false);
            voiture.getPlaces().getLast().setPlaceDispo(false);

            List<Place> placesDispo = receptionniste.afficherPlaceDispo(voiture,LocalDate.of(2025,6,1));

            assertEquals(14, placesDispo.size());
        }

        @Test
        @DisplayName("afficherPlaceDispo retourne une liste vide si aucune place dispo")
        void afficher_place_dispo_ou_tout_est_indsipo(){
            for (Place place: voiture.getPlaces()){
                place.setPlaceDispo(false);
            }

            List<Place> placesDispo = receptionniste.afficherPlaceDispo(voiture,LocalDate.of(2025,6,1));

            assertTrue(placesDispo.isEmpty());
        }

        @Test
        @DisplayName("donnerTicket retourne un ticket pour une réservation payée")
        void donnerTicket_reservationPayee_retourneTicket() {
            Ticket ticket = receptionniste.donnerTicket(reservationPayee);

            assertNotNull(ticket);
        }

        @Test
        @DisplayName("donnerTicket retourne null pour une réservation non payée")
        void donnerTicket_reservationNonPayee_retourneNull() {
            Ticket ticket = receptionniste.donnerTicket(reservationNonPayee);

            assertNull(ticket);
        }

        @Test
        @DisplayName("donnerTicket associe le bon voyageur au ticket")
        void donnerTicket_contientBonVoyageur() {
            Ticket ticket = receptionniste.donnerTicket(reservationPayee);

            assertNotNull(ticket);
            assertEquals(voyageur, ticket.getVoyageur());
        }

        @Test
        @DisplayName("donnerFacture retourne une facture pour une réservation valide")
        void donnerFacture_reservationPayee_retourneFacture() {
            Facture facture = receptionniste.donnerFacture(reservationPayee);

            assertNotNull(facture);
        }

        @Test
        @DisplayName("donnerFacture retourne null si le montant est invalide")
        void donnerFacture_montantInvalide_retourneNull() {
            Facture facture = receptionniste.donnerFacture(reservationNonPayee);

            assertNull(facture);
        }

        @Test
        @DisplayName("HistoriqueAction est correctement créée")
        void historiqueAction_creation_correcte() {
            HistoriqueAction log = new HistoriqueAction(
                    "H1",
                    "Reservation enregistrée",
                    LocalDateTime.now(),
                    receptionniste
            );

            assertEquals("H1", log.getId());
            assertEquals("Reservation enregistrée", log.getAction());
            assertEquals(receptionniste, log.getEmployee());
            assertNotNull(log.getDateHeure());
        }

        @Test
        @DisplayName("HistoriqueAction est immuable (pas de setters)")
        void historiqueAction_immuable_pasDeModification() {
            HistoriqueAction log = new HistoriqueAction(
                    "H2",
                    "Test action",
                    LocalDateTime.of(2025, 1, 1, 10, 0),
                    receptionniste
            );

            assertEquals("Test action", log.getAction());
            assertEquals(LocalDateTime.of(2025, 1, 1, 10, 0), log.getDateHeure());
        }
    }



    @Nested
    class ReservationTest {
        private Voyageur voyageur;
        private Voyage voyage;
        private Place place;
        private Bagage bagage;
        private Reservation reservation;

        @BeforeEach
        void setUp() {
            Trajet trajet = new Trajet(
                    "T1",
                    Ville.ANTANANARIVO,
                    Ville.MORONDAVA,
                    14,
                    70.000
            );

            voyage = new Voyage(
                    "V1",
                    LocalDateTime.now(),
                    LocalDateTime.now().plusHours(8),
                    trajet,
                    null,
                    null
            );

            voyageur = new Voyageur(
                    "1",
                    "RAJAO",
                    "Philipe",
                    "034 46 872 09",
                    "philiperajao@gmail.com",
                    null,
                    null,
                    new ArrayList<>()
            );

            place = new Place(1, TypeVoiture.VVIP);

            bagage = new Bagage(
                    "B1",
                    "Valise",
                    25,
                    15.000
            );

            reservation = new Reservation(
                    "R1",
                    LocalDateTime.now(),
                    LocalDate.now(),
                    voyageur,
                    voyage,
                    place,
                    StatutDeReservation.PAYEE,
                    85.000,
                    bagage
            );
        }

        @Test
        public void testAnnuler() {
            reservation.annuler();

            assertEquals(StatutDeReservation.ANNULEE, reservation.getStatut());
        }

        @Test
        public void testAjouterPaiement() {

            PaiementEspece paiement = new PaiementEspece(
                    "P1",
                    LocalDateTime.now(),
                    160000,
                    "Jean",
                    145000,
                    15000
            );

            reservation.ajouterPaiement(paiement);

            assertEquals(paiement, reservation.getPaiements());
        }

        @Test
        public void testEstPayee() {

            PaiementEspece paiement = new PaiementEspece(
                    "P2",
                    LocalDateTime.now(),
                    245000,
                    "Jean",
                    250000,
                    5000
            );

            reservation.ajouterPaiement(paiement);

            assertTrue(reservation.estPayee());
        }

        @Test
        public void testEstPayeeSansPaiement() {

            assertFalse(reservation.estPayee());
        }
    }



    @Nested
    @DisplayName("Tests - Sexe enum")
    class SexeTest {

        @Test
        @DisplayName("Sexe contient 3 valeurs")
        void sexe_contient3valeurs() {
            Sexe[] values = Sexe.values();
            assertEquals(3, values.length);
        }

        @Test
        @DisplayName("Sexe contient MASCULIN, FEMININ, AUTRE")
        void sexe_contientValeursAttendues() {
            assertTrue(contains(Sexe.MASCULIN));
            assertTrue(contains(Sexe.FEMININ));
            assertTrue(contains(Sexe.AUTRE));
        }

        @Test
        @DisplayName("Tous les sexes sont non null")
        void tousLesSexes_sontNonNull() {
            Sexe[] values = Sexe.values();
            for (Sexe s : values) {
                assertNotNull(s);
            }
        }

        private boolean contains(Sexe target) {
            for (Sexe s : Sexe.values()) {
                if (s == target) return true;
            }
            return false;
        }
    }


    @Nested
    @DisplayName("Tests - StatutDeReservation enum")
    class StatutDeReservationTest {

        @Test
        @DisplayName("StatutDeReservation contient 3 valeurs")
        void statut_contient3valeurs() {
            StatutDeReservation[] values = StatutDeReservation.values();
            assertEquals(3, values.length);
        }

        @Test
        @DisplayName("StatutDeReservation contient PAYEE, ANNULEE, ATTENTE")
        void statut_contientValeursAttendues() {
            assertTrue(contains(StatutDeReservation.PAYEE));
            assertTrue(contains(StatutDeReservation.ANNULEE));
            assertTrue(contains(StatutDeReservation.ATTENTE));
        }

        @Test
        @DisplayName("PAYEE.name() retourne PAYEE")
        void payee_name() {
            StatutDeReservation statut = StatutDeReservation.PAYEE;
            String name = statut.name();
            assertEquals("PAYEE", name);
        }

        private boolean contains(StatutDeReservation target) {
            for (StatutDeReservation s : StatutDeReservation.values()) {
                if (s == target) return true;
            }
            return false;
        }
    }



    @Nested
    @DisplayName("Ticket")
    class TicketTest {

        private Ticket ticket;
        private LocalDateTime dateDepart;
        private LocalDateTime dateArrivee;
        private Voyageur voyageur;
        private Place place;
        private Voiture voiture;

        @BeforeEach
        void setUp() {
            dateDepart = LocalDateTime.of(2026, 8, 15, 8, 0);
            dateArrivee = LocalDateTime.of(2026, 8, 15, 14, 0);
            voyageur = new Voyageur("V1", "RAKOTO", "Paul", "0341234567",
                    "paul.rakoto@email.com", null, null, null);
            place = new Place(12, TypeVoiture.PREMIUM);
            voiture = new Voiture("VOIT1", "1234TAB", TypeVoiture.PREMIUM, null);
            ticket = new Ticket("T1", dateDepart, dateArrivee, voyageur, place, voiture);
        }

        @Test
        @DisplayName("Constructor initializes all fields correctly")
        void testConstructor() {
            LocalDateTime dep = LocalDateTime.of(2026, 9, 1, 10, 0);
            LocalDateTime arr = LocalDateTime.of(2026, 9, 1, 18, 0);
            Voyageur voy = new Voyageur("V2", "RABE", "Alice", "0347654321",
                    "alice.rabe@email.com", null, null, null);
            Place pl = new Place(5, TypeVoiture.VVIP);
            Voiture voit = new Voiture("VOIT2", "5678TAB", TypeVoiture.VVIP, null);

            Ticket t = new Ticket("T2", dep, arr, voy, pl, voit);

            assertEquals("T2", t.getId());
            assertEquals(dep, t.getDateDepart());
            assertEquals(arr, t.getDateArrivee());
            assertEquals(voy, t.getVoyageur());
            assertEquals(pl, t.getPlace());
            assertEquals(voit, t.getVoiture());
        }

        @Test
        @DisplayName("Getters return correct values")
        void testGetters() {
            assertEquals("T1", ticket.getId());
            assertEquals(dateDepart, ticket.getDateDepart());
            assertEquals(dateArrivee, ticket.getDateArrivee());
            assertEquals(voyageur, ticket.getVoyageur());
            assertEquals(place, ticket.getPlace());
            assertEquals(voiture, ticket.getVoiture());
        }

        @Test
        @DisplayName("Setters update fields correctly")
        void testSetters() {
            LocalDateTime newDep = LocalDateTime.of(2026, 10, 1, 6, 0);
            LocalDateTime newArr = LocalDateTime.of(2026, 10, 1, 12, 0);
            Voyageur newVoy = new Voyageur("V3", "RAVELO", "Boby", "0341112233",
                    "boby.ravelo@email.com", null, null, null);
            Place newPlace = new Place(8, TypeVoiture.LITE);
            Voiture newVoit = new Voiture("VOIT3", "9012TAB", TypeVoiture.LITE, null);

            ticket.setId("T-EDIT");
            ticket.setDateDepart(newDep);
            ticket.setDateArrivee(newArr);
            ticket.setVoyageur(newVoy);
            ticket.setPlace(newPlace);
            ticket.setVoiture(newVoit);

            assertEquals("T-EDIT", ticket.getId());
            assertEquals(newDep, ticket.getDateDepart());
            assertEquals(newArr, ticket.getDateArrivee());
            assertEquals(newVoy, ticket.getVoyageur());
            assertEquals(newPlace, ticket.getPlace());
            assertEquals(newVoit, ticket.getVoiture());
        }

        @Test
        @DisplayName("Constructor allows null id")
        void testConstructorNullId() {
            Ticket t = new Ticket(null, dateDepart, dateArrivee, voyageur, place, voiture);

            assertNull(t.getId());
        }

        @Test
        @DisplayName("Constructor allows null voyageur")
        void testConstructorNullVoyageur() {
            Ticket t = new Ticket("T3", dateDepart, dateArrivee, null, place, voiture);

            assertNull(t.getVoyageur());
        }

        @Test
        @DisplayName("Constructor allows null place")
        void testConstructorNullPlace() {
            Ticket t = new Ticket("T4", dateDepart, dateArrivee, voyageur, null, voiture);

            assertNull(t.getPlace());
        }

        @Test
        @DisplayName("Constructor allows null voiture")
        void testConstructorNullVoiture() {
            Ticket t = new Ticket("T5", dateDepart, dateArrivee, voyageur, place, null);

            assertNull(t.getVoiture());
        }

        @Test
        @DisplayName("Constructor allows null dates")
        void testConstructorNullDates() {
            Ticket t = new Ticket("T6", null, null, voyageur, place, voiture);

            assertNull(t.getDateDepart());
            assertNull(t.getDateArrivee());
        }

        @Test
        @DisplayName("Setter allows null reference fields")
        void testSettersNull() {
            ticket.setId(null);
            ticket.setDateDepart(null);
            ticket.setDateArrivee(null);
            ticket.setVoyageur(null);
            ticket.setPlace(null);
            ticket.setVoiture(null);

            assertNull(ticket.getId());
            assertNull(ticket.getDateDepart());
            assertNull(ticket.getDateArrivee());
            assertNull(ticket.getVoyageur());
            assertNull(ticket.getPlace());
            assertNull(ticket.getVoiture());
        }
    }



    @Nested
    @DisplayName("Trajet")
    class TrajetTest {

        private Trajet trajet;

        @BeforeEach
        void setUp() {
            trajet = new Trajet("T1", Ville.ANTANANARIVO, Ville.MORONDAVA, 14, 70000.0);
        }

        @Test
        @DisplayName("Constructor initializes all fields correctly")
        void testConstructor() {
            String id = "T2";
            Ville depart = Ville.TOAMASINA;
            Ville arrive = Ville.FIANARANTSOA;
            int duree = 8;
            double prix = 45000.0;

            Trajet t = new Trajet(id, depart, arrive, duree, prix);

            assertEquals(id, t.getId());
            assertEquals(depart, t.getVilleDepart());
            assertEquals(arrive, t.getVilleArrive());
            assertEquals(duree, t.getDureeEstime());
            assertEquals(prix, t.getPrixBase());
        }

        @Test
        @DisplayName("Getters return correct values")
        void testGetters() {
            assertEquals("T1", trajet.getId());
            assertEquals(Ville.ANTANANARIVO, trajet.getVilleDepart());
            assertEquals(Ville.MORONDAVA, trajet.getVilleArrive());
            assertEquals(14, trajet.getDureeEstime());
            assertEquals(70000.0, trajet.getPrixBase());
        }

        @Test
        @DisplayName("Setters update fields correctly")
        void testSetters() {
            trajet.setId("T-EDIT");
            trajet.setVilleDepart(Ville.MAHAJANGA);
            trajet.setVilleArrive(Ville.TOLIARA);
            trajet.setDureeEstime(10);
            trajet.setPrixBase(55000.0);

            assertEquals("T-EDIT", trajet.getId());
            assertEquals(Ville.MAHAJANGA, trajet.getVilleDepart());
            assertEquals(Ville.TOLIARA, trajet.getVilleArrive());
            assertEquals(10, trajet.getDureeEstime());
            assertEquals(55000.0, trajet.getPrixBase());
        }

        @Test
        @DisplayName("getPrix returns prizeBase for LITE (default case)")
        void testGetPrixLite() {
            double prix = trajet.getPrix(TypeVoiture.LITE);

            assertEquals(70000.0, prix, 0.001);
        }

        @Test
        @DisplayName("getPrix returns prixBase * 1.3 for PREMIUM")
        void testGetPrixPremium() {
            double prix = trajet.getPrix(TypeVoiture.PREMIUM);

            assertEquals(70000.0 * 1.3, prix, 0.001);
        }

        @Test
        @DisplayName("getPrix returns prixBase * 1.7 for VIP")
        void testGetPrixVip() {
            double prix = trajet.getPrix(TypeVoiture.VIP);

            assertEquals(70000.0 * 1.7, prix, 0.001);
        }

        @Test
        @DisplayName("getPrix returns prixBase * 2.2 for VVIP")
        void testGetPrixVvip() {
            double prix = trajet.getPrix(TypeVoiture.VVIP);

            assertEquals(70000.0 * 2.2, prix, 0.001);
        }

        @Test
        @DisplayName("equals returns true when same object reference")
        void testEqualsSameReference() {
            assertEquals(trajet, trajet);
        }

        @Test
        @DisplayName("equals returns true when two Trajets have the same id")
        void testEqualsSameId() {
            Trajet sameId = new Trajet("T1", Ville.TOAMASINA, Ville.ANTSIRABE, 5, 30000.0);

            assertEquals(trajet, sameId);
        }

        @Test
        @DisplayName("equals returns false when two Trajets have different ids")
        void testEqualsDifferentId() {
            Trajet different = new Trajet("T99", Ville.ANTANANARIVO, Ville.MORONDAVA, 14, 70000.0);

            assertNotEquals(trajet, different);
        }

        @Test
        @DisplayName("equals returns false when compared to null")
        void testEqualsNull() {
            assertNotNull(trajet);
            assertFalse(trajet.equals(null));
        }

        @Test
        @DisplayName("equals returns false when compared to different class")
        void testEqualsDifferentClass() {
            assertFalse(trajet.equals("T1"));
        }

        @Test
        @DisplayName("hashCode is consistent across multiple calls")
        void testHashCodeConsistency() {
            int hash1 = trajet.hashCode();
            int hash2 = trajet.hashCode();
            int hash3 = trajet.hashCode();

            assertEquals(hash1, hash2);
            assertEquals(hash2, hash3);
        }

        @Test
        @DisplayName("hashCode returns same value for equal objects")
        void testHashCodeEqualObjects() {
            Trajet sameId = new Trajet("T1", Ville.DIEGO, Ville.MAHAJANGA, 3, 20000.0);

            assertEquals(trajet.hashCode(), sameId.hashCode());
        }

        @Test
        @DisplayName("hashCode returns different values for objects with different ids")
        void testHashCodeDifferentIds() {
            Trajet different = new Trajet("T2", Ville.ANTANANARIVO, Ville.MORONDAVA, 14, 70000.0);

            assertNotEquals(trajet.hashCode(), different.hashCode());
        }

        @Test
        @DisplayName("Constructor handles negative dureeEstime")
        void testConstructorNegativeDuree() {
            Trajet t = new Trajet("T3", Ville.ANTANANARIVO, Ville.TOAMASINA, -1, 50000.0);

            assertEquals(-1, t.getDureeEstime());
        }

        @Test
        @DisplayName("Constructor handles zero prixBase")
        void testConstructorZeroPrix() {
            Trajet t = new Trajet("T4", Ville.ANTANANARIVO, Ville.TOAMASINA, 5, 0.0);

            assertEquals(0.0, t.getPrixBase());
        }
    }


    @Nested
    @DisplayName("Tests — Classe abstraite TypeDePaiments (via PaiementEspece)")
    class TypeDePaimentsTest {

        private PaiementEspece paiement;

        @BeforeEach
        void setUp() {
            paiement = new PaiementEspece(
                    "P001",
                    LocalDateTime.of(2025, 6, 1, 10, 30),
                    50_000.0,
                    "Rabe",
                    60_000.0,
                    10_000.0
            );
        }

        @Test
        @DisplayName("Constructeur initialise correctement tous les attributs")
        void constructeur_attributsValides_initialisationCorrecte() {
            assertEquals("P001", paiement.getId());
            assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), paiement.getDateTransaction());
            assertEquals(50_000.0, paiement.getMontant(), 0.01);
            assertEquals("Rabe", paiement.getNomDuPayeur());
        }

        @Test
        @DisplayName("getId retourne l'identifiant")
        void getId_identifiantValide_retourneId() {
            String id = paiement.getId();

            assertEquals("P001", id);
        }

        @Test
        @DisplayName("setId modifie l'identifiant")
        void setId_nouvelId_idModifie() {
            assertEquals("P001", paiement.getId());

            paiement.setId("P002");

            assertEquals("P002", paiement.getId());
        }

        @Test
        @DisplayName("getDateTransaction retourne la date de transaction")
        void getDateTransaction_dateValide_retourneDate() {
            LocalDateTime date = paiement.getDateTransaction();

            assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), date);
        }

        @Test
        @DisplayName("setDateTransaction modifie la date")
        void setDateTransaction_nouvelleDate_dateModifiee() {
            LocalDateTime nouvelleDate = LocalDateTime.of(2025, 7, 1, 14, 0);

            paiement.setDateTransaction(nouvelleDate);

            assertEquals(nouvelleDate, paiement.getDateTransaction());
        }

        @Test
        @DisplayName("getMontant retourne le montant")
        void getMontant_montantValide_retourneMontant() {
            double montant = paiement.getMontant();

            assertEquals(50_000.0, montant, 0.01);
        }

        @Test
        @DisplayName("setMontant modifie le montant")
        void setMontant_nouveauMontant_montantModifie() {
            assertEquals(50_000.0, paiement.getMontant(), 0.01);

            paiement.setMontant(75_000.0);

            assertEquals(75_000.0, paiement.getMontant(), 0.01);
        }

        @Test
        @DisplayName("getNomDuPayeur retourne le nom du payeur")
        void getNomDuPayeur_nomValide_retourneNom() {
            String nom = paiement.getNomDuPayeur();

            assertEquals("Rabe", nom);
        }

        @Test
        @DisplayName("setNomDuPayeur modifie le nom du payeur")
        void setNomDuPayeur_nouveauNom_nomModifie() {
            assertEquals("Rabe", paiement.getNomDuPayeur());

            paiement.setNomDuPayeur("Jean");

            assertEquals("Jean", paiement.getNomDuPayeur());
        }

        @Test
        @DisplayName("TypeDePaiments avec montant nul fonctionne")
        void constructeur_montantZero_initialisationCorrecte() {
            PaiementEspece paiementZero = new PaiementEspece(
                    "P003", LocalDateTime.now(), 0.0, "Client", 0.0, 0.0
            );

            double montant = paiementZero.getMontant();

            assertEquals(0.0, montant, 0.01);
        }
    }


    @Nested
    @DisplayName("Tests - TypeVoiture enum")
    class TypeVoitureTest {

        @Test
        @DisplayName("LITE a 16 places")
        void lite_a16places() {
            TypeVoiture type = TypeVoiture.LITE;
            int places = type.getNbrePlaces();
            assertEquals(16, places);
        }

        @Test
        @DisplayName("PREMIUM a 16 places")
        void premium_a16places() {
            TypeVoiture type = TypeVoiture.PREMIUM;
            int places = type.getNbrePlaces();
            assertEquals(16, places);
        }

        @Test
        @DisplayName("VIP a 8 places")
        void vip_a8places() {
            TypeVoiture type = TypeVoiture.VIP;
            int places = type.getNbrePlaces();
            assertEquals(8, places);
        }

        @Test
        @DisplayName("VVIP a 8 places")
        void vvip_a8places() {
            TypeVoiture type = TypeVoiture.VVIP;
            int places = type.getNbrePlaces();
            assertEquals(8, places);
        }
    }


    @Nested
    @DisplayName("Tests - Ville enum")
    class VilleTest {

        @Test
        @DisplayName("Ville contient 8 valeurs")
        void ville_contient8valeurs() {
            Ville[] values = Ville.values();
            assertEquals(8, values.length);
        }

        @Test
        @DisplayName("Ville contient toutes les villes attendues")
        void ville_contientVillesAttendues() {
            assertTrue(contains(Ville.ANTANANARIVO));
            assertTrue(contains(Ville.TOAMASINA));
            assertTrue(contains(Ville.MAHAJANGA));
            assertTrue(contains(Ville.TOLIARA));
            assertTrue(contains(Ville.ANTSIRABE));
            assertTrue(contains(Ville.MORONDAVA));
            assertTrue(contains(Ville.FIANARANTSOA));
            assertTrue(contains(Ville.DIEGO));
        }

        @Test
        @DisplayName("ANTANANARIVO est la capitale")
        void antananarivo_estCapitale() {
            Ville capitale = Ville.ANTANANARIVO;
            String name = capitale.name();
            assertEquals("ANTANANARIVO", name);
        }

        @Test
        @DisplayName("Toutes les villes sont non null")
        void toutesLesVilles_sontNonNull() {
            Ville[] values = Ville.values();
            for (Ville v : values) {
                assertNotNull(v);
            }
        }

        private boolean contains(Ville target) {
            for (Ville v : Ville.values()) {
                if (v == target) return true;
            }
            return false;
        }
    }


    @Nested
    @DisplayName("Tests — Classe Voiture")
    class VoitureTest {

        private Voiture voitureLite;
        private Voiture voitureVIP;
        private Chauffeur chauffeur;
        private Voyage voyage;

        @BeforeEach
        void setUp() {
            chauffeur = new Chauffeur("C001", "Rakoto", "Jean", "0341111111", Sexe.MASCULIN, 600_000.0, "B");

            voitureLite = new Voiture("V001", "1234-TBA", TypeVoiture.LITE, chauffeur);
            voitureVIP = new Voiture("V002", "5678-TBA", TypeVoiture.VIP, chauffeur);

            Trajet trajet = new Trajet("T1", Ville.ANTANANARIVO, Ville.TOAMASINA, 8, 50_000.0);

            voyage = new Voyage(
                    "VY1",
                    LocalDateTime.of(2025, 6, 1, 8, 0),
                    LocalDateTime.of(2025, 6, 1, 16, 0),
                    trajet,
                    voitureLite,
                    chauffeur
            );
        }

        @Test
        @DisplayName("Constructeur initialise correctement les attributs")
        void constructeur_attributsValides_initialisationCorrecte() {
            assertEquals("V001", voitureLite.getId());
            assertEquals("1234-TBA", voitureLite.getNumeroMatricule());
            assertEquals(TypeVoiture.LITE, voitureLite.getTypeVoiture());
            assertEquals(chauffeur, voitureLite.getChauffeur());
            assertNotNull(voitureLite.getPlaces());
        }

        @Test
        @DisplayName("getNbrePlace retourne le nombre de places selon le type")
        void getNbrePlace_typeLite_retourne16() {
            int nombrePlaces = voitureLite.getNbrePlace();

            assertEquals(16, nombrePlaces);
        }

        @Test
        @DisplayName("getNbrePlace pour VIP retourne 8 places")
        void getNbrePlace_typeVIP_retourne8() {
            int nombrePlaces = voitureVIP.getNbrePlace();

            assertEquals(8, nombrePlaces);
        }

        @Test
        @DisplayName("getPlacesDisponibles retourne toutes les places quand tout est dispo")
        void getPlacesDisponibles_toutDispo_retourneToutesLesPlaces() {
            List<Place> placesDispo = voitureLite.getPlacesDisponibles(voyage);

            assertEquals(16, placesDispo.size());
        }

        @Test
        @DisplayName("getPlacesDisponibles exclut les places marquées indisponibles")
        void getPlacesDisponibles_certainesIndisponibles_retourneSeulementDisponibles() {
            voitureLite.getPlaces().get(0).setDisponible(false);
            voitureLite.getPlaces().get(5).setDisponible(false);
            voitureLite.getPlaces().get(10).setDisponible(false);

            List<Place> placesDispo = voitureLite.getPlacesDisponibles(voyage);

            assertEquals(13, placesDispo.size());
        }

        @Test
        @DisplayName("setChauffeur modifie le chauffeur")
        void setChauffeur_nouveauChauffeur_chauffeurModifie() {
            Chauffeur nouveauChauffeur = new Chauffeur("C002", "Rasoa", "Marie", "0342222222", Sexe.FEMININ, 500_000.0, "B");

            voitureLite.setChauffeur(nouveauChauffeur);

            assertEquals(nouveauChauffeur, voitureLite.getChauffeur());
        }

        @Test
        @DisplayName("Voiture avec chauffeur null fonctionne")
        void constructeur_chauffeurNull_initialisationCorrecte() {
            Voiture voitureSansChauffeur = new Voiture("V003", "9999-TBA", TypeVoiture.VVIP, null);

            Chauffeur chauf = voitureSansChauffeur.getChauffeur();

            assertNull(chauf);
            assertEquals(8, voitureSansChauffeur.getNbrePlace());
        }
    }


    @Nested
    @DisplayName("Tests — Classe Voyage")
    class VoyageTest {

        private Voyage voyage;
        private Trajet trajet;
        private Voiture voiture;
        private Chauffeur chauffeur;

        @BeforeEach
        void setUp() {
            chauffeur = new Chauffeur("C001", "Rakoto", "Jean", "0341111111", Sexe.MASCULIN, 600_000.0, "B");
            voiture = new Voiture("V001", "1234-TBA", TypeVoiture.LITE, chauffeur);
            trajet = new Trajet("T1", Ville.ANTANANARIVO, Ville.TOAMASINA, 8, 50_000.0);

            voyage = new Voyage(
                    "VY001",
                    LocalDateTime.of(2025, 6, 1, 8, 0),
                    LocalDateTime.of(2025, 6, 1, 16, 0),
                    trajet,
                    voiture,
                    chauffeur
            );
        }

        @Test
        @DisplayName("Constructeur avec paramètres initialise correctement tous les attributs")
        void constructeurComplet_attributsValides_initialisationCorrecte() {
            assertEquals("VY001", voyage.getId());
            assertEquals(LocalDateTime.of(2025, 6, 1, 8, 0), voyage.getDateDepart());
            assertEquals(LocalDateTime.of(2025, 6, 1, 16, 0), voyage.getDateArrive());
            assertEquals(trajet, voyage.getTrajet());
            assertEquals(voiture, voyage.getVoiture());
            assertEquals(chauffeur, voyage.getChauffeur());
        }

        @Test
        @DisplayName("Constructeur vide crée une instance avec tous les champs null")
        void constructeurVide_aucunParametre_tousChampsNull() {
            Voyage voyageVide = new Voyage();

            assertNull(voyageVide.getId());
            assertNull(voyageVide.getDateDepart());
            assertNull(voyageVide.getDateArrive());
            assertNull(voyageVide.getTrajet());
            assertNull(voyageVide.getVoiture());
            assertNull(voyageVide.getChauffeur());
        }

        @Test
        @DisplayName("setId modifie l'identifiant")
        void setId_nouvelId_idModifie() {
            assertEquals("VY001", voyage.getId());

            voyage.setId("VY002");

            assertEquals("VY002", voyage.getId());
        }

        @Test
        @DisplayName("setDateDepart modifie la date de départ")
        void setDateDepart_nouvelleDate_dateDepartModifiee() {
            LocalDateTime nouvelleDate = LocalDateTime.of(2025, 7, 1, 10, 0);

            voyage.setDateDepart(nouvelleDate);

            assertEquals(nouvelleDate, voyage.getDateDepart());
        }

        @Test
        @DisplayName("setDateArrive modifie la date d'arrivée")
        void setDateArrive_nouvelleDate_dateArriveModifiee() {
            LocalDateTime nouvelleDate = LocalDateTime.of(2025, 7, 1, 18, 0);

            voyage.setDateArrive(nouvelleDate);

            assertEquals(nouvelleDate, voyage.getDateArrive());
        }

        @Test
        @DisplayName("setTrajet modifie le trajet")
        void setTrajet_nouveauTrajet_trajetModifie() {
            Trajet nouveauTrajet = new Trajet("T2", Ville.ANTANANARIVO, Ville.MAHAJANGA, 10, 60_000.0);

            voyage.setTrajet(nouveauTrajet);

            assertEquals(nouveauTrajet, voyage.getTrajet());
        }

        @Test
        @DisplayName("setVoiture modifie la voiture")
        void setVoiture_nouvelleVoiture_voitureModifiee() {
            Voiture nouvelleVoiture = new Voiture("V002", "5678-TBA", TypeVoiture.VIP, null);

            voyage.setVoiture(nouvelleVoiture);

            assertEquals(nouvelleVoiture, voyage.getVoiture());
        }

        @Test
        @DisplayName("setChauffeur modifie le chauffeur")
        void setChauffeur_nouveauChauffeur_chauffeurModifie() {
            Chauffeur nouveauChauffeur = new Chauffeur("C002", "Rasoa", "Marie", "0342222222", Sexe.FEMININ, 500_000.0, "B");

            voyage.setChauffeur(nouveauChauffeur);

            assertEquals(nouveauChauffeur, voyage.getChauffeur());
        }
    }


    @Nested
    @DisplayName("Tests — Classe Voyageur")
    class VoyageurTest {

        private Voyageur voyageur;
        private Reservation reservation;

        @BeforeEach
        void setUp() {
            voyageur = new Voyageur(
                    "VG001",
                    "Rakoto",
                    "Jean",
                    "0341234567",
                    "jean.rakoto@email.com",
                    null,
                    null,
                    new ArrayList<>()
            );

            Trajet trajet = new Trajet("T1", Ville.ANTANANARIVO, Ville.TOAMASINA, 8, 50_000.0);
            Chauffeur chauffeur = new Chauffeur("C1", "Rabe", "Paul", "0341111111", Sexe.MASCULIN, 600_000.0, "B");
            Voiture voiture = new Voiture("V1", "1234-TBA", TypeVoiture.LITE, chauffeur);
            Place place = voiture.getPlaces().get(0);
            Bagage bagage = new Bagage("B1", "Valise", 15.0, 0.0);

            Voyage voyage = new Voyage(
                    "VY1",
                    LocalDateTime.of(2025, 6, 1, 8, 0),
                    LocalDateTime.of(2025, 6, 1, 16, 0),
                    trajet,
                    voiture,
                    chauffeur
            );

            reservation = new Reservation(
                    "R1",
                    LocalDateTime.now(),
                    LocalDate.of(2025, 6, 1),
                    voyageur,
                    voyage,
                    place,
                    StatutDeReservation.PAYEE,
                    50_000.0,
                    bagage
            );
        }

        @Test
        @DisplayName("Constructeur initialise correctement les attributs")
        void constructeur_attributsValides_initialisationCorrecte() {
            assertEquals("VG001", voyageur.getId());
            assertEquals("Rakoto", voyageur.getNom());
            assertEquals("Jean", voyageur.getPrenom());
            assertEquals("0341234567", voyageur.getTelephone());
            assertEquals("jean.rakoto@email.com", voyageur.getEmail());
            assertNotNull(voyageur.getReservation());
            assertNull(voyageur.getTicket());
            assertNotNull(voyageur.getListeBagage());
            assertTrue(voyageur.getListeBagage().isEmpty());
        }

        @Test
        @DisplayName("setId modifie l'identifiant")
        void setId_nouvelId_idModifie() {
            assertEquals("VG001", voyageur.getId());

            voyageur.setId("VG002");

            assertEquals("VG002", voyageur.getId());
        }

        @Test
        @DisplayName("setNom modifie le nom")
        void setNom_nouveauNom_nomModifie() {
            assertEquals("Rakoto", voyageur.getNom());

            voyageur.setNom("Rasoa");

            assertEquals("Rasoa", voyageur.getNom());
        }

        @Test
        @DisplayName("setPrenom modifie le prénom")
        void setPrenom_nouveauPrenom_prenomModifie() {
            assertEquals("Jean", voyageur.getPrenom());

            voyageur.setPrenom("Marie");

            assertEquals("Marie", voyageur.getPrenom());
        }

        @Test
        @DisplayName("setTelephone modifie le téléphone")
        void setTelephone_nouveauTelephone_telephoneModifie() {
            assertEquals("0341234567", voyageur.getTelephone());

            voyageur.setTelephone("0347654321");

            assertEquals("0347654321", voyageur.getTelephone());
        }

        @Test
        @DisplayName("setEmail modifie l'email")
        void setEmail_nouvelEmail_emailModifie() {
            assertEquals("jean.rakoto@email.com", voyageur.getEmail());

            voyageur.setEmail("nouveau@email.com");

            assertEquals("nouveau@email.com", voyageur.getEmail());
        }

        @Test
        @DisplayName("faireReservation associe une réservation au voyageur")
        void faireReservation_reservationValide_reservationAssociee() {
            Voyageur nouveauVoyageur = new Voyageur(
                    "VG002", "Rasoa", "Marie", "0347654321", "marie@email.com",
                    null, null, new ArrayList<>()
            );
            assertNull(nouveauVoyageur.getReservation());

            nouveauVoyageur.faireReservation(reservation);

            assertEquals(reservation, nouveauVoyageur.getReservation());
        }

        @Test
        @DisplayName("setReservation modifie la réservation")
        void setReservation_nouvelleReservation_reservationModifiee() {
            Reservation autreReservation = new Reservation();

            voyageur.setReservation(autreReservation);

            assertEquals(autreReservation, voyageur.getReservation());
        }

        @Test
        @DisplayName("setTicket modifie le ticket")
        void setTicket_nouveauTicket_ticketModifie() {
            Ticket ticket = new Ticket(
                    "TK1",
                    LocalDateTime.now(),
                    LocalDateTime.now().plusHours(8),
                    voyageur,
                    null,
                    null
            );

            voyageur.setTicket(ticket);

            assertEquals(ticket, voyageur.getTicket());
        }

        @Test
        @DisplayName("setListeBagage modifie la liste des bagages")
        void setListeBagage_nouvelleListe_listeModifiee() {
            Bagage bagage = new Bagage("B1", "Sac", 10.0, 0.0);
            List<Bagage> nouveauxBagages = new ArrayList<>(List.of(bagage));

            voyageur.setListeBagage(nouveauxBagages);

            assertEquals(1, voyageur.getListeBagage().size());
            assertTrue(voyageur.getListeBagage().contains(bagage));
        }
    }
}
