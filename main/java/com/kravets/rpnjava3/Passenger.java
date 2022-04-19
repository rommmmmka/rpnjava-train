package com.kravets.rpnjava3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Passenger implements Serializable {
    private String lastName;
    private String firstName;
    private String patronymic;
    private List<Baggage> baggage;

    public Passenger(String lastName, String firstName, String patronymic) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.baggage = new ArrayList();
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public List<Baggage> getBaggage() {
        return baggage;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%s", lastName, firstName, patronymic);
    }

    public String toStringShort() {
        return String.format("%s %s. %s.", lastName, firstName.charAt(0), patronymic.charAt(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return lastName.equals(passenger.lastName) && firstName.equals(passenger.firstName) &&
                patronymic.equals(passenger.patronymic) && Objects.equals(baggage, passenger.baggage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, patronymic, baggage);
    }

    public void addBaggage(Baggage baggage) {
        this.baggage.add(baggage);
    }

    public void removeBaggage(Baggage baggage) {
        this.baggage.remove(baggage);
    }
}
