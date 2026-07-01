package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HistoriqueAction")
class HistoriqueActionTest {

    private HistoriqueAction historique;
    private Employee employee;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.of(2026, 7, 2, 10, 30, 0);
        employee = new Receptionniste("EMP1", "Dupont", "Jean", "0123456789", Sexe.MASCULIN, 200000.0);
        historique = new HistoriqueAction("H1", "Création réservation", now, employee);
    }

    @Test
    @DisplayName("Constructor initializes all fields correctly")
    void testConstructor() {
        // GIVEN
        String id = "H2";
        String action = "Annulation";
        LocalDateTime date = LocalDateTime.of(2026, 7, 1, 15, 0);
        Employee emp = new Receptionniste("EMP2", "Martin", "Sophie", "0987654321", Sexe.FEMININ, 250000.0);

        // WHEN
        HistoriqueAction h = new HistoriqueAction(id, action, date, emp);

        // THEN
        assertEquals(id, h.getId());
        assertEquals(action, h.getAction());
        assertEquals(date, h.getDateHeure());
        assertEquals(emp, h.getEmployee());
    }

    @Test
    @DisplayName("Getters return correct values")
    void testGetters() {
        // GIVEN / WHEN / THEN
        assertEquals("H1", historique.getId());
        assertEquals("Création réservation", historique.getAction());
        assertEquals(now, historique.getDateHeure());
        assertEquals(employee, historique.getEmployee());
    }

    @Test
    @DisplayName("No setters — class is immutable after construction")
    void testNoSetters() {
        // THEN — only 4 public methods beyond Object methods
        assertEquals(4, historique.getClass().getDeclaredMethods().length);
    }

    @Test
    @DisplayName("Constructor accepts null id")
    void testConstructorNullId() {
        // GIVEN
        HistoriqueAction h = new HistoriqueAction(null, "Action", now, employee);

        // THEN
        assertNull(h.getId());
    }

    @Test
    @DisplayName("Constructor accepts null action")
    void testConstructorNullAction() {
        // GIVEN
        HistoriqueAction h = new HistoriqueAction("H3", null, now, employee);

        // THEN
        assertNull(h.getAction());
    }

    @Test
    @DisplayName("Constructor accepts null dateHeure")
    void testConstructorNullDateHeure() {
        // GIVEN
        HistoriqueAction h = new HistoriqueAction("H4", "Action", null, employee);

        // THEN
        assertNull(h.getDateHeure());
    }

    @Test
    @DisplayName("Constructor accepts null employee")
    void testConstructorNullEmployee() {
        // GIVEN
        HistoriqueAction h = new HistoriqueAction("H5", "Action", now, null);

        // THEN
        assertNull(h.getEmployee());
    }
}
