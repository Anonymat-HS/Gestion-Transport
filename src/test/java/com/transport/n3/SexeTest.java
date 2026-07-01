package com.transport.n3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests - Sexe enum")
public class SexeTest {

    @Test
    @DisplayName("Sexe contient 3 valeurs")
    void sexe_contient3valeurs() {
        // GIVEN
        Sexe[] values = Sexe.values();
        // WHEN & THEN
        assertEquals(3, values.length);
    }

    @Test
    @DisplayName("Sexe contient MASCULIN, FEMININ, AUTRE")
    void sexe_contientValeursAttendues() {
        // GIVEN / WHEN / THEN
        assertTrue(contains(Sexe.MASCULIN));
        assertTrue(contains(Sexe.FEMININ));
        assertTrue(contains(Sexe.AUTRE));
    }

    @Test
    @DisplayName("Tous les sexes sont non null")
    void tousLesSexes_sontNonNull() {
        // GIVEN
        Sexe[] values = Sexe.values();
        // WHEN & THEN
        for (Sexe s : values) {
            assertNotNull(s);
        }
    }

    private boolean contains(Sexe target) {
        for (Sexe s : Sexe.values()) {
            if (s == target) return true;
        }
        return false;
    }
}
