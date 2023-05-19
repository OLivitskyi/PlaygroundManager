package org.example.Impl;

import org.example.Domain.Kid;
import org.example.Domain.PlaySite;
import org.example.Interfaces.PlaygroundManager;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlaygroundManagerImpl implements PlaygroundManager {
    private Map<String, PlaySite> playSites;
    private int totalVisitorCount;

    public PlaygroundManagerImpl() {
        this.playSites = new HashMap<>();
        this.totalVisitorCount = 0;
    }

    @Override
    public PlaySite createPlaySite(String name, int capacity, int doubleSwings, int carousel, int slide, int ballPit) {
        PlaySite playSite = new PlaySite();
        playSite.setId(UUID.randomUUID().toString());
        playSite.setName(name);
        playSite.setCapacity(capacity);
        playSite.setDoubleSwings(doubleSwings);
        playSite.setCarousel(carousel);
        playSite.setSlide(slide);
        playSite.setBallPit(ballPit);
        playSite.setKids(new ArrayList<>());
        playSite.setQueue(new LinkedList<>());

        playSites.put(playSite.getId(), playSite);
        return playSite;
    }

    @Override
    public List<PlaySite> getAllPlaySites() {
        return new ArrayList<>(playSites.values());
    }

    @Override
    public PlaySite getPlaySiteById(String id) {
        return playSites.get(id);
    }

    @Override
    public void removePlaySite(String id) {
        playSites.remove(id);
    }

    @Override
    public void addKidToPlaySite(String playSiteId, Kid kid) {
        PlaySite playSite = playSites.get(playSiteId);
        if (playSite != null && playSite.getKids().size() < playSite.getCapacity()) {
            playSite.getKids().add(kid);
            totalVisitorCount++;
        } else {
            System.out.println("Play site is at full capacity. Kid cannot be added.");
        }
    }

    @Override
    public void removeKidFromPlaySite(String playSiteId, Kid kid) {
        PlaySite playSite = playSites.get(playSiteId);
        if (playSite != null) {
            playSite.getKids().remove(kid);
            totalVisitorCount--;
        }
    }

    @Override
    public boolean enqueueKid(String playSiteId, Kid kid) {
        PlaySite playSite = playSites.get(playSiteId);
        if (playSite != null && playSite.getKids().size() >= playSite.getCapacity()) {
            kid.setAcceptsWaiting(true);
            playSite.getQueue().offer(kid);
            return true;
        }
        return false;
    }

    @Override
    public void removeKidFromQueue(String playSiteId, Kid kid) {
        PlaySite playSite = playSites.get(playSiteId);
        if (playSite != null) {
            playSite.getQueue().remove(kid);
        }
    }

    @Override
    public double getPlaySiteUtilization(String playSiteId) {
        PlaySite playSite = playSites.get(playSiteId);
        if (playSite != null) {
            int totalCapacity = playSite.getCapacity();
            int currentOccupancy = playSite.getKids().size();
            return (double) currentOccupancy / totalCapacity * 100;
        }
        return 0;
    }

    @Override
    public int getTotalVisitorCount() {
        return totalVisitorCount;
    }
}
