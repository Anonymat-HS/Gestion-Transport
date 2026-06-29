package com.transport.n3;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

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
}
