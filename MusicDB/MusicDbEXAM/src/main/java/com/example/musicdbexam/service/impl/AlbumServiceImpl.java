package com.example.musicdbexam.service.impl;

import com.example.musicdbexam.model.entity.Album;
import com.example.musicdbexam.model.service.AlbumServiceModel;
import com.example.musicdbexam.model.view.AlbumViewModel;
import com.example.musicdbexam.repository.AlbumRepository;
import com.example.musicdbexam.service.AlbumService;
import com.example.musicdbexam.service.ArtistService;
import com.example.musicdbexam.service.UserService;
import com.example.musicdbexam.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final UserService userService;
    private final ArtistService artistService;

    public AlbumServiceImpl(AlbumRepository albumRepository, ModelMapper modelMapper, CurrentUser currentUser, UserService userService, ArtistService artistService) {
        this.albumRepository = albumRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.userService = userService;
        this.artistService = artistService;
    }

    @Override
    public void addAlbum(AlbumServiceModel albumServiceModel) {

        Album album = modelMapper.map(albumServiceModel, Album.class);
        album.setAddedFrom(userService.findById(currentUser.getId()));

        album.setArtist(artistService.findByArtistNameEnum(albumServiceModel.getArtist()));

        albumRepository.save(album);
    }

    @Override
    public List<AlbumViewModel> findAllOrderedByNumberOfCopiesDesc() {
        return albumRepository.findAllAlbumsAndOrderThemByCopiesDesc()
                .stream()
                .map(album -> modelMapper.map(album, AlbumViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}
