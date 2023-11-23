package org.example;


import org.example.modelo.Room;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MMove extends JPanel implements MMoveInt {


    private java.util.List<Room> rooms;
    private MMoveListener listener;

    private JButton north;
    private JButton south;
    private JButton weast;
    private JButton east;

    private JLabel actualRoomDescription;

    public MMove(MMoveListener listener) {
        //super();
        this.listener = listener;
        initComponente();
    }

    private void initComponente() {
//        actualRoomDescription = new JLabel("COMENZAR");
//        actualRoomDescription.setFont(new Font("Arial", Font.BOLD, 20));
        actualRoomDescription.setHorizontalAlignment(SwingConstants.CENTER);
        actualRoomDescription.setVerticalAlignment(SwingConstants.CENTER);

        north = new JButton("North");
        north.setEnabled(false);
        south = new JButton("South");
        south.setEnabled(false);
        weast = new JButton("Weast");
        weast.setEnabled(false);
        east = new JButton("East");
        east.setEnabled(false);

        setLayout(new BorderLayout());
        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.SOUTH);
        add(weast, BorderLayout.WEST);
        add(east, BorderLayout.EAST);
        add(actualRoomDescription, BorderLayout.CENTER);
    }

    @Override
    public void setRooms(java.util.List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public void loadRoom(Room room) {
        north.setEnabled(false);
        south.setEnabled(false);
        weast.setEnabled(false);
        east.setEnabled(false);

        actualRoomDescription.setText(room.getDescription());

        room.getDoorList().forEach(door -> {
            if (door.getName().equalsIgnoreCase("Norte")) {
                north.setEnabled(true);
                north.removeActionListener(north.getActionListeners().length > 0 ? north.getActionListeners()[0] : null);
                north.addActionListener(e -> {
                    Room nextRoom = rooms.stream()
                            .filter(r -> r.getId()
                                    .equalsIgnoreCase(room.getDoorList().stream().filter(d -> d.getName().equalsIgnoreCase("Norte"))
                                            .findFirst().get()
                                            .getDest())
                            ).findFirst().get();
                    listener.roomUpdated(nextRoom);
                    loadRoom(nextRoom);
                });
            } else if (door.getName().equalsIgnoreCase("Sur")) {
                south.setEnabled(true);
                south.removeActionListener(south.getActionListeners().length > 0 ? south.getActionListeners()[0] : null);
                south.addActionListener(e -> {
                    Room nextRoom = rooms.stream()
                            .filter(r -> r.getId()
                                    .equalsIgnoreCase(room.getDoorList().stream().filter(d -> d.getName().equalsIgnoreCase("Sur"))
                                            .findFirst().get()
                                            .getDest())
                            ).findFirst().get();
                    listener.roomUpdated(nextRoom);
                    loadRoom(nextRoom);
                });
            } else if (door.getName().equalsIgnoreCase("Oeste")) {
                weast.setEnabled(true);
                weast.removeActionListener(weast.getActionListeners().length > 0 ? weast.getActionListeners()[0] : null);
                weast.addActionListener(e -> {
                    Room nextRoom = rooms.stream()
                            .filter(r -> r.getId()
                                    .equalsIgnoreCase(room.getDoorList().stream().filter(d -> d.getName().equalsIgnoreCase("Oeste"))
                                            .findFirst().get()
                                            .getDest())
                            ).findFirst().get();
                    listener.roomUpdated(nextRoom);
                    loadRoom(nextRoom);
                });
            } else if (door.getName().equalsIgnoreCase("Este")) {
                east.setEnabled(true);
                east.removeActionListener(east.getActionListeners().length > 0 ? east.getActionListeners()[0] : null);
                east.addActionListener(e -> {
                    Room nextRoom = rooms.stream()
                            .filter(r -> r.getId()
                                    .equalsIgnoreCase(room.getDoorList().stream().filter(d -> d.getName().equalsIgnoreCase("Este"))
                                            .findFirst().get()
                                            .getDest())
                            ).findFirst().get();
                    listener.roomUpdated(nextRoom);
                    loadRoom(nextRoom);
                });
            }
        });
    }
}