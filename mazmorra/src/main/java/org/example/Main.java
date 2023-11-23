package org.example;

import org.example.model.Dungeon;
import org.example.model.Room;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        JPanel mainPanel = new JPanel(new BorderLayout());

        JFrame frame = new JFrame("Mazmorra");
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setSize(1280, 720);
        frame.setTitle("Mazmorras");
        frame.setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        JMenuItem menuItemLoad = new JMenuItem("Load");
        menu.add(menuItemSalir);
        menu.add(menuItemLoad);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        MLoad mLoad = new MLoad();
        MLog mLog = new MLog();
        MTree mTree = new MTree();

        MMoveListener listener = new MMoveListener() {
            @Override
            public void roomUpdated(Room room) {
                mLog.addLogMessage("Has ido a: " + room.getId() + "\n");
            }
        };

        MMove mMove = new MMove(listener);


        JSplitPane splitPaneVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneVertical.setTopComponent(mMove);
        splitPaneVertical.setBottomComponent(mLog);
        splitPaneVertical.setDividerLocation(300);

        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneHorizontal.setLeftComponent(mTree);
        splitPaneHorizontal.setRightComponent(splitPaneVertical);
        splitPaneHorizontal.setDividerLocation(200);

        mainPanel.add(splitPaneHorizontal, BorderLayout.CENTER);
        mainPanel.updateUI();

        menuItemSalir.addActionListener(e -> System.exit(0));
        menuItemLoad.addActionListener(e -> {
            mLog.clearLog();
            mLoad.loadXMLFile();
            Dungeon dungeon = mLoad.getDungeon();
            List<Room> rooms = dungeon.getRooms();
            mMove.setRooms(rooms);
            mMove.loadRoom(rooms.get(0));
            mLog.addLogMessage("Comienza tu aventura, estás en la habitación " + rooms.get(0).getId() + "\n");
            splitPaneHorizontal.setLeftComponent(mTree.createJTree(dungeon));
            mainPanel.updateUI();

        });
    }
}