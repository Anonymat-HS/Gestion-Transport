package com.transport.n3;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class Receptionniste extends Employee {

    private List<Reservation> listeReservation;

    public Receptionniste(String id, String nom, String prenom, String telephone, Sexe sexe, double salaire) {
        super(id, nom, prenom, telephone, sexe, salaire);
        this.listeReservation = new ArrayList<>();
    }

    public List <Place> afficherPlaceDispo (Voiture voiture, LocalDate date){
        List<Place> placeDispo = new ArrayList<>();
        for ( Place place : voiture.getPlaces()){
            if (place.estDispo()){
                placeDispo.add(place);

            }
        }
        return placeDispo;
    }
    //donner ticket ; // a besoi du methode estpayee de la partie 1
    public Ticket donnerTicket (Reservation r) {
        if (!r.estPayee()) {
            System.out.println("Réservation non payé");
            return null;
        }
        return new Ticket(
                r.getId(),
                r.getPlanDeVoyage().getDateDepart(),
                r.getPlanDeVoyage().getDateArrive(),
                r.getVoyageur(),
                r.getPlace(),
                r.getPlanDeVoyage().getVoiture()
        );
    }
    //donner facture;
    //besoin du prix de la reservation de la partie 1

    public  Facture donnerFacture (Reservation r){
        if(r.getPrixTotal() <= 0){
            System.out.println("Montant invalide");
            return null;
        }
        return  new Facture(
                r.getId(),
                "Caisse-01",
                r.getPrixTotal(),
                r
        );

    }
    //enregsitrer reservation
    public void enregistrerReservation (Reservation r){
        listeReservation.add(r);

        HistoriqueAction log = new HistoriqueAction(
                 r.getId(),
                "Reservation fait pour" + r.getVoyageur().getNom(),
                 LocalDateTime.now(),
                 this
        );
    }

}




