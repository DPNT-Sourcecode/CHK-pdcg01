package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

public class CheckoutSolution {
    private enum ItemValue {
        A(50),
        B(30),
        C(20),
        D(15);

        private final int value;

        ItemValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public Integer checkout(String skus) {
        // Firstly, we check if the string is empty so that means the checkout basket is empty
        if (skus.isEmpty()) return 0;
        int checkoutSum = 0;

        for (int i=0; i<skus.length(); i++) {
            char item = skus.charAt(i);
            int currentNumber = 0;
            if (Character.isAlphabetic(item)) {
                if (currentNumber == 0)
                    currentNumber  = Integer.parseInt(String.valueOf(item));
                else
                    currentNumber = currentNumber * 10 + Integer.parseInt(String.valueOf(item));
            }
            if (item == 'A' || item == 'B' || item == 'C' || item == 'D') {
                // We check and see how many items of that type the user purchased
                int numberOfItems = 1;
                if (i > 0 && Character.isAlphabetic(skus.charAt(i - 1))) {
                    numberOfItems = skus.charAt(i - 1);
                }

                switch (item) {
                    case 'A':
                        checkoutSum += ItemValue.A.value * numberOfItems;
                        break;
                    case 'B':
                        checkoutSum += ItemValue.B.value * numberOfItems;
                        break;
                    case 'C':
                        checkoutSum += ItemValue.C.value * numberOfItems;
                        break;
                    case 'D':
                        checkoutSum += ItemValue.D.value * numberOfItems;
                        break;
                    default: return -1;
            }

            }
        }
        return checkoutSum;
    }
}

