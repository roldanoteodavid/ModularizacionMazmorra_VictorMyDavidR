package org.example;

import org.example.modelo.Room;

import java.util.EventListener;

public interface MMoveListener extends EventListener {
    void roomUpdated(Room room);
}
