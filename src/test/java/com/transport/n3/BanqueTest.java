package com.transport.n3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests - Banque enum")
public class BanqueTest {

    @Test
    @DisplayName("Banque contient 2 valeurs")
    void banque_contient2valeurs() {
        // GIVEN
        Banque[] values = Banque.values();
        // WHEN & THEN
        assertEquals(2, values.length);
    }

    @Test
    @DisplayName("Banque contient BRED et BOA")
    void banque_contientValeursAttendues() {
        // GIVEN / WHEN / THEN
        assertTrue(contains(Banque.BRED));
        assertTrue(contains(Banque.BOA));
    }

    @Test
    @DisplayName("BRED name() retourne BRED")
    void bred_name() {
        // GIVEN
        Banque banque = Banque.BRED;
        // WHEN
        String name = banque.name();
        // THEN
        assertEquals("BRED", name);
    }

    @Test
    @DisplayName("BOA name() retourne BOA")
    void boa_name() {
        // GIVEN
        Banque banque = Banque.BOA;
        // WHEN
        String name = banque.name();
        // THEN
        assertEquals("BOA", name);
    }

    private boolean contains(Banque target) {
        for (Banque b : Banque.values()) {
            if (b == target) return true;
        }
        return false;
    }
}
