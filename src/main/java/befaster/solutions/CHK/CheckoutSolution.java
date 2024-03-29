package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {

    private static final Map<Character, Integer> priceMap = new HashMap<>();
    private static final Map<Character, List<Integer>> specialOfferCountMap = new HashMap<>();
    private static final Map<Character, List<Integer>> specialOfferPriceMap = new HashMap<>();
    private static final Map<Character, List<Character>> specialOfferCharacterMap = new HashMap<>();

    static {
        // This is where we put all the prices in the map
        priceMap.put('A', 50);
        priceMap.put('B', 30);
        priceMap.put('C', 20);
        priceMap.put('D', 15);
        priceMap.put('E', 40);
        priceMap.put('F', 10);

        // This is where we put the special promotions in two separate maps
        specialOfferPriceMap.put('A', new ArrayList<>(List.of(200, 130)));
        specialOfferPriceMap.put('B', new ArrayList<>(List.of(45)));

        specialOfferCharacterMap.put('E', new ArrayList<>(List.of('B')));
        specialOfferCharacterMap.put('F', new ArrayList<>(List.of('F')));

        specialOfferCountMap.put('A', new ArrayList<>(List.of(5, 3)));
        specialOfferCountMap.put('B', new ArrayList<>(List.of(2)));
        specialOfferCountMap.put('E', new ArrayList<>(List.of(2)));
        specialOfferCountMap.put('F', new ArrayList<>(List.of(2)));
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
        int totalSum = calculateTotal(itemCount);

        // Check for only numbers in string
        if (totalSum == 0) return -1;

        return totalSum;
    }

    private int calculateTotal(Map<Character, Integer> itemCountMap) {
        int checkoutSum = 0;

        // We should first check for free items and remove them
        itemCountMap = deductFreeItems(itemCountMap);

        for (Map.Entry<Character, Integer> entry: itemCountMap.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();
            int normalPrice = priceMap.get(item);

            if (specialOfferCountMap.containsKey(item) && specialOfferPriceMap.containsKey(item)) {
                List<Integer> itemSpecialOffers = specialOfferCountMap.get(item);
                List<Integer> itemSpecialOffersPrices = specialOfferPriceMap.get(item);
                for (int i=0; i<itemSpecialOffers.size(); i++) {
                    if (itemSpecialOffers.get(i) <= count) {
                        int numberOfSpecialOffers = count / itemSpecialOffers.get(i);
                        checkoutSum += numberOfSpecialOffers * itemSpecialOffersPrices.get(i);
                        count = count % itemSpecialOffers.get(i);
                    }
                }
                if (count > 0) {
                    checkoutSum += count * normalPrice;
                }
            } else {
                checkoutSum += normalPrice * count;
            }
        }

        return checkoutSum;
    }

    private Map<Character, Integer> deductFreeItems(Map<Character, Integer> itemCountMap) {
        for (Map.Entry<Character, List<Character>> entry: specialOfferCharacterMap.entrySet()) {
            if (itemCountMap.containsKey(entry.getKey())) {
                char specialOfferItem = entry.getKey();
                List<Integer> itemSpecialOffers = specialOfferCountMap.get(specialOfferItem);
                List<Character> itemSpecialOffersCharacters = specialOfferCharacterMap.get(specialOfferItem);

                int countOfSpecialItems = itemCountMap.get(specialOfferItem);

                for (int i=0; i<itemSpecialOffers.size(); i++) {
                    if (itemSpecialOffers.get(i) <= countOfSpecialItems) {
                        int numberOfSpecialOffers = countOfSpecialItems / itemSpecialOffers.get(i);
                        if (itemCountMap.get(entry.getValue().get(i)) != null) {
                            int paidItems = (itemCountMap.get(entry.getValue().get(i)) / (itemSpecialOffers.get(i) + 1)) * itemSpecialOffers.get(i) + itemCountMap.get(entry.getValue().get(i)) % (itemSpecialOffers.get(i) + 1);
                            System.out.println(paidItems);
                            //if (specialOfferItem != entry.getValue().get(i) && numberOfSpecialOffers * itemSpecialOffers.get(i) < itemCountMap.get(entry.getValue().get(i))) {
                                if (itemCountMap.get(entry.getValue().get(i)) <= numberOfSpecialOffers) {
                                    itemCountMap.put(entry.getValue().get(i), 0);
                                } else {
                                    itemCountMap.put(entry.getValue().get(i), itemCountMap.get(entry.getValue().get(i)) - numberOfSpecialOffers);
                                }
                            //}
                        }
                        countOfSpecialItems = countOfSpecialItems % itemSpecialOffers.get(i);
                    }
                }
            }
        }
        return itemCountMap;
    }
}

