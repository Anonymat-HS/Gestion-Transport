package com.transport.n3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Chauffeur extends Employee{
    private String permis;
    private List<Voyage> calendrierDeVoyage;

    public Chauffeur(String id, String nom, String prenom, String telephone, Sexe sexe, double salaire, String permis) {
        super(id, nom, prenom, telephone, sexe, salaire);
        this.permis = permis;
        this.calendrierDeVoyage = new ArrayList<>();
    }

    public  int getNbreTotalVoyage (){
        return calendrierDeVoyage.size();
    }

    public String getPermis() {
        return permis;
    }

    public List<Voyage> getCalendrierDeVoyage() {
        return calendrierDeVoyage;
    }

    public void setPermis(String permis) {
        this.permis = permis;
    }

    public void setCalendrierDeVoyage(List<Voyage> calendrierDeVoyage) {
        this.calendrierDeVoyage = calendrierDeVoyage;
    }

    public boolean pasAuRepos (LocalDate date ){
        for (Voyage planDeVoyage : calendrierDeVoyage){
            LocalDate dateDepart = planDeVoyage.getDateDepart().toLocalDate();
            LocalDate dateArrivee = planDeVoyage.getDateArrive().toLocalDate();

            if (!date.isBefore(dateDepart) && !date.isAfter(dateArrivee)){
                return true;
            }
        }
        return false;
    }
    public void ajouterVoyage (Voyage plan){
        calendrierDeVoyage.add(plan);

    }

}
