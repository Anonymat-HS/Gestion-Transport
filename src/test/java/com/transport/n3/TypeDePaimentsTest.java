package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
        // Given + When : création dans setUp()

        // Then
        assertEquals("P001", paiement.getId());
        assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), paiement.getDateTransaction());
        assertEquals(50_000.0, paiement.getMontant(), 0.01);
        assertEquals("Rabe", paiement.getNomDuPayeur());
    }

    @Test
    @DisplayName("getId retourne l'identifiant")
    void getId_identifiantValide_retourneId() {
        // Given

        // When
        String id = paiement.getId();

        // Then
        assertEquals("P001", id);
    }

    @Test
    @DisplayName("setId modifie l'identifiant")
    void setId_nouvelId_idModifie() {
        // Given
        assertEquals("P001", paiement.getId());

        // When
        paiement.setId("P002");

        // Then
        assertEquals("P002", paiement.getId());
    }

    @Test
    @DisplayName("getDateTransaction retourne la date de transaction")
    void getDateTransaction_dateValide_retourneDate() {
        // Given

        // When
        LocalDateTime date = paiement.getDateTransaction();

        // Then
        assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), date);
    }

    @Test
    @DisplayName("setDateTransaction modifie la date")
    void setDateTransaction_nouvelleDate_dateModifiee() {
        // Given
        LocalDateTime nouvelleDate = LocalDateTime.of(2025, 7, 1, 14, 0);

        // When
        paiement.setDateTransaction(nouvelleDate);

        // Then
        assertEquals(nouvelleDate, paiement.getDateTransaction());
    }

    @Test
    @DisplayName("getMontant retourne le montant")
    void getMontant_montantValide_retourneMontant() {
        // Given

        // When
        double montant = paiement.getMontant();

        // Then
        assertEquals(50_000.0, montant, 0.01);
    }

    @Test
    @DisplayName("setMontant modifie le montant")
    void setMontant_nouveauMontant_montantModifie() {
        // Given
        assertEquals(50_000.0, paiement.getMontant(), 0.01);

        // When
        paiement.setMontant(75_000.0);

        // Then
        assertEquals(75_000.0, paiement.getMontant(), 0.01);
    }

    @Test
    @DisplayName("getNomDuPayeur retourne le nom du payeur")
    void getNomDuPayeur_nomValide_retourneNom() {
        // Given

        // When
        String nom = paiement.getNomDuPayeur();

        // Then
        assertEquals("Rabe", nom);
    }

    @Test
    @DisplayName("setNomDuPayeur modifie le nom du payeur")
    void setNomDuPayeur_nouveauNom_nomModifie() {
        // Given
        assertEquals("Rabe", paiement.getNomDuPayeur());

        // When
        paiement.setNomDuPayeur("Jean");

        // Then
        assertEquals("Jean", paiement.getNomDuPayeur());
    }

    @Test
    @DisplayName("TypeDePaiments avec montant nul fonctionne")
    void constructeur_montantZero_initialisationCorrecte() {
        // Given
        PaiementEspece paiementZero = new PaiementEspece(
                "P003", LocalDateTime.now(), 0.0, "Client", 0.0, 0.0
        );

        // When
        double montant = paiementZero.getMontant();

        // Then
        assertEquals(0.0, montant, 0.01);
    }
}
