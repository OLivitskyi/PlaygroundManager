package org.example.playground.service;

import org.example.playground.repository.KidRepository;
import org.example.playground.domain.PlaySite;
import org.example.playground.domain.Kid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaySiteService {

    @Autowired
    private KidRepository kidRepository;

    public boolean addKidToPlaySite(PlaySite playSite, Long kidId) {
        if (playSite.getKids().size() >= playSite.getCapacity()) {
            if (playSite.isHasQueue()) {
                Kid kid = kidRepository.findById(kidId).orElse(null);
                if (kid != null && kid.isAcceptsWaiting()) {
                    playSite.getQueue().add(kid);
                    return true;
                }
            }
            return false;
        } else {
            Kid kid = kidRepository.findById(kidId).orElse(null);
            if (kid != null) {
                playSite.getKids().add(kid);
                return true;
            }
            return false;
        }
    }

    public boolean removeKidFromPlaySite(PlaySite playSite, Long kidId) {
        Kid kid = kidRepository.findById(kidId).orElse(null);
        if (kid != null && playSite.getKids().contains(kid)) {
            playSite.getKids().remove(kid);
            return true;
        }
        return false;
    }
}
