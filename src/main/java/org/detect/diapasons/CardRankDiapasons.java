package org.detect.diapasons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiPredicate;

@Getter
@RequiredArgsConstructor
public enum CardRankDiapasons implements Diapasons, BiPredicate<Double, Double> {
    SIX((top, bottom) -> (top >= 0.3050 && top <= 0.3210) && (bottom >= 0.3350 && bottom <= 0.3575), "SIX(ШЕСТЬ)", "6"),
    ACE((top, bottom) -> (top >= 0.2135 && top <= 0.2365) && (bottom >= 0.3695 && bottom <= 0.3935), "ACE(ТУЗ)", "A"),
    KING((top, bottom) -> (top >= 0.2680 && top <= 0.3119) && (bottom >= 0.3160 && bottom <= 0.3499), "KING(КОРОЛЬ)", "K"),
    QUEEN((top, bottom) -> (top >= 0.3490 && top <= 0.3610) && (bottom >= 0.4575 && bottom <= 0.4735), "QUEEN(ДАМА)", "Q"),
    JACK((top, bottom) -> (top >= 0.1300 && top <= 0.1375) && (bottom >= 0.2370 && bottom <= 0.2560), "JACK(ВАЛЕТ)", "J"),
    TWO((top, bottom) -> (top >= 0.2490 && top <= 0.2640) && (bottom >= 0.3150 && bottom <= 0.3275), "TWO(ДВА)", "2"),
    THREE((top, bottom) -> (top >= 0.2685 && top <= 0.2790) && (bottom >= 0.2885 && bottom <= 0.2975), "THREE(ТРИ)", "3"),
    FOUR((top, bottom) -> (top >= 0.2230 && top <= 0.2510) && (bottom >= 0.3010 && bottom <= 0.3310), "FOUR(ЧЕТЫРЕ)", "4"),
    FIVE((top, bottom) -> (top >= 0.2715 && top <= 0.2890) && (bottom >= 0.2860 && bottom <= 0.3015), "FIVE(ПЯТЬ)", "5"),
    SEVEN((top, bottom) -> (top >= 0.2476 && top <= 0.2630) && (bottom >= 0.1550 && bottom <= 0.1685), "SEVEN(СЕМЬ)", "7"),
    EIGHT((top, bottom) -> (top >= 0.3115 && top <= 0.3310) && (bottom >= 0.3540 && bottom <= 0.3639), "EIGHT(ВОСЕМЬ)", "8"),
    NINE((top, bottom) -> (top >= 0.3210 && top <= 0.3430) && (bottom >= 0.3270 && bottom <= 0.3485), "NINE(ДЕВЯТЬ)", "9"),
    TEN((top, bottom) -> (top >= 0.4820 && top <= 0.5340) && (bottom >= 0.4310 && bottom <= 0.4785), "TEN(ДЕСЯТЬ)", "10"),
    NOT_DETECTED(
            (top, bottom) -> !ACE.test(top, bottom)
                    && !KING.test(top, bottom)
                    && !QUEEN.test(top, bottom)
                    && !JACK.test(top, bottom)
                    && !TWO.test(top, bottom)
                    && !THREE.test(top, bottom)
                    && !FOUR.test(top, bottom)
                    && !FIVE.test(top, bottom)
                    && !SIX.test(top, bottom)
                    && !SEVEN.test(top, bottom)
                    && !EIGHT.test(top, bottom)
                    && !NINE.test(top, bottom)
                    && !TEN.test(top, bottom),
            "NOT_DETECTED", "X"
    );

    private final BiPredicate<Double, Double> predicate;
    private final String description;
    private final String shortDescription;

    @Override
    public boolean test(Double topSum, Double bottomSum) {
        return topSum < 0.8 && bottomSum < 0.8 && predicate.test(topSum, bottomSum);
    }
}