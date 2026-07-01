package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import  java.time.LocalDate;
import java.time.LocalDateTime;



@AllArgsConstructor
@Getter
@Setter

public class Reservation {
    private String id;
    private LocalDateTime dateReservation;
    private LocalDate dateVoyage;
    private Voyageur voyageur;
    private Voyage planDeVoyage;
    private Place place;
    private TypeDePaiments paiements;
    private StatutDeReservation statut;
    private double prixTotal;
    private Bagage bagage;

    public Reservation(String id, LocalDateTime dateReservation, LocalDate dateVoyage, Voyageur voyageur, Voyage voyage, Place place, StatutDeReservation statut, double prixTotal, Bagage bagage) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.dateVoyage = dateVoyage;
        this.voyageur = voyageur;
        this.voyage = voyage;
        this.place = place;
        this.paiements = null;
        this.statut = statut;
        this.bagage = bagage;
        this.prixTotal =  voyage.getTrajet().getPrix(place.getTypeVoiture()) + bagage.getPrixSupplementaire();

        if (voyageur != null) {
            voyageur.faireReservation(this);
        } else {
            throw new IllegalArgumentException("La résérvation ne peut pas etre éfféctuée");
        }
    }


    public boolean estPayee() {
        if (paiements == null) {
            return false;
        }
        return paiements.getMontant() >= getPrixTotal();
    }

    public double getPrixTotal() {
        return  prixTotal;
    }

    public void annuler() {
        this.statut = StatutDeReservation.ANNULEE;
    }

    public void ajouterPaiement(TypeDePaiments paiements) {
        if (paiements!= null && statut!= StatutDeReservation.ANNULEE) {
            this.paiements = paiements;
        }
    }



}
