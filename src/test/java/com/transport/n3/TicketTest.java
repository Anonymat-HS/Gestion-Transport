package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ticket")
class TicketTest {

    private Ticket ticket;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;
    private Voyageur voyageur;
    private Place place;
    private Voiture voiture;

    @BeforeEach
    void setUp() {
        dateDepart = LocalDateTime.of(2026, 8, 15, 8, 0);
        dateArrivee = LocalDateTime.of(2026, 8, 15, 14, 0);
        voyageur = new Voyageur("V1", "RAKOTO", "Paul", "0341234567",
                "paul.rakoto@email.com", null, null, null);
        place = new Place(12, TypeVoiture.PREMIUM);
        voiture = new Voiture("VOIT1", "1234TAB", TypeVoiture.PREMIUM, null);
        ticket = new Ticket("T1", dateDepart, dateArrivee, voyageur, place, voiture);
    }

    @Test
    @DisplayName("Constructor initializes all fields correctly")
    void testConstructor() {
        // GIVEN
        LocalDateTime dep = LocalDateTime.of(2026, 9, 1, 10, 0);
        LocalDateTime arr = LocalDateTime.of(2026, 9, 1, 18, 0);
        Voyageur voy = new Voyageur("V2", "RABE", "Alice", "0347654321",
                "alice.rabe@email.com", null, null, null);
        Place pl = new Place(5, TypeVoiture.VVIP);
        Voiture voit = new Voiture("VOIT2", "5678TAB", TypeVoiture.VVIP, null);

        // WHEN
        Ticket t = new Ticket("T2", dep, arr, voy, pl, voit);

        // THEN
        assertEquals("T2", t.getId());
        assertEquals(dep, t.getDateDepart());
        assertEquals(arr, t.getDateArrivee());
        assertEquals(voy, t.getVoyageur());
        assertEquals(pl, t.getPlace());
        assertEquals(voit, t.getVoiture());
    }

    @Test
    @DisplayName("Getters return correct values")
    void testGetters() {
        // GIVEN / WHEN / THEN
        assertEquals("T1", ticket.getId());
        assertEquals(dateDepart, ticket.getDateDepart());
        assertEquals(dateArrivee, ticket.getDateArrivee());
        assertEquals(voyageur, ticket.getVoyageur());
        assertEquals(place, ticket.getPlace());
        assertEquals(voiture, ticket.getVoiture());
    }

    @Test
    @DisplayName("Setters update fields correctly")
    void testSetters() {
        // GIVEN
        LocalDateTime newDep = LocalDateTime.of(2026, 10, 1, 6, 0);
        LocalDateTime newArr = LocalDateTime.of(2026, 10, 1, 12, 0);
        Voyageur newVoy = new Voyageur("V3", "RAVELO", "Boby", "0341112233",
                "boby.ravelo@email.com", null, null, null);
        Place newPlace = new Place(8, TypeVoiture.LITE);
        Voiture newVoit = new Voiture("VOIT3", "9012TAB", TypeVoiture.LITE, null);

        ticket.setId("T-EDIT");
        ticket.setDateDepart(newDep);
        ticket.setDateArrivee(newArr);
        ticket.setVoyageur(newVoy);
        ticket.setPlace(newPlace);
        ticket.setVoiture(newVoit);

        // THEN
        assertEquals("T-EDIT", ticket.getId());
        assertEquals(newDep, ticket.getDateDepart());
        assertEquals(newArr, ticket.getDateArrivee());
        assertEquals(newVoy, ticket.getVoyageur());
        assertEquals(newPlace, ticket.getPlace());
        assertEquals(newVoit, ticket.getVoiture());
    }

    @Test
    @DisplayName("Constructor allows null id")
    void testConstructorNullId() {
        // GIVEN
        Ticket t = new Ticket(null, dateDepart, dateArrivee, voyageur, place, voiture);

        // THEN
        assertNull(t.getId());
    }

    @Test
    @DisplayName("Constructor allows null voyageur")
    void testConstructorNullVoyageur() {
        // GIVEN
        Ticket t = new Ticket("T3", dateDepart, dateArrivee, null, place, voiture);

        // THEN
        assertNull(t.getVoyageur());
    }

    @Test
    @DisplayName("Constructor allows null place")
    void testConstructorNullPlace() {
        // GIVEN
        Ticket t = new Ticket("T4", dateDepart, dateArrivee, voyageur, null, voiture);

        // THEN
        assertNull(t.getPlace());
    }

    @Test
    @DisplayName("Constructor allows null voiture")
    void testConstructorNullVoiture() {
        // GIVEN
        Ticket t = new Ticket("T5", dateDepart, dateArrivee, voyageur, place, null);

        // THEN
        assertNull(t.getVoiture());
    }

    @Test
    @DisplayName("Constructor allows null dates")
    void testConstructorNullDates() {
        // GIVEN
        Ticket t = new Ticket("T6", null, null, voyageur, place, voiture);

        // THEN
        assertNull(t.getDateDepart());
        assertNull(t.getDateArrivee());
    }

    @Test
    @DisplayName("Setter allows null reference fields")
    void testSettersNull() {
        // GIVEN
        ticket.setId(null);
        ticket.setDateDepart(null);
        ticket.setDateArrivee(null);
        ticket.setVoyageur(null);
        ticket.setPlace(null);
        ticket.setVoiture(null);

        // THEN
        assertNull(ticket.getId());
        assertNull(ticket.getDateDepart());
        assertNull(ticket.getDateArrivee());
        assertNull(ticket.getVoyageur());
        assertNull(ticket.getPlace());
        assertNull(ticket.getVoiture());
    }
}
