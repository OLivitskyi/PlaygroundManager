package org.example.playground.impl;

import org.example.playground.domain.PlaySite;
import org.example.playground.domain.Swing;
import org.example.playground.interfaces.PlaySiteFactory;
import org.springframework.stereotype.Component;

@Component
public class SwingFactory implements PlaySiteFactory {
    @Override
    public PlaySite createPlaySite(String name, int capacity) {
        Swing swing = new Swing();
        swing.setName(name);
        swing.setCapacity(capacity);
        return swing;
    }
}