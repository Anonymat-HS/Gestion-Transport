package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class Bagage {
    private String id;
    private String description;
    private double poids;
    private double prixSupplementaire;

    private static final double poidsInclu = 20.0;
    private static final double tarifParKilo = 500.0;

    public Bagage(double prixSupplementaire) {
        this.prixSupplementaire = calculSurcharge();
    }

    public double calculSurcharge (){
        if (poids <= poidsInclu){
            return 0.0;
        }
        double excedent = poids -poidsInclu;
        return excedent * tarifParKilo;
    }

}
