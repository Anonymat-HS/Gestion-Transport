package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Trajet")
class TrajetTest {

    private Trajet trajet;

    @BeforeEach
    void setUp() {
        trajet = new Trajet("T1", Ville.ANTANANARIVO, Ville.MORONDAVA, 14, 70000.0);
    }

    @Test
    @DisplayName("Constructor initializes all fields correctly")
    void testConstructor() {
        // GIVEN
        String id = "T2";
        Ville depart = Ville.TOAMASINA;
        Ville arrive = Ville.FIANARANTSOA;
        int duree = 8;
        double prix = 45000.0;

        // WHEN
        Trajet t = new Trajet(id, depart, arrive, duree, prix);

        // THEN
        assertEquals(id, t.getId());
        assertEquals(depart, t.getVilleDepart());
        assertEquals(arrive, t.getVilleArrive());
        assertEquals(duree, t.getDureeEstime());
        assertEquals(prix, t.getPrixBase());
    }

    @Test
    @DisplayName("Getters return correct values")
    void testGetters() {
        // GIVEN / WHEN / THEN
        assertEquals("T1", trajet.getId());
        assertEquals(Ville.ANTANANARIVO, trajet.getVilleDepart());
        assertEquals(Ville.MORONDAVA, trajet.getVilleArrive());
        assertEquals(14, trajet.getDureeEstime());
        assertEquals(70000.0, trajet.getPrixBase());
    }

    @Test
    @DisplayName("Setters update fields correctly")
    void testSetters() {
        // GIVEN
        trajet.setId("T-EDIT");
        trajet.setVilleDepart(Ville.MAHAJANGA);
        trajet.setVilleArrive(Ville.TOLIARA);
        trajet.setDureeEstime(10);
        trajet.setPrixBase(55000.0);

        // THEN
        assertEquals("T-EDIT", trajet.getId());
        assertEquals(Ville.MAHAJANGA, trajet.getVilleDepart());
        assertEquals(Ville.TOLIARA, trajet.getVilleArrive());
        assertEquals(10, trajet.getDureeEstime());
        assertEquals(55000.0, trajet.getPrixBase());
    }

    @Test
    @DisplayName("getPrix returns prizeBase for LITE (default case)")
    void testGetPrixLite() {
        // GIVEN / WHEN
        double prix = trajet.getPrix(TypeVoiture.LITE);

        // THEN
        assertEquals(70000.0, prix, 0.001);
    }

    @Test
    @DisplayName("getPrix returns prixBase * 1.3 for PREMIUM")
    void testGetPrixPremium() {
        // GIVEN / WHEN
        double prix = trajet.getPrix(TypeVoiture.PREMIUM);

        // THEN
        assertEquals(70000.0 * 1.3, prix, 0.001);
    }

    @Test
    @DisplayName("getPrix returns prixBase * 1.7 for VIP")
    void testGetPrixVip() {
        // GIVEN / WHEN
        double prix = trajet.getPrix(TypeVoiture.VIP);

        // THEN
        assertEquals(70000.0 * 1.7, prix, 0.001);
    }

    @Test
    @DisplayName("getPrix returns prixBase * 2.2 for VVIP")
    void testGetPrixVvip() {
        // GIVEN / WHEN
        double prix = trajet.getPrix(TypeVoiture.VVIP);

        // THEN
        assertEquals(70000.0 * 2.2, prix, 0.001);
    }

    @Test
    @DisplayName("equals returns true when same object reference")
    void testEqualsSameReference() {
        // GIVEN / WHEN / THEN
        assertEquals(trajet, trajet);
    }

    @Test
    @DisplayName("equals returns true when two Trajets have the same id")
    void testEqualsSameId() {
        // GIVEN
        Trajet sameId = new Trajet("T1", Ville.TOAMASINA, Ville.ANTSIRABE, 5, 30000.0);

        // WHEN / THEN
        assertEquals(trajet, sameId);
    }

    @Test
    @DisplayName("equals returns false when two Trajets have different ids")
    void testEqualsDifferentId() {
        // GIVEN
        Trajet different = new Trajet("T99", Ville.ANTANANARIVO, Ville.MORONDAVA, 14, 70000.0);

        // WHEN / THEN
        assertNotEquals(trajet, different);
    }

    @Test
    @DisplayName("equals returns false when compared to null")
    void testEqualsNull() {
        // GIVEN / WHEN / THEN
        assertNotNull(trajet);
        assertFalse(trajet.equals(null));
    }

    @Test
    @DisplayName("equals returns false when compared to different class")
    void testEqualsDifferentClass() {
        // GIVEN / WHEN / THEN
        assertFalse(trajet.equals("T1"));
    }

    @Test
    @DisplayName("hashCode is consistent across multiple calls")
    void testHashCodeConsistency() {
        // GIVEN
        int hash1 = trajet.hashCode();
        int hash2 = trajet.hashCode();
        int hash3 = trajet.hashCode();

        // THEN
        assertEquals(hash1, hash2);
        assertEquals(hash2, hash3);
    }

    @Test
    @DisplayName("hashCode returns same value for equal objects")
    void testHashCodeEqualObjects() {
        // GIVEN
        Trajet sameId = new Trajet("T1", Ville.DIEGO, Ville.MAHAJANGA, 3, 20000.0);

        // WHEN / THEN
        assertEquals(trajet.hashCode(), sameId.hashCode());
    }

    @Test
    @DisplayName("hashCode returns different values for objects with different ids")
    void testHashCodeDifferentIds() {
        // GIVEN
        Trajet different = new Trajet("T2", Ville.ANTANANARIVO, Ville.MORONDAVA, 14, 70000.0);

        // WHEN / THEN
        assertNotEquals(trajet.hashCode(), different.hashCode());
    }

    @Test
    @DisplayName("Constructor handles negative dureeEstime")
    void testConstructorNegativeDuree() {
        // GIVEN
        Trajet t = new Trajet("T3", Ville.ANTANANARIVO, Ville.TOAMASINA, -1, 50000.0);

        // THEN
        assertEquals(-1, t.getDureeEstime());
    }

    @Test
    @DisplayName("Constructor handles zero prixBase")
    void testConstructorZeroPrix() {
        // GIVEN
        Trajet t = new Trajet("T4", Ville.ANTANANARIVO, Ville.TOAMASINA, 5, 0.0);

        // THEN
        assertEquals(0.0, t.getPrixBase());
    }
}
