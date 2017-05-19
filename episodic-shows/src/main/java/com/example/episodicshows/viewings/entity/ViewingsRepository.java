package com.example.episodicshows.viewings.entity;


import com.example.episodicshows.model.GenericRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

public interface ViewingsRepository extends GenericRepository<Viewing, Long> {
    List<Viewing> findAllByUserId(long userId);

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    default Stream<Viewing> findAllByUserIdAsStream(long userId) { return findAllByUserId(userId).stream();}

    Viewing findByUserIdAndShowId(long userId, long showId);
}
