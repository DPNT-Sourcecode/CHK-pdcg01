package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.*;

public class CheckoutSolution {

    private static final Map<Character, Integer> priceMap = new HashMap<>();
    private static final Map<Character, List<Integer>> specialOfferCountMap = new HashMap<>();
    private static final Map<Character, List<Integer>> specialOfferPriceMap = new HashMap<>();
    private static final Map<Character, List<Character>> specialOfferCharacterMap = new HashMap<>();
    private static final Map<List<Character>, Integer> specialGroupOfferMap = new HashMap<>();

    static {
        // This is where we put all the prices in the map
        priceMap.put('A', 50);
        priceMap.put('B', 30);
        priceMap.put('C', 20);
        priceMap.put('D', 15);
        priceMap.put('E', 40);
        priceMap.put('F', 10);
        priceMap.put('G', 20);
        priceMap.put('H', 10);
        priceMap.put('I', 35);
        priceMap.put('J', 60);
        priceMap.put('K', 70);
        priceMap.put('L', 90);
        priceMap.put('M', 15);
        priceMap.put('N', 40);
        priceMap.put('O', 10);
        priceMap.put('P', 50);
        priceMap.put('Q', 30);
        priceMap.put('R', 50);
        priceMap.put('S', 20);
        priceMap.put('T', 20);
        priceMap.put('U', 40);
        priceMap.put('V', 50);
        priceMap.put('W', 20);
        priceMap.put('X', 17);
        priceMap.put('Y', 20);
        priceMap.put('Z', 21);

        // This is where we put the special promotions in two separate maps
        specialOfferPriceMap.put('A', new ArrayList<>(List.of(200, 130)));
        specialOfferPriceMap.put('B', new ArrayList<>(List.of(45)));
        specialOfferPriceMap.put('H', new ArrayList<>(List.of(80, 45)));
        specialOfferPriceMap.put('K', new ArrayList<>(List.of(120)));
        specialOfferPriceMap.put('P', new ArrayList<>(List.of(200)));
        specialOfferPriceMap.put('Q', new ArrayList<>(List.of(80)));
        specialOfferPriceMap.put('V', new ArrayList<>(List.of(130, 90)));
        specialOfferPriceMap.put('d', new ArrayList<>(List.of(45)));

        specialOfferCharacterMap.put('E', new ArrayList<>(List.of('B')));
        specialOfferCharacterMap.put('F', new ArrayList<>(List.of('F')));
        specialOfferCharacterMap.put('N', new ArrayList<>(List.of('M')));
        specialOfferCharacterMap.put('R', new ArrayList<>(List.of('Q')));
        specialOfferCharacterMap.put('U', new ArrayList<>(List.of('U')));

        specialOfferCountMap.put('A', new ArrayList<>(List.of(5, 3)));
        specialOfferCountMap.put('B', new ArrayList<>(List.of(2)));
        specialOfferCountMap.put('E', new ArrayList<>(List.of(2)));
        specialOfferCountMap.put('F', new ArrayList<>(List.of(2)));
        specialOfferCountMap.put('H', new ArrayList<>(List.of(10, 5)));
        specialOfferCountMap.put('K', new ArrayList<>(List.of(2)));
        specialOfferCountMap.put('N', new ArrayList<>(List.of(3)));
        specialOfferCountMap.put('P', new ArrayList<>(List.of(5)));
        specialOfferCountMap.put('Q', new ArrayList<>(List.of(3)));
        specialOfferCountMap.put('R', new ArrayList<>(List.of(3)));
        specialOfferCountMap.put('U', new ArrayList<>(List.of(3)));
        specialOfferCountMap.put('V', new ArrayList<>(List.of(3, 2)));

        specialGroupOfferMap.put(new ArrayList<>(List.of('S', 'T', 'X', 'Y', 'Z')), 3);
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

        // Apply group discounts
        // Firstly, I will create a map which has all the group discount items in descending order
        List<Map.Entry<Character, Integer>> list = new ArrayList<>();
        List<Integer> groupDiscountsMinPurchase = new ArrayList<>();
        for (Map.Entry<List<Character>, Integer> entry: specialGroupOfferMap.entrySet()) {
            groupDiscountsMinPurchase.add(entry.getValue());
            for (int i=0; i<entry.getKey().size(); i++) {
                char charToCheck = entry.getKey().get(i);
                for (Map.Entry<Character, Integer> priceMapEntry: priceMap.entrySet()) {
                    if (priceMapEntry.getKey() == charToCheck)
                        list.add(priceMapEntry);
                }
            }
        }
        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // We now calculate how many items from the special group discount are there in the basket
        int specialGroupOfferItemCount = 0;
        for (Map.Entry<List<Character>, Integer> entry: specialGroupOfferMap.entrySet()) {
            for (int i=0; i<entry.getKey().size(); i++) {
                char charToCheck = entry.getKey().get(i);
                if (itemCountMap.get(charToCheck) != null) {
                    specialGroupOfferItemCount += itemCountMap.get(charToCheck);
                }
            }
        }

        // We now remove some items from the discount group and add the group price to the total
        int discountsToBeApplied = specialGroupOfferItemCount / groupDiscountsMinPurchase.get(0);
        int discountedItemsToBeRemoved = discountsToBeApplied * groupDiscountsMinPurchase.get(0);
        for (Map.Entry<Character, Integer> entry: list) {
            if (itemCountMap.containsKey(entry.getKey()) && discountedItemsToBeRemoved > 0) {
                int amount = itemCountMap.get(entry.getKey());
                if (discountedItemsToBeRemoved >= amount) {
                    itemCountMap.put(entry.getKey(), 0);
                } else {
                    itemCountMap.put(entry.getKey(), amount - discountedItemsToBeRemoved);
                }
                discountedItemsToBeRemoved = discountedItemsToBeRemoved - amount;
            }
        }
        checkoutSum += discountsToBeApplied * specialOfferPriceMap.get('d').get(0);

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
                            if (entry.getValue().get(i) == specialOfferItem) {
                                // This is a formula for the amount of paid items
                                int paidItems = (itemCountMap.get(entry.getValue().get(i)) / (itemSpecialOffers.get(i) + 1)) * itemSpecialOffers.get(i) + itemCountMap.get(entry.getValue().get(i)) % (itemSpecialOffers.get(i) + 1);
                                itemCountMap.put(entry.getValue().get(i), paidItems);
                            } else {
                                if (itemCountMap.get(entry.getValue().get(i)) <= numberOfSpecialOffers) {
                                    itemCountMap.put(entry.getValue().get(i), 0);
                                } else {
                                    itemCountMap.put(entry.getValue().get(i), itemCountMap.get(entry.getValue().get(i)) - numberOfSpecialOffers);
                                }
                            }
                        }
                        countOfSpecialItems = countOfSpecialItems % itemSpecialOffers.get(i);
                    }
                }
            }
        }
        return itemCountMap;
    }
}





