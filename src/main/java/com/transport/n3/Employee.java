package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public abstract class Employee {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private Sexe sexe;
    private double salaire;

}
