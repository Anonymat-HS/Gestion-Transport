package com.transport.n3;

public enum TypeVoiture {
    LITE(16), PREMIUM(16), VIP(8), VVIP(8);

    private final int nbrePlaces;

    TypeVoiture(int nbrePlaces){
        this.nbrePlaces = nbrePlaces;
    }

    public int getNbrePlaces(){
        return nbrePlaces;
    }
}
