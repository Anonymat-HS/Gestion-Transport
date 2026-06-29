package com.transport.n3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter


public class Voyageur {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private Reservation reservation;
    private List <Bagage> listeBagage;
    private Ticket ticket;


}
