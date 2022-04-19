package com.kravets.rpnjava3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Railcar implements Comparable<Railcar>, Serializable {
    private String model;
    private Integer passengersMax, comfortLevel;
    private List<Passenger> passengers;

    public Railcar(String model, int passengersMax, int comfortLevel) {
        this.model = model;
        this.passengersMax = passengersMax;
        this.comfortLevel = comfortLevel;
        this.passengers = new ArrayList<>();
    }

    public String getModel() {
        return model;
    }

    public int getPassengersMax() {
        return passengersMax;
    }

    public int getComfortLevel() {
        return comfortLevel;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPassengersMax(int passengersMax) {
        this.passengersMax = passengersMax;
    }

    public void setComfortLevel(int comfortLevel) {
        this.comfortLevel = comfortLevel;
    }

    @Override
    public String toString() {
        return String.format("Вагон %s\nМакс. пасажыраў: %s\nУзр. камфортнасці: %s", model, passengersMax, comfortLevel);
    }

    public String toStringShort() {
        return String.format("Вагон %s", model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Railcar railcar = (Railcar) o;
        return passengersMax == railcar.passengersMax && comfortLevel == railcar.comfortLevel && Objects.equals(model, railcar.model) && Objects.equals(passengers, railcar.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, passengersMax, comfortLevel, passengers);
    }

    @Override
    public int compareTo(Railcar o) {
        return this.comfortLevel.compareTo(o.getComfortLevel());
    }

    public void addPassenger(Passenger passenger) throws RailcarNoFreeSpaceException {
        if (passengers.size() == passengersMax)
            throw new RailcarNoFreeSpaceException();
        else
            this.passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
    }
}
