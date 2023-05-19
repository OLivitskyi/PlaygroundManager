package org.example.Controller;

import org.example.Domain.Kid;
import org.example.Domain.PlaySite;
import org.example.Dto.KidDTO;
import org.example.Dto.PlaySiteDTO;
import org.example.Dto.PlaySiteRequest;
import org.example.Interfaces.PlaygroundManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playground")
public class PlaygroundController {
    @Autowired
    private PlaygroundManager playgroundManager;

    @PostMapping("/playSites")
    public PlaySiteDTO createPlaySite(@RequestBody PlaySiteRequest playSiteRequest) {
        PlaySite playSite = playgroundManager.createPlaySite(playSiteRequest.getName(),
                playSiteRequest.getCapacity(),
                playSiteRequest.getDoubleSwings(),
                playSiteRequest.getCarousel(),
                playSiteRequest.getSlide(),
                playSiteRequest.getBallPit());
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
    public void addKidToPlaySite(@PathVariable("id") String playSiteId, @RequestBody KidDTO kidDto) {
        Kid kid = convertToKid(kidDto);
        playgroundManager.addKidToPlaySite(playSiteId, kid);
    }

    @DeleteMapping("/playSites/{id}/kids")
    public void removeKidFromPlaySite(@PathVariable("id") String playSiteId, @RequestBody KidDTO kidDto) {
        Kid kid = convertToKid(kidDto);
        playgroundManager.removeKidFromPlaySite(playSiteId, kid);
    }

    @PostMapping("/playSites/{id}/queue")
    public boolean enqueueKid(@PathVariable("id") String playSiteId, @RequestBody KidDTO kidDto) {
        Kid kid = convertToKid(kidDto);
        return playgroundManager.enqueueKid(playSiteId, kid);
    }

    @DeleteMapping("/playSites/{id}/queue")
    public void removeKidFromQueue(@PathVariable("id") String playSiteId, @RequestBody KidDTO kidDto) {
        Kid kid = convertToKid(kidDto);
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

    private Kid convertToKid(KidDTO kidDto) {
        Kid kid = new Kid();
        kid.setName(kidDto.getName());
        kid.setAge(kidDto.getAge());
        kid.setTicketNumber(kidDto.getTicketNumber());
        kid.setAcceptsWaiting(kidDto.isAcceptsWaiting());
        return kid;
    }
}
