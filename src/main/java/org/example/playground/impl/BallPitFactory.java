package org.example.playground.impl;

import org.example.playground.domain.BallPit;
import org.example.playground.domain.PlaySite;
import org.example.playground.interfaces.PlaySiteFactory;
import org.springframework.stereotype.Component;

@Component
public class BallPitFactory implements PlaySiteFactory {
    @Override
    public PlaySite createPlaySite(String name, int capacity) {
        BallPit ballPit = new BallPit();
        ballPit.setName(name);
        ballPit.setCapacity(capacity);
        return ballPit;
    }
}
