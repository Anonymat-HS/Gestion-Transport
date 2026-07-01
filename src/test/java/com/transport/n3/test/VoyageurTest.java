package com.transport.n3.test;

import com.transport.n3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VoyageurTest {

    private Voyageur voyageur;
    private Reservation reservation;
    private PlanDeVoyage planDeVoyage;
    private Trajet trajet;
    private Place place;
    private TypeDePaiments paiement;

    @BeforeEach
    void setUp() {

        // =========================
        // 1. Voyageur
        // =========================
        voyageur = new Voyageur();

        // =========================
        // 2. Trajet
        // =========================
        trajet = new Trajet(
                "TR001",
                Ville.ANTANANARIVO,
                Ville.TOAMASINA,
                6,
                15000.0
        );

        // =========================
        // 3. PlanDeVoyage
        // =========================
        planDeVoyage = new PlanDeVoyage(
                "PV001",
                LocalDateTime.of(2026, 7, 5, 8, 0),
                LocalDateTime.of(2026, 7, 5, 12, 0),
                trajet,
                null,
                null
        );

        // =========================
        // 4. Place
        // =========================
        place = new Place(
                1,
                TypeVoiture.LITE,
                true,
                true
        );

        place.setPlaceDispo(true);

        // =========================
        // 5. Paiement (classe concrète)
        // =========================
        paiement = new PaiementParCarte(
                "PAY001",
                LocalDateTime.now(),
                15000.0,
                "Julien",
                Banque.BOA,
                "1234567890"
        );

        // =========================
        // 6. Reservation
        // =========================
        reservation = new Reservation(
                "RES001",
                LocalDateTime.now(),
                LocalDate.of(2026, 7, 10),
                voyageur,
                planDeVoyage,
                place,
                paiement,
                StatutDeReservation.PAYEE
        );
    }

    // =========================
    // TESTS
    // =========================

    @Test
    void faireReservation_shouldAddReservation() {

        voyageur.faireReservation(reservation);

        assertEquals(1, voyageur.getNbreTotalVoyages());
    }

    @Test
    void faireReservation_shouldThrowException_whenReservationIsNull() {

        assertThrows(IllegalArgumentException.class, () -> {
            voyageur.faireReservation(null);
        });
    }

    @Test
    void faireReservation_shouldThrowException_whenReservationAlreadyExists() {

        voyageur.faireReservation(reservation);

        assertThrows(IllegalStateException.class, () -> {
            voyageur.faireReservation(reservation);
        });
    }

    @Test
    void annulerReservation_shouldRemoveReservation() {

        voyageur.faireReservation(reservation);
        voyageur.annulerReservation(reservation);

        assertEquals(0, voyageur.getNbreTotalVoyages());
    }

    @Test
    void annulerReservation_shouldThrowException_whenReservationIsNull() {

        assertThrows(IllegalArgumentException.class, () -> {
            voyageur.annulerReservation(null);
        });
    }

    @Test
    void annulerReservation_shouldThrowException_whenReservationNotFound() {

        assertThrows(IllegalStateException.class, () -> {
            voyageur.annulerReservation(reservation);
        });
    }

    @Test
    void getNbreTotalVoyages_shouldReturnCorrectSize() {

        var r1 = new Reservation(
                "RES002",
                LocalDateTime.now(),
                LocalDate.of(2026, 7, 10),
                voyageur,
                planDeVoyage,
                place,
                paiement,
                StatutDeReservation.PAYEE
        );

        var r2 = new Reservation(
                "RES003",
                LocalDateTime.now(),
                LocalDate.of(2026, 7, 11),
                voyageur,
                planDeVoyage,
                place,
                paiement,
                StatutDeReservation.PAYEE
        );

        voyageur.faireReservation(r1);
        voyageur.faireReservation(r2);

        assertEquals(2, voyageur.getNbreTotalVoyages());
    }
}