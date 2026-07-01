package com.transport.n3;


import java.util.ArrayList;
import java.util.List;


public class Admin extends  Employee{
    private List<Employee> listeEmployee;

    public Admin(String id, String nom, String prenom, String telephone, Sexe sexe, double salaire) {
        super(id, nom, prenom, telephone, sexe, salaire);
        this.listeEmployee = new ArrayList<>();
    }

    public List<Employee> getListeEmployee() {
        return listeEmployee;
    }

    public void setListeEmployee(List<Employee> listeEmployee) {
        this.listeEmployee = listeEmployee;
    }

    public double consulterBenefice (Entreprise entreprise, int mois, int annee){
        return entreprise.calculerBeneficeParMois(mois, annee);
    }

    public List<Employee> afficherListeEmployee (Entreprise entreprise){
        return entreprise.getListeEmployee();
    }
}
