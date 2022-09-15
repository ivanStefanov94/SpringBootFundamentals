package com.example.musicdbexam.service;

import com.example.musicdbexam.model.entity.Artist;
import com.example.musicdbexam.model.entity.enums.ArtistNameEnum;

public interface ArtistService {
    void initializeArtists();

    Artist findByArtistNameEnum(ArtistNameEnum artist);

}
