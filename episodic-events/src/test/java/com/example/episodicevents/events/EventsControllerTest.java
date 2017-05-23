package com.example.episodicevents.events;


import com.example.episodicevents.events.entity.EventsRepository;
import com.example.episodicevents.events.entity.ScrubEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EventsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EventsRepository eventsRepository;

    @Before
    public void setup() {
        eventsRepository.deleteAll();
    }

    @Test
    public void createPlayEvent () throws Exception {
        long count = eventsRepository.count();
        MockHttpServletRequestBuilder request = post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSON("/play.json"));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.recent_events.href").exists());

        assert count + 1 == eventsRepository.count();
    }

    @Test
    public void createPauseEvent () throws Exception {
        long count = eventsRepository.count();
        MockHttpServletRequestBuilder request = post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSON("/pause.json"));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.recent_events.href").exists());

        assert count + 1 == eventsRepository.count();
    }

    @Test
    public void createFFEvent () throws Exception {
        long count = eventsRepository.count();
        MockHttpServletRequestBuilder request = post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSON("/fastForward.json"));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.recent_events.href").exists());

        assert count + 1 == eventsRepository.count();
    }

    @Test
    public void createRewindEvent () throws Exception {
        long count = eventsRepository.count();
        MockHttpServletRequestBuilder request = post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSON("/rewind.json"));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.recent_events.href").exists());

        assert count + 1 == eventsRepository.count();
    }

    @Test
    public void createProgressEvent () throws Exception {
        long count = eventsRepository.count();
        MockHttpServletRequestBuilder request = post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSON("/progress.json"));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.recent_events.href").exists());

        assert count + 1 == eventsRepository.count();
    }

    @Test
    public void createScrubEvent () throws Exception {
        long count = eventsRepository.count();
        MockHttpServletRequestBuilder request = post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSON("/scrub.json"));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.recent_events.href").exists());

        assert count + 1 == eventsRepository.count();
    }

    @Test
    public void getEventsTest () throws Exception {
        MockHttpServletRequestBuilder request = post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSON("/scrub.json"));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.recent_events.href").exists());
//        ScrubEvent event = new ScrubEvent();
//        event.setData(new ScrubEvent.Data());
//        event.getData().setEndOffset(12);
//
//        eventsRepository.save(event);

        MockHttpServletRequestBuilder request2 = get("/recent?page=0&pageSize=20")
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request2)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events.[0].type", is("scrub")));
    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }

}
