package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    private static final Map<Character, Integer> priceMap = new HashMap<>();
    private static final Map<Character, Integer> specialOfferCountMap = new HashMap<>();
    private static final Map<Character, Integer> specialOfferPriceMap = new HashMap<>();

    static {
        // This is where we put all the prices in the map
        priceMap.put('A', 50);
        priceMap.put('B', 30);
        priceMap.put('C', 20);
        priceMap.put('D', 15);

        // This is where we put the special promotions in two separate maps
        specialOfferPriceMap.put('A', 130);
        specialOfferPriceMap.put('B', 45);

        specialOfferCountMap.put('A', 3);
        specialOfferCountMap.put('B', 2);
    }
    public Integer checkout(String skus) {
        // Firstly, we check if the string is empty so that means the checkout basket is empty
        if (skus.isEmpty()) return 0;

        // Create an empty map to keep track of how many items of each type we have
        Map<Character, Integer> itemCount = new HashMap<>();
        int currentNumber = 0;

        for (int i=0; i<skus.length(); i++) {
            char item = skus.charAt(i);

            // We keep track of the numbers passed in
            if (Character.isDigit(item)) {
                currentNumber = currentNumber * 10 + Integer.parseInt(String.valueOf(item));
            }

            if (Character.isAlphabetic(item)) {
                // We check if the item exists
                if (priceMap.get(item) == null) return -1;

                // We check and see how many items of that type the user purchased
                int numberOfItems;
                if (currentNumber == 0)
                    numberOfItems = 1;
                else {
                    numberOfItems = currentNumber;
                    currentNumber = 0;
                }

                itemCount.put(item, itemCount.get(item) != null ?  itemCount.get(item) + numberOfItems : numberOfItems);
            }
        }
        return calculateTotal(itemCount);
    }

    private int calculateTotal(Map<Character, Integer> itemCountMap) {
        int checkoutSum = 0;

        for (Map.Entry<Character, Integer> entry: itemCountMap.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();
            int price = priceMap.get(item);

            if (specialOfferPriceMap.containsKey(item)) {
                int numberOfSpecialOffers = count / specialOfferCountMap.get(item);
                int remainingItems = count % specialOfferCountMap.get(item);
                checkoutSum += numberOfSpecialOffers * specialOfferPriceMap.get(item) + remainingItems * price;
            } else {
                checkoutSum += price * count;
            }
        }

        return checkoutSum;
    }
}
