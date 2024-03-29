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
    public void testCheckoutWithEValueShouldReturn40() {
        assertThat(chk.checkout("E"), equalTo(40));
    }

    @Test
    public void testCheckoutWith2EValueShouldReturn80() {
        assertThat(chk.checkout("2E"), equalTo(80));
    }

    @Test
    public void testCheckoutWith2EBValueShouldReturn80() {
        assertThat(chk.checkout("2EB"), equalTo(80));
    }

    @Test
    public void testCheckoutWith2E2BValueShouldReturn110() {
        assertThat(chk.checkout("2E2B"), equalTo(110));
    }

    @Test
    public void testCheckoutWith8A2E3BValueShouldReturn455() {
        assertThat(chk.checkout("8A2E3B"), equalTo(455));
    }

    @Test
    public void testCheckoutWith9A5B3C1D3EValueShouldReturn665() {
        assertThat(chk.checkout("9A5B3C1D3E"), equalTo(665));
    }

    @Test
    public void testCheckoutWith24AShouldReturn980() {
        assertThat(chk.checkout("24A"), equalTo(980));
    }

    @Test
    public void testCheckoutWith10AShouldReturn400() {
        assertThat(chk.checkout("10A"), equalTo(400));
    }

    @Test
    public void testCheckoutWithMultipleItemsAndSpacesInBetween() {
        assertThat(chk.checkout("2B  10A"), equalTo(445));
    }

    @Test
    public void testCheckoutWithSequencingCharacter() {
        assertThat(chk.checkout("ABAABCDE"), equalTo(250));
    }

    @Test
    public void testCheckoutWith3FShouldReturn20() {
        assertThat(chk.checkout("2F"), equalTo(20));
    }

    @Test
    public void testCheckoutWith2FShouldReturn20() {
        assertThat(chk.checkout("2F"), equalTo(20));
    }

    @Test
    public void testCheckoutWith2F2E2BShouldReturn20() {
        assertThat(chk.checkout("2F2E2B"), equalTo(130));
    }

    @Test
    public void testCheckoutWith3F2E2BShouldReturn20() {
        assertThat(chk.checkout("3F2E2B"), equalTo(130));
    }

    @Test
    public void testCheckoutWith47HShouldReturn385() {
        assertThat(chk.checkout("47H"), equalTo(385));
    }

    @Test
    public void testCheckoutWith5KShouldReturn380() {
        assertThat(chk.checkout("5K"), equalTo(380));
    }

    @Test
    public void testCheckoutWith3NMShouldReturn120() {
        assertThat(chk.checkout("3NM"), equalTo(120));
    }

    @Test
    public void testCheckoutWith5N2MShouldReturn215() {
        assertThat(chk.checkout("5N2M"), equalTo(215));
    }

    @Test
    public void testCheckoutWith12PShouldReturn500() {
        assertThat(chk.checkout("12P"), equalTo(500));
    }

    @Test
    public void testCheckoutWith8QShouldReturn220() {
        assertThat(chk.checkout("8Q"), equalTo(220));
    }

    @Test
    public void testCheckoutWith7R4QShouldReturn410() {
        assertThat(chk.checkout("7R4Q"), equalTo(410));
    }

    @Test
    public void testCheckoutWith8UShouldReturn240() {
        assertThat(chk.checkout("8U"), equalTo(240));
    }

    @Test
    public void testCheckoutWith11VShouldReturn480() {
        assertThat(chk.checkout("11V"), equalTo(480));
    }
}