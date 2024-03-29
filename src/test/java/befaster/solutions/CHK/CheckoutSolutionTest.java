package befaster.solutions.CHK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class CheckoutSolutionTest {
    private CheckoutSolution chk;

    @BeforeEach
    public void setUp() {
        chk = new CheckoutSolution();
    }

    @Test
    public void testCheckoutWithInvalidString() {
        assertThat(chk.checkout("John"), equalTo(-1));
    }

    @Test
    public void testCheckoutWithEmptyString() {
        assertThat(chk.checkout(""), equalTo(0));
    }

    @Test
    public void testCheckoutWith2AShouldReturn100() {
        assertThat(chk.checkout("2A"), equalTo(100));
    }

    @Test
    public void testCheckoutWith10AShouldReturn440() {
        assertThat(chk.checkout("10A"), equalTo(440));
    }

    @Test
    public void testCheckoutWithMultipleItemsAndSpacesInBetween() {
        assertThat(chk.checkout("2B  10A"), equalTo(485));
    }

    @Test
    public void testCheckoutWithSequencingCharacter() {
        assertThat(chk.checkout("ABAABC"), equalTo(195));
    }
}