package com.example.episodicshows.model;


import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

public interface GenericRepository<T extends GenericEntity, ID extends Serializable> extends CrudRepository<T, ID> {
    List<T> findAll();

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    default Stream<T> findAllAsStream() {
        return findAll().stream();
    }

    default Map<Long, T> findAllAsMap () {
        return findAll().stream().collect(Collectors.toMap(
                GenericEntity::getId,
                Function.identity()
        ));
    }

}
