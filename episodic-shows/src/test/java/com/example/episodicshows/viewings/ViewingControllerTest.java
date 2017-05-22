package com.example.episodicshows.viewings;

import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.EpisodesRepository;
import com.example.episodicshows.shows.entity.Show;
import com.example.episodicshows.shows.entity.ShowsRepository;
import com.example.episodicshows.viewings.entity.Viewing;
import com.example.episodicshows.viewings.entity.ViewingsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ViewingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ViewingsRepository viewingRepository;

    @Autowired
    private ShowsRepository showsRepository;

    @Autowired
    private EpisodesRepository episodesRepository;



    private long show1Id;
    private long show2Id;
    private long ep1Id;
    private long ep2Id;

    @Before
    public void setup () {
        Show show = new Show("Game of Thrones");
        Show show1 = new Show("Fireplace 2017");

        this.show1Id = showsRepository.save(show).getId();
        this.show2Id = showsRepository.save(show1).getId();

        Episode episode = new Episode(1, 1, show2Id);
        Episode episode1 = new Episode(1, 2, show2Id);

        this.ep1Id = episodesRepository.save(episode).getId();
        this.ep2Id = episodesRepository.save(episode1).getId();

    }

    @Transactional
    @Rollback
    @Test
    public void getRecentViewingsTest () throws Exception {
        LocalDateTime date = LocalDateTime.now();
        Viewing view1 = new Viewing(1L, show1Id, ep1Id, date, 50);

        viewingRepository.save(view1);

        MockHttpServletRequestBuilder request = get("/users/1/recently-watched")
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recentViewings.[0].show.id", is((int) show1Id)))
                .andExpect(jsonPath("$.recentViewings.[0].show.name", is("Game of Thrones")))
                .andExpect(jsonPath("$.recentViewings.[0].episode.id", is((int) ep1Id)))
                .andExpect(jsonPath("$.recentViewings.[0].episode.seasonNumber", is(1)))
                .andExpect(jsonPath("$.recentViewings.[0].episode.episodeNumber", is(1)))
                .andExpect(jsonPath("$.recentViewings.[0].episode.title", is("S1 E1")))
                .andExpect(jsonPath("$.recentViewings.[0].updatedAt", is(date.toString())))
                .andExpect(jsonPath("$.recentViewings.[0].timeCode", is(50)));
    }

    @Transactional
    @Rollback
    @Test
    public void createRecentViewingsTest () throws Exception {
        long count = viewingRepository.count();
        String payload = String.format("{\"episodeId\": %d, \"updatedAt\": \"2017-05-04T11:45:34.9182\", \"timeCode\": 50 }", ep1Id);

        MockHttpServletRequestBuilder request = patch("/users/1/viewings")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeCode").doesNotExist());

        assert count + 1 == viewingRepository.count();
    }

    @Transactional
    @Rollback
    @Test
    public void updateRecentViewingsTest () throws Exception {
        Viewing view1 = new Viewing(1L, show2Id, ep1Id, LocalDateTime.now(), 50);

        viewingRepository.save(view1);

        long count = viewingRepository.count();

        String payload = String.format("{\"episodeId\": %d, \"updatedAt\": \"%s\", \"timeCode\": 51 }", ep1Id, LocalDateTime.now().toString());

        MockHttpServletRequestBuilder request = patch("/users/1/viewings")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeCode").doesNotExist());

        assert count == viewingRepository.count();
    }

}
