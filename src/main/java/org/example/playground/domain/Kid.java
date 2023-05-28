package org.example.playground.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Kid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private static int ticketCounter = 0;
    private String name;
    private int age;
    private int ticketNumber;
    private boolean acceptsWaiting;

    public Kid(String name, int age, boolean acceptsWaiting) {
        this.name = name;
        this.age = age;
        this.acceptsWaiting = acceptsWaiting;
        this.ticketNumber = ++ticketCounter;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kid kid = (Kid) o;
        return Objects.equals(id, kid.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}