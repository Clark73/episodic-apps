package com.example.episodicshows.users.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.episodicshows.users.UsersController;
import com.example.episodicshows.users.entity.User;
import com.example.episodicshows.users.entity.UsersRepository;
import com.example.episodicshows.users.model.UserView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional(readOnly = true)
    public List<UserView> getAllUsers() {
        return usersRepository.findAllAsStream()
                .map(u -> new UserView(
                        u.getId(),
                        u.getEmail()))
                .map(u -> {
                    u.add(linkTo(methodOn(UsersController.class).getUserById(u.getEntityId())).withRel("get_user"));
                    return u;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserView getUserById(long userId) {
        User user = usersRepository.findOne(userId);
        return new UserView(user.getId(), user.getEmail());
    }

    public UserView createUser (User user) {
        user = usersRepository.save(user);
        return new UserView(user.getId(), user.getEmail());
    }

}
