package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceptionnisteTest {
    private Receptionniste receptionniste;
    private Voiture voiture;
    private Chauffeur chauffeur;
    private Place place;
    private Voyage planDeVoyage;
    private Voyageur voyageur;
    private Trajet trajet;
    private Reservation reservationPayee;
    private Reservation reservationNonPayee;
    private TypeDePaiments paiementEspece;
    private Bagage bagage;

    @BeforeEach
    void setUp (){
        receptionniste = new Receptionniste("R1", "Rasoa", "Volana", "0341234567", Sexe.FEMININ, 500000);

        chauffeur = new Chauffeur("C1", "Rakoto", "Jean", "0341111111", Sexe.MASCULIN, 600000, "B");

        trajet = new Trajet("T1", Ville.ANTANANARIVO, Ville.TOAMASINA, 8, 50000);

        voiture = new Voiture("V1", "MAD-1234", TypeVoiture.LITE,chauffeur);

        voyageur = new Voyageur("VG1", "Rabe", "Paul", "0349999999", "rabe@gmail.com", null, null, null);

        paiementEspece  = new PaiementEspece(
                "P1",
                LocalDateTime.now(),
                50000,
                "Rabe",
                50000,
                0.0
        );

        planDeVoyage = new Voyage(
                "PL1",
                LocalDateTime.of(2025, 6, 1, 8, 0),
                LocalDateTime.of(2025, 6, 1, 16, 0),
                trajet,
                voiture,
                chauffeur
        );

        place = voiture.getPlaces().get(0);

        bagage = new Bagage("r21", "plein d'habille et valise de couleur bleu", 15.0,0.0);

        // réservation payée
        reservationPayee = new Reservation(
                "RES1",
                LocalDateTime.now(),
                LocalDate.of(2025, 6, 1),
                voyageur,
                planDeVoyage,
                place,
                StatutDeReservation.PAYEE,
                50000.0,
                bagage
        );

        // réservation non payée
        reservationNonPayee = new Reservation(
                "RES2",
                LocalDateTime.now(),
                LocalDate.of(2025, 6, 1),
                voyageur,
                planDeVoyage,
                place,
                StatutDeReservation.ATTENTE,
                0.0,
                bagage
        );
    }

    @Test
    void erengistrerReservation_ajoute_bien_dans_la_liste(){
        receptionniste.enregistrerReservation(reservationNonPayee);
        receptionniste.enregistrerReservation(reservationNonPayee);

        assertEquals(2, receptionniste.getListeReservation().size() );
    }

    @Test
    void afficher_place_Dispo_ou_tout_dispo(){
        List<Place> placesDispo = receptionniste.afficherPlaceDispo(voiture, LocalDate.of(2025,6,1));

        assertEquals(16, placesDispo.size());
    }

    @Test
    void afficher_place_dispo_ou_certains_sont_pas_dispo(){
        voiture.getPlaces().getFirst().setPlaceDispo(false);
        voiture.getPlaces().getLast().setPlaceDispo(false);

        List<Place> placesDispo = receptionniste.afficherPlaceDispo(voiture,LocalDate.of(2025,6,1));

        assertEquals(14, placesDispo.size());
    }

    @Test
    void afficher_place_dispo_ou_tout_est_indsipo(){
        for (Place place: voiture.getPlaces()){
            place.setPlaceDispo(false);
        }
        List<Place> placesDispo = receptionniste.afficherPlaceDispo(voiture,LocalDate.of(2025,6,1));

        assertTrue(placesDispo.isEmpty());
    }

    @Test
    void donnerTicket_reservationPayee_retourneTicket() {
        Ticket ticket = receptionniste.donnerTicket(reservationPayee);
        assertNotNull(ticket);
    }

    @Test
    void donnerTicket_reservationNonPayee_retourneNull() {
        Ticket ticket = receptionniste.donnerTicket(reservationNonPayee);
        assertNull(ticket);
    }

    @Test
    void donnerTicket_contientBonVoyageur() {
        Ticket ticket = receptionniste.donnerTicket(reservationPayee);
        assertNotNull(ticket);
        assertEquals(voyageur, ticket.getVoyageur());
    }


    @Test
    void donnerFacture_reservationPayee_retourneFacture() {
        Facture facture = receptionniste.donnerFacture(reservationPayee);
        assertNotNull(facture);
    }

    @Test
    void donnerFacture_montantInvalide_retourneNull() {
        // réservation avec prixTotal = 0 (méthodes Partie 1 pas encore faites)
        Facture facture = receptionniste.donnerFacture(reservationNonPayee);
        assertNull(facture);
    }


    @Test
    void historiqueAction_creation_correcte() {
        HistoriqueAction log = new HistoriqueAction(
                "H1",
                "Reservation enregistrée",
                LocalDateTime.now(),
                receptionniste
        );
        assertEquals("H1", log.getId());
        assertEquals("Reservation enregistrée", log.getAction());
        assertEquals(receptionniste, log.getEmployee());
        assertNotNull(log.getDateHeure());
    }

    @Test
    void historiqueAction_immuable_pasDeModification() {
        // HistoriqueAction n'a pas de setters — on vérifie juste qu'on peut lire
        HistoriqueAction log = new HistoriqueAction(
                "H2",
                "Test action",
                LocalDateTime.of(2025, 1, 1, 10, 0),
                receptionniste
        );
        assertEquals("Test action", log.getAction());
        assertEquals(LocalDateTime.of(2025, 1, 1, 10, 0), log.getDateHeure());
    }


}