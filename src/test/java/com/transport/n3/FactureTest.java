package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Facture")
class FactureTest {

    private Facture facture;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        facture = new Facture("F1", "Caisse-01", 15000.0, reservation);
    }

    @Test
    @DisplayName("Constructor initializes all fields correctly")
    void testConstructor() {
        // GIVEN
        String id = "F2";
        String caisse = "Caisse-02";
        double montant = 25000.0;
        Reservation resa = new Reservation();

        // WHEN
        Facture f = new Facture(id, caisse, montant, resa);

        // THEN
        assertEquals(id, f.getId());
        assertEquals(caisse, f.getNumeroCaisse());
        assertEquals(montant, f.getMontantTotalPaye());
        assertEquals(resa, f.getReservation());
    }

    @Test
    @DisplayName("Constructor allows null reservation")
    void testConstructorNullReservation() {
        // GIVEN
        Facture f = new Facture("F3", "Caisse-03", 0.0, null);

        // THEN
        assertNull(f.getReservation());
    }

    @Test
    @DisplayName("Constructor allows zero montant")
    void testConstructorZeroMontant() {
        // GIVEN
        Facture f = new Facture("F4", "Caisse-04", 0.0, null);

        // THEN
        assertEquals(0.0, f.getMontantTotalPaye());
    }

    @Test
    @DisplayName("Getters return correct values")
    void testGetters() {
        // GIVEN / WHEN / THEN
        assertEquals("F1", facture.getId());
        assertEquals("Caisse-01", facture.getNumeroCaisse());
        assertEquals(15000.0, facture.getMontantTotalPaye());
        assertEquals(reservation, facture.getReservation());
    }

    @Test
    @DisplayName("Setters update fields correctly")
    void testSetters() {
        // GIVEN
        Reservation newResa = new Reservation();
        facture.setId("F-EDIT");
        facture.setNumeroCaisse("Caisse-99");
        facture.setMontantTotalPaye(99999.0);
        facture.setReservation(newResa);

        // THEN
        assertEquals("F-EDIT", facture.getId());
        assertEquals("Caisse-99", facture.getNumeroCaisse());
        assertEquals(99999.0, facture.getMontantTotalPaye());
        assertEquals(newResa, facture.getReservation());
    }

    @Test
    @DisplayName("Setter allows null reservation")
    void testSetNullReservation() {
        // GIVEN
        facture.setReservation(null);

        // THEN
        assertNull(facture.getReservation());
    }

    @Test
    @DisplayName("Setter allows negative montant")
    void testSetNegativeMontant() {
        // GIVEN
        facture.setMontantTotalPaye(-100.0);

        // THEN
        assertEquals(-100.0, facture.getMontantTotalPaye());
    }
}
