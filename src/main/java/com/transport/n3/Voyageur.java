package com.transport.n3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter


public class   Voyageur {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private List<Reservation> reservations;
    private List <Bagage> listeBagage;
    private Ticket ticket;


    public Voyageur() {
        this.reservations = new ArrayList<>();
        this.listeBagage = new ArrayList<>();
    }


    public void faireReservation(Reservation reservation) {

        if (reservation == null) {
            throw new IllegalArgumentException("La réservation ne peut pas être null");
        }

        if (this.reservations.contains(reservation)) {
            throw new IllegalStateException("Cette réservation existe déjà");
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
