package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

public class CheckoutSolution {
    public Integer checkout(String skus) {
        if (skus.isEmpty()) return 0;
        for (int i=0; i<skus.length(); i++) {
            char item = skus.charAt(i);
            if (item == 'A' || item == 'B' || item == 'C' || item == 'D') {
                
            }
        }
        return 1;
    }
}

