package com.cultofboobles.utils.day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayGenerator {

    public static List<String> allOrders = Arrays.asList(
        "gigners",
        "tatooed"
    );

    public static Map<String, Boolean> orderMap = new HashMap<>(Map.of(
        allOrders.get(0), false,
        allOrders.get(1), false
    ));


    public static Day getDay(int count) {

        int customerCount = 10 + (count * 5);
        String orderName = "";
        for (int i = 0; i < 100; i++) {
            int randomNum = (int) (Math.random() * allOrders.size());

            orderName = allOrders.get(randomNum);

            // if unused order is found, it is chosen and set as used
            if (!orderMap.get(orderName)) {
                orderMap.put(orderName, true);
                break;
            }

            // if all orders were used get the first one as default
            if (i == 99) {
                orderName = allOrders.getFirst();
            }

        }
        return new Day(customerCount, orderName);
    }
}
