package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class Trajet {
    private String id;
    private Ville villeDepart;
    private Ville villeArrive;
    private int dureeEstime;
    private double prixBase;

    public double getPrix(TypeVoiture typeVoiture) {
       switch (typeVoiture){
           case PREMIUM -> {
               return prixBase*1.3;
           }
           case VIP -> {
               return prixBase * 1.7;
           }
           case VVIP -> {
               return prixBase * 2.2;
           }
           default -> {
               return prixBase;
           }
       }
    }
}
