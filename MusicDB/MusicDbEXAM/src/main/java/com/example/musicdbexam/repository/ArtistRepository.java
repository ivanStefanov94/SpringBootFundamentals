package com.example.musicdbexam.repository;

import com.example.musicdbexam.model.entity.Artist;
import com.example.musicdbexam.model.entity.enums.ArtistNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByName(ArtistNameEnum artist);
}
