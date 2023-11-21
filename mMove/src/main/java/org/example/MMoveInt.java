package org.example;

import org.example.modelo.Room;

import java.time.LocalDate;
import java.util.List;

public interface MMoveInt {

    void setRooms(List<Room> rooms);

    void loadRoom(Room room);
}
