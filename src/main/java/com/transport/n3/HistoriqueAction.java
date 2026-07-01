package com.transport.n3;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class HistoriqueAction {
    private String id;
    private String action;
    private LocalDateTime dateHeure;
    private Employee employee;


}
