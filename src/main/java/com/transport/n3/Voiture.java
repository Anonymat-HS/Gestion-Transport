package com.transport.n3;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class Voiture {
    private String id;
    private String numeroMatricule;
    private TypeVoiture typeVoiture;
    private Chauffeur chauffeur;
    private List<Place> places;



    public Voiture(String id, String numeroMatricule, TypeVoiture typeVoiture, Chauffeur chauffeur) {
        this.id = id;
        this.numeroMatricule = numeroMatricule;
        this.typeVoiture = typeVoiture;
        this.chauffeur = chauffeur;
        this.places = genererPlace();
    }

    public  int getNbrePlace (){
      return   places.size();
    }


    private List<Place> genererPlace (){
        List<Place> listePlaces = new ArrayList<>();

        int nombre = typeVoiture.getNbrePlaces();

        for (int i = 1; i <= nombre; i++){
           listePlaces.add(new Place(i,typeVoiture));
        }
       return  listePlaces;
    }
    public List<Place> getPlacesDisponibles(Voyage planDeVoyage) {
        List<Place> dispo = new ArrayList<>();
        for (Place place : places) {
            if (place.estDispo()) {
                dispo.add(place);
            }
        }
        return dispo;
    }


}
