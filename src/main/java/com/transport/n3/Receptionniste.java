package com.transport.n3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Receptionniste extends Employee{

    private List<Reservation> listeReservation;

    public Receptionniste(String id, String nom, String prenom, String telephone, Sexe sexe, double salaire) {
        super(id, nom, prenom, telephone, sexe, salaire);
    }

    public List <Place> afficherPlaceDispo (Voiture voiture, LocalDate date){
        List<Place> placeDispo = new ArrayList<>();
        for ( Place place : voiture.getPlaces()){
            if (place.estDispo()){
                placeDispo.add(place);

            }
        }
        return placeDispo;
    }

    public void enregistrerReservation (Reservation re){
        listeReservation.add(re);


    }

    //donner ticket
    //donner facture
}
