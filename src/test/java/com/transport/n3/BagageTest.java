package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bagage")
class BagageTest {

    private Bagage bagage;

    @BeforeEach
    void setUp() {
        bagage = new Bagage("B1", "Valise cabine", 15.0, 10.0);
    }

    @Test
    @DisplayName("Constructor initializes all fields correctly")
    void testConstructor() {
        // GIVEN
        String id = "B2";
        String description = "Sac voyage";
        double poids = 25.0;
        double prixSupp = 5000.0;

        // WHEN
        Bagage b = new Bagage(id, description, poids, prixSupp);

        // THEN
        assertEquals(id, b.getId());
        assertEquals(description, b.getDescription());
        assertEquals(poids, b.getPoids());
        double expectedSurcharge = (poids - 20.0) * 500.0;
        assertEquals(expectedSurcharge, b.getPrixSupplementaire(), 0.001);
    }

    @Test
    @DisplayName("Constructor recalculates prixSupplementaire via calculSurcharge()")
    void testConstructorRecalculatesPrixSupplementaire() {
        // GIVEN
        double poids = 30.0;
        double prixSuppProvided = 9999.0;

        // WHEN
        Bagage b = new Bagage("B3", "Valise", poids, prixSuppProvided);

        // THEN
        double expected = (poids - 20.0) * 500.0;
        assertEquals(expected, b.getPrixSupplementaire(), 0.001);
    }

    @Test
    @DisplayName("Getters return correct values")
    void testGetters() {
        // GIVEN
        Bagage b = new Bagage("B10", "Sac à dos", 22.0, 1000.0);

        // WHEN / THEN
        assertEquals("B10", b.getId());
        assertEquals("Sac à dos", b.getDescription());
        assertEquals(22.0, b.getPoids());
    }

    @Test
    @DisplayName("Setters update fields correctly")
    void testSetters() {
        // GIVEN
        bagage.setId("B-EDIT");
        bagage.setDescription("Nouvelle description");
        bagage.setPoids(18.0);
        bagage.setPrixSupplementaire(0.0);

        // THEN
        assertEquals("B-EDIT", bagage.getId());
        assertEquals("Nouvelle description", bagage.getDescription());
        assertEquals(18.0, bagage.getPoids());
        assertEquals(0.0, bagage.getPrixSupplementaire());
    }

    @Test
    @DisplayName("calculSurcharge returns 0 when poids <= 20.0")
    void testCalculSurchargeUnderLimit() {
        // GIVEN
        Bagage b = new Bagage("B4", "Petit sac", 15.0, 0.0);

        // WHEN
        double surcharge = b.calculSurcharge();

        // THEN
        assertEquals(0.0, surcharge, 0.001);
    }

    @Test
    @DisplayName("calculSurcharge returns (poids - 20) * 500 when poids > 20.0")
    void testCalculSurchargeOverLimit() {
        // GIVEN
        double poids = 25.0;
        Bagage b = new Bagage("B5", "Grosse valise", poids, 0.0);

        // WHEN
        double surcharge = b.calculSurcharge();

        // THEN
        double expected = (poids - 20.0) * 500.0;
        assertEquals(expected, surcharge, 0.001);
    }

    @Test
    @DisplayName("calculSurcharge handles boundary at exactly 20.0")
    void testCalculSurchargeAtBoundary() {
        // GIVEN
        Bagage b = new Bagage("B6", "Valise limite", 20.0, 0.0);

        // WHEN
        double surcharge = b.calculSurcharge();

        // THEN
        assertEquals(0.0, surcharge, 0.001);
    }

    @Test
    @DisplayName("calculSurcharge handles zero poids")
    void testCalculSurchargeZeroPoids() {
        // GIVEN
        Bagage b = new Bagage("B7", "Vide", 0.0, 0.0);

        // WHEN
        double surcharge = b.calculSurcharge();

        // THEN
        assertEquals(0.0, surcharge, 0.001);
    }

    @Test
    @DisplayName("Constructor accepts null id")
    void testConstructorNullId() {
        // GIVEN
        Bagage b = new Bagage(null, "Test", 10.0, 0.0);

        // THEN
        assertNull(b.getId());
    }

    @Test
    @DisplayName("Constructor accepts null description")
    void testConstructorNullDescription() {
        // GIVEN
        Bagage b = new Bagage("B8", null, 10.0, 0.0);

        // THEN
        assertNull(b.getDescription());
    }
}
