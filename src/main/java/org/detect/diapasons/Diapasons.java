package org.detect.diapasons;

public interface Diapasons {
    String getDescription();
    String getShortDescription();

    static boolean diapason(double sum, double from, double to) {
        return sum >= from && sum <= to;
    }
}
