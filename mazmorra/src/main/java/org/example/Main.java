package org.example;

import org.example.modelo.Door;
import org.example.modelo.Room;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Main {
    public static String actualRoom = "R0";
    public static JButton north;
    public static JButton south;
    public static JButton east;
    public static JButton west;
    public static JTextArea textArea;
    public static JTextArea salasVisitadas;
    public static Panel leftPanel;
    public static Panel rightPanel;
    public static XMLJTree xmljTree;
    static List<Room> roomList = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mazmorra");
        frame.setSize(1280, 720);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem start = new JMenuItem("Start");
        menu.add(load);
        menu.add(start);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        leftPanel = new Panel();
        leftPanel.setLayout(new CardLayout());

        xmljTree = new XMLJTree(null);
        xmljTree.setPreferredSize(new Dimension(200, 400));
        xmljTree.setVisible(false);
        leftPanel.add(new JScrollPane(xmljTree));
        load.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML File", "xml");
            fileChooser.setFileFilter(filter);
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                xmljTree.setPath(fileChooser.getSelectedFile().getAbsolutePath());
                xmljTree.setVisible(true);
            }
        });

        start.addActionListener(e -> setInicio());

        rightPanel = new Panel();
        rightPanel.setLayout(new CardLayout());
        rightPanel.setVisible(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        frame.getContentPane().add(splitPane);

        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new CardLayout());
        Panel topPanel = new Panel();
        topPanel.setLayout(new BorderLayout());
        north = new JButton("Norte");
        north.setVisible(false);
        south = new JButton("Sur");
        south.setVisible(false);
        east = new JButton("Este");
        east.setVisible(false);
        west = new JButton("Oeste");
        west.setVisible(false);

        textArea = new JTextArea();

        topPanel.add(north, BorderLayout.NORTH);
        topPanel.add(south, BorderLayout.SOUTH);
        topPanel.add(east, BorderLayout.EAST);
        topPanel.add(west, BorderLayout.WEST);
        topPanel.add(textArea, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(400, 400));
        salasVisitadas = new JTextArea();
        bottomPanel.add(salasVisitadas);

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
        splitPane.setRightComponent(splitPane2);
        frame.validate();
        frame.repaint();

    }

    static void setInicio() {
        salasVisitadas.setText("");
        setRoom(0);
        xmljTree.setVisible(true);
        rightPanel.setVisible(true);

        ActionListener moveAction = e -> {
            String direction = e.getActionCommand();
            Room room = roomList.stream().filter(room1 -> room1.getId().equals(actualRoom)).findFirst().orElse(null);
            if (room != null) {
                Optional<Door> doorOptional = room.getDoorList()
                        .stream()
                        .filter(door -> door.getName().equals(direction))
                        .findFirst();

                if (doorOptional.isPresent()) {
                    Door door = doorOptional.get();
                    Room roomDestino = roomList.stream()
                            .filter(habitacion -> habitacion.getId().equals(door.getDest()))
                            .findFirst()
                            .orElse(null);

                    if (roomDestino != null) {
                        actualRoom = roomDestino.getId();
                        setRoom(roomList.indexOf(roomDestino));
                    } else {
                        // Manejo del caso en el que roomDestino es nulo
                    }
                } else {
                    // Manejo del caso en el que no se encuentra la puerta en la dirección especificada
                }
            } else {
                // Manejo del caso en el que no se encuentra la habitación actual
            }
        };
        if (north.getActionListeners().length > 0) {
            north.removeActionListener(north.getActionListeners()[0]);
            south.removeActionListener(south.getActionListeners()[0]);
            east.removeActionListener(east.getActionListeners()[0]);
            west.removeActionListener(west.getActionListeners()[0]);
        }

        north.addActionListener(moveAction);
        south.addActionListener(moveAction);
        east.addActionListener(moveAction);
        west.addActionListener(moveAction);


    }

    static void setRoom(int index) {
        north.setVisible(false);
        south.setVisible(false);
        east.setVisible(false);
        west.setVisible(false);

        textArea.setText(roomList.get(index).getDescription());
        textArea.setEditable(false);
        if (!salasVisitadas.getText().isEmpty()) {
            salasVisitadas.append(", ");
        }
        salasVisitadas.append(roomList.get(index).getId());
        salasVisitadas.setEditable(false);
        for (int i = 0; i < roomList.get(index).getDoorList().size(); i++) {
            switch (roomList.get(index).getDoorList().get(i).getName()) {
                case "Norte":
                    north.setVisible(true);
                    break;
                case "Sur":
                    south.setVisible(true);
                    break;
                case "Este":
                    east.setVisible(true);
                    break;
                case "Oeste":
                    west.setVisible(true);
                    break;
            }
        }
    }

    public static class XMLJTree extends JTree {
        DefaultTreeModel dtModel = null;

        public XMLJTree(String filePath) {
            if (filePath != null) {
                setPath(filePath);
            }
        }

        public void setPath(String filePath) {
            Node root;
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(filePath);
                root = doc.getDocumentElement();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Can't parse file", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (root != null) {
                dtModel = new DefaultTreeModel(builtTreeNode(root));
                this.setModel(dtModel);
                parseXml(root);
            }
        }

        private DefaultMutableTreeNode builtTreeNode(Node root) {
            DefaultMutableTreeNode dmtNode;
            NamedNodeMap attributes = root.getAttributes();
            if (attributes != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(root.getNodeName());
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(i);
                    sb.append(" ").append(attribute.getNodeValue());
                }
                if (Objects.equals(root.getNodeName(), "description")) {
                    sb.append(": ").append(root.getTextContent());
                }
                dmtNode = new DefaultMutableTreeNode(sb.toString());
            } else {
                dmtNode = new DefaultMutableTreeNode(root.getNodeValue());
            }

            NodeList children = root.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    dmtNode.add(builtTreeNode(child));
                }
            }
            return dmtNode;
        }

        private void parseXml(Node root) {
            NodeList rooms = root.getChildNodes();

            for (int i = 1; i < rooms.getLength(); i++) {
                if (rooms.item(i).getNodeName().equals("room")) {
                    List<Door> doorList = new ArrayList<>();
                    String description = "";
                    String id = rooms.item(i).getAttributes().getNamedItem("id").getNodeValue();

                    for (int j = 1; j < rooms.item(i).getChildNodes().getLength(); j++) {
                        if (rooms.item(i).getChildNodes().item(j).getNodeName().equals("door")) {
                            doorList.add(new Door(rooms.item(i).getChildNodes().item(j).getAttributes().getNamedItem("name").getNodeValue(),
                                    rooms.item(i).getChildNodes().item(j).getAttributes().getNamedItem("dest").getNodeValue()));
                        }

                        if (rooms.item(i).getChildNodes().item(j).getNodeName().equals("description")) {
                            description = rooms.item(i).getChildNodes().item(j).getTextContent();
                        }
                    }

                    roomList.add(new Room(id, doorList, description));
                }
            }
        }
    }
}