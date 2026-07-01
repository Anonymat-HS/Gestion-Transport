package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;




public class Reservation {
    private String id;
    private LocalDateTime dateReservation;
    private LocalDate dateVoyage;
    private Voyageur voyageur;
    private Voyage voyage;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDate getDateVoyage() {
        return dateVoyage;
    }

    public void setDateVoyage(LocalDate dateVoyage) {
        this.dateVoyage = dateVoyage;
    }

    public Voyageur getVoyageur() {
        return voyageur;
    }

    public void setVoyageur(Voyageur voyageur) {
        this.voyageur = voyageur;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public TypeDePaiments getPaiements() {
        return paiements;
    }

    public void setPaiements(TypeDePaiments paiements) {
        this.paiements = paiements;
    }

    public StatutDeReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutDeReservation statut) {
        this.statut = statut;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Bagage getBagage() {
        return bagage;
    }

    public void setBagage(Bagage bagage) {
        this.bagage = bagage;
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
