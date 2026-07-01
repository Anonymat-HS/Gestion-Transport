package com.transport.n3;



import java.time.LocalDateTime;


public class HistoriqueAction {
    private String id;
    private String action;
    private LocalDateTime dateHeure;
    private Employee employee;

    public HistoriqueAction(String id, String action, LocalDateTime dateHeure, Employee employee) {
        this.id = id;
        this.action = action;
        this.dateHeure = dateHeure;
        this.employee = employee;
    }

    public String getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public Employee getEmployee() {
        return employee;
    }
}
