package it.twinsbrain.adc2022.dayo4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static it.twinsbrain.adc2022.dayo4.Day04Solution.howManyRangesOverlaps;
import static it.twinsbrain.adc2022.dayo4.Day04Solution.howManyRangesOverlapsFully;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day04SolutionTest {

    @Test
    void fullyContaining() {
        var input = List.of("2-4,5-6", "1-2,2-3", "2-6,1-6");
        assertThat(howManyRangesOverlapsFully(input), equalTo(1L));
    }

    @Test
    void overlapping() {
        var input = List.of("2-4,5-6", "1-2,2-3", "2-6,1-6");
        assertThat(howManyRangesOverlaps(input), equalTo(2L));
    }
}
