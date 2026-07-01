package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
        // Given + When : création dans setUp()

        // Then : attributs hérités
        assertEquals("P001", paiementEspece.getId());
        assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), paiementEspece.getDateTransaction());
        assertEquals(50_000.0, paiementEspece.getMontant(), 0.01);
        assertEquals("Rabe", paiementEspece.getNomDuPayeur());

        // Then : attributs spécifiques
        assertEquals(60_000.0, paiementEspece.getMontantRemis(), 0.01);
        assertEquals(10_000.0, paiementEspece.getMonnaieRendue(), 0.01);
    }

    @Test
    @DisplayName("getMontantRemis retourne le montant remis")
    void getMontantRemis_montantRemisValide_retourneValeur() {
        // Given

        // When
        double result = paiementEspece.getMontantRemis();

        // Then
        assertEquals(60_000.0, result, 0.01);
    }

    @Test
    @DisplayName("getMonnaieRendue retourne la monnaie rendue")
    void getMonnaieRendue_monnaieRendueValide_retourneValeur() {
        // Given

        // When
        double result = paiementEspece.getMonnaieRendue();

        // Then
        assertEquals(10_000.0, result, 0.01);
    }

    @Test
    @DisplayName("setMontantRemis modifie le montant remis")
    void setMontantRemis_nouvelleValeur_montantRemisModifie() {
        // Given
        assertEquals(60_000.0, paiementEspece.getMontantRemis(), 0.01);

        // When
        paiementEspece.setMontantRemis(70_000.0);

        // Then
        assertEquals(70_000.0, paiementEspece.getMontantRemis(), 0.01);
    }

    @Test
    @DisplayName("setMonnaieRendue modifie la monnaie rendue")
    void setMonnaieRendue_nouvelleValeur_monnaieRendueModifiee() {
        // Given
        assertEquals(10_000.0, paiementEspece.getMonnaieRendue(), 0.01);

        // When
        paiementEspece.setMonnaieRendue(5_000.0);

        // Then
        assertEquals(5_000.0, paiementEspece.getMonnaieRendue(), 0.01);
    }

    @Test
    @DisplayName("PaiementEspece avec monnaie rendue à 0 fonctionne correctement")
    void constructeur_monnaieRendueZero_initialisationCorrecte() {
        // Given
        PaiementEspece paiementExact = new PaiementEspece(
                "P002", LocalDateTime.now(), 30_000.0, "Client", 30_000.0, 0.0
        );

        // When
        double monnaie = paiementExact.getMonnaieRendue();

        // Then
        assertEquals(0.0, monnaie, 0.01);
    }
}
