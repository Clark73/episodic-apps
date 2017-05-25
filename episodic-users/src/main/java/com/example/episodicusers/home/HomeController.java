package com.example.episodicusers.home;


import com.example.episodicusers.home.model.HomeOptions;
import com.example.episodicusers.users.UsersController;
import com.example.episodicusers.users.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class HomeController {


    @GetMapping("/")
    public ResponseEntity<HomeOptions> options() {
        HomeOptions homeOptions = new HomeOptions();

        homeOptions.add(linkTo(methodOn(UsersController.class).getUsers()).withRel("get_users"));
        homeOptions.add(linkTo(methodOn(UsersController.class).createUser(new User())).withRel("create_user"));

        homeOptions.add(linkTo(methodOn(HomeController.class).options()).withSelfRel());

        return new ResponseEntity<>(homeOptions, HttpStatus.OK);
    }





}
