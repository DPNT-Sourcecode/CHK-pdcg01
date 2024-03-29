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
    public void testCheckoutWithJustNumbers() {
        assertThat(chk.checkout("330"), equalTo(-1));
    }

    @Test
    public void testCheckoutWithAValueShouldReturn50() {
        assertThat(chk.checkout("A"), equalTo(50));
    }

    @Test
    public void testCheckoutWithBValueShouldReturn30() {
        assertThat(chk.checkout("B"), equalTo(30));
    }

    @Test
    public void testCheckoutWithCValueShouldReturn20() {
        assertThat(chk.checkout("C"), equalTo(20));
    }

    @Test
    public void testCheckoutWithDValueShouldReturn15() {
        assertThat(chk.checkout("D"), equalTo(15));
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
        assertThat(chk.checkout("ABAABCD"), equalTo(210));
    }


}