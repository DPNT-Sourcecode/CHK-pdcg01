package befaster.solutions.HLO;

import befaster.solutions.SUM.SumSolution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class HelloSolutionTest {
    private HelloSolution hlo;

    @BeforeEach
    public void setUp() {
        hlo = new HelloSolution();
    }

    @Test
    public void compute_hlo_with_friend_name() {
        assertThat(hlo.hello("John"), equalTo("Hello, John!"));
    }
}