package com.transport.n3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests - Operateur enum")
public class OperateurTest {

    @Test
    @DisplayName("Operateur contient 2 valeurs")
    void operateur_contient2valeurs() {
        // GIVEN
        Operateur[] values = Operateur.values();
        // WHEN & THEN
        assertEquals(2, values.length);
    }

    @Test
    @DisplayName("Operateur contient ORANGE et YAS")
    void operateur_contientValeursAttendues() {
        // GIVEN / WHEN / THEN
        assertTrue(contains(Operateur.ORANGE));
        assertTrue(contains(Operateur.YAS));
    }

    @Test
    @DisplayName("ORANGE name() retourne ORANGE")
    void orange_name() {
        // GIVEN
        Operateur op = Operateur.ORANGE;
        // WHEN
        String name = op.name();
        // THEN
        assertEquals("ORANGE", name);
    }

    @Test
    @DisplayName("YAS name() retourne YAS")
    void yas_name() {
        // GIVEN
        Operateur op = Operateur.YAS;
        // WHEN
        String name = op.name();
        // THEN
        assertEquals("YAS", name);
    }

    private boolean contains(Operateur target) {
        for (Operateur o : Operateur.values()) {
            if (o == target) return true;
        }
        return false;
    }
}
