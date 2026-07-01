package com.transport.n3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests — Classe Receptionniste")
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
        // GIVEN : un réceptionniste, un chauffeur, un trajet et une voiture
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

        // GIVEN : une réservation payée
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

        // GIVEN : une réservation non payée
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
        reservationNonPayee.setPrixTotal(0.0);
    }

    @Test
    @DisplayName("enregistrerReservation ajoute la réservation dans la liste")
    void erengistrerReservation_ajoute_bien_dans_la_liste(){
        // GIVEN : un réceptionniste avec une liste vide
        // WHEN : on enregistre deux réservations
        receptionniste.enregistrerReservation(reservationNonPayee);
        receptionniste.enregistrerReservation(reservationNonPayee);

        // THEN : la liste contient 2 réservations
        assertEquals(2, receptionniste.getListeReservation().size() );
    }

    @Test
    @DisplayName("afficherPlaceDispo retourne toutes les places quand tout est disponible")
    void afficher_place_Dispo_ou_tout_dispo(){
        // GIVEN : une voiture avec toutes ses places disponibles
        // WHEN : on affiche les places disponibles
        List<Place> placesDispo = receptionniste.afficherPlaceDispo(voiture, LocalDate.of(2025,6,1));

        // THEN : les 16 places sont retournées
        assertEquals(16, placesDispo.size());
    }

    @Test
    @DisplayName("afficherPlaceDispo exclut les places marquées indisponibles")
    void afficher_place_dispo_ou_certains_sont_pas_dispo(){
        // GIVEN : une voiture dont 2 places sont indisponibles
        voiture.getPlaces().getFirst().setPlaceDispo(false);
        voiture.getPlaces().getLast().setPlaceDispo(false);

        // WHEN : on affiche les places disponibles
        List<Place> placesDispo = receptionniste.afficherPlaceDispo(voiture,LocalDate.of(2025,6,1));

        // THEN : seules 14 places sont retournées
        assertEquals(14, placesDispo.size());
    }

    @Test
    @DisplayName("afficherPlaceDispo retourne une liste vide si aucune place dispo")
    void afficher_place_dispo_ou_tout_est_indsipo(){
        // GIVEN : une voiture dont toutes les places sont indisponibles
        for (Place place: voiture.getPlaces()){
           place.setPlaceDispo(false);
        }

        // WHEN : on affiche les places disponibles
        List<Place> placesDispo = receptionniste.afficherPlaceDispo(voiture,LocalDate.of(2025,6,1));

        // THEN : la liste est vide
        assertTrue(placesDispo.isEmpty());
    }

    @Test
    @DisplayName("donnerTicket retourne un ticket pour une réservation payée")
    void donnerTicket_reservationPayee_retourneTicket() {
        // GIVEN : une réservation avec statut PAYEE
        // WHEN : on demande un ticket
        Ticket ticket = receptionniste.donnerTicket(reservationPayee);

        // THEN : un ticket est créé
        assertNotNull(ticket);
    }

    @Test
    @DisplayName("donnerTicket retourne null pour une réservation non payée")
    void donnerTicket_reservationNonPayee_retourneNull() {
        // GIVEN : une réservation avec statut ATTENTE
        // WHEN : on demande un ticket
        Ticket ticket = receptionniste.donnerTicket(reservationNonPayee);

        // THEN : aucun ticket n'est délivré
        assertNull(ticket);
    }

    @Test
    @DisplayName("donnerTicket associe le bon voyageur au ticket")
    void donnerTicket_contientBonVoyageur() {
        // GIVEN : une réservation payée avec un voyageur spécifique
        // WHEN : on génère le ticket
        Ticket ticket = receptionniste.donnerTicket(reservationPayee);

        // THEN : le ticket contient le même voyageur que la réservation
        assertNotNull(ticket);
        assertEquals(voyageur, ticket.getVoyageur());
    }


    @Test
    @DisplayName("donnerFacture retourne une facture pour une réservation valide")
    void donnerFacture_reservationPayee_retourneFacture() {
        // GIVEN : une réservation avec un prix valide
        // WHEN : on demande une facture
        Facture facture = receptionniste.donnerFacture(reservationPayee);

        // THEN : une facture est créée
        assertNotNull(facture);
    }

    @Test
    @DisplayName("donnerFacture retourne null si le montant est invalide")
    void donnerFacture_montantInvalide_retourneNull() {
        // GIVEN : une réservation avec prixTotal = 0
        // WHEN : on demande une facture
        Facture facture = receptionniste.donnerFacture(reservationNonPayee);

        // THEN : aucune facture n'est générée
        assertNull(facture);
    }


    @Test
    @DisplayName("HistoriqueAction est correctement créée")
    void historiqueAction_creation_correcte() {
        // GIVEN : les paramètres d'un log
        // WHEN : on crée un HistoriqueAction
        HistoriqueAction log = new HistoriqueAction(
                "H1",
                "Reservation enregistrée",
                LocalDateTime.now(),
                receptionniste
        );

        // THEN : tous les champs sont bien initialisés
        assertEquals("H1", log.getId());
        assertEquals("Reservation enregistrée", log.getAction());
        assertEquals(receptionniste, log.getEmployee());
        assertNotNull(log.getDateHeure());
    }

    @Test
    @DisplayName("HistoriqueAction est immuable (pas de setters)")
    void historiqueAction_immuable_pasDeModification() {
        // GIVEN : un log avec des données spécifiques
        HistoriqueAction log = new HistoriqueAction(
                "H2",
                "Test action",
                LocalDateTime.of(2025, 1, 1, 10, 0),
                receptionniste
        );

        // WHEN & THEN : on peut lire les valeurs, pas de setters pour les modifier
        assertEquals("Test action", log.getAction());
        assertEquals(LocalDateTime.of(2025, 1, 1, 10, 0), log.getDateHeure());
    }


}
