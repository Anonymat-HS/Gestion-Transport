package com.transport.n3;


import java.time.LocalDateTime;


public class Ticket {
    private String id;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;
    private Voyageur voyageur;
    private Place place;
    private  Voiture voiture;

    public Ticket(String id, LocalDateTime dateDepart, LocalDateTime dateArrivee, Voyageur voyageur, Place place, Voiture voiture) {
        this.id = id;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.voyageur = voyageur;
        this.place = place;
        this.voiture = voiture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public LocalDateTime getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(LocalDateTime dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Voyageur getVoyageur() {
        return voyageur;
    }

    public void setVoyageur(Voyageur voyageur) {
        this.voyageur = voyageur;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }
}