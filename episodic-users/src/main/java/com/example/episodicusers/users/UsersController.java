package com.example.episodicusers.users;


import com.example.episodicusers.users.entity.User;
import com.example.episodicusers.users.model.UserView;
import com.example.episodicusers.users.service.UsersService;
import org.springframework.cloud.client.hypermedia.DynamicServiceInstanceProvider;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final DynamicServiceInstanceProvider shows;

    public UsersController(UsersService usersService, DynamicServiceInstanceProvider shows) {
        this.usersService = usersService;
        this.shows = shows;
        assert usersService != null;
    }

    @PostMapping("")
    public ResponseEntity<UserView> createUser(@RequestBody User user) {
        UserView userView = usersService.createUser(user);
        userView.add(linkTo(methodOn(this.getClass()).createUser(new User())).withSelfRel());
        userView.add(linkTo(methodOn(this.getClass()).getUserById(userView.getEntityId())).withRel("get_user"));

        return new ResponseEntity<>(userView, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserView> getUserById (@PathVariable long userId) {
        UserView userView = usersService.getUserById(userId);
        userView.add(linkTo(methodOn(this.getClass()).getUserById(userId)).withSelfRel());
//        userView.add(linkTo(methodOn(ViewingsController.class).getRecentlyViewed(userId)).withRel("recently_viewed"));
//        userView.add(linkTo(methodOn(ViewingsController.class).updateOrCreateViewingStatus(userId, new Viewing())).withRel("add_viewing"));

        return new ResponseEntity<>(userView, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Resources<UserView>> getUsers() {
        return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
    }

}
