package com.transport.n3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests - Ville enum")
public class VilleTest {

    @Test
    @DisplayName("Ville contient 8 valeurs")
    void ville_contient8valeurs() {
        // GIVEN
        Ville[] values = Ville.values();
        // WHEN & THEN
        assertEquals(8, values.length);
    }

    @Test
    @DisplayName("Ville contient toutes les villes attendues")
    void ville_contientVillesAttendues() {
        // GIVEN / WHEN / THEN
        assertTrue(contains(Ville.ANTANANARIVO));
        assertTrue(contains(Ville.TOAMASINA));
        assertTrue(contains(Ville.MAHAJANGA));
        assertTrue(contains(Ville.TOLIARA));
        assertTrue(contains(Ville.ANTSIRABE));
        assertTrue(contains(Ville.MORONDAVA));
        assertTrue(contains(Ville.FIANARANTSOA));
        assertTrue(contains(Ville.DIEGO));
    }

    @Test
    @DisplayName("ANTANANARIVO est la capitale")
    void antananarivo_estCapitale() {
        // GIVEN
        Ville capitale = Ville.ANTANANARIVO;
        // WHEN
        String name = capitale.name();
        // THEN
        assertEquals("ANTANANARIVO", name);
    }

    @Test
    @DisplayName("Toutes les villes sont non null")
    void toutesLesVilles_sontNonNull() {
        // GIVEN
        Ville[] values = Ville.values();
        // WHEN & THEN
        for (Ville v : values) {
            assertNotNull(v);
        }
    }

    private boolean contains(Ville target) {
        for (Ville v : Ville.values()) {
            if (v == target) return true;
        }
        return false;
    }
}
