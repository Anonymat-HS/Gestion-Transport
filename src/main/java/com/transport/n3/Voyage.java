package com.transport.n3;


import java.time.LocalDateTime;


public class Voyage {
    private String id;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrive;
    private Trajet trajet;
    private Voiture voiture;
    private Chauffeur chauffeur;

    public Voyage() {
    }

    public Voyage(String id, LocalDateTime dateDepart, LocalDateTime dateArrive, Trajet trajet, Voiture voiture, Chauffeur chauffeur) {
        this.id = id;
        this.dateDepart = dateDepart;
        this.dateArrive = dateArrive;
        this.trajet = trajet;
        this.voiture = voiture;
        this.chauffeur = chauffeur;
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

    public LocalDateTime getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(LocalDateTime dateArrive) {
        this.dateArrive = dateArrive;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }
}
