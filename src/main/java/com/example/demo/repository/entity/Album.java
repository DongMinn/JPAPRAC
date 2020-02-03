package com.example.demo.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Setter @Getter
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;
    private String etc;

    @Override
    public void removeStock(int count) {

    }
}
