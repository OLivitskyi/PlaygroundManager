package org.example.playground.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Queue;
@Data
@NoArgsConstructor
public class PlaySite {
    private String id;
    private String name;
    private int capacity;
    private int doubleSwings;
    private int carousel;
    private int slide;
    private int ballPit;
    private List<Kid> kids;
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
