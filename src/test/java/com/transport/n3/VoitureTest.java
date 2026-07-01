package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        // Given + When : création dans setUp()

        // Then
        assertEquals("V001", voitureLite.getId());
        assertEquals("1234-TBA", voitureLite.getNumeroMatricule());
        assertEquals(TypeVoiture.LITE, voitureLite.getTypeVoiture());
        assertEquals(chauffeur, voitureLite.getChauffeur());
        assertNotNull(voitureLite.getPlaces());
    }

    @Test
    @DisplayName("getNbrePlace retourne le nombre de places selon le type")
    void getNbrePlace_typeLite_retourne16() {
        // Given : TypeVoiture.LITE a 16 places

        // When
        int nombrePlaces = voitureLite.getNbrePlace();

        // Then
        assertEquals(16, nombrePlaces);
    }

    @Test
    @DisplayName("getNbrePlace pour VIP retourne 8 places")
    void getNbrePlace_typeVIP_retourne8() {
        // Given : TypeVoiture.VIP a 8 places

        // When
        int nombrePlaces = voitureVIP.getNbrePlace();

        // Then
        assertEquals(8, nombrePlaces);
    }

    @Test
    @DisplayName("getPlacesDisponibles retourne toutes les places quand tout est dispo")
    void getPlacesDisponibles_toutDispo_retourneToutesLesPlaces() {
        // Given : toutes les places sont disponibles par défaut

        // When
        List<Place> placesDispo = voitureLite.getPlacesDisponibles(voyage);

        // Then
        assertEquals(16, placesDispo.size());
    }

    @Test
    @DisplayName("getPlacesDisponibles exclut les places marquées indisponibles")
    void getPlacesDisponibles_certainesIndisponibles_retourneSeulementDisponibles() {
        // Given : on rend 3 places indisponibles
        voitureLite.getPlaces().get(0).setDisponible(false);
        voitureLite.getPlaces().get(5).setDisponible(false);
        voitureLite.getPlaces().get(10).setDisponible(false);

        // When
        List<Place> placesDispo = voitureLite.getPlacesDisponibles(voyage);

        // Then
        assertEquals(13, placesDispo.size());
    }

    @Test
    @DisplayName("setChauffeur modifie le chauffeur")
    void setChauffeur_nouveauChauffeur_chauffeurModifie() {
        // Given
        Chauffeur nouveauChauffeur = new Chauffeur("C002", "Rasoa", "Marie", "0342222222", Sexe.FEMININ, 500_000.0, "B");

        // When
        voitureLite.setChauffeur(nouveauChauffeur);

        // Then
        assertEquals(nouveauChauffeur, voitureLite.getChauffeur());
    }

    @Test
    @DisplayName("Voiture avec chauffeur null fonctionne")
    void constructeur_chauffeurNull_initialisationCorrecte() {
        // Given
        Voiture voitureSansChauffeur = new Voiture("V003", "9999-TBA", TypeVoiture.VVIP, null);

        // When
        Chauffeur chauf = voitureSansChauffeur.getChauffeur();

        // Then
        assertNull(chauf);
        assertEquals(8, voitureSansChauffeur.getNbrePlace());
    }
}
