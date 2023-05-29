package org.example.playground.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SLIDE")
public class Slide extends PlaySite {
    // ... To do
}