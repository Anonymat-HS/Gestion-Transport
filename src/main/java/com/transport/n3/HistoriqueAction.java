package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class HistoriqueAction {
    private String id;
    private String action;
    private LocalDateTime dateHeure;
    private Employee employee;


}
