package org.example.playground.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SWING")
public class Swing extends PlaySite {
    // ... To do
}