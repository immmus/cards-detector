package org.detect.diapasons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum SuitDiapasons implements Diapasons, Predicate<Double> {
    DIAMONDS(sum -> sum >= 0.4500 && sum <= 0.4650, "DIAMONDS(БУБИ)", "b"),
    CLUBS(sum -> sum >= 0.5450 && sum <= 0.5650, "CLUBS(ТРЕФЫ)", "c"),
    SPADES(sum -> sum >= 0.5250 && sum <= 0.5400, "SPADES(ПИКИ)", "s"),
    HEARTS(sum -> sum >= 0.5000 && sum <= 0.5100, "HEARTS(ЧЕРВЫ)", "h"),
    NOT_DETECTED(
            sum -> !DIAMONDS.test(sum)
                    && !CLUBS.test(sum)
                    && !SPADES.test(sum)
                    && !HEARTS.test(sum),
            "NOT_DETECTED", "x"
    );

    private final Predicate<Double> predicate;
    private final String description;
    private final String shortDescription;

    @Override
    public boolean test(Double val) {
        return val < 0.8 && predicate.test(val);
    }
}