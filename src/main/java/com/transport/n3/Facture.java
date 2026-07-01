package com.transport.n3;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class Facture {
    private String id;
    private String numeroCaisse;
    private double MontantTotalPaye;
    private Reservation reservation;

    public Facture(String id, String numeroCaisse, double montantTotalPaye, Reservation reservation) {
        this.id = id;
        this.numeroCaisse = numeroCaisse;
        MontantTotalPaye = montantTotalPaye;
        this.reservation = reservation;
    }

    public String getId() {
        return id;
    }

    public String getNumeroCaisse() {
        return numeroCaisse;
    }

    public double getMontantTotalPaye() {
        return MontantTotalPaye;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumeroCaisse(String numeroCaisse) {
        this.numeroCaisse = numeroCaisse;
    }

    public void setMontantTotalPaye(double montantTotalPaye) {
        MontantTotalPaye = montantTotalPaye;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}

