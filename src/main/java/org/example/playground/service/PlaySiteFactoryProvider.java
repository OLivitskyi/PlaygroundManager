package org.example.playground.service;

import org.example.playground.impl.BallPitFactory;
import org.example.playground.impl.CarouselFactory;
import org.example.playground.impl.SlideFactory;
import org.example.playground.impl.SwingFactory;
import org.example.playground.interfaces.PlaySiteFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlaySiteFactoryProvider {

    private Map<String, PlaySiteFactory> factoryMap;

    public PlaySiteFactoryProvider() {
        factoryMap = new HashMap<>();
        factoryMap.put("SWING", new SwingFactory());
        factoryMap.put("CAROUSEL", new CarouselFactory());
        factoryMap.put("SLIDE", new SlideFactory());
        factoryMap.put("BALL_PIT", new BallPitFactory());
    }

    public PlaySiteFactory getFactory(String type) {
        PlaySiteFactory factory = factoryMap.get(type.toUpperCase());
        if (factory == null) {
            throw new IllegalArgumentException("Invalid play site type: " + type);
        }
        return factory;
    }
}