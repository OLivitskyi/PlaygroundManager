package org.example.playground.controller;

import org.example.playground.domain.Kid;
import org.example.playground.domain.PlaySite;
import org.example.playground.dto.request.KidRequest;
import org.example.playground.dto.response.PlaySiteDTO;
import org.example.playground.interfaces.PlaygroundManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playground")
public class PlaygroundController {
    @Autowired
    private PlaygroundManager playgroundManager;

    @PostMapping("/playSites")
    @ResponseStatus(HttpStatus.CREATED)
    public PlaySiteDTO createPlaySite(@RequestBody PlaySiteDTO playSiteDTO) {
        PlaySite playSite = playgroundManager.createPlaySite(playSiteDTO.getName(),
                playSiteDTO.getCapacity(),
                playSiteDTO.getDoubleSwings(),
                playSiteDTO.getCarousel(),
                playSiteDTO.getSlide(),
                playSiteDTO.getBallPit());
        return convertToDto(playSite);
    }

    @GetMapping("/playSites")
    public List<PlaySiteDTO> getAllPlaySites() {
        List<PlaySite> playSites = playgroundManager.getAllPlaySites();
        return playSites.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/playSites/{id}")
    public PlaySiteDTO getPlaySiteById(@PathVariable("id") String id) {
        PlaySite playSite = playgroundManager.getPlaySiteById(id);
        return convertToDto(playSite);
    }

    @DeleteMapping("/playSites/{id}")
    public void removePlaySite(@PathVariable("id") String id) {
        playgroundManager.removePlaySite(id);
    }

    @PostMapping("/playSites/{id}/kids")
    @ResponseStatus(HttpStatus.CREATED)
    public void addKidToPlaySite(@PathVariable("id") String playSiteId, @RequestBody KidRequest kidRequest) {
        Kid kid = convertToKid(kidRequest);
        playgroundManager.addKidToPlaySite(playSiteId, kid);
    }

    @DeleteMapping("/playSites/{id}/kids")
    public void removeKidFromPlaySite(@PathVariable("id") String playSiteId, @RequestBody KidRequest kidRequest) {
        Kid kid = convertToKid(kidRequest);
        playgroundManager.removeKidFromPlaySite(playSiteId, kid);
    }

    @PostMapping("/playSites/{id}/queue")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean enqueueKid(@PathVariable("id") String playSiteId, @RequestBody KidRequest kidRequest) {
        Kid kid = convertToKid(kidRequest);
        return playgroundManager.enqueueKid(playSiteId, kid);
    }

    @DeleteMapping("/playSites/{id}/queue")
    public void removeKidFromQueue(@PathVariable("id") String playSiteId, @RequestBody KidRequest kidRequest) {
        Kid kid = convertToKid(kidRequest);
        playgroundManager.removeKidFromQueue(playSiteId, kid);
    }

    @GetMapping("/playSites/{id}/utilization")
    public double getPlaySiteUtilization(@PathVariable("id") String playSiteId) {
        return playgroundManager.getPlaySiteUtilization(playSiteId);
    }

    @GetMapping("/totalVisitorCount")
    public int getTotalVisitorCount() {
        return playgroundManager.getTotalVisitorCount();
    }

    private PlaySiteDTO convertToDto(PlaySite playSite) {
        PlaySiteDTO playSiteDTO = new PlaySiteDTO();
        playSiteDTO.setId(playSite.getId());
        playSiteDTO.setName(playSite.getName());
        playSiteDTO.setCapacity(playSite.getCapacity());
        playSiteDTO.setDoubleSwings(playSite.getDoubleSwings());
        playSiteDTO.setCarousel(playSite.getCarousel());
        playSiteDTO.setSlide(playSite.getSlide());
        playSiteDTO.setBallPit(playSite.getBallPit());
        return playSiteDTO;
    }

    private Kid convertToKid(KidRequest kidRequest) {
        Kid kid = new Kid();
        kid.setName(kidRequest.getName());
        kid.setAge(kidRequest.getAge());
        kid.setTicketNumber(kidRequest.getTicketNumber());
        kid.setAcceptsWaiting(kidRequest.isAcceptsWaiting());
        return kid;
    }
}
