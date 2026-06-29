package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter

public abstract class TypeDePaiments {
    private  String id;
    private LocalDateTime dateTransaction;
    private double montant;
    private  String nomDuPayeur;


}
