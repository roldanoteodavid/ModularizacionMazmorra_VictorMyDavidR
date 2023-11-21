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

    private JButton up;
    private JButton down;
    private JButton left;
    private JButton right;

    private JLabel actualRoomDescription;

    public void MMove(MMoveListener listener) {
        super();
        this.listener = listener;
        initComponente();
    }

    private void initComponente() {
        actualRoomDescription = new JLabel("Carga una mazmorra");
        actualRoomDescription.setFont(new Font("Arial", Font.BOLD, 20));
        actualRoomDescription.setHorizontalAlignment(SwingConstants.CENTER);
        actualRoomDescription.setVerticalAlignment(SwingConstants.CENTER);

        up = new JButton("Up");
        up.setEnabled(false);
        down = new JButton("Down");
        down.setEnabled(false);
        left = new JButton("Left");
        left.setEnabled(false);
        right = new JButton("Right");
        right.setEnabled(false);

        setLayout(new BorderLayout());
        add(up, BorderLayout.NORTH);
        add(down, BorderLayout.SOUTH);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(actualRoomDescription, BorderLayout.CENTER);
    }

    @Override
    public void setRooms(java.util.List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public void loadRoom(Room room) {
        up.setEnabled(false);
        down.setEnabled(false);
        left.setEnabled(false);
        right.setEnabled(false);

        actualRoomDescription.setText(room.getDescription());

        room.getDoorList().forEach(door -> {
            if (door.getName().equalsIgnoreCase("Norte")) {
                up.setEnabled(true);
                up.removeActionListener(up.getActionListeners().length > 0 ? up.getActionListeners()[0] : null);
                up.addActionListener(e -> {
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
                down.setEnabled(true);
                down.removeActionListener(down.getActionListeners().length > 0 ? down.getActionListeners()[0] : null);
                down.addActionListener(e -> {
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
                left.setEnabled(true);
                left.removeActionListener(left.getActionListeners().length > 0 ? left.getActionListeners()[0] : null);
                left.addActionListener(e -> {
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
                right.setEnabled(true);
                right.removeActionListener(right.getActionListeners().length > 0 ? right.getActionListeners()[0] : null);
                right.addActionListener(e -> {
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
