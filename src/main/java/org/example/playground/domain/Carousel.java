package org.example.playground.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAROUSEL")
public class Carousel extends PlaySite {
    // ... To do
}