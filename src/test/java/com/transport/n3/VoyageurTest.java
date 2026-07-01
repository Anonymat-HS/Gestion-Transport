package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
                java.time.LocalDateTime.of(2025, 6, 1, 8, 0),
                java.time.LocalDateTime.of(2025, 6, 1, 16, 0),
                trajet,
                voiture,
                chauffeur
        );

        reservation = new Reservation(
                "R1",
                java.time.LocalDateTime.now(),
                java.time.LocalDate.of(2025, 6, 1),
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
        // Given + When : création dans setUp()

        // Then
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
        // Given
        assertEquals("VG001", voyageur.getId());

        // When
        voyageur.setId("VG002");

        // Then
        assertEquals("VG002", voyageur.getId());
    }

    @Test
    @DisplayName("setNom modifie le nom")
    void setNom_nouveauNom_nomModifie() {
        // Given
        assertEquals("Rakoto", voyageur.getNom());

        // When
        voyageur.setNom("Rasoa");

        // Then
        assertEquals("Rasoa", voyageur.getNom());
    }

    @Test
    @DisplayName("setPrenom modifie le prénom")
    void setPrenom_nouveauPrenom_prenomModifie() {
        // Given
        assertEquals("Jean", voyageur.getPrenom());

        // When
        voyageur.setPrenom("Marie");

        // Then
        assertEquals("Marie", voyageur.getPrenom());
    }

    @Test
    @DisplayName("setTelephone modifie le téléphone")
    void setTelephone_nouveauTelephone_telephoneModifie() {
        // Given
        assertEquals("0341234567", voyageur.getTelephone());

        // When
        voyageur.setTelephone("0347654321");

        // Then
        assertEquals("0347654321", voyageur.getTelephone());
    }

    @Test
    @DisplayName("setEmail modifie l'email")
    void setEmail_nouvelEmail_emailModifie() {
        // Given
        assertEquals("jean.rakoto@email.com", voyageur.getEmail());

        // When
        voyageur.setEmail("nouveau@email.com");

        // Then
        assertEquals("nouveau@email.com", voyageur.getEmail());
    }

    @Test
    @DisplayName("faireReservation associe une réservation au voyageur")
    void faireReservation_reservationValide_reservationAssociee() {
        // Given : un nouveau voyageur sans réservation
        Voyageur nouveauVoyageur = new Voyageur(
                "VG002", "Rasoa", "Marie", "0347654321", "marie@email.com",
                null, null, new ArrayList<>()
        );
        assertNull(nouveauVoyageur.getReservation());

        // When
        nouveauVoyageur.faireReservation(reservation);

        // Then
        assertEquals(reservation, nouveauVoyageur.getReservation());
    }

    @Test
    @DisplayName("setReservation modifie la réservation")
    void setReservation_nouvelleReservation_reservationModifiee() {
        // Given
        Reservation autreReservation = new Reservation();

        // When
        voyageur.setReservation(autreReservation);

        // Then
        assertEquals(autreReservation, voyageur.getReservation());
    }

    @Test
    @DisplayName("setTicket modifie le ticket")
    void setTicket_nouveauTicket_ticketModifie() {
        // Given
        Ticket ticket = new Ticket(
                "TK1",
                java.time.LocalDateTime.now(),
                java.time.LocalDateTime.now().plusHours(8),
                voyageur,
                null,
                null
        );

        // When
        voyageur.setTicket(ticket);

        // Then
        assertEquals(ticket, voyageur.getTicket());
    }

    @Test
    @DisplayName("setListeBagage modifie la liste des bagages")
    void setListeBagage_nouvelleListe_listeModifiee() {
        // Given
        Bagage bagage = new Bagage("B1", "Sac", 10.0, 0.0);
        List<Bagage> nouveauxBagages = new ArrayList<>(List.of(bagage));

        // When
        voyageur.setListeBagage(nouveauxBagages);

        // Then
        assertEquals(1, voyageur.getListeBagage().size());
        assertTrue(voyageur.getListeBagage().contains(bagage));
    }
}
