package org.example.playground.interfaces;

import org.example.playground.domain.PlaySite;

public interface PlaySiteFactory {
    PlaySite createPlaySite(String name, int capacity);
}
