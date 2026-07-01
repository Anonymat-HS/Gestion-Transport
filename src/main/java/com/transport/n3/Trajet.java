package com.transport.n3;



public class Trajet {
    private String id;
    private Ville villeDepart;
    private Ville villeArrive;
    private int dureeEstime;
    private double prixBase;

    public Trajet(String id, Ville villeDepart, Ville villeArrive, int dureeEstime, double prixBase) {
        this.id = id;
        this.villeDepart = villeDepart;
        this.villeArrive = villeArrive;
        this.dureeEstime = dureeEstime;
        this.prixBase = prixBase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ville getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(Ville villeDepart) {
        this.villeDepart = villeDepart;
    }

    public Ville getVilleArrive() {
        return villeArrive;
    }

    public void setVilleArrive(Ville villeArrive) {
        this.villeArrive = villeArrive;
    }

    public int getDureeEstime() {
        return dureeEstime;
    }

    public void setDureeEstime(int dureeEstime) {
        this.dureeEstime = dureeEstime;
    }

    public double getPrixBase() {
        return prixBase;
    }

    public void setPrixBase(double prixBase) {
        this.prixBase = prixBase;
    }

    public double getPrix(TypeVoiture typeVoiture) {
       switch (typeVoiture){
           case PREMIUM -> {
               return prixBase*1.3;
           }
           case VIP -> {
               return prixBase * 1.7;
           }
           case VVIP -> {
               return prixBase * 2.2;
           }
           default -> {
               return prixBase;
           }
       }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trajet trajet = (Trajet) o;
        return id != null && id.equals(trajet.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
