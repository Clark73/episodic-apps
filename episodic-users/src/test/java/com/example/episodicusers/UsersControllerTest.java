package com.example.episodicusers;


import com.example.episodicusers.users.entity.User;
import com.example.episodicusers.users.entity.UsersRepository;
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
public class UsersControllerTest{

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    @Rollback
    @Test
    public void getUserTest () throws Exception {
        User user = new User("kevin.test@dish.com");
        User user1 = new User("kevin.test2@dish.com");

        usersRepository.save(user);
        usersRepository.save(user1);

        MockHttpServletRequestBuilder request = get("/users")
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].email", is("kevin.test2@dish.com")))
                .andExpect(jsonPath("$.[0].id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.[1].email", is("kevin.test@dish.com")))
                .andExpect(jsonPath("$.[1].id", instanceOf(Number.class)));;
    }

    @Transactional
    @Rollback
    @Test
    public void createUserTest () throws Exception {
        long count = usersRepository.count();
        String payload = "{\"email\": \"kevin.clark@dish.com\"}";

        MockHttpServletRequestBuilder request = post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("kevin.clark@dish.com")))
                .andExpect(jsonPath("$.id", instanceOf(Number.class)));

        assert count + 1 == usersRepository.count();
    }

}
