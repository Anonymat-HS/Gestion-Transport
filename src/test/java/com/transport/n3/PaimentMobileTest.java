package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
        // Given + When : création dans setUp()

        // Then : attributs hérités
        assertEquals("P001", paimentMobile.getId());
        assertEquals(LocalDateTime.of(2025, 6, 1, 10, 30), paimentMobile.getDateTransaction());
        assertEquals(25_000.0, paimentMobile.getMontant(), 0.01);
        assertEquals("Rabe", paimentMobile.getNomDuPayeur());

        // Then : attributs spécifiques
        assertEquals("0341234567", paimentMobile.getNumero());
        assertEquals("REF-2025-001", paimentMobile.getReferenceDePaiement());
        assertEquals(Operateur.ORANGE, paimentMobile.getOperateur());
    }

    @Test
    @DisplayName("getNumero retourne le numéro de téléphone")
    void getNumero_numeroValide_retourneNumero() {
        // Given

        // When
        String result = paimentMobile.getNumero();

        // Then
        assertEquals("0341234567", result);
    }

    @Test
    @DisplayName("getReferenceDePaiement retourne la référence")
    void getReferenceDePaiement_referenceValide_retourneReference() {
        // Given

        // When
        String result = paimentMobile.getReferenceDePaiement();

        // Then
        assertEquals("REF-2025-001", result);
    }

    @Test
    @DisplayName("getOperateur retourne l'opérateur")
    void getOperateur_operateurValide_retourneOperateur() {
        // Given

        // When
        Operateur result = paimentMobile.getOperateur();

        // Then
        assertEquals(Operateur.ORANGE, result);
    }

    @Test
    @DisplayName("setNumero modifie le numéro")
    void setNumero_nouveauNumero_numeroModifie() {
        // Given
        assertEquals("0341234567", paimentMobile.getNumero());

        // When
        paimentMobile.setNumero("0347654321");

        // Then
        assertEquals("0347654321", paimentMobile.getNumero());
    }

    @Test
    @DisplayName("setReferenceDePaiement modifie la référence")
    void setReferenceDePaiement_nouvelleReference_referenceModifiee() {
        // Given
        assertEquals("REF-2025-001", paimentMobile.getReferenceDePaiement());

        // When
        paimentMobile.setReferenceDePaiement("REF-2025-002");

        // Then
        assertEquals("REF-2025-002", paimentMobile.getReferenceDePaiement());
    }

    @Test
    @DisplayName("setOperateur modifie l'opérateur")
    void setOperateur_nouvelOperateur_operateurModifie() {
        // Given
        assertEquals(Operateur.ORANGE, paimentMobile.getOperateur());

        // When
        paimentMobile.setOperateur(Operateur.YAS);

        // Then
        assertEquals(Operateur.YAS, paimentMobile.getOperateur());
    }

    @Test
    @DisplayName("PaimentMobile avec operateur YAS fonctionne correctement")
    void constructeur_operateurYAS_initialisationCorrecte() {
        // Given
        PaimentMobile paiementYAS = new PaimentMobile(
                "P002", LocalDateTime.now(), 30_000.0, "Client",
                "0321111111", "REF-2025-002", Operateur.YAS
        );

        // When
        Operateur operateur = paiementYAS.getOperateur();

        // Then
        assertEquals(Operateur.YAS, operateur);
    }
}
