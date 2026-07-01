package com.transport.n3;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter

public class Chauffeur extends Employee{
    private String permis;
    private List<PlanDeVoyage> calendrierDeVoyage;

    public Chauffeur(String id, String nom, String prenom, String telephone, Sexe sexe, double salaire, String permis) {
        super(id, nom, prenom, telephone, sexe, salaire);
        this.permis = permis;
        this.calendrierDeVoyage = new ArrayList<>();
    }

    public  int getNbreTotalVoyage (){
        return calendrierDeVoyage.size();
    }

    public boolean pasAuRepos (LocalDate date ){
        for (PlanDeVoyage planDeVoyage : calendrierDeVoyage){
            LocalDate dateDepart = planDeVoyage.getDateDepart().toLocalDate();
            LocalDate dateArrivee = planDeVoyage.getDateArrive().toLocalDate();

            if (!date.isBefore(dateDepart) && !date.isAfter(dateArrivee)){
                return true;
            }
        }
        return false;
    }
    public void ajouterVoyage (PlanDeVoyage plan){
        calendrierDeVoyage.add(plan);

    }

}
