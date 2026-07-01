package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests — Classe Admin")
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
