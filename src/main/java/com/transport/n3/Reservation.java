package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import  java.time.LocalDate;
import java.time.LocalDateTime;



@AllArgsConstructor
@Getter
@Setter

public class Reservation {
    private String id;
    private LocalDateTime dateReservation;
    private LocalDate dateVoyage;
    private Voyageur voyageur;
    private PlanDeVoyage planDeVoyage;
    private Place place;
    private TypeDePaiments paiments;
    private StatutDeReservation statut;




}
