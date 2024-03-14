package com.tqs108636;

import java.time.LocalDate;

public class TestUtils {
    public static LocalDate parseDateFromString(String date) {
        // date string is as follows -> "DD-MM-YYYY"
        String[] parts = date.split("-");
        LocalDate d = LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
        return d;
    }

}
