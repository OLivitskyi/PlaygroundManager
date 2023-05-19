package org.example.Interfaces;

import org.example.Domain.Kid;
import org.example.Domain.PlaySite;

import java.util.List;

public interface PlaygroundManager {
    PlaySite createPlaySite(String name, int capacity, int doubleSwings, int carousel, int slide, int ballPit);
    List<PlaySite> getAllPlaySites();
    PlaySite getPlaySiteById(String id);
    void removePlaySite(String id);

    void addKidToPlaySite(String playSiteId, Kid kid);
    void removeKidFromPlaySite(String playSiteId, Kid kid);
    boolean enqueueKid(String playSiteId, Kid kid);
    void removeKidFromQueue(String playSiteId, Kid kid);

    double getPlaySiteUtilization(String playSiteId);
    int getTotalVisitorCount();
}
