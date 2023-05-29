package org.example.playground.impl;

import org.example.playground.domain.Kid;
import org.example.playground.domain.PlaySite;
import org.example.playground.interfaces.PlaygroundManager;
import org.example.playground.repository.KidRepository;
import org.example.playground.repository.PlaySiteRepository;
import org.example.playground.service.PlaySiteFactoryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlaygroundManagerImpl implements PlaygroundManager {
    private final PlaySiteRepository playSiteRepository;
    private final PlaySiteFactoryProvider factoryProvider;

    private KidRepository kidRepository;
    private int totalVisitorCount;

    private static final int WAITING_TIME = 30; // Waiting time in minutes

    @Autowired
    public PlaygroundManagerImpl(PlaySiteRepository playSiteRepository, PlaySiteFactoryProvider factoryProvider) {
        this.playSiteRepository = playSiteRepository;
        this.factoryProvider = factoryProvider;
    }
    @Override
    public boolean enqueueKid(String playSiteId, Kid kid) {
        Optional<PlaySite> optionalPlaySite = playSiteRepository.findById(playSiteId);
        if (optionalPlaySite.isPresent()) {
            PlaySite playSite = optionalPlaySite.get();
            if (playSite.getKids().size() >= playSite.getCapacity()) {
                if(kid.isAcceptsWaiting()){
                    kid.setAcceptsWaiting(true);
                    playSite.getQueue().add(kid);
                    kidRepository.save(kid);  // save kid entity after adding it to the queue
                    playSiteRepository.save(playSite);
                    schedulePatienceTimer(playSiteId, kid);
                    return true;
                }
            }
        }
        return false;
    }


    private void schedulePatienceTimer(String playSiteId, Kid kid) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                removeKidFromQueue(playSiteId, kid);
            }
        }, WAITING_TIME * 60 * 1000);
    }
    @Override
    public PlaySite createPlaySite(String name, int capacity, int doubleSwings, int carousel, int slide, int ballPit) {
        PlaySite playSite = new PlaySite();
        playSite.setName(name);
        playSite.setCapacity(capacity);
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

    public void addKidToPlaySite(String playSiteId, Kid kid) {
        Optional<PlaySite> optionalPlaySite = playSiteRepository.findById(playSiteId);
        if (optionalPlaySite.isPresent()) {
            PlaySite playSite = optionalPlaySite.get();
            boolean success = playSite.addKidToPlaySite(kid);
            if (success) {
                totalVisitorCount++;
                kidRepository.save(kid);  // save kid entity after adding it
                playSiteRepository.save(playSite);
            } else {
                System.out.println("Play site is at full capacity. Kid cannot be added.");
            }
        }
    }


    public void removeKidFromPlaySite(String playSiteId, Kid kid) {
        Optional<PlaySite> optionalPlaySite = playSiteRepository.findById(playSiteId);
        if (optionalPlaySite.isPresent()) {
            PlaySite playSite = optionalPlaySite.get();
            boolean success = playSite.removeKidFromPlaySite(kid);
            if (success) {
                totalVisitorCount--;
                kidRepository.save(kid);  // save kid entity after removing it
                playSiteRepository.save(playSite); // save playSite entity after removing a kid from it
            }
        }
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
            int currentOccupancy = playSite.getKids().size() + playSite.getQueue().size();
            return (double) currentOccupancy / totalCapacity * 100;
        }
        return 0;
    }

    @Override
    public int getTotalVisitorCount() {
        return totalVisitorCount;
    }
}
