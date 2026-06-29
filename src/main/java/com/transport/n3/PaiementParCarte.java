package com.transport.n3;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter

public class PaiementParCarte extends  TypeDePaiments {

    private Banque banque;
    private String numeroCompte;

    public PaiementParCarte(String id, LocalDateTime dateTransaction, double montant, String nomDuPayeur, Banque banque, String numeroCompte) {
        super(id, dateTransaction, montant, nomDuPayeur);
        this.banque = banque;
        this.numeroCompte = numeroCompte;
    }
}
