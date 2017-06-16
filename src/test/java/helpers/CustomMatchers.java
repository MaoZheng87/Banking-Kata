package helpers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.number.IsCloseTo;

public class CustomMatchers {
  
  public static Matcher<?> closeToFloat(double expected, double tolerace) {
    IsCloseTo doubleMatcher = new IsCloseTo(expected, tolerace);
    return new TypeSafeDiagnosingMatcher<Float>() {

      @Override
      public void describeTo(Description description) {
        doubleMatcher.describeTo(description);
      }

      @Override
      protected boolean matchesSafely(Float item, Description mismatchDescription) {
        return doubleMatcher.matchesSafely(item.doubleValue());
      }
    };
  }

}
