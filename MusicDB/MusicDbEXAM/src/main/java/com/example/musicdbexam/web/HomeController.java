package com.example.musicdbexam.web;

import com.example.musicdbexam.model.view.AlbumViewModel;
import com.example.musicdbexam.service.AlbumService;
import com.example.musicdbexam.service.UserService;
import com.example.musicdbexam.util.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final CurrentUser currentUser;
    private final UserService userService;
    private final AlbumService albumService;

    public HomeController(CurrentUser currentUser, UserService userService, AlbumService albumService) {
        this.currentUser = currentUser;
        this.userService = userService;
        this.albumService = albumService;
    }

    @GetMapping()
    public String index(Model model){

        if(currentUser.getId() == null) {
            return "index";
        }

        List<AlbumViewModel> albums =albumService.findAllOrderedByNumberOfCopiesDesc();

        model.addAttribute("albums", albums);

        model.addAttribute("totalCopies",
                albums.stream()
                        .map(AlbumViewModel::getCopies)
                        .reduce(Integer::sum)
                        .orElse(0)
        );

        return "home";
    }
}
