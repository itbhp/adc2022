package it.twinsbrain.adc2022;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MathModuleTest {

  @ParameterizedTest
  @CsvSource({"45, 20, 5", "60, 90, 30", "42, 210, 42"})
  void gcd_should_work(long a, long b, long expected) {
    assertThat(MathModule.gcd(a, b), is(expected));
  }

  @ParameterizedTest
  @CsvSource({
    "45, 20, 180", // 45 = 3^2 * 5, 20 = 2^2 * 5 => lcm = 2^2 * 3^2 * 5 = 180
    "60, 90, 180", // 50 = 2^2 * 3 * 5, 90 = 2 * 3^2 * 5 => lcm = 2^2 * 3^2 * 5 = 180
    "42, 210, 210" // 42 = 2 * 3 * 7, 210 = 2 * 3 * 5 * 7 => lcm = 2 * 3 * 5 * 7 = 210
  })
  void lcm_should_work(long a, long b, long expected) {
    assertThat(MathModule.lcm(a, b), is(expected));
  }
}
