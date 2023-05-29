package org.example.playground.impl;

import org.example.playground.domain.Carousel;
import org.example.playground.domain.PlaySite;
import org.example.playground.interfaces.PlaySiteFactory;
import org.springframework.stereotype.Component;

@Component
public class CarouselFactory implements PlaySiteFactory {
    @Override
    public PlaySite createPlaySite(String name, int capacity) {
        Carousel carousel = new Carousel();
        carousel.setName(name);
        carousel.setCapacity(capacity);
        return carousel;
    }
}