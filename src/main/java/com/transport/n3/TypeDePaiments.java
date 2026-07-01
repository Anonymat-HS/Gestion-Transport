package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public abstract class TypeDePaiments {
    private  String id;
    private LocalDateTime dateTransaction;
    private double montant;
    private  String nomDuPayeur;

    public TypeDePaiments(String id, LocalDateTime dateTransaction, double montant, String nomDuPayeur) {
        this.id = id;
        this.dateTransaction = dateTransaction;
        this.montant = montant;
        this.nomDuPayeur = nomDuPayeur;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getNomDuPayeur() {
        return nomDuPayeur;
    }

    public void setNomDuPayeur(String nomDuPayeur) {
        this.nomDuPayeur = nomDuPayeur;
    }
}
