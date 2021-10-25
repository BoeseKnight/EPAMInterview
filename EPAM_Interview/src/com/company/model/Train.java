package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private List<FreightCar> freightCars;
    private List<PassengerCar> passengerCars;
    private int carryingCapacity;
    private int passengerCapacity;
    private int carsQuantity;

    public Train(List<PassengerCar> passengerCars, List<FreightCar> freightCars) {
        this.freightCars = freightCars;
        this.passengerCars = passengerCars;
        countCarsQuantity();
        countCarryingCapacity();
        countPassengerCapacity();
    }

    private int countCarryingCapacity() {
        freightCars.forEach((car) -> carryingCapacity += car.getCarryingCapacity());
        System.out.println("Carrying capacity of the train: "+carryingCapacity + " tons");
        return carryingCapacity;

    }

    private int countPassengerCapacity() {
        passengerCars.forEach((car) -> passengerCapacity += car.getPassengerCapacity());
        System.out.println("Quantity of passenger seats: "+passengerCapacity + " passenger seats");
        return passengerCapacity;
    }

    private int countCarsQuantity() {
        carsQuantity = passengerCars.size() + freightCars.size();
        return carsQuantity;
    }

    public int getPassengerCarsNumber() {
        return passengerCars.size();
    }

    public int getFreightCarsNumber() {
        return freightCars.size();
    }

    public int getPassengerCarCapacityByIndex(int i) {
        return passengerCars.get(i).getPassengerCapacity();
    }

    public int getFreightCarCapacityByIndex(int i) {
        return freightCars.get(i).getCarryingCapacity();
    }

    public int getCarryingCapacity() {
        return carryingCapacity;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public int getCarsQuantity() {
        return carsQuantity;
    }
}
