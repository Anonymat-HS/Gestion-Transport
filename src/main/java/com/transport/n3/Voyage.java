package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter

public class Voyage {
    private String id;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrive;
    private Trajet trajet;
    private Voiture voiture;
    private Chauffeur chauffeur;

}
