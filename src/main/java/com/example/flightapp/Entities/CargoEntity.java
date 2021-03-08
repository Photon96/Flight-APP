package com.example.flightapp.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CargoEntity {

    @JsonProperty("flightId")
    @Getter private @Id Long id;

    @JsonProperty("baggage")
    @ElementCollection(targetClass = Baggage.class)
    @Getter private List<Baggage> baggage;

    @JsonProperty("cargo")
    @ElementCollection(targetClass = Cargo.class)
    @Getter private List<Cargo> cargo;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("{ flightID = %s, baggage = \n [", this.id));
        for (Baggage baggage : this.baggage) {
            sb.append(baggage.toString() + "\n");
        }
        sb.append("]\ncargo = [");
        for (Cargo cargo : this.cargo) {
            sb.append(cargo.toString() + " \n");
        }
        sb.append("]\n");
        return sb.toString();
    }
}
