package com.transport.n3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests - StatutDeReservation enum")
public class StatutDeReservationTest {

    @Test
    @DisplayName("StatutDeReservation contient 3 valeurs")
    void statut_contient3valeurs() {
        // GIVEN
        StatutDeReservation[] values = StatutDeReservation.values();
        // WHEN & THEN
        assertEquals(3, values.length);
    }

    @Test
    @DisplayName("StatutDeReservation contient PAYEE, ANNULEE, ATTENTE")
    void statut_contientValeursAttendues() {
        // GIVEN / WHEN / THEN
        assertTrue(contains(StatutDeReservation.PAYEE));
        assertTrue(contains(StatutDeReservation.ANNULEE));
        assertTrue(contains(StatutDeReservation.ATTENTE));
    }

    @Test
    @DisplayName("PAYEE.name() retourne PAYEE")
    void payee_name() {
        // GIVEN
        StatutDeReservation statut = StatutDeReservation.PAYEE;
        // WHEN
        String name = statut.name();
        // THEN
        assertEquals("PAYEE", name);
    }

    private boolean contains(StatutDeReservation target) {
        for (StatutDeReservation s : StatutDeReservation.values()) {
            if (s == target) return true;
        }
        return false;
    }
}
