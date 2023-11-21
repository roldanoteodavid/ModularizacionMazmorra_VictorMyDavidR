package org.example;

import org.example.exceptions.FechaException;

import java.time.LocalDate;

public interface ComponenteFechaInt {

    LocalDate getDate() throws FechaException;

    void setDate(int dia, int mes, int year);
}
