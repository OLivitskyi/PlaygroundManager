package org.example.playground.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BALL_PIT")
public class BallPit extends PlaySite {
    // ... To do
}