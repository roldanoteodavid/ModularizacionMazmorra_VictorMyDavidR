package main;

import componente.*;
import modelo.Room;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainClass {

    public static void main(String[] args) {

        JPanel mainPanel = new JPanel(new BorderLayout());

        JFrame frame = new JFrame();
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setSize(800, 600);
        frame.setTitle("Mazmorras");
        frame.setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        JMenuItem menuItemLoad = new JMenuItem("Cargar mapa de mazmorras");
        menu.add(menuItemSalir);
        menu.add(menuItemLoad);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        MLoad xmlLoader = new MLoad();
        MLog jMazmorraLog = new MLog();
        MTree jMazmorraTree = new MTree();

        JMazmorraNavigationListener listener = new JMazmorraNavigationListener() {
            @Override
            public void roomUpdated(Room room) {
                jMazmorraLog.addLogMessage("Has ido a: " + room.getDescription());
            }
        };

        JMazmorraNavigation jMazmorraNavigation = new JMazmorraNavigation(listener);


        JSplitPane splitPaneVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneVertical.setTopComponent(jMazmorraNavigation);
        splitPaneVertical.setBottomComponent(jMazmorraLog);
        splitPaneVertical.setDividerLocation(100);

        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneHorizontal.setLeftComponent(jMazmorraTree);
        splitPaneHorizontal.setRightComponent(splitPaneVertical);
        splitPaneHorizontal.setDividerLocation(200);

        mainPanel.add(splitPaneHorizontal, BorderLayout.CENTER);
        mainPanel.updateUI();

        menuItemSalir.addActionListener(e -> System.exit(0));
        menuItemLoad.addActionListener(e -> {
            try {
                xmlLoader.loadXMLFile();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            List<Room> rooms = new ArrayList<Room>(xmlLoader.getDungeon().getRooms());
            jMazmorraNavigation.setRooms(rooms);
            jMazmorraNavigation.loadRoom(rooms.get(0));
            jMazmorraLog.addLogMessage("Room " + rooms.get(0).getDescription());
            jMazmorraTree.createTree(rooms);
        });
    }
}