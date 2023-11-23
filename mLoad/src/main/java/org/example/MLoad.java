package org.example;


import org.example.model.Door;
import org.example.model.Dungeon;
import org.example.model.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MLoad implements MLoadInterface{

    private Dungeon dungeon;
    @Override
    public void loadXMLFile() {
        try {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            j.setAcceptAllFileFilterUsed(false);
            j.addChoosableFileFilter(new FileNameExtensionFilter("Only XML", "xml"));
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File xmlFile = new File(j.getSelectedFile().getAbsolutePath());
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                NodeList roomList = doc.getElementsByTagName("room");
                dungeon = new Dungeon();
                List<Room> rooms = new ArrayList<>();

                for (int i = 0; i < roomList.getLength(); i++) {
                    Node roomNode = roomList.item(i);

                    if (roomNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element roomElement = (Element) roomNode;
                        Room room = new Room();
                        room.setId(roomElement.getAttribute("id"));
                        room.setDescription(roomElement.getElementsByTagName("description").item(0).getTextContent());

                        NodeList doorList = roomElement.getElementsByTagName("door");
                        List<Door> doors = new ArrayList<>();
                        for (int k = 0; k < doorList.getLength(); k++) {
                            Node doorNode = doorList.item(k);
                            if (doorNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element doorElement = (Element) doorNode;
                                Door door = new Door(doorElement.getAttribute("name"), doorElement.getAttribute("dest"));
                                doors.add(door);
                            }
                        }
                        room.setDoors(doors);
                        rooms.add(room);
                    }
                }

                dungeon.setRooms(rooms);
            }
        } catch (Exception e) {
            dungeon = null;
        }
    }

    @Override
    public Dungeon getDungeon() {
        return dungeon;
    }
}

