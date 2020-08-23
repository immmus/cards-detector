package org.detect.diapasons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

import static org.detect.diapasons.Diapasons.diapason;

@Getter
@RequiredArgsConstructor
public enum CardRankDiapasons implements Diapasons, Predicate<RankAreasSums> {
    SIX(area ->
            diapason(area.getTopL(), 0.3815, 0.4695)
                    && diapason(area.getTopR(), 0.1625, 0.2115)
                    && diapason(area.getBottomL(), 0.3650, 0.4355)
                    && diapason(area.getBottomR(), 0.2615, 0.3077),
            "SIX(ШЕСТЬ)", "6"),
    ACE(area ->
            diapason(area.getTopL(), 0.2981, 0.3915)
                    && diapason(area.getTopR(), 0.0429, 0.1490)
                    && diapason(area.getBottomL(), 0.3894, 0.4915)
                    && diapason(area.getBottomR(), 0.2715, 0.3462),
            "ACE(ТУЗ)", "A"),
    KING(area ->
            diapason(area.getTopL(), 0.2981, 0.3965)
                    && diapason(area.getTopR(), 0.1895, 0.2610)
                    && diapason(area.getBottomL(), 0.3163, 0.3965)
                    && diapason(area.getBottomR(), 0.2725, 0.3175),
            "KING(КОРОЛЬ)", "K"),
    QUEEN(area ->
            diapason(area.getTopL(), 0.3085, 0.3665)
                    && diapason(area.getTopR(), 0.3410, 0.4075)
                    && diapason(area.getBottomL(), 0.3215, 0.3845)
                    && diapason(area.getBottomR(), 0.5429, 0.6155),
            "QUEEN(ДАМА)", "Q"),
    JACK(area ->
            diapason(area.getTopL(), 0.0329, 0.1650)
                    && diapason(area.getTopR(), 0.0090, 0.2155)
                    && diapason(area.getBottomL(), 0.3060, 0.4015)
                    && diapason(area.getBottomR(), 0.0050, 0.1735),
            "JACK(ВАЛЕТ)", "J"),
    TWO(area ->
            diapason(area.getTopL(), 0.2308, 0.3115)
                    && diapason(area.getTopR(), 0.2010, 0.2560)
                    && diapason(area.getBottomL(), 0.4327, 0.5115)
                    && diapason(area.getBottomR(), 0.1442, 0.1935),
            "TWO(ДВА)", "2"),
    THREE(area ->
            diapason(area.getTopL(), 0.3221, 0.3846)
                    && diapason(area.getTopR(), 0.1635, 0.2215)
                    && diapason(area.getBottomL(), 0.2981, 0.3558)
                    && diapason(area.getBottomR(), 0.2250, 0.2645),
            "THREE(ТРИ)", "3"),
    FOUR(area ->
            diapason(area.getTopL(), 0.1765, 0.2375)
                    && diapason(area.getTopR(), 0.2644, 0.2644)
                    && diapason(area.getBottomL(), 0.2580, 0.3215)
                    && diapason(area.getBottomR(), 0.3335, 0.3462),
            "FOUR(ЧЕТЫРЕ)", "4"),
    FIVE(area ->
            diapason(area.getTopL(), 0.3894, 0.4695)
                    && diapason(area.getTopR(), 0.1075, 0.1575)
                    && diapason(area.getBottomL(), 0.2875, 0.3925)
                    && diapason(area.getBottomR(), 0.2025, 0.2725),
            "FIVE(ПЯТЬ)", "5"),
    SEVEN(area ->
            diapason(area.getTopL(), 0.2352, 0.3195)
                    && diapason(area.getTopR(), 0.1835, 0.2525)
                    && diapason(area.getBottomL(), 0.2910, 0.3285)
                    && diapason(area.getBottomR(), 0.0035, 0.0215),
            "SEVEN(СЕМЬ)", "7"),
    EIGHT(area ->
            diapason(area.getTopL(), 0.3630, 0.4635)
                    && diapason(area.getTopR(), 0.1735, 0.2548)
                    && diapason(area.getBottomL(), 0.4135, 0.4975)
                    && diapason(area.getBottomR(), 0.2195, 0.2933),
            "EIGHT(ВОСЕМЬ)", "8"),
    NINE(area ->
            diapason(area.getTopL(), 0.3680, 0.4110)
                    && diapason(area.getTopR(), 0.2580, 0.2933)
                    && diapason(area.getBottomL(), 0.3640, 0.4145)
                    && diapason(area.getBottomR(), 0.2544, 0.2885),
            "NINE(ДЕВЯТЬ)", "9"),
    TEN(area ->
            diapason(area.getTopL(), 0.4855, .6125)
                    && diapason(area.getTopR(), 0.4010, 0.4712)
                    && diapason(area.getBottomL(), 0.4115, 0.5148)
                    && diapason(area.getBottomR(), 0.4115, 0.4808),
            "TEN(ДЕСЯТЬ)", "10"),
    NOT_DETECTED(
            area -> !ACE.test(area)
                    && !KING.test(area)
                    && !QUEEN.test(area)
                    && !JACK.test(area)
                    && !TWO.test(area)
                    && !THREE.test(area)
                    && !FOUR.test(area)
                    && !FIVE.test(area)
                    && !SIX.test(area)
                    && !SEVEN.test(area)
                    && !EIGHT.test(area)
                    && !NINE.test(area)
                    && !TEN.test(area),
            "NOT_DETECTED", "X"
    );

    private final Predicate<RankAreasSums> predicate;
    private final String description;
    private final String shortDescription;

    @Override
    public boolean test(RankAreasSums sums) {
        return (sums.getBottomL() < 0.8
                && sums.getBottomR() < 0.8
                && sums.getTopL() < 8.0
                && sums.getTopR() < 0.8) && predicate.test(sums);
    }
}