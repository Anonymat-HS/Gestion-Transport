package com.transport.n3;

import java.util.ArrayList;
import java.util.List;




public class   Voyageur {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private List<Reservation> reservations;
    private Ticket ticket;
    private List <Bagage> listeBagage;

    public Voyageur(String id, String nom, String prenom, String telephone, String email, Reservation reservation, Ticket ticket, List <Bagage> listeBagage) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.reservations = new ArrayList<>();
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

    public List<Reservation> getReservation() {
        return reservations;
    }

    public void setReservation(List<Reservation> reservations) {
        this.reservations = reservations;
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


        if (reservation == null) {
            throw new IllegalArgumentException("La réservation ne peut pas être null");
        }

        if (this.reservations.contains(reservation)) {
            throw new IllegalStateException("Réservation déjà existante");
        }

        this.reservations.add(reservation);


    }

    public void annulerReservation(Reservation reservation) {


        if (reservation == null) {
            throw new IllegalArgumentException("La réservation ne peut pas être null");
        }

        if (!this.reservations.contains(reservation)) {
            throw new IllegalStateException("Réservation introuvable");
        }

        this.reservations.remove(reservation);


    }

    public int getNbreTotalVoyages() {
        return this.reservations.size();
    }
}
