import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.example.playground.domain.*;
import org.example.playground.impl.*;
import org.example.playground.repository.PlaySiteRepository;
import org.example.playground.service.PlaySiteFactoryProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlaygroundManagerTest {

    private PlaygroundManagerImpl playgroundManager;

    @Mock
    private PlaySiteRepository playSiteRepository;

    @Mock
    private PlaySiteFactoryProvider playSiteFactoryProvider;

    @BeforeEach
    public void setup() {
        playgroundManager = new PlaygroundManagerImpl(playSiteRepository, playSiteFactoryProvider);
    }

    @Test
    public void testCreatePlaySite_Swing() {
        when(playSiteFactoryProvider.getFactory("swing")).thenReturn(new SwingFactory());

        PlaySite swing = playgroundManager.createPlaySite("swing", 2, 3, 5, 6, 7);
        assertEquals(swing.getClass(), Swing.class);
    }

    @Test
    public void testCreatePlaySite_Carousel() {
        when(playSiteFactoryProvider.getFactory("carousel")).thenReturn(new CarouselFactory());

        PlaySite carousel = playgroundManager.createPlaySite("carousel", 2, 3, 5, 6, 7);
        assertEquals(carousel.getClass(), Carousel.class);
    }

    @Test
    public void testCreatePlaySite_Slide() {
        when(playSiteFactoryProvider.getFactory("slide")).thenReturn(new SlideFactory());

        PlaySite slide = playgroundManager.createPlaySite("slide", 2, 3, 5, 6, 7);
        assertEquals(slide.getClass(), Slide.class);
    }

    @Test
    public void testCreatePlaySite_BallPit() {
        when(playSiteFactoryProvider.getFactory("ballpit")).thenReturn(new BallPitFactory());

        PlaySite ballPit = playgroundManager.createPlaySite("ballpit", 2, 3, 5, 6, 7);
        assertEquals(ballPit.getClass(), BallPit.class);
    }

    @Test
    public void testAddKidToPlaySite() {
        PlaySite swing = new Swing();
        Kid kid = new Kid();
        kid.setId(1L);
        kid.setAge(7);
        kid.setName("Kid1");
        assertTrue(swing.addKidToPlaySite(kid));
    }

    @Test
    public void testRemoveKidFromPlaySite() {
        PlaySite swing = new Swing();
        Kid kid = new Kid();
        kid.setId(1L);
        kid.setAge(7);
        kid.setName("Kid1");
        swing.addKidToPlaySite(kid);
        assertTrue(swing.removeKidFromPlaySite(kid));
    }

    @Test
    public void testRemoveKidFromPlaySite_NotInPlaySite() {
        PlaySite swing = new Swing();
        Kid kid = new Kid();
        kid.setId(1L);
        kid.setAge(7);
        kid.setName("Kid1");
        assertFalse(swing.removeKidFromPlaySite(kid));
    }


}
