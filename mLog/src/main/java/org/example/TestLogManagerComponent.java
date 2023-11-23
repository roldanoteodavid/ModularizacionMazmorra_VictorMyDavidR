package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TestLogManagerComponent {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test LogManagerComponent");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Crear LogManagerComponent (nuestro JTextArea con funcionalidades de LogManager)
        MMLog logTextArea = new MMLog();

        // Botón para generar números aleatorios
        JButton generateNumberButton = new JButton("Generar numero");


        generateNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generar un número aleatorio y agregarlo al logTextArea
                Random rand = new Random();
                int randomNumber = rand.nextInt(100); // Generar números del 0 al 99

                logTextArea.addLogMessage("Número generado: " + randomNumber + "\n");

            }
        });

        JButton clearLog = new JButton("Borrar historial");

        clearLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logTextArea.clearLog();
            }
        });

        JPanel panel = new JPanel();
        panel.add(generateNumberButton);
        panel.add(clearLog);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        panel.add(scrollPane);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
