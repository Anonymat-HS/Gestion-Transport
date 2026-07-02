package com.transport.n3;

import java.util.ArrayList;
import java.util.List;

public class   Voyageur {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private Reservation reservation;
    private Ticket ticket;
    private List <Bagage> listeBagage;

    public Voyageur(String id, String nom, String prenom, String telephone, String email, Reservation reservation, Ticket ticket, List <Bagage> listeBagage) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.reservation = reservation;
        this.ticket = ticket;
        this.listeBagage = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Bagage> getListeBagage() {
        return listeBagage;
    }

    public void setListeBagage(List<Bagage> listeBagage) {
        this.listeBagage = listeBagage;
    }

    public void faireReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
