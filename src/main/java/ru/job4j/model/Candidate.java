package ru.job4j.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "candidate")
@Getter
@Setter
@ToString
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int experience;

    private int salary;

    public Candidate() {

    }

    @Builder
    public static Candidate of(String name, int experience, int salary) {
        Candidate candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        return candidate;
    }
}
