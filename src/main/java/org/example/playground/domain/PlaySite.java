package org.example.playground.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Queue;

@Entity
@Data
@NoArgsConstructor
public class PlaySite {
    @Id
    private String id;
    private String name;
    private int capacity;
    private int doubleSwings;
    private int carousel;
    private int slide;
    private int ballPit;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Kid> kids;

    @OneToMany(cascade = CascadeType.ALL)
    private Queue<Kid> queue;

    public PlaySite(String id, String name, int capacity, int doubleSwings, int carousel, int slide, int ballPit) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.doubleSwings = doubleSwings;
        this.carousel = carousel;
        this.slide = slide;
        this.ballPit = ballPit;
    }
}
