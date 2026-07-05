package com.transport.n3;

import com.transport.n3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VoyageurTest {


    private Voyageur voyageur;
    private Reservation reservation;
    private Voyage planDeVoyage;
    private Trajet trajet;
    private Place place;
    private TypeDePaiments paiement;
    private Bagage bagage;

    @BeforeEach
    void setUp() {

        // =========================
        // 1. Voyageur
        // =========================
        voyageur = new Voyageur("VG1", "Rabe", "Paul", "0349999999", "rabe@gmail.com", null, null, null);

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
        bagage = new Bagage("B1", "Valise cabine", 15.0, 10.0);

        // =========================
        // 3. PlanDeVoyage
        // =========================
        planDeVoyage = new Voyage(
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
                "RES1",
                LocalDateTime.now(),
                LocalDate.of(2025, 6, 1),
                voyageur,
                planDeVoyage,
                place,
                StatutDeReservation.PAYEE,
                50000.0,
                bagage
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

        // réservation NON ajoutée
        var res = new Reservation(
                "RES2", // ✅ ID différent
                LocalDateTime.now(),
                LocalDate.of(2026, 8, 1),
                voyageur,
                planDeVoyage,
                place,
                StatutDeReservation.PAYEE,
                50000.0,
                bagage
        );

        assertThrows(IllegalStateException.class, () -> {
            voyageur.annulerReservation(res);
        });
    }

    @Test
    void getNbreTotalVoyages_shouldReturnCorrectSize() {

        var res1 = new Reservation(
                "RES3", // ✅ ID différent
                LocalDateTime.now(),
                LocalDate.of(2025, 7, 10),
                voyageur,
                planDeVoyage,
                place,
                StatutDeReservation.PAYEE,
                50000.0,
                bagage
        );

        var res2 = new Reservation(
                "RES4", // ✅ ID différent
                LocalDateTime.now(),
                LocalDate.of(2026, 8, 1),
                voyageur,
                planDeVoyage,
                place,
                StatutDeReservation.PAYEE,
                50000.0,
                bagage
        );

        voyageur.faireReservation(res1);
        voyageur.faireReservation(res2);

        assertEquals(2, voyageur.getNbreTotalVoyages());
    }


}
