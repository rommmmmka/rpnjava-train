package com.kravets.rpnjava3;

import java.io.Serializable;
import java.util.Objects;

public class Baggage implements Serializable {
    private int weight;

    public Baggage(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Baggage baggage = (Baggage) o;
        return weight == baggage.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight);
    }
}
