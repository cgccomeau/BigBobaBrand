package edu.gatech.cs2340.m5bigbobabrand.Model;

import android.util.Log;

import edu.gatech.cs2340.m5bigbobabrand.entity.Coordinates;
import edu.gatech.cs2340.m5bigbobabrand.entity.Player;
import edu.gatech.cs2340.m5bigbobabrand.entity.SolarSystem;

public class TravelInteractor {
    SolarSystem initialSystem;
    SolarSystem finalSystem;
    public TravelInteractor(SolarSystem initialSystem, SolarSystem finalSystem) {
        this.initialSystem = initialSystem;
        this.finalSystem = finalSystem;
    }

    public int calculateFuelCosts() {
        Coordinates a = initialSystem.getCoordinates();
        Coordinates b = finalSystem.getCoordinates();
        if (a.equals(b)) {
            return 0;
        }
        int distance = (int) (Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2)) + .5);
        int fuelCost = (distance / 10); //will add fuel efficiency per ship;
        if (fuelCost == 0) {
            fuelCost += 1;
        }
        return fuelCost;
    }

}
