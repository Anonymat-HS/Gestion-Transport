package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
        // Given + When : création dans setUp()

        // Then : attributs hérités
        assertEquals("P001", paiementParCarte.getId());
        assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), paiementParCarte.getDateTransaction());
        assertEquals(75_000.0, paiementParCarte.getMontant(), 0.01);
        assertEquals("Rabe", paiementParCarte.getNomDuPayeur());

        // Then : attributs spécifiques
        assertEquals(Banque.BOA, paiementParCarte.getBanque());
        assertEquals("MG1234567890", paiementParCarte.getNumeroCompte());
    }

    @Test
    @DisplayName("getBanque retourne la banque")
    void getBanque_banqueValide_retourneBanque() {
        // Given

        // When
        Banque result = paiementParCarte.getBanque();

        // Then
        assertEquals(Banque.BOA, result);
    }

    @Test
    @DisplayName("getNumeroCompte retourne le numéro de compte")
    void getNumeroCompte_numeroValide_retourneNumero() {
        // Given

        // When
        String result = paiementParCarte.getNumeroCompte();

        // Then
        assertEquals("MG1234567890", result);
    }

    @Test
    @DisplayName("setBanque modifie la banque")
    void setBanque_nouvelleBanque_banqueModifiee() {
        // Given
        assertEquals(Banque.BOA, paiementParCarte.getBanque());

        // When
        paiementParCarte.setBanque(Banque.BRED);

        // Then
        assertEquals(Banque.BRED, paiementParCarte.getBanque());
    }

    @Test
    @DisplayName("setNumeroCompte modifie le numéro de compte")
    void setNumeroCompte_nouveauNumero_numeroModifie() {
        // Given
        assertEquals("MG1234567890", paiementParCarte.getNumeroCompte());

        // When
        paiementParCarte.setNumeroCompte("MG0987654321");

        // Then
        assertEquals("MG0987654321", paiementParCarte.getNumeroCompte());
    }

    @Test
    @DisplayName("PaiementParCarte avec Banque.BRED fonctionne correctement")
    void constructeur_banqueBRED_initialisationCorrecte() {
        // Given
        PaiementParCarte paiementBRED = new PaiementParCarte(
                "P002", LocalDateTime.now(), 100_000.0, "Client", Banque.BRED, "MG1111111111"
        );

        // When
        Banque banque = paiementBRED.getBanque();

        // Then
        assertEquals(Banque.BRED, banque);
    }
}
