package com.example.flightapp.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Cargo {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("weight")
    private int weight;
    @JsonProperty("weightUnit")
    private String weightUnit;
    @JsonProperty("pieces")
    private int pieces;

    private final double conversionRate = 0.454;
    private final String toStringFormat = "{id = %s, weight = %d, weightUnit = %s, pieces = %d}";

    public double getWeightInKg() {
        if (this.weightUnit.equals("lb")) {
            return this.weight * this.conversionRate * this.pieces;
        } else {
            return this.weight * this.pieces;
        }
    }

    @Override
    public String toString() {
        return String.format(toStringFormat,
                this.id, this.weight, this.weightUnit, this.pieces);
    }
}
