package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



public class Place {
    private int numeroPlace;
    private TypeVoiture  typeVoiture;
    private boolean placeDispo;
    private boolean disponible;

    public Place(int numeroPlace, TypeVoiture typeVoiture, boolean placeDispo, boolean disponible) {
        this.numeroPlace = numeroPlace;
        this.typeVoiture = typeVoiture;
        this.placeDispo = placeDispo;
        this.disponible = true;
    }


    public Place(int i, TypeVoiture typeVoiture) {
    }

    public int getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(int numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public TypeVoiture getTypeVoiture() {
        return typeVoiture;
    }

    public void setTypeVoiture(TypeVoiture typeVoiture) {
        this.typeVoiture = typeVoiture;
    }

    public boolean isPlaceDispo() {
        return placeDispo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean estDispo (){
        return disponible;
    }

    public void setPlaceDispo(boolean etat) {
        this.placeDispo = etat;
    }
}
