package com.transport.n3;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter


public class Facture {
    private String id;
    private String numeroCaisse;
    private double MontantTotalPaye;
    private Reservation reservation;

}
