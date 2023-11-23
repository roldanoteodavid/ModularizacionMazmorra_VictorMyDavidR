package org.example;

import org.example.model.Room;

import java.util.EventListener;

public interface MMoveListener extends EventListener {
    void roomUpdated(Room room);
}
