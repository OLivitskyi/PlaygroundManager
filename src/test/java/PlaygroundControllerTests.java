import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.playground.controller.PlaygroundController;
import org.example.playground.domain.Kid;
import org.example.playground.domain.PlaySite;
import org.example.playground.dto.request.KidRequest;
import org.example.playground.dto.response.PlaySiteDTO;
import org.example.playground.dto.request.PlaySiteRequest;
import org.example.playground.interfaces.PlaygroundManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PlaygroundControllerTests {
    private MockMvc mockMvc;
    @Mock
    private PlaygroundManager playgroundManager;

    @InjectMocks
    private PlaygroundController playgroundController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(playgroundController).build();
    }

    @Test
    void createPlaySite_ShouldCallPlaygroundManagerAndReturnCreatedPlaySite() throws Exception {
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

        MvcResult result = mockMvc.perform(post("/playground/playSites")
                        .contentType("application/json")
                        .content(asJsonString(playSiteRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        PlaySiteDTO playSiteDTO = new ObjectMapper().readValue(result.getResponse().getContentAsString(), PlaySiteDTO.class);

        verify(playgroundManager).createPlaySite("Playground A", 50, 2, 1, 1, 1);
        assertEquals("1", playSiteDTO.getId());
        assertEquals("Playground A", playSiteDTO.getName());
        assertEquals(50, playSiteDTO.getCapacity());
        assertEquals(2, playSiteDTO.getDoubleSwings());
        assertEquals(1, playSiteDTO.getCarousel());
        assertEquals(1, playSiteDTO.getSlide());
        assertEquals(1, playSiteDTO.getBallPit());
    }

    @Test
    void getAllPlaySites_ShouldCallPlaygroundManagerAndReturnAllPlaySites() throws Exception {
        List<PlaySite> playSites = new ArrayList<>();
        playSites.add(new PlaySite("1", "Playground A", 50, 2, 1, 1, 1));
        playSites.add(new PlaySite("2", "Playground B", 30, 1, 0, 2, 0));

        when(playgroundManager.getAllPlaySites()).thenReturn(playSites);

        MvcResult result = mockMvc.perform(get("/playground/playSites"))
                .andExpect(status().isOk())
                .andReturn();

        PlaySiteDTO[] playSiteDTOs = new ObjectMapper().readValue(result.getResponse().getContentAsString(), PlaySiteDTO[].class);

        verify(playgroundManager).getAllPlaySites();
        assertEquals(2, playSiteDTOs.length);
    }

    @Test
    void getPlaySiteUtilization_ShouldReturnPlaySiteUtilization() throws Exception {
        String playSiteId = "1";
        double utilization = 50.0;

        when(playgroundManager.getPlaySiteUtilization(eq(playSiteId))).thenReturn(utilization);

        mockMvc.perform(get("/playground/playSites/{id}/utilization", playSiteId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(utilization)));
    }

    @Test
    void getTotalVisitorCount_ShouldReturnTotalVisitorCount() throws Exception {
        int totalVisitorCount = 100;

        when(playgroundManager.getTotalVisitorCount()).thenReturn(totalVisitorCount);

        mockMvc.perform(get("/playground/totalVisitorCount"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(totalVisitorCount)));
    }

    @Test
    void removePlaySite_ShouldCallPlaygroundManagerToRemovePlaySite() throws Exception {
        String playSiteId = "1";

        mockMvc.perform(delete("/playground/playSites/{id}", playSiteId))
                .andExpect(status().isOk());

        verify(playgroundManager).removePlaySite(playSiteId);
    }

    @Test
    void addKidToPlaySite_ShouldCallPlaygroundManagerToAddKidToPlaySite() throws Exception {
        String playSiteId = "1";
        KidRequest kidRequest = new KidRequest();
        kidRequest.setName("John");
        kidRequest.setAge(5);
        kidRequest.setTicketNumber(123);

        mockMvc.perform(post("/playground/playSites/{id}/kids", playSiteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(kidRequest)))
                .andExpect(status().isCreated());

        Kid kid = convertToKid(kidRequest);
        verify(playgroundManager).addKidToPlaySite(playSiteId, kid);
    }

    @Test
    void removeKidFromPlaySite_ShouldCallPlaygroundManagerToRemoveKidFromPlaySite() throws Exception {
        String playSiteId = "1";
        KidRequest kidRequest = new KidRequest();
        kidRequest.setName("John");
        kidRequest.setAge(5);
        kidRequest.setTicketNumber(123);

        mockMvc.perform(delete("/playground/playSites/{id}/kids", playSiteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(kidRequest)))
                .andExpect(status().isOk());

        Kid kid = convertToKid(kidRequest);
        verify(playgroundManager).removeKidFromPlaySite(playSiteId, kid);
    }

    @Test
    void removeKidFromQueue_ShouldCallPlaygroundManagerToRemoveKidFromQueue() throws Exception {
        String playSiteId = "1";
        KidRequest kidRequest = new KidRequest();
        kidRequest.setName("John");
        kidRequest.setAge(5);
        kidRequest.setTicketNumber(123);

        mockMvc.perform(delete("/playground/playSites/{id}/queue", playSiteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(kidRequest)))
                .andExpect(status().isOk());

        Kid kid = convertToKid(kidRequest);
        verify(playgroundManager).removeKidFromQueue(playSiteId, kid);
    }

    // Helper method to convert object to JSON string
    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    // Helper method to convert KidRequest to Kid
    private Kid convertToKid(KidRequest kidRequest) {
        Kid kid = new Kid();
        kid.setName(kidRequest.getName());
        kid.setAge(kidRequest.getAge());
        kid.setTicketNumber(kidRequest.getTicketNumber());
        return kid;
    }
}
