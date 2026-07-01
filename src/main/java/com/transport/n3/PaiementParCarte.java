package com.transport.n3;


import java.time.LocalDateTime;


public class PaiementParCarte extends  TypeDePaiments {

    private Banque banque;
    private String numeroCompte;

    public PaiementParCarte(String id, LocalDateTime dateTransaction, double montant, String nomDuPayeur, Banque banque, String numeroCompte) {
        super(id, dateTransaction, montant, nomDuPayeur);
        this.banque = banque;
        this.numeroCompte = numeroCompte;
    }



    public Banque getBanque() {
        return banque;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }
}
