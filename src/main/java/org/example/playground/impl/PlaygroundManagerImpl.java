package org.example.playground.impl;

import org.example.playground.domain.Kid;
import org.example.playground.domain.PlaySite;
import org.example.playground.interfaces.PlaygroundManager;
import org.example.playground.repository.PlaySiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlaygroundManagerImpl implements PlaygroundManager {
    private PlaySiteRepository playSiteRepository;
    private int totalVisitorCount;

    @Autowired
    public PlaygroundManagerImpl(PlaySiteRepository playSiteRepository) {
        this.playSiteRepository = playSiteRepository;
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

        playSiteRepository.save(playSite);
        return playSite;
    }

    @Override
    public List<PlaySite> getAllPlaySites() {
        return playSiteRepository.findAll();
    }

    @Override
    public PlaySite getPlaySiteById(String id) {
        Optional<PlaySite> playSite = playSiteRepository.findById(id);
        return playSite.orElse(null);
    }

    @Override
    public void removePlaySite(String id) {
        playSiteRepository.deleteById(id);
    }

    @Override
    public void addKidToPlaySite(String playSiteId, Kid kid) {
        Optional<PlaySite> optionalPlaySite = playSiteRepository.findById(playSiteId);
        if (optionalPlaySite.isPresent()) {
            PlaySite playSite = optionalPlaySite.get();
            if (playSite.getKids().size() < playSite.getCapacity()) {
                playSite.getKids().add(kid);
                totalVisitorCount++;
                playSiteRepository.save(playSite);
            } else {
                System.out.println("Play site is at full capacity. Kid cannot be added.");
            }
        }
    }

    @Override
    public void removeKidFromPlaySite(String playSiteId, Kid kid) {
        Optional<PlaySite> optionalPlaySite = playSiteRepository.findById(playSiteId);
        if (optionalPlaySite.isPresent()) {
            PlaySite playSite = optionalPlaySite.get();
            playSite.getKids().remove(kid);
            totalVisitorCount--;
            playSiteRepository.save(playSite);
        }
    }

    @Override
    public boolean enqueueKid(String playSiteId, Kid kid) {
        Optional<PlaySite> optionalPlaySite = playSiteRepository.findById(playSiteId);
        if (optionalPlaySite.isPresent()) {
            PlaySite playSite = optionalPlaySite.get();
            if (playSite.getKids().size() >= playSite.getCapacity()) {
                kid.setAcceptsWaiting(true);
                playSite.getQueue().offer(kid);
                playSiteRepository.save(playSite);
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeKidFromQueue(String playSiteId, Kid kid) {
        Optional<PlaySite> optionalPlaySite = playSiteRepository.findById(playSiteId);
        if (optionalPlaySite.isPresent()) {
            PlaySite playSite = optionalPlaySite.get();
            playSite.getQueue().remove(kid);
            playSiteRepository.save(playSite);
        }
    }

    @Override
    public double getPlaySiteUtilization(String playSiteId) {
        Optional<PlaySite> optionalPlaySite = playSiteRepository.findById(playSiteId);
        if (optionalPlaySite.isPresent()) {
            PlaySite playSite = optionalPlaySite.get();
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
