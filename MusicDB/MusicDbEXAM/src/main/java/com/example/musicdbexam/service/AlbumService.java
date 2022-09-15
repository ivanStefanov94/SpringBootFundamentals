package com.example.musicdbexam.service;

import com.example.musicdbexam.model.service.AlbumServiceModel;
import com.example.musicdbexam.model.view.AlbumViewModel;

import java.util.List;

public interface AlbumService {
    void addAlbum(AlbumServiceModel albumServiceModel);

    List<AlbumViewModel> findAllOrderedByNumberOfCopiesDesc();

    void deleteAlbum(Long id);
}
