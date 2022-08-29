package ru.job4j.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brand")
@ToString
@Setter
@Getter
public class CarBrand {

    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(orphanRemoval = true, mappedBy = "brand")
    @ToString.Exclude
    private List<CarModel> models = new ArrayList<>();

    public static CarBrand of(String name) {
        CarBrand brand = new CarBrand();
        brand.name = name;
        return brand;
    }

    public void addModel(CarModel model) {
        this.models.add(model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarBrand brand = (CarBrand) o;
        return id == brand.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
