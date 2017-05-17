package com.example.episodicshows.users;



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
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ShowsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ShowsRepository showsRepository;

    @Transactional
    @Rollback
    @Test
    public void getShowsTest () throws Exception {
        Show show = new Show("Game of Thrones");
        Show show1 = new Show("Fireplace 2017");

        showsRepository.save(show);
        showsRepository.save(show1);

        MockHttpServletRequestBuilder request = get("/shows")
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is("Game of Thrones")))
                .andExpect(jsonPath("$.[0].id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.[1].name", is("Fireplace 2017")))
                .andExpect(jsonPath("$.[1].id", instanceOf(Number.class)));;
    }

    @Transactional
    @Rollback
    @Test
    public void createShowTest () throws Exception {
        long count = showsRepository.count();
        String payload = "{\"name\": \"GOT\"}";

        MockHttpServletRequestBuilder request = post("/shows")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("GOT")))
                .andExpect(jsonPath("$.id", instanceOf(Number.class)));

        assert count + 1 == showsRepository.count();
    }

}
