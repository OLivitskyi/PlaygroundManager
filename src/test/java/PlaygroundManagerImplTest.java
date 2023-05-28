import org.example.playground.domain.Kid;
import org.example.playground.domain.PlaySite;
import org.example.playground.impl.PlaygroundManagerImpl;
import org.example.playground.repository.KidRepository;
import org.example.playground.repository.PlaySiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaygroundManagerImplTest {
    private PlaygroundManagerImpl playgroundManager;

    @Mock
    private KidRepository kidRepository;

    @Mock
    private PlaySiteRepository playSiteRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        playgroundManager = new PlaygroundManagerImpl(kidRepository, playSiteRepository);
    }

    @Test
    public void testAddKidToPlaySite() {
        Kid mockKid = new Kid();
        mockKid.setId(1L);
        PlaySite mockPlaySite = new PlaySite();
        mockPlaySite.setId(1L);
        mockPlaySite.setCapacity(1);

        when(playSiteRepository.findById(anyString())).thenReturn(Optional.of(mockPlaySite));

        playgroundManager.addKidToPlaySite("1", mockKid);

        verify(playSiteRepository, times(1)).save(mockPlaySite);
        assertEquals(1, mockPlaySite.getKids().size());
        assertTrue(mockPlaySite.getKids().contains(mockKid));
    }

    @Test
    public void testAddKidToPlaySite_whenPlaySiteFull() {
        Kid mockKid = new Kid();
        mockKid.setId(1L);
        PlaySite mockPlaySite = new PlaySite();
        mockPlaySite.setId(1L);
        mockPlaySite.setCapacity(0);

        when(playSiteRepository.findById(anyString())).thenReturn(Optional.of(mockPlaySite));

        playgroundManager.addKidToPlaySite("1", mockKid);

        verify(playSiteRepository, never()).save(mockPlaySite);
        assertFalse(mockPlaySite.getKids().contains(mockKid));
    }


    @Test
    public void testRemoveKidFromPlaySite_whenKidNotPresent() {
        Kid mockKid = new Kid();
        mockKid.setId(1L);
        PlaySite mockPlaySite = new PlaySite();
        mockPlaySite.setId(1L);

        when(playSiteRepository.findById(anyString())).thenReturn(Optional.of(mockPlaySite));

        playgroundManager.removeKidFromPlaySite("1", mockKid);

        verify(playSiteRepository, never()).save(mockPlaySite);
    }

    @Test
    public void testEnqueueKid() {
        Kid mockKid = new Kid();
        mockKid.setId(1L);
        mockKid.setAcceptsWaiting(true);
        PlaySite mockPlaySite = new PlaySite();
        mockPlaySite.setId(1L);
        mockPlaySite.setCapacity(0);

        when(playSiteRepository.findById(anyString())).thenReturn(Optional.of(mockPlaySite));

        boolean result = playgroundManager.enqueueKid("1", mockKid);

        verify(playSiteRepository, times(1)).save(mockPlaySite);
        assertTrue(result);
        assertTrue(mockPlaySite.getQueue().contains(mockKid));
    }

    @Test
    public void testEnqueueKid_whenKidNotAcceptWaiting() {
        Kid mockKid = new Kid();
        mockKid.setId(1L);
        mockKid.setAcceptsWaiting(false);
        PlaySite mockPlaySite = new PlaySite();
        mockPlaySite.setId(1L);
        mockPlaySite.setCapacity(0);

        when(playSiteRepository.findById(anyString())).thenReturn(Optional.of(mockPlaySite));

        boolean result = playgroundManager.enqueueKid("1", mockKid);

        verify(playSiteRepository, never()).save(mockPlaySite);
        assertFalse(result);
        assertFalse(mockPlaySite.getQueue().contains(mockKid));
    }
}
