package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter

public class Ticket {
    private String id;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;
    private Voyageur voyageur;
    private Place place;
    private  Voiture voiture;
    private Trajet trajet;
    private StatutDeReservation statutDeReservation;
}