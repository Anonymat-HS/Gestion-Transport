package com.transport.n3;


import java.time.LocalDateTime;


public class PaimentMobile extends TypeDePaiments{
    private String numero;
    private String referenceDePaiement;
    private Operateur operateur;

    public PaimentMobile(String id, LocalDateTime dateTransaction, double montant, String nomDuPayeur, String numero, String referenceDePaiement, Operateur operateur) {
        super(id, dateTransaction, montant, nomDuPayeur);
        this.numero = numero;
        this.referenceDePaiement = referenceDePaiement;
        this.operateur = operateur;
    }

    public String getNumero() {
        return numero;
    }

    public String getReferenceDePaiement() {
        return referenceDePaiement;
    }

    public Operateur getOperateur() {
        return operateur;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setReferenceDePaiement(String referenceDePaiement) {
        this.referenceDePaiement = referenceDePaiement;
    }

    public void setOperateur(Operateur operateur) {
        this.operateur = operateur;
    }
}
