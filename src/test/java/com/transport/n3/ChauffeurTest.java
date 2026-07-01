package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests — Classe Chauffeur")
class ChauffeurTest {

    private Chauffeur chauffeur;
    private Voyage voyage1;
    private Voyage voyage2;

    @BeforeEach
    void setUp() {
        chauffeur = new Chauffeur("C001", "Rakoto", "Jean", "0341111111", Sexe.MASCULIN, 600_000.0, "B");

        Trajet trajet = new Trajet("T1", Ville.ANTANANARIVO, Ville.TOAMASINA, 8, 50_000.0);

        voyage1 = new Voyage(
                "V1",
                LocalDateTime.of(2025, 6, 1, 8, 0),
                LocalDateTime.of(2025, 6, 1, 16, 0),
                trajet,
                null,
                chauffeur
        );

        voyage2 = new Voyage(
                "V2",
                LocalDateTime.of(2025, 6, 5, 8, 0),
                LocalDateTime.of(2025, 6, 5, 16, 0),
                trajet,
                null,
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
