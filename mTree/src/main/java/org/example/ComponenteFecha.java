package org.example;



import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;

public class ComponenteFecha extends JPanel implements ComponenteFechaInt {

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

    @Override
    public LocalDate getDate() throws FechaException {
        if (comboYear.getText().isEmpty()) throw new FechaIncompletaException();

        dia = (int) comboDay.getSelectedItem();
        mes = (int) comboMonth.getSelectedItem();
        year = Integer.parseInt(comboYear.getText());


        if (mes == 2 && (dia > 29 || (dia == 29 && !esBisiesto(year)))) {
            throw new FechaImposibleException();
        }

        if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia == 31) {
            throw new FechaIncorrectaException();
        }

        return LocalDate.of(year, mes, dia);
    }

    @Override
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
