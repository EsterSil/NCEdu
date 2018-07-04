import java.util.Map;

public interface ComplexityMeasurer<T,E> {
    /**
     * its a tester-method collect statistics into the map to draw a plot
     * it measure a program execution time
     * @return map of statistics
     */
    Map<T,E> measure();
}
