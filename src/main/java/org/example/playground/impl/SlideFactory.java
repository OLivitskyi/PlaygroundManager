package org.example.playground.impl;

import org.example.playground.domain.PlaySite;
import org.example.playground.domain.Slide;
import org.example.playground.interfaces.PlaySiteFactory;
import org.springframework.stereotype.Component;

@Component
public class SlideFactory implements PlaySiteFactory {
    @Override
    public PlaySite createPlaySite(String name, int capacity) {
        Slide slide = new Slide();
        slide.setName(name);
        slide.setCapacity(capacity);
        return slide;
    }
}