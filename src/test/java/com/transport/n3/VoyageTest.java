package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
        // Given + When : création dans setUp()

        // Then
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
        // Given
        Voyage voyageVide = new Voyage();

        // When + Then
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
        // Given
        assertEquals("VY001", voyage.getId());

        // When
        voyage.setId("VY002");

        // Then
        assertEquals("VY002", voyage.getId());
    }

    @Test
    @DisplayName("setDateDepart modifie la date de départ")
    void setDateDepart_nouvelleDate_dateDepartModifiee() {
        // Given
        LocalDateTime nouvelleDate = LocalDateTime.of(2025, 7, 1, 10, 0);

        // When
        voyage.setDateDepart(nouvelleDate);

        // Then
        assertEquals(nouvelleDate, voyage.getDateDepart());
    }

    @Test
    @DisplayName("setDateArrive modifie la date d'arrivée")
    void setDateArrive_nouvelleDate_dateArriveModifiee() {
        // Given
        LocalDateTime nouvelleDate = LocalDateTime.of(2025, 7, 1, 18, 0);

        // When
        voyage.setDateArrive(nouvelleDate);

        // Then
        assertEquals(nouvelleDate, voyage.getDateArrive());
    }

    @Test
    @DisplayName("setTrajet modifie le trajet")
    void setTrajet_nouveauTrajet_trajetModifie() {
        // Given
        Trajet nouveauTrajet = new Trajet("T2", Ville.ANTANANARIVO, Ville.MAHAJANGA, 10, 60_000.0);

        // When
        voyage.setTrajet(nouveauTrajet);

        // Then
        assertEquals(nouveauTrajet, voyage.getTrajet());
    }

    @Test
    @DisplayName("setVoiture modifie la voiture")
    void setVoiture_nouvelleVoiture_voitureModifiee() {
        // Given
        Voiture nouvelleVoiture = new Voiture("V002", "5678-TBA", TypeVoiture.VIP, null);

        // When
        voyage.setVoiture(nouvelleVoiture);

        // Then
        assertEquals(nouvelleVoiture, voyage.getVoiture());
    }

    @Test
    @DisplayName("setChauffeur modifie le chauffeur")
    void setChauffeur_nouveauChauffeur_chauffeurModifie() {
        // Given
        Chauffeur nouveauChauffeur = new Chauffeur("C002", "Rasoa", "Marie", "0342222222", Sexe.FEMININ, 500_000.0, "B");

        // When
        voyage.setChauffeur(nouveauChauffeur);

        // Then
        assertEquals(nouveauChauffeur, voyage.getChauffeur());
    }
}
