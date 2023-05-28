package org.example.playground.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Entity
@Data
public class PlaySite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int capacity;
    private boolean hasQueue;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Kid> kids = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Kid> queue = new ArrayList<>();
    public boolean addKidToPlaySite(Kid kid) {
        if (this.kids.size() < this.capacity) {
            this.kids.add(kid);
            return true;
        } else {
            return false;
        }
    }
    public boolean removeKidFromPlaySite(Kid kid) {
        return this.kids.remove(kid);
    }

}
