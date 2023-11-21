package org.example.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Room {
    private String id;
    private List<Door> doorList;
    private String description;
}
