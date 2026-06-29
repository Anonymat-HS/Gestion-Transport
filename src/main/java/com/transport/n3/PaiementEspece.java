package com.transport.n3;

import java.time.LocalDateTime;

public class PaiementEspece extends TypeDePaiments {
    private double montantRemis;
    private double monnaieRendue;

    public PaiementEspece(String id, LocalDateTime dateTransaction, double montant, String nomDuPayeur, double montantRemis, double monnaieRendue) {
        super(id, dateTransaction, montant, nomDuPayeur);
        this.montantRemis = montantRemis;
        this.monnaieRendue = monnaieRendue;
    }


}
