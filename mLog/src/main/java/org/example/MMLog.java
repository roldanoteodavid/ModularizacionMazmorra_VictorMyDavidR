package org.example;

import javax.swing.*;

public class MMLog extends JTextArea implements MLogInt {

    public MMLog() {
        super();
        this.setEditable(false);
        this.setRows(10);
        this.setColumns(30);
    }


    @Override
    public void addLogMessage(String message) {
        this.append(message);
    }

    @Override
    public void clearLog() {
        this.setText("");
    }
}
