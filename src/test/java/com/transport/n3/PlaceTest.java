package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Place")
class PlaceTest {

    private Place place;

    @BeforeEach
    void setUp() {
        place = new Place(1, TypeVoiture.LITE);
    }

    @Test
    @DisplayName("Two-parameter constructor initializes fields correctly")
    void testTwoParamConstructor() {
        // GIVEN
        int numero = 5;
        TypeVoiture type = TypeVoiture.VIP;

        // WHEN
        Place p = new Place(numero, type);

        // THEN
        assertEquals(numero, p.getNumeroPlace());
        assertEquals(type, p.getTypeVoiture());
        assertTrue(p.isPlaceDispo());
        assertTrue(p.isDisponible());
        assertTrue(p.estDispo());
    }

    @Test
    @DisplayName("Four-parameter constructor sets disponible to true regardless of parameter")
    void testFourParamConstructorIgnoresDisponibleParam() {
        // GIVEN
        int numero = 10;
        TypeVoiture type = TypeVoiture.PREMIUM;

        // WHEN — passing false for disponible but constructor sets it to true
        Place p = new Place(numero, type, false, false);

        // THEN
        assertEquals(numero, p.getNumeroPlace());
        assertEquals(type, p.getTypeVoiture());
        assertFalse(p.isPlaceDispo());       // from 3rd param
        assertTrue(p.isDisponible());        // constructor forces true
        assertTrue(p.estDispo());            // estDispo() returns disponible
    }

    @Test
    @DisplayName("Getters return correct values")
    void testGetters() {
        // GIVEN / WHEN / THEN
        assertEquals(1, place.getNumeroPlace());
        assertEquals(TypeVoiture.LITE, place.getTypeVoiture());
        assertTrue(place.isPlaceDispo());
        assertTrue(place.isDisponible());
    }

    @Test
    @DisplayName("Setters update fields correctly")
    void testSetters() {
        // GIVEN
        place.setNumeroPlace(99);
        place.setTypeVoiture(TypeVoiture.VVIP);
        place.setPlaceDispo(false);
        place.setDisponible(false);

        // THEN
        assertEquals(99, place.getNumeroPlace());
        assertEquals(TypeVoiture.VVIP, place.getTypeVoiture());
        assertFalse(place.isPlaceDispo());
        assertFalse(place.isDisponible());
    }

    @Test
    @DisplayName("estDispo returns true when disponible is true")
    void testEstDispoTrue() {
        // GIVEN
        place.setDisponible(true);

        // WHEN / THEN
        assertTrue(place.estDispo());
    }

    @Test
    @DisplayName("estDispo returns false when disponible is false")
    void testEstDispoFalse() {
        // GIVEN
        place.setDisponible(false);

        // WHEN / THEN
        assertFalse(place.estDispo());
    }

    @Test
    @DisplayName("estDispo returns true by default after construction")
    void testEstDispoDefaultTrue() {
        // GIVEN / WHEN
        Place p = new Place(1, TypeVoiture.LITE);

        // THEN
        assertTrue(p.estDispo());
    }

    @Test
    @DisplayName("Constructor accepts zero for numeroPlace")
    void testConstructorZeroNumero() {
        // GIVEN
        Place p = new Place(0, TypeVoiture.LITE);

        // THEN
        assertEquals(0, p.getNumeroPlace());
    }

    @Test
    @DisplayName("Constructor accepts negative numeroPlace")
    void testConstructorNegativeNumero() {
        // GIVEN
        Place p = new Place(-1, TypeVoiture.LITE);

        // THEN
        assertEquals(-1, p.getNumeroPlace());
    }

    @Test
    @DisplayName("Constructor accepts null TypeVoiture")
    void testConstructorNullType() {
        // GIVEN
        Place p = new Place(1, null);

        // THEN
        assertNull(p.getTypeVoiture());
    }
}
