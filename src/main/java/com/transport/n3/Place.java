package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class Place {
    private int numeroPlace;
    private TypeVoiture  typeVoiture;
    private boolean placeDispo;
    private boolean disponible;

    public Place(boolean placeDispo) {
        this.placeDispo = true;
    }

    public Place(int i, TypeVoiture typeVoiture) {
    }

    public boolean estDispo (){
        return disponible;
    }

    public void setPlaceDispo(boolean etat) {
        this.placeDispo = etat;
    }
}
