package com.example.episodicshows.users;

import com.example.episodicshows.users.entity.User;
import com.example.episodicshows.users.entity.UsersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        assert usersRepository != null;
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return usersRepository.save(user);
    }

    @GetMapping("")
    public List<User> getUsers() {
        return usersRepository.findAll();
    }

}
