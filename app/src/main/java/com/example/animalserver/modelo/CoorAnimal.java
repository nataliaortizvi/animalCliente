package com.example.animalserver.modelo;

public class CoorAnimal {
    float posx, posy;

    public CoorAnimal() {}

    public CoorAnimal(float posx, float posy) {
        super();
        this.posx = posx;
        this.posy = posy;
    }

    public float getPosx() {
        return posx;
    }

    public void setPosx(float posx) {
        this.posx = posx;
    }

    public float getPosy() {
        return posy;
    }

    public void setPosy(float posy) {
        this.posy = posy;
    }
}
