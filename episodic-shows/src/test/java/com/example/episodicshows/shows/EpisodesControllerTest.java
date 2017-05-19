package com.example.episodicshows.shows;

import com.example.episodicshows.shows.entity.Episode;
import com.example.episodicshows.shows.entity.EpisodesRepository;
import com.example.episodicshows.shows.entity.Show;
import com.example.episodicshows.shows.entity.ShowsRepository;
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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EpisodesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EpisodesRepository episodesRepository;

    @Autowired
    private ShowsRepository showsRepository;

    @Transactional
    @Rollback
    @Test
    public void getEpisodesTest () throws Exception {

        Episode episode = new Episode(1, 1, 2);
        Episode episode1 = new Episode(1, 2, 2);

        episodesRepository.save(episode);
        episodesRepository.save(episode1);

        MockHttpServletRequestBuilder request = get("/shows/2/episodes")
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.[0].seasonNumber", is(1)))
                .andExpect(jsonPath("$.[0].episodeNumber", is(1)))
                .andExpect(jsonPath("$.[0].title", is("S1 E1")))
                .andExpect(jsonPath("$.[0].showId").doesNotExist());

    }

    @Transactional
    @Rollback
    @Test
    public void createEpisodeTest () throws Exception {
        long count = episodesRepository.count();
        String payload = "{\"seasonNumber\": 1, \"episodeNumber\": 2}";

        MockHttpServletRequestBuilder request = post("/shows/11/episodes")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.seasonNumber", is(1)))
                .andExpect(jsonPath("$.episodeNumber", is(2)))
                .andExpect(jsonPath("$.title", is("S1 E2")))
                .andExpect(jsonPath("$.show_id").doesNotExist());

        assert count + 1 == episodesRepository.count();
    }



}
