import org.example.playground.controller.PlaygroundController;
import org.example.playground.domain.PlaySite;
import org.example.playground.dto.response.PlaySiteDTO;
import org.example.playground.dto.request.PlaySiteRequest;
import org.example.playground.interfaces.PlaygroundManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlaygroundControllerTests {
    @Mock
    private PlaygroundManager playgroundManager;

    @InjectMocks
    private PlaygroundController playgroundController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlaySite_shouldCallPlaygroundManagerAndReturnCreatedPlaySite() {
        PlaySiteRequest playSiteRequest = new PlaySiteRequest();
        playSiteRequest.setName("Playground A");
        playSiteRequest.setCapacity(50);
        playSiteRequest.setDoubleSwings(2);
        playSiteRequest.setCarousel(1);
        playSiteRequest.setSlide(1);
        playSiteRequest.setBallPit(1);

        PlaySite createdPlaySite = new PlaySite();
        createdPlaySite.setId("1");
        createdPlaySite.setName("Playground A");
        createdPlaySite.setCapacity(50);
        createdPlaySite.setDoubleSwings(2);
        createdPlaySite.setCarousel(1);
        createdPlaySite.setSlide(1);
        createdPlaySite.setBallPit(1);

        when(playgroundManager.createPlaySite(anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(createdPlaySite);

        PlaySiteDTO result = playgroundController.createPlaySite(playSiteRequest);

        verify(playgroundManager).createPlaySite("Playground A", 50, 2, 1, 1, 1);
        assertEquals("1", result.getId());
        assertEquals("Playground A", result.getName());
        assertEquals(50, result.getCapacity());
        assertEquals(2, result.getDoubleSwings());
        assertEquals(1, result.getCarousel());
        assertEquals(1, result.getSlide());
        assertEquals(1, result.getBallPit());
    }

    @Test
    void getAllPlaySites_shouldCallPlaygroundManagerAndReturnAllPlaySites() {
        List<PlaySite> playSites = new ArrayList<>();
        playSites.add(new PlaySite("1", "Playground A", 50, 2, 1, 1, 1));
        playSites.add(new PlaySite("2", "Playground B", 30, 1, 0, 2, 0));

        when(playgroundManager.getAllPlaySites()).thenReturn(playSites);

        List<PlaySiteDTO> result = playgroundController.getAllPlaySites();

        verify(playgroundManager).getAllPlaySites();
        assertEquals(2, result.size());
    }
}
