package com.example.musicdbexam.model.entity;

import com.example.musicdbexam.model.entity.enums.ArtistNameEnum;

import javax.persistence.*;

@Entity
@Table(name = "artists")
public class Artist extends BaseEntity{

    private ArtistNameEnum name;
    private String careerInformation;

    public Artist() {
    }

    @Enumerated(EnumType.STRING)
    public ArtistNameEnum getName() {
        return name;
    }

    public void setName(ArtistNameEnum name) {
        this.name = name;
    }

    @Column(name = "career_information", columnDefinition = "TEXT")
    public String getCareerInformation() {
        return careerInformation;
    }

    public void setCareerInformation(String careerInformation) {
        this.careerInformation = careerInformation;
    }
}
