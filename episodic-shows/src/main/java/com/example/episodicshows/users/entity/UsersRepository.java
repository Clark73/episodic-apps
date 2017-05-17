package com.example.episodicshows.users.entity;


import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface UsersRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    default Stream<User> findAllAsStream() {
        return findAll().stream();
    }
}
