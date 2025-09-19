package com.synclife.studyroom.room.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String roomname;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Long capacity;

    @Builder
    public Room(String roomname, String location, Long capacity){
        this.roomname = roomname;
        this.location = location;
        this.capacity = capacity;
    }

}
