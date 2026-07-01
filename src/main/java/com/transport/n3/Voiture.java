package com.transport.n3;


import java.util.ArrayList;
import java.util.List;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroMatricule() {
        return numeroMatricule;
    }

    public void setNumeroMatricule(String numeroMatricule) {
        this.numeroMatricule = numeroMatricule;
    }

    public TypeVoiture getTypeVoiture() {
        return typeVoiture;
    }

    public void setTypeVoiture(TypeVoiture typeVoiture) {
        this.typeVoiture = typeVoiture;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
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
