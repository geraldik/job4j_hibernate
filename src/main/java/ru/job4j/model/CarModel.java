package ru.job4j.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "model")
@ToString
@Getter
@Setter
public class CarModel {
    @Id
    @Column(name = "model_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static CarModel of(String name) {
        CarModel model = new CarModel();
        model.name = name;
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carModel = (CarModel) o;
        return id == carModel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
