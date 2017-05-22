package com.example.episodicshows.home;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.episodicshows.home.model.HomeOptions;
import com.example.episodicshows.shows.ShowsController;
import com.example.episodicshows.shows.entity.Show;
import com.example.episodicshows.users.UsersController;
import com.example.episodicshows.users.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping("/")
    public ResponseEntity<HomeOptions> options() {
        HomeOptions homeOptions = new HomeOptions();

        homeOptions.add(linkTo(methodOn(ShowsController.class).getShows()).withRel("get_shows"));
        homeOptions.add(linkTo(methodOn(ShowsController.class).createShow(new Show())).withRel("create_show"));

        homeOptions.add(linkTo(methodOn(UsersController.class).getUsers()).withRel("get_users"));
        homeOptions.add(linkTo(methodOn(UsersController.class).createUser(new User())).withRel("create_user"));

        homeOptions.add(linkTo(methodOn(HomeController.class).options()).withSelfRel());

        return new ResponseEntity<>(homeOptions, HttpStatus.OK);
    }





}
