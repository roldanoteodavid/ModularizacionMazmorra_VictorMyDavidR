package org.example;



import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;

public class ComponenteFecha extends JPanel {

    private int dia, mes, year;

    private JComboBox<Integer> comboDay;
    private JComboBox<Integer> comboMonth;
    private JTextField comboYear;

    public ComponenteFecha() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        comboDay = new JComboBox<>();
        comboMonth = new JComboBox<>();
        comboYear = new JTextField("", 4);
        comboYear.setFont(comboYear.getFont().deriveFont(20f));

        for (int i = 1; i <= 31; i++) {
            comboDay.addItem(i);
        }
        for (int i = 1; i <= 12; i++) {
            comboMonth.addItem(i);
        }
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        setBorder(border);

        add(comboDay);
        add(comboMonth);
        add(comboYear);
    }




    public void setDate(int diai, int mesi, int yeari) {
        this.dia = diai;
        this.mes = mesi;
        this.year = yeari;

        comboDay.setSelectedItem(diai);
        comboMonth.setSelectedItem(mesi);
        comboYear.setText(String.valueOf(yeari));
    }

    private boolean esBisiesto(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
