package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {
    private Voyageur voyageur;
    private Voyage voyage;
    private Place place;
    private Bagage bagage;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        Trajet trajet = new Trajet(
                "T1",
                Ville.ANTANANARIVO,
                Ville.MORONDAVA,
                14,
                70.000
        );

        voyage = new Voyage(
                "V1",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(8),
                trajet,
                null,
                null
        );

        voyageur = new Voyageur(
                "1",
                "RAJAO",
                "Philipe",
                "034 46 872 09",
                "philiperajao@gmail.com",
                null,
                null,
                new ArrayList<>()
        );

        place = new Place(1, TypeVoiture.VVIP);

        bagage = new Bagage(
                "B1",
                "Valise",
                25,
                15.000
        );

        reservation = new Reservation(
                "R1",
                LocalDateTime.now(),
                LocalDate.now(),
                voyageur,
                voyage,
                place,
                StatutDeReservation.PAYEE,
                85.000,
                bagage
        );
    }

    @Test
    public void testAnnuler() {
        reservation.annuler();

        assertEquals(StatutDeReservation.ANNULEE, reservation.getStatut());
    }

    @Test
    public void testAjouterPaiement() {

        PaiementEspece paiement = new PaiementEspece(
                "P1",
                LocalDateTime.now(),
                160000,
                "Jean",
                145000,
                15000
        );

        reservation.ajouterPaiement(paiement);

        assertEquals(paiement, reservation.getPaiements());
    }

    @Test
    public void testEstPayee() {

        PaiementEspece paiement = new PaiementEspece(
                "P2",
                LocalDateTime.now(),
                245000,
                "Jean",
                250000,
                5000
        );

        reservation.ajouterPaiement(paiement);

        assertTrue(reservation.estPayee());
    }

    @Test
    public void testEstPayeeSansPaiement() {

        assertFalse(reservation.estPayee());
    }
}