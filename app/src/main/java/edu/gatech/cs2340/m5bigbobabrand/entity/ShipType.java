package edu.gatech.cs2340.m5bigbobabrand.entity;

public enum ShipType {
    GNAT("Gnat", 10),
    FLEA("Flea", 15),
    FIREFlY("Firefly", 20),
    MOSQUITO("Mosquito", 30),
    BUMBLEBEE("Bumblebee", 40),
    BEETLE("Beetle", 50),
    HORNET("Hornet", 45),
    GRASSHOPPER("Grasshopper", 50),
    TERMITE("Termite", 40),
    WASP("Wasp", 30);

    private String rep;
    private int cargoCapacity;

    ShipType(String rep, int cargoCapacity) {
        this.rep = rep;
        this.cargoCapacity = cargoCapacity;
    }

    public String toString() {
        return this.rep;
    }
    public int getCargoCapacity() {
        return this.cargoCapacity;
    }

}
