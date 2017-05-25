package com.example.episodicshows.users;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.episodicshows.users.entity.User;
import com.example.episodicshows.users.model.UserView;
import com.example.episodicshows.users.service.UsersService;
import com.example.episodicshows.viewings.ViewingsController;
import com.example.episodicshows.viewings.entity.Viewing;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {

//    private final UsersService usersService;
//
//    public UsersController(UsersService usersService) {
//        this.usersService = usersService;
//        assert usersService != null;
//    }
//
//    @PostMapping("")
//    public ResponseEntity<UserView> createUser(@RequestBody User user) {
//        UserView userView = usersService.createUser(user);
//        userView.add(linkTo(methodOn(this.getClass()).createUser(new User())).withSelfRel());
//        userView.add(linkTo(methodOn(this.getClass()).getUserById(userView.getEntityId())).withRel("get_user"));
//
//        return new ResponseEntity<>(userView, HttpStatus.OK);
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserView> getUserById (@PathVariable long userId) {
//        UserView userView = usersService.getUserById(userId);
//        userView.add(linkTo(methodOn(this.getClass()).getUserById(userId)).withSelfRel());
//        userView.add(linkTo(methodOn(ViewingsController.class).getRecentlyViewed(userId)).withRel("recently_viewed"));
//        userView.add(linkTo(methodOn(ViewingsController.class).updateOrCreateViewingStatus(userId, new Viewing())).withRel("add_viewing"));
//
//        return new ResponseEntity<>(userView, HttpStatus.OK);
//    }
//
//    @GetMapping("")
//    public ResponseEntity<Resources<UserView>> getUsers() {
//        return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
//    }

}
